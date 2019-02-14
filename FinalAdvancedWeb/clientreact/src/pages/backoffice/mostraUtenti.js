import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Header, Segment, Table, Loader, Button } from 'semantic-ui-react';

export default class MostraUtenti extends Component {
    constructor() {
        super();
        this.state = {
            users: [],
            redirect: null
        };
    }

    componentWillMount() {
        fetch("http://localhost:8080/AdvancedWeb/rest/auth/" + this.props.token + "/users")
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
                var users = [];
                results.forEach(c => {
                    users.push(c);
                });
                this.setState({ users: users });
            });
        });
    }

    render() {
        if (this.state.redirect) {
            return <Redirect to = {'/Teachers/' + this.state.redirect}/>
        }
        return(
            <Segment className = 'col-md-8' color = 'teal' style = {{ marginTop: 4 }}>
                <div hidden = {!this.state.loading} className = 'loader-container'>
                    <Loader active size = 'massive'>Inviando Informazioni</Loader>
                </div>
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Lista Utenti</Header>
                <Table celled striped color = 'grey' className = 'center-table'>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Username</Table.HeaderCell>
                            <Table.HeaderCell>Ruolo</Table.HeaderCell>
                            <Table.HeaderCell>Profilo</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {
                            this.state.users.map(utente => 
                                <Table.Row key = {utente.idUtente}>
                                    <Table.Cell>{utente.username}</Table.Cell>
                                    <Table.Cell>{utente.docente ? 'Docente' : 'Amministratore'}</Table.Cell>
                                    <Table.Cell textAlign = 'center'>{utente.docente ? <Button icon = 'external' onClick = {() => this.setState({ redirect: utente.docente })}/> : null}</Table.Cell>
                                </Table.Row>
                            )
                        }
                    </Table.Body>
                </Table>
            </Segment>
        );
    }
}