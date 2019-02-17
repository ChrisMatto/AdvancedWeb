import React, { Component } from 'react';
import { Segment, Form, Button, Loader, Header } from 'semantic-ui-react';
import { Redirect } from 'react-router-dom';
import CreateCdl from './createCdl';

export default class UpdateCdl extends Component {
    constructor() {
        super();
        this.state = {
            cdl: [],
            idCdl: null,
            loading: false,
            selectedCdl: false
        };
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
    }

    handleChange = (e, {name, value}) => {
        this.setState({ [name]: value });
    }

    deleteCdl = () => {
        this.setState({ loading: true });
        var headers = new Headers();
        headers.append("Content-Type", "application/json");
        fetch("http://localhost:8080/AdvancedWeb/rest/auth/" + this.props.token + "/cdl/" + this.state.idCdl, {
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

        let sCdl = this.state.cdl.find(c => c.idcdl === this.state.idCdl);
        if (this.state.selectedCdl) {
            return <CreateCdl utente = {this.props.utente} token = {this.props.token} cdl = {sCdl}/>
        }

        var cdl = [];
        this.state.cdl.forEach(c => {
            let obj = {
                key: c.idcdl,
                text: c.nomeIt,
                value: c.idcdl
            };
            cdl.push(obj);
        });

        return(
            <Segment className = 'col-md-8' color = 'teal' style = {{ marginTop: 4 }}>
                <div hidden = {!this.state.loading} className = 'loader-container'>
                    <Loader active size = 'massive'>Inviando Informazioni</Loader>
                </div>
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Scegli Un Corso Di Laurea</Header>
                <Form>
                    <Form.Group>
                        <Form.Field width = '3'/>
                        <Form.Dropdown 
                            width = '10'
                            fluid
                            scrolling
                            search
                            selection
                            label = 'Seleziona Un CDL'
                            onChange = {this.handleChange}
                            name = 'idCdl'
                            options = {cdl}
                            placeholder = 'Seleziona CDL...'
                            closeOnBlur
                            required
                            value = {this.state.idCdl}
                        />
                        <Form.Field width = '3'/>
                    </Form.Group>
                </Form>
                <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                    {
                        this.props.isDelete
                        ?
                            <Button onClick = {() => this.deleteCdl()} color = 'red' size = 'large' disabled = {!this.state.idCdl}>Elimina</Button>
                        :
                        <Button onClick = {() => this.setState({ selectedCdl: true })} color = 'facebook' size = 'large' disabled = {!this.state.idCdl}>Conferma</Button>
                    }
                </div>
            </Segment>
        );
    }
}