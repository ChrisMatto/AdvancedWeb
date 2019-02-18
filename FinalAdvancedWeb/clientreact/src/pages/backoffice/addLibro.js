import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Segment, Header, Form, Button, Loader } from 'semantic-ui-react';
import AnnoCdlStep from './stepCorso/annoCdlStep';
import LibroForm from './libroForm/libroForm';

export default class AddLibro extends Component {
    constructor() {
        super();
        this.state = {
            cdl: [],
            corsi: [],
            anniCorsi: [],
            idCorso: null,
            annoCorso: null,
            cdlCorso: null,
            redirect: false,
            selected: false,
            libro: {
                titolo: "",
                autore: "",
                volume: "",
                anno: "",
                editore: "",
                link: ""
            }
        };
    }

    componentWillMount() {
        if (!this.props.utente.docente) {
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

    handleChange = (e, {name, value}) => {
        this.setState({
            libro: {
                ...this.state.libro,
                [name]: value
            }
        });
    }

    addLibro = () => {
        this.setState({ loading: true });
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/books/course/' + this.state.annoCorso + '/' + this.state.idCorso, {
            method: 'POST',
            headers: headers,
            body: JSON.stringify(this.state.libro)
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
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Aggiungi Un Libro</Header>
                <Form>
                    {
                        this.state.selected
                        ?
                            <LibroForm
                                handleChange = {this.handleChange}
                                libro = {this.state.libro}
                            />
                        :
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
                    }                    
                </Form>
                <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                    <Button onClick = {() => !this.state.selected ? this.setState({ selected: true }) : this.addLibro()} color = 'facebook' size = 'large' disabled = {!this.state.selected ? !this.state.idCorso : !this.state.libro.titolo.trim() || !this.state.libro.autore.trim() || !this.state.libro.anno}>Conferma</Button>
                </div>
            </Segment>
        );
    }
}