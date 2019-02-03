import React, { Component, Fragment } from 'react';
import { Link, Redirect } from 'react-router-dom';
import { Segment, Header, Progress, Form, Button, Tab } from 'semantic-ui-react';

export default class CreateCorso extends Component {
    constructor() {
        super();
        this.state = {
            step: 0,
            corso: {
                nomeIt: "",
                nomeEn: "",
                ssd: "",
                lingua: "",
                cfu: "",
                descrizioneIt: {},
                descrizioneEn: {},
                dublinoIt: {},
                dublinoEn: {},
                docenti: [],
                cdl: [],
                relazioniCorso: {
                    propedeudici: [],
                    modulo: [],
                    mutuati: []
                }
            },
            docenti: [],
            cdl: [],
            corsi: [],
            formError: false
        };
    }

    componentWillMount() {
        if (!this.props.utente.docente) {
            fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/teachers')
            .then(res => res.json())
            .then(result => {
                let promises = [];
                result.forEach(uri => {
                    promises.push(fetch(uri));
                });
                Promise.all(promises).then(responses => {
                    var jsonPromises = [];
                    responses.forEach(response => {
                        jsonPromises.push(response.json());
                    });
                    return Promise.all(jsonPromises);
                }).then(results => {
                    var teachers = [];
                    results.forEach(teacher => {
                        teachers.push(teacher);
                    });
                    this.setState({ docenti: teachers });
                });
            });

            fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/cdl')
            .then(res => res.json())
            .then(result => {
                let promises = [];
                result.forEach(uri => {
                    promises.push(fetch(uri));
                });
                Promise.all(promises).then(responses => {
                    var jsonPromises = [];
                    responses.forEach(response => {
                        jsonPromises.push(response.json());
                    });
                    return Promise.all(jsonPromises);
                }).then(results => {
                    var cdl = [];
                    results.forEach(c => {
                        cdl.push(c);
                    });
                    this.setState({ cdl: cdl });
                });
            });

            fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/courses/current')
            .then(res => res.json())
            .then(result => {
                let promises = [];
                result.forEach(uri => {
                    promises.push(fetch(uri));
                });
                Promise.all(promises).then(responses => {
                    var jsonPromises = [];
                    responses.forEach(response => {
                        jsonPromises.push(response.json());
                    });
                    return Promise.all(jsonPromises);
                }).then(results => {
                    var corsi = [];
                    results.forEach(corso => {
                        corsi.push(corso);
                    });
                    this.setState({ corsi: corsi });
                });
            });
        }
    }

    handleChange = (e, {name, value}) => {
        this.setState({
            corso: {...this.state.corso, [name]: value}
        });
    }

    handleSelectChange = (e, {name, value}) => {
        var arr = [];
        var isRelazione = false;
        switch(name) {
            case 'docenti':
                value.forEach(v => {
                    let obj = {
                        idDocente: v
                    };
                    arr.push(obj);
                });
                break;
            case 'cdl':
                value.forEach(v => {
                    let obj = {
                        idCdl: v
                    };
                    arr.push(obj);
                });
                break;
            case 'propedeudici':
                isRelazione = true;
                value.forEach(v => {
                    let string = 'http://localhost:8080/AdvancedWeb/rest/courses/current/' + v;
                    arr.push(string);
                });
                break;
            case 'mutuati':
                isRelazione = true;
                    value.forEach(v => {
                        let string = 'http://localhost:8080/AdvancedWeb/rest/courses/current/' + v;
                        arr.push(string);
                    });
                break;
            case 'modulo':
                isRelazione = true;
                    value.forEach(v => {
                        let string = 'http://localhost:8080/AdvancedWeb/rest/courses/current/' + v;
                        arr.push(string);
                    });
                break;
            default:
                break;
        }
        if (isRelazione) {
            this.setState({
                corso: {
                    ...this.state.corso,
                    relazioniCorso: {
                        ...this.state.corso.relazioniCorso,
                        [name]: arr
                    }
                }
            });
        } else {
            this.setState({
                corso: {
                    ...this.state.corso,
                    [name]: arr
                }
            });
        }
    }

    handleEditorChange = (className, objName, content) => {
        console.log(className);
        console.log(objName);
        console.log(content);
        this.setState({
            corso: {
                ...this.state.corso,
                [className]: {
                    ...this.state.corso[className],
                    [objName]: content
                }
            }
        });
    }

    aheadButtonClick = () => {
        if (this.state.corso.nomeIt.trim().length > 0 && this.state.corso.cdl.length > 0) {
            this.setState({ step: this.state.step + 1, formError: false });
        } else {
            this.setState({ formError: true });
        }
    }

    render() {
        var Step;
        switch (this.state.step) {
            default:
                Step = <FirstStep 
                            handleChange = {this.handleChange} 
                            handleSelectChange = {this.handleSelectChange}
                            admin = {this.props.utente.docente ? false : true} 
                            docenti = {this.state.docenti} 
                            cdl = {this.state.cdl}
                            corsi = {this.state.corsi}
                            selectedCdl = {this.state.corso.cdl}
                            formError = {this.state.formError}
                            corso = {this.state.corso}
                        />
                break;
            case 1:
                Step = <SecondStep descrizione = {this.state.corso.descrizioneIt} handleEditorChange = {this.handleEditorChange}/>
                break;
        }
        var title;
        switch(this.state.step) {
            case 1:
                title = 'Descrizione Italiana Del Corso';
                break;
            default:
                title = 'Crea Un Nuovo Corso';
                break;
        }
        return (
            <Segment className = 'col-md-8' color = 'teal' style = {{ marginTop: 4 }}>
                <Progress value = {this.state.step} total='5' indicating/>
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>{title}</Header>
                <Form>
                    {Step}
                </Form>
                <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                    <Button.Group>
                        <Button onClick = {() => this.setState({ step: this.state.step - 1 })} disabled = {this.state.step === 0}>Indietro</Button>
                        <Button.Or text = 'O'/>
                        <Button positive onClick = {this.aheadButtonClick} disabled = {this.state.step === 5}>Avanti</Button>
                    </Button.Group>
                </div>
            </Segment>
        );
    }
}

function FirstStep(props) {
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
                <Form.Input fluid name = 'cfu' value = {props.corso.cfu} onChange = {props.handleChange} label = 'CFU' placeholder = 'CFU'/>
            </Form.Group>
            <Form.Group widths = 'two'>
                <Form.Select fluid name = 'tipologia' value = {props.corso.tipologia} onChange = {props.handleChange} label = 'Tipologia CFU' placeholder = 'Seleziona una tipologia...' options = {[{value: 'A', text: 'A'}, {value: 'B', text: 'B'}, {value: 'F', text: 'F'}]}/>
                <Form.Field/>
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
                                value = {props.corso.relazioniCorso.mutuati.map(url => {
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
                                label = 'Seleziona Corsi Propedeudici'
                                onChange = {props.handleSelectChange}
                                name = 'propedeudici'
                                options = {filteredCorsi}
                                placeholder = {props.selectedCdl.length > 0 ? 'Seleziona Corsi...' : 'Seleziona Un CDL...'}
                                disabled = {props.selectedCdl.length > 0 ? false : true}
                                closeOnBlur
                                value = {props.corso.relazioniCorso.propedeudici.map(url => {
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
                                disabled = {props.selectedCdl.length > 0 ? false : true}
                                closeOnBlur
                                value = {props.corso.relazioniCorso.modulo.map(url => {
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

function SecondStep(props) {
    return (
        <Fragment>
            
        </Fragment>
    );
}