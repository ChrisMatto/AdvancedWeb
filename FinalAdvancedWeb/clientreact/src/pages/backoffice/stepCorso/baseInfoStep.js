import React, { Fragment } from 'react';
import { Form } from 'semantic-ui-react';

export default function BaseInfoStep(props) {
    var docenti = [];
    props.docenti.forEach(docente => {
        let obj = {
            key: docente.idDocente,
            text: docente.nome + ' ' + docente.cognome,
            value: docente.idDocente
        };
        docenti.push(obj);
    });
    var cdl = [];
    props.cdl.forEach(c => {
        let obj = {
            key: c.idcdl,
            text: c.nomeIt,
            value: c.idcdl
        };
        cdl.push(obj);
    });

    var corsiToFilter = props.corsi.filter(corso => {
        var result = false;
        corso.cdl.forEach(cdl => {
            props.selectedCdl.forEach(sCdl => {
                if (cdl.idCdl === sCdl.idCdl) {
                    result = true;
                }
            });
        });
        return result;
    });
    var filteredCorsi = [];
    corsiToFilter.forEach(corso => {
        let obj = {
            key: corso.idCorso,
            text: corso.nomeIt,
            value: corso.idCorso
        };
        filteredCorsi.push(obj);
    });

    var corsiPerMutuati = [];
    props.corsi.forEach(corso => {
        let obj = {
            key: corso.idCorso,
            text: corso.nomeIt,
            value: corso.idCorso
        };
        corsiPerMutuati.push(obj);
    });

    var anniAccademici = [];
    var currentYear = new Date().getFullYear();
    for (var i = currentYear - 5; i < currentYear + 5; i++) {
        let obj = {
            key: i,
            text: i + '/' + (i + 1),
            value: i
        };
        anniAccademici.push(obj);
    }

    return (
        <React.Fragment>
            <Form.Group widths = 'equal'>
                <Form.Input fluid name = 'nomeIt' value = {props.corso.nomeIt} onChange = {props.handleChange} label = 'Nome Del Corso' placeholder = 'Nome Corso' required error = {props.formError && props.corso.nomeIt.trim().length === 0}/>
                <Form.Input fluid name = 'nomeEn' value = {props.corso.nomeEn} onChange = {props.handleChange} label = 'Nome Del Corso EN' placeholder = 'Nome Corso EN'/>
            </Form.Group>
            <Form.Group widths = 'equal'>
                <Form.Input fluid name = 'ssd' value = {props.corso.ssd} onChange = {props.handleChange} label = 'SSD' placeholder = 'SSD'/>
                <Form.Input fluid name = 'lingua' value = {props.corso.lingua} onChange = {props.handleChange} label = 'Lingua' placeholder = 'Lingua'/>
            </Form.Group>
            <Form.Group widths = 'equal'>
                <Form.Select fluid name = 'semestre' value = {props.corso.semestre} onChange = {props.handleChange} label = 'Semestre' placeholder = 'Seleziona un semestre...' options = {[{value: 1, text: 1}, {value: 2, text: 2}]}/>
                <Form.Dropdown 
                    fluid
                    scrolling
                    search
                    selection
                    label = 'Anno Accademico'
                    onChange = {props.handleChange}
                    name = 'anno'
                    options = {anniAccademici}
                    placeholder = 'Seleziona Anno...'
                    closeOnBlur
                    required
                    value = {props.corso.anno}
                    error = {props.formError && !props.corso.anno}
                />
            </Form.Group>
            <Form.Group widths = 'two'>
                <Form.Input fluid name = 'cfu' value = {props.corso.cfu} onChange = {props.handleChange} label = 'CFU' placeholder = 'CFU'/>
                <Form.Select fluid name = 'tipologia' value = {props.corso.tipologia} onChange = {props.handleChange} label = 'Tipologia CFU' placeholder = 'Seleziona una tipologia...' options = {[{value: 'A', text: 'A'}, {value: 'B', text: 'B'}, {value: 'F', text: 'F'}]}/>
            </Form.Group>
            {
                props.admin ?
                    <Fragment>
                        <Form.Group widths = 'equal'>
                            <Form.Dropdown 
                                clearable
                                fluid
                                scrolling
                                multiple
                                search
                                selection
                                label = 'Associa Docenti Al Corso'
                                onChange = {props.handleSelectChange}
                                name = 'docenti'
                                options = {docenti}
                                placeholder = 'Seleziona Docenti...'
                                closeOnBlur
                                value = {props.corso.docenti.map(doc => doc.idDocente)}
                            />
                            <Form.Dropdown 
                                clearable
                                fluid
                                scrolling
                                multiple
                                search
                                selection
                                label = 'Associa CDL Al Corso'
                                onChange = {props.handleSelectChange}
                                name = 'cdl'
                                options = {cdl}
                                placeholder = 'Seleziona CDL...'
                                closeOnBlur
                                required
                                value = {props.corso.cdl.map(cdl => cdl.idCdl)}
                                error = {props.formError && props.corso.cdl.length === 0}
                            />
                        </Form.Group>
                        <Form.Group widths = 'equal'>
                            <Form.Dropdown
                                clearable
                                fluid
                                scrolling
                                multiple
                                search
                                selection
                                label = 'Seleziona Corsi Mutuati'
                                onChange = {props.handleSelectChange}
                                name = 'mutuati'
                                options = {corsiPerMutuati}
                                placeholder = 'Seleziona Corsi...'
                                closeOnBlur
                                value = {props.corso.relazioni.mutuati.map(url => {
                                    var arr = url.split('/');
                                    return parseInt(arr[arr.length - 1]);
                                })}
                                disabled = {props.corso.anno ? false : true}
                            />
                            <Form.Dropdown
                                clearable
                                fluid
                                scrolling
                                multiple
                                search
                                selection
                                label = 'Seleziona Corsi Propedeutici'
                                onChange = {props.handleSelectChange}
                                name = 'propedeudici'
                                options = {filteredCorsi}
                                placeholder = {props.selectedCdl.length > 0 ? 'Seleziona Corsi...' : 'Seleziona Un CDL...'}
                                disabled = {props.selectedCdl.length > 0 && props.corso.anno ? false : true}
                                closeOnBlur
                                value = {props.corso.relazioni.propedeudici.map(url => {
                                    var arr = url.split('/');
                                    return parseInt(arr[arr.length - 1]);
                                })}
                            />
                            <Form.Dropdown
                                clearable
                                fluid
                                scrolling
                                multiple
                                search
                                selection
                                label = 'Seleziona Corsi Modulo'
                                onChange = {props.handleSelectChange}
                                name = 'modulo'
                                options = {filteredCorsi}
                                placeholder = {props.selectedCdl.length > 0 ? 'Seleziona Corsi...' : 'Seleziona Un CDL...'}
                                disabled = {props.selectedCdl.length > 0 && props.corso.anno ? false : true}
                                closeOnBlur
                                value = {props.corso.relazioni.modulo.map(url => {
                                    var arr = url.split('/');
                                    return parseInt(arr[arr.length - 1]);
                                })}
                            />
                        </Form.Group>
                    </Fragment>
                :
                    null
            }
        </React.Fragment>
    );
}