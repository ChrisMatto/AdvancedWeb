import React, { Component } from 'react';
import { Segment, Form, Button, Loader, Header } from 'semantic-ui-react';
import { Redirect } from 'react-router-dom';
import CustomEditor from './stepCorso/customEditor';
import { validateCurr, validateImg } from '../../js/functions';

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
            immagine: {},
            curriculum: {}
        };
    }

    registraDocente = () => {

    }

    handleDocenteChange = (e, {name, value}) => {
        var canChange = true;
        switch (name) {
            default:
                break;
            case 'immagine':
                e.persist();
                canChange = validateImg(value);
                if (canChange) {
                    this.setState({ [name]: e.target.files[0] });
                }
                break;
            case 'curriculum':
                e.persist();
                canChange = validateCurr(value);
                if (canChange) {
                    this.setState({ [name]: e.target.files[0] });
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
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Registra Un Docente</Header>
                <Form>
                    <Form.Group widths = 'equal'>
                        <Form.Input fluid name = 'username' value = {this.state.utente.username} onChange = {this.handleUtenteChange} label = 'Username' placeholder = 'Username' required/>
                        <Form.Input type = 'password' fluid name = 'password' value = {this.state.utente.password} onChange = {this.handleUtenteChange} label = 'Password' placeholder = 'Password' required/>
                    </Form.Group>
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
                        <Form.Input fluid name = 'Ricevimento' value = {this.state.docente.ricevimento} onChange = {this.handleDocenteChange} label = 'Ricevimento' placeholder = 'Ricevimento'/>
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
                    <Button onClick = {this.registraDocente} color = 'facebook' size = 'large' disabled = {!this.state.utente.username.trim() || !this.state.utente.password.trim() || !this.state.docente.nome.trim() || !this.state.docente.cognome.trim()}>Registra</Button>
                </div>
            </Segment>
        );
    }
}