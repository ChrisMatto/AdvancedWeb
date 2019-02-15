import React, { Component } from 'react';
import { Segment, Form, Button, Loader, Header } from 'semantic-ui-react';
import { Redirect } from 'react-router-dom';
import CustomEditor from './stepCorso/customEditor';
import { validateCurr, validateImg, fileToBase64 } from '../../js/functions';

export default class RegistraDocente extends Component {
    constructor() {
        super();
        this.state = {
            utente: {
                username: "",
                password: "",
                docente: null
            },
            docente: {
                nome: "",
                cognome: "",
                email: "",
                telefono: "",
                ufficio: "",
                specializzazione: "",
                ricevimento: "",
                ricerche: "",
                pubblicazioni: "",
                curriculum: "",
                immagine: ""
            },
            immagine: null,
            curriculum: null,
            loading: false,
            redirect: false
        };
    }

    componentWillMount() {
        let docente = this.props.docente;
        if (docente) {
            docente.immagine = "";
            docente.curriculum = "";
            delete docente.corsi;
            var keys = Object.keys(docente);
            keys.forEach(key => {
                if (!docente[key]) {
                    docente[key] = "";
                }
            });
            this.setState({ docente: docente });
        }
    }

    registraDocente = () => {
        this.setState({ loading: true });
        let docente = JSON.parse(JSON.stringify(this.state.docente));
        let utente = JSON.parse(JSON.stringify(this.state.utente));
        docente.immagine = this.state.immagine;
        docente.curriculum = this.state.curriculum;
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        if (this.state.docente.curriculum && this.state.docente.curriculum.trim()) {
            let arr = this.state.docente.curriculum.split(".");
            headers.append("file-type", arr[arr.length - 1]);
        }
        if (this.state.docente.immagine && this.state.docente.immagine.trim()) {
            let arr = this.state.docente.immagine.split(".");
            headers.append("image-type", arr[arr.length - 1]);
        }
        if (!this.props.docente) {
            fetch("http://localhost:8080/AdvancedWeb/rest/auth/" + this.props.token + "/teachers", {
                method: 'POST',
                headers: headers,
                body: JSON.stringify(docente)
            })
            .then(res => res.ok ? res.text() : null)
            .then(result => {
                utente.docente = result;
                utente.gruppo = 2;
                fetch("http://localhost:8080/AdvancedWeb/rest/auth/" + this.props.token + "/users", {
                    method: 'POST',
                    headers: headers,
                    body: JSON.stringify(utente)
                })
                .then(res => {
                    if (res.ok) {
                        this.setState({ redirect: true });
                    }
                });
            });
        } else {
            fetch("http://localhost:8080/AdvancedWeb/rest/auth/" + this.props.token + "/teachers/" + docente.idDocente, {
                method: 'PUT',
                headers: headers,
                body: JSON.stringify(docente)
            })
            .then(res => res.ok ? this.setState({ redirect: true }) : null);
        }
        
    }

    handleDocenteChange = (e, {name, value}) => {
        var canChange = true;
        switch (name) {
            default:
                break;
            case 'immagine':
                e.persist();
                canChange = validateImg(value);
                let immagine = e.target.files[0];
                if (canChange && value.length > 0) {
                    fileToBase64(immagine)
                    .then(immagine => {
                        this.setState({ immagine: immagine });
                    });
                } else {
                    this.setState({ immagine: null });
                }
                break;
            case 'curriculum':
                e.persist();
                canChange = validateCurr(value);
                let curriculum = e.target.files[0];
                if (canChange && value.length > 0) {
                    fileToBase64(curriculum)
                    .then(curriculum => {
                        this.setState({ curriculum: curriculum });
                    });
                } else {
                    this.setState({ curriculum: null });
                }
                break;
        }
        if (canChange) {
            this.setState({
                docente: {
                    ...this.state.docente,
                    [name]: value
                }
            });
        } else {
            this.setState({
                docente: {
                    ...this.state.docente,
                    [name]: ""
                }
            });
        }
    }

    handleUtenteChange = (e, {name, value}) => {
        this.setState({
            utente: {
                ...this.state.utente,
                [name]: value
            }
        });
    }

    handleEditorChange = (className, objName, content) => {
        this.setState({
            [className]: {
                ...this.state[className],
                [objName]: content
            }
        });
    }

    render() {
        if (this.state.redirect) {
            return <Redirect to = '/Backoffice'/>
        }
        return(
            <Segment className = 'col-md-8' color = 'teal' style = {{ marginTop: 4 }}>
                <div hidden = {!this.state.loading} className = 'loader-container'>
                    <Loader active size = 'massive'>Inviando Informazioni</Loader>
                </div>
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>{!this.props.docente ? 'Registra Un Docente' : 'Modifica Profilo Docente'}</Header>
                <Form>
                    {
                        !this.props.docente
                        ?
                            <Form.Group widths = 'equal'>
                                <Form.Input fluid name = 'username' value = {this.state.utente.username} onChange = {this.handleUtenteChange} label = 'Username' placeholder = 'Username' required/>
                                <Form.Input type = 'password' fluid name = 'password' value = {this.state.utente.password} onChange = {this.handleUtenteChange} label = 'Password' placeholder = 'Password' required/>
                            </Form.Group>
                        :
                            null
                    }
                    
                    <Form.Group widths = 'equal'>
                        <Form.Input fluid name = 'nome' value = {this.state.docente.nome} onChange = {this.handleDocenteChange} label = 'Nome' placeholder = 'Nome' required/>
                        <Form.Input fluid name = 'cognome' value = {this.state.docente.cognome} onChange = {this.handleDocenteChange} label = 'Cognome' placeholder = 'Cognome' required/>
                    </Form.Group>
                    <Form.Group widths = 'equal'>
                        <Form.Input type = 'email' fluid name = 'email' value = {this.state.docente.email} onChange = {this.handleDocenteChange} label = 'Email' placeholder = 'Email'/>
                        <Form.Input type = 'tel' fluid name = 'telefono' value = {this.state.docente.telefono} onChange = {this.handleDocenteChange} label = 'Telefono' placeholder = 'Telefono'/>
                    </Form.Group>
                    <Form.Group widths = 'equal'>
                        <Form.Input fluid name = 'ufficio' value = {this.state.docente.ufficio} onChange = {this.handleDocenteChange} label = 'Ufficio' placeholder = 'Ufficio'/>
                        <Form.Input fluid name = 'specializzazione' value = {this.state.docente.specializzazione} onChange = {this.handleDocenteChange} label = 'Specializzazione' placeholder = 'Specializzazione'/>
                    </Form.Group>
                    <Form.Group widths = 'equal'>
                        <Form.Input fluid name = 'ricevimento' value = {this.state.docente.ricevimento} onChange = {this.handleDocenteChange} label = 'Ricevimento' placeholder = 'Ricevimento'/>
                        <Form.Field/>
                    </Form.Group>
                    <CustomEditor
                        className = 'docente'
                        objName = 'ricerche' 
                        title = 'Ricerche Docente' 
                        placeholder = 'Scrivi Le Ricerche Qui'
                        handleEditorChange = {this.handleEditorChange}
                        value = {this.state.docente.ricerche}
                    />
                    <CustomEditor
                        className = 'docente'
                        objName = 'pubblicazioni' 
                        title = 'Pubblicazioni Docente' 
                        placeholder = 'Scrivi Le Pubblicazioni Qui'
                        handleEditorChange = {this.handleEditorChange}
                        value = {this.state.docente.pubblicazioni}
                    />
                    <Form.Group widths = 'equal'>
                        <Form.Input name = 'immagine' type = 'file' value = {this.state.docente.immagine} onChange = {this.handleDocenteChange} label = 'Immagine Docente'/>
                        <Form.Input name = 'curriculum' type = 'file' value = {this.state.docente.curriculum} onChange = {this.handleDocenteChange} label = 'Curriculum Docente'/>
                    </Form.Group>
                </Form>
                <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                    <Button onClick = {this.registraDocente} color = 'facebook' size = 'large' disabled = {!this.props.docente ? !this.state.utente.username.trim() || !this.state.utente.password.trim() || !this.state.docente.nome.trim() || !this.state.docente.cognome.trim() : !this.state.docente.nome.trim() || !this.state.docente.cognome.trim()}>{!this.props.docente ? 'Registra' : 'Conferma'}</Button>
                </div>
            </Segment>
        );
    }
}