import React, { Component } from 'react';
import { Segment, Form, Button, Loader, Header } from 'semantic-ui-react';
import { Redirect } from 'react-router-dom';

export default class RegistraAmministratore extends Component {
    constructor() {
        super();
        this.state = {
            username: "",
            password: "",
            redirect: false
        };
    }

    handleChange = (e, {name, value}) => {
        this.setState({
            [name]: value
        });
    }

    registraAmministratore = () => {
        var utente = {
            username: this.state.username,
            password: this.state.password
        }
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        fetch("http://localhost:8080/AdvancedWeb/rest/auth/" + this.props.token + "/users", {
            method: 'POST',
            headers: headers,
            body: JSON.stringify(utente)
        })
        .then(res => this.setState({ redirect: true }));
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
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Registra Un Amministratore</Header>
                <Form>
                    <Form.Group widths = 'equal'>
                        <Form.Input fluid name = 'username' value = {this.state.username} onChange = {this.handleChange} label = 'Username' placeholder = 'Username' required/>
                        <Form.Input type = 'password' fluid name = 'password' value = {this.state.password} onChange = {this.handleChange} label = 'Password' placeholder = 'Password' required/>
                    </Form.Group>
                </Form>
                <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                    <Button onClick = {this.registraAmministratore} color = 'facebook' size = 'large' disabled = {!this.state.username.trim() || !this.state.password.trim()}>Registra</Button>
                </div>
            </Segment>
        );
    }
}