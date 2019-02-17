import React, { Component } from 'react';
import { Segment, Form, Button, Loader, Header } from 'semantic-ui-react';
import { Redirect } from 'react-router-dom';
import RegistraDocente from './registraDocente';

export default class UpdateDocente extends Component {
    constructor() {
        super();
        this.state = {
            docenti: [],
            idDocente: null,
            loading: false,
            selectedDocente: false
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
        } else {
            this.setState({ loading: true });
            fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/teachers/' + this.props.utente.docente)
            .then(res => res.ok ? res.json() : null)
            .then(result => {
                let docenti = [result];
                this.setState({ docenti: docenti, idDocente: result.idDocente, selectedDocente: true });
            });
        }
    }

    handleChange = (e, {name, value}) => {
        this.setState({ [name]: value });
    }

    render() {
        if (this.state.redirect) {
            return <Redirect to = '/Backoffice'/>
        }

        let docente = this.state.docenti.find(docente => docente.idDocente === this.state.idDocente);
        if (this.state.selectedDocente) {
            return <RegistraDocente utente = {this.props.utente} token = {this.props.token} docente = {docente}/>
        }

        if (this.props.utente.docente) {
            return (
                <div hidden = {!this.state.loading} className = 'loader-container'>
                    <Loader active size = 'massive'>Inviando Informazioni</Loader>
                </div>
            );
        }

        var docenti = [];
        this.state.docenti.forEach(docente => {
            let obj = {
                key: docente.idDocente,
                text: docente.nome + ' ' + docente.cognome,
                value: docente.idDocente
            };
            docenti.push(obj);
        });

        return(
            <Segment className = 'col-md-8' color = 'teal' style = {{ marginTop: 4 }}>
                <div hidden = {!this.state.loading} className = 'loader-container'>
                    <Loader active size = 'massive'>Inviando Informazioni</Loader>
                </div>
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Scegli Un Docente</Header>
                <Form>
                    <Form.Group>
                        <Form.Field width = '3'/>
                        <Form.Dropdown 
                            width = '10'
                            fluid
                            scrolling
                            search
                            selection
                            label = 'Seleziona Un Docente'
                            onChange = {this.handleChange}
                            name = 'idDocente'
                            options = {docenti}
                            placeholder = 'Seleziona Docente...'
                            closeOnBlur
                            required
                            value = {this.state.idDocente}
                        />
                        <Form.Field width = '3'/>
                    </Form.Group>
                </Form>
                <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                    <Button onClick = {() => this.setState({ selectedDocente: true })} color = 'facebook' size = 'large' disabled = {!this.state.idDocente}>Conferma</Button>
                </div>
            </Segment>
        );
    }
}