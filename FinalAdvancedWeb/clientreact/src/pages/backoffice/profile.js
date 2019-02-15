import React, { Component } from 'react';
import { Segment, Form, Button, Loader, Header } from 'semantic-ui-react';
import { Redirect } from 'react-router-dom';

export default class UpdateProfilo extends Component {
    constructor() {
        super();
        this.state = {
            username: "",
            password: "",
            redirect: false,
            loading: false
        };
    }

    handleChange = (e, {name, value}) => {
        this.setState({
            [name]: value
        });
    }

    modificaProfilo = () => {
        this.setState({ loading: true });
        var utente = {
            username: this.state.username.trim() ? this.state.username : this.props.username,
            password: this.state.password
        }
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        fetch("http://localhost:8080/AdvancedWeb/rest/auth/" + this.props.token + "/users/" + this.props.utente.idUtente, {
            method: 'PUT',
            headers: headers,
            body: JSON.stringify(utente)
        })
        .then(res => res.ok ? this.setState({ redirect: true }) : null);
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
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Modifica Profilo</Header>
                <Form>
                    <Form.Group widths = 'equal'>
                        <Form.Input fluid name = 'username' value = {this.state.username} onChange = {this.handleChange} label = 'Username' placeholder = {this.props.utente.username} required/>
                        <Form.Input type = 'password' fluid name = 'password' value = {this.state.password} onChange = {this.handleChange} label = 'Password' placeholder = 'Password' required/>
                    </Form.Group>
                </Form>
                <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                    <Button onClick = {this.modificaProfilo} color = 'facebook' size = 'large' disabled = {!this.state.password.trim()}>Modifica</Button>
                </div>
            </Segment>
        );
    }
}