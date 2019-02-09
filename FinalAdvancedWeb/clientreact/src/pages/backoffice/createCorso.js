import React, { Component, Fragment } from 'react';
import { Redirect } from 'react-router-dom';
import { Segment, Header, Progress, Form, Button, Accordion, Icon, Loader } from 'semantic-ui-react';
import Editor from 'react-quill';
import 'react-quill/dist/quill.snow.css';

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
                anno: null,
                descrizioneIt: {},
                descrizioneEn: {},
                dublinoIt: {},
                dublinoEn: {},
                docenti: [],
                cdl: [],
                relazioni: {
                    propedeudici: [],
                    modulo: [],
                    mutuati: []
                },
                links: {}
            },
            docenti: [],
            cdl: [],
            corsi: [],
            formError: false,
            loading: false,
            redirect: false
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
        }
    }

    handleChange = (e, {name, value}) => {
        if (name === "anno") {
            fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/courses/' + value)
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
                    this.setState({
                        corsi: corsi,
                        corso: {
                            ...this.state.corso,
                            relazioni: {
                                propedeudici: [],
                                modulo: [],
                                mutuati: []
                            }
                        }
                    });
                });
            });
        }
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
                    let string = 'http://localhost:8080/AdvancedWeb/rest/courses/' + (this.state.corso.anno ? this.state.corso.anno : 'current') + '/' + v;
                    arr.push(string);
                });
                break;
            case 'mutuati':
                isRelazione = true;
                    value.forEach(v => {
                        let string = 'http://localhost:8080/AdvancedWeb/rest/courses/' + (this.state.corso.anno ? this.state.corso.anno : 'current') + '/' + v;
                        arr.push(string);
                    });
                break;
            case 'modulo':
                isRelazione = true;
                    value.forEach(v => {
                        let string = 'http://localhost:8080/AdvancedWeb/rest/courses/' + (this.state.corso.anno ? this.state.corso.anno : 'current') + '/' + v;
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
                    relazioni: {
                        ...this.state.corso.relazioni,
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
        if (this.state.corso.nomeIt.trim().length > 0 && this.state.corso.cdl.length > 0 && this.state.corso.anno) {
            this.setState({ step: this.state.step + 1, formError: false });
        } else {
            this.setState({ formError: true });
        }
        document.documentElement.scrollTop = 200;
    }

    confirmCorso = () => {
        this.setState({ loading: true });
        var headers = new Headers();
        headers.append("Content-Type", "application/json");
        fetch("http://localhost:8080/AdvancedWeb/rest/auth/" + this.props.token + "/courses/" + this.state.corso.anno, {
            method: 'POST',
            headers: headers,
            body: JSON.stringify(this.state.corso)
        })
        .then(res => {
            if (res.ok) {
                this.setState({ redirect: true });
            }
        });
    }

    render() {
        if (this.state.redirect) {
            return <Redirect to = '/Backoffice'/>
        }
        var title;
        var Step;
        switch (this.state.step) {
            default:
                title = 'Crea Un Nuovo Corso';
                Step = <BaseInfoStep 
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
                title = 'Descrizione Italiana Del Corso';
                Step = <DescriptionStep 
                            descrizione = {this.state.corso.descrizioneIt} 
                            handleEditorChange = {this.handleEditorChange}
                            className = {"descrizioneIt"}
                            step = {this.state.step}
                        />
                break;
            case 2:
                title = 'Descrizione Inglese Del Corso';
                Step = <DescriptionStep
                            descrizione = {this.state.corso.descrizioneEn}
                            handleEditorChange = {this.handleEditorChange}
                            className = {"descrizioneEn"}
                            step = {this.state.step}
                        />
                break;
            case 3:
                title = 'Descrittori Di Dublino (Italiano)';
                Step = <DublinoStep
                            dublino = {this.state.corso.dublinoIt}
                            handleEditorChange = {this.handleEditorChange}
                            className = {"dublinoIt"}
                            step = {this.state.step}
                        />
                break;
            case 4:
                title = 'Descrittori Di Dublino (Inglese)';
                Step = <DublinoStep
                            dublino = {this.state.corso.dublinoEn}
                            handleEditorChange = {this.handleEditorChange}
                            className = {"dublinoEn"}
                            step = {this.state.step}
                        />
                break;
            case 5:
                title = 'Risorse Esterne';
                Step = <LinkStep
                            links = {this.state.corso.links}
                            handleEditorChange = {this.handleEditorChange}
                        />
                break;
            case 6:
                title = 'Conferma';
                Step = <ConfirmStep onClick = {this.confirmCorso}/>
        }
        return (
            <Segment className = 'col-md-8' color = 'teal' style = {{ marginTop: 4 }}>
                <div hidden = {!this.state.loading} className = 'loader-container'>
                    <Loader active size = 'massive'>Inviando Informazioni</Loader>
                </div>
                <Progress value = {this.state.step} total='6' indicating/>
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>{title}</Header>
                <Form>
                    {Step}
                </Form>
                <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                    <Button.Group>
                        <Button onClick = {() => {this.setState({ step: this.state.step - 1 }); document.documentElement.scrollTop = 200}} disabled = {this.state.step === 0}>Indietro</Button>
                        <Button.Or text = 'O'/>
                        <Button positive onClick = {this.aheadButtonClick} disabled = {this.state.step === 6}>Avanti</Button>
                    </Button.Group>
                </div>
            </Segment>
        );
    }
}

function BaseInfoStep(props) {
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

function DescriptionStep(props) {
    return (
        <Fragment>
            <CustomEditor 
                className = {props.className}
                objName = 'prerequisiti' 
                title = 'Prerequisiti Del Corso' 
                placeholder = 'Scrivi I Prerequisiti Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.descrizione.prerequisiti}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'obiettivi' 
                title = 'Obiettivi Del Corso' 
                placeholder = 'Scrivi Gli Obiettivi Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.descrizione.obiettivi}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'modEsame' 
                title = "Modalità D'Esame Del Corso" 
                placeholder = "Scrivi le Modalità D'Esame Qui"
                handleEditorChange = {props.handleEditorChange}
                value = {props.descrizione.modEsame}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'modInsegnamento' 
                title = "Modalità D'Insegnamento Del Corso" 
                placeholder = "Scrivi Le Modalità D'Insegnamento Qui"
                handleEditorChange = {props.handleEditorChange}
                value = {props.descrizione.modInsegnamento}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'sillabo' 
                title = 'Sillabo Del Corso' 
                placeholder = 'Scrivi Il Sillabo Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.descrizione.sillabo}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'note' 
                title = 'Note Extra Sul Corso' 
                placeholder = 'Scrivi Le Note Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.descrizione.note}
                step = {props.step}
            />
        </Fragment>
    );
}

function DublinoStep(props) {
    return (
        <Fragment>
            <CustomEditor 
                className = {props.className}
                objName = 'knowledge' 
                title = 'Knowledge' 
                placeholder = 'Knowledge Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.dublino.knowledge}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'application' 
                title = 'Application' 
                placeholder = 'Application Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.dublino.application}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'evaluation' 
                title = 'Evaluation' 
                placeholder = 'Evaluation Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.dublino.evaluation}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'communication' 
                title = 'Communication' 
                placeholder = 'Communication Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.dublino.communication}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'lifelong' 
                title = 'Lifelong' 
                placeholder = 'Lifelong Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.dublino.lifelong}
                step = {props.step}
            />
        </Fragment>
    );
}

function LinkStep(props) {
    return (
        <Fragment>
            <Form.Group widths = 'equal'>
                <Form.Input fluid name = 'homepage' value = {props.links.homepage ? props.links.homepage : ""} onChange = {(e, {name, value}) => props.handleEditorChange('links', name, value)} label = 'Homepage' placeholder = 'Homepage'/>
                <Form.Input fluid name = 'forum' value = {props.links.forum ? props.links.forum : ""} onChange = {(e, {name, value}) => props.handleEditorChange('links', name, value)} label = 'Forum' placeholder = 'Forum'/>
            </Form.Group>
            <Form.Group widths = 'equal'>
                <Form.Input fluid name = 'elearning' value = {props.links.elearning ? props.links.elearning : ""} onChange = {(e, {name, value}) => props.handleEditorChange('links', name, value)} label = 'E-Learning' placeholder = 'E-Learning'/>
                <Form.Input fluid name = 'risorseExt' value = {props.links.risorseExt ? props.links.risorseExt : ""} onChange = {(e, {name, value}) => props.handleEditorChange('links', name, value)} label = 'Risorse Esterne' placeholder = 'Risorse Esterne'/>
            </Form.Group>
        </Fragment>
    );
}

function ConfirmStep(props) {
    return (
        <div style = {{ textAlign: 'center', paddingTop: 30, paddingBottom: 50 }}>
            <Icon name = 'share square' size = 'massive' className = 'confirmIcon'/>
            <Button inverted color = 'green' size = 'massive' onClick = {props.onClick}>Completa La Registazione Del Corso</Button>
        </div>
    );
}

class CustomEditor extends Component{

    step;
    
    componentWillMount() {
        this.step = this.props.step;
    }

    render() {
        var panels = [
            {
                key: this.props.objName, 
                title: this.props.title, 
                content: {
                    as: Editor,
                    placeholder: this.props.placeholder,
                    value: this.props.value ? this.props.value : "",
                    onChange: (value) => {
                        if (this.step !== this.props.step) {
                            this.step = this.props.step;
                        } else {
                            this.props.handleEditorChange(this.props.className, this.props.objName, value);
                        }
                    }
                }
            }
        ];
        return (
            <Fragment>
                <Accordion as = {Form.Field} panels = {panels} styled style = {{ width: '100%' }}/>
            </Fragment>
        );
    }
}