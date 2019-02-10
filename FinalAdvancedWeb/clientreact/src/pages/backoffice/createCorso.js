import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Segment, Header, Progress, Form, Button, Loader } from 'semantic-ui-react';
import BaseInfoStep from './stepCorso/baseInfoStep';
import DescriptionStep from './stepCorso/descriptionStep';
import DublinoStep from './stepCorso/dublinoStep';
import LinkStep from './stepCorso/linkStep';
import ConfirmStep from './stepCorso/confirmStep';

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