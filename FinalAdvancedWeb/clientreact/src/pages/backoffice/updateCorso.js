import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Segment, Header, Progress, Form, Button, Loader } from 'semantic-ui-react';
import AnnoCdlStep from './stepCorso/annoCdlStep';
import BaseInfoStep from './stepCorso/baseInfoStep';

export default class UpdateCorso extends Component {
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
            anniCorsi: [],
            idCorso: null,
            annoCorso: null,
            cdlCorso: null,
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
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/courses/years')
        .then(res => res.json())
        .then(result => {
            this.setState({ anniCorsi: result });
        });
    }

    selectedCorso = (idCorso, annoCorso, cdlCorso) => {
        this.setState({
            annoCorso: annoCorso,
            cdlCorso: cdlCorso,
            idCorso: idCorso
        });
        if (idCorso) {
            fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/courses/' + annoCorso + '/' + idCorso)
            .then(res => res.ok ? res.json() : null)
            .then(result => {
                this.setState({
                    corso: result
                });
            });
            this.getCorsiAnno(annoCorso, false);
        }
    }

    getCorsiAnno = (anno, mustClear) => {
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/courses/' + anno)
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
                let relazioni = this.state.corso.relazioni;
                if (mustClear) {
                    relazioni = {
                        propedeudici: [],
                        modulo: [],
                        mutuati: []
                    };
                }
                this.setState({
                    corsi: corsi,
                    corso: {
                        ...this.state.corso,
                        relazioni: relazioni
                    }
                });
            });
        });
    }

    handleChange = (e, {name, value}) => {
        if (name === "anno") {
            this.getCorsiAnno(value, true);
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
            let relazioni = this.state.corso.relazioni;
            if (name === 'cdl') {
                relazioni = {
                    propedeudici: [],
                    modulo: [],
                    mutuati: []
                }
            }
            this.setState({
                corso: {
                    ...this.state.corso,
                    relazioni: relazioni,
                    [name]: arr
                }
            });
        }
    }

    aheadButtonClick = () => {
        var canGo = false;
        if (this.state.step === 0) {
            if (this.props.utente.docente) {
                if (this.state.annoCorso && this.state.idCorso) {
                    canGo = true;
                }
            } else {
                if (this.state.annoCorso && this.state.cdlCorso && this.state.idCorso) {
                    canGo = true;
                }
            }
        } else if (this.state.step > 0) {
            if (this.state.corso.nomeIt.trim().length > 0 && this.state.corso.cdl.length > 0 && this.state.corso.anno) {
                canGo = true;
            }
        }
        if (canGo) {
            this.setState({ step: this.state.step + 1, formError: false });
        } else {
            this.setState({ formError: true });
        }
        document.documentElement.scrollTop = 200;
    }
    
    render() {
        if (this.state.redirect) {
            return <Redirect to = '/Backoffice'/>
        }
        var title;
        var Step;
        switch (this.state.step) {
            default:
                title = 'Scegli Anno e CDL Del Corso Da Modificare';
                Step = <AnnoCdlStep 
                            annoCorso = {this.state.annoCorso}
                            cdlCorso = {this.state.cdlCorso}
                            corso = {this.state.corso}
                            token = {this.props.token}
                            formError = {this.state.formError}
                            cdl = {this.state.cdl}
                            anniCorsi = {this.state.anniCorsi}
                            selectedCorso = {this.selectedCorso}
                            utente = {this.props.utente}
                        />
                break;
                case 1:
                    title = 'Modifica Le Informazioni Base';
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