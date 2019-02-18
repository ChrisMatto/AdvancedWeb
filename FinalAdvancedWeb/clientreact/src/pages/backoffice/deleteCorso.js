import React, { Component } from 'react';
import AnnoCdlStep from './stepCorso/annoCdlStep';
import { Redirect } from 'react-router-dom';
import { Segment, Form, Button, Loader, Header } from 'semantic-ui-react';

export default class DeleteCorso extends Component {
    constructor() {
        super();
        this.state = {
            cdl: [],
            corsi: [],
            anniCorsi: [],
            idCorso: null,
            annoCorso: null,
            cdlCorso: null,
            redirect: false
        }
    }

    componentWillMount() {
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

        fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/courses/years')
        .then(res => res.json())
        .then(result => {
            result.sort((a, b) => (a > b) ? -1 : (a < b) ? 1 : 0);
            this.setState({ anniCorsi: result });
        });
    }

    selectedCorso = (idCorso, annoCorso, cdlCorso) => {
        this.setState({
            annoCorso: annoCorso,
            cdlCorso: cdlCorso,
            idCorso: idCorso
        });
    }

    deleteCorso = () => {
        this.setState({ loading: true });
        var headers = new Headers();
        headers.append("Content-Type", "application/json");
        fetch("http://localhost:8080/AdvancedWeb/rest/auth/" + this.props.token + "/courses/" + this.state.annoCorso + '/' + this.state.idCorso, {
            method: 'DELETE',
            headers: headers
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
        return(
            <Segment className = 'col-md-8' color = 'teal' style = {{ marginTop: 4 }}>
                <div hidden = {!this.state.loading} className = 'loader-container'>
                    <Loader active size = 'massive'>Inviando Informazioni</Loader>
                </div>
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Elimina Un Corso</Header>
                <Form>
                    <AnnoCdlStep 
                        annoCorso = {this.state.annoCorso}
                        cdlCorso = {this.state.cdlCorso}
                        corso = {this.state.corso}
                        token = {this.props.token}
                        cdl = {this.state.cdl}
                        anniCorsi = {this.state.anniCorsi}
                        selectedCorso = {this.selectedCorso}
                        utente = {this.props.utente}
                    />
                </Form>
                <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                    <Button onClick = {this.deleteCorso} negative size = 'large' disabled = {!this.state.idCorso || !this.state.annoCorso}>Elimina il Corso</Button>
                </div>
            </Segment>
        );
    }
}