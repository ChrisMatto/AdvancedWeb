import React, { Component, Fragment } from 'react';
import { Redirect } from 'react-router-dom';
import { Segment, Header, Form, Button, Loader } from 'semantic-ui-react';
import AnnoCdlStep from './stepCorso/annoCdlStep';

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
                    <Button onClick = {() => !this.state.selected ? this.setState({ selected: true }) : this.addLibro()} color = 'facebook' size = 'large' disabled = {!this.state.idCorso}>Conferma</Button>
                </div>
            </Segment>
        );
    }
}

function LibroForm(props) {
    return (
        <Fragment>
            <Form.Group widths = 'equal'>
                <Form.Input fluid name = 'titolo' value = {props.libro.titolo} onChange = {props.handleChange} label = 'Titolo Libro' placeholder = 'Titolo' required/>
                <Form.Input fluid name = 'autore' value = {props.libro.autore} onChange = {props.handleChange} label = 'Autore Libro' placeholder = 'Autore' required/>
            </Form.Group>
            <Form.Group widths = 'equal'>
                <Form.Input type = 'number' fluid name = 'anno' value = {props.libro.anno} onChange = {props.handleChange} label = 'Anno Pubblicazione Libro' placeholder = 'Anno' required/>
                <Form.Input type = 'number' fluid name = 'volume' value = {props.libro.volume} onChange = {props.handleChange} label = 'Volume Libro' placeholder = 'Volume'/>
            </Form.Group>
            <Form.Group widths = 'equal'>
                <Form.Input fluid name = 'editore' value = {props.libro.editore} onChange = {props.handleChange} label = 'Editore Libro' placeholder = 'Editore'/>
                <Form.Input fluid name = 'link' value = {props.libro.link} onChange = {props.handleChange} label = 'Link Libro' placeholder = 'Link'/>
            </Form.Group>
        </Fragment>
    );
}