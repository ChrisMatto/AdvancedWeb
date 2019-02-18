import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Segment, Header, Form, Button, Loader, Accordion, Icon } from 'semantic-ui-react';
import AnnoCdlStep from './stepCorso/annoCdlStep';
import LibroForm from './libroForm/libroForm';

export default class ManageLibri extends Component {
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
            libri: [],
            activeAccordionIndex: null
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
        if (idCorso) {
            fetch('http://localhost:8080/AdvancedWeb/rest/books/course/' + annoCorso + '/' + idCorso)
            .then(res => res.ok ? res.json() : [])
            .then(result => {
                this.setState({ libri: result })
            });
        }
    }

    handleChange = (e, {name, value}) => {
        let libri = this.state.libri;
        let index = libri.findIndex(l => l.idLibro === this.state.activeAccordionIndex);
        libri[index] = {
            ...libri[index],
            [name]: value
        };
        this.setState({
            libri: libri
        });
    }

    updateLibro = (idLibro) => {
        this.setState({ loading: true });
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        let libro = this.state.libri.find(l => l.idLibro === idLibro);
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/books/course/' + this.state.annoCorso + '/' + this.state.idCorso + '/' + idLibro, {
            method: 'PUT',
            headers: headers,
            body: JSON.stringify(libro)
        })
        .then(res => res.ok ? this.setState({ redirect: true }) : null);
    }

    deleteLibro = (idLibro) => {
        this.setState({ loading: true });
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/books/course/' + this.state.annoCorso + '/' + this.state.idCorso + '/' + idLibro, {
            method: 'DELETE'
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
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Gestisci Libri Corso</Header>
                <Form>
                    {
                        this.state.selected
                        ?
                            this.state.libri.map(libro => 
                                <CustomAccordion 
                                    key = {libro.idLibro} 
                                    activeIndex = {this.state.activeAccordionIndex} 
                                    libro = {libro} 
                                    handleChange = {this.handleChange}
                                    handleClick = {(id) => this.setState({ activeAccordionIndex: id })}
                                    updateLibro = {this.updateLibro}
                                    deleteLibro = {this.deleteLibro}
                                />
                            )
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
                {
                    !this.state.selected
                    ?
                        <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                            <Button onClick = {() => this.setState({ selected: true })} color = 'facebook' size = 'large' disabled = {!this.state.idCorso}>Conferma</Button>
                        </div>
                    :
                        null
                }
            </Segment>
        );
    }
}

function CustomAccordion(props) {
    let keys = Object.keys(props.libro);
    let libro = props.libro;
    keys.forEach(key => {
        if (libro[key] === null) {
            libro[key] = "";
        }
    });
    return (
        <Accordion styled style = {{ width: '100%', marginTop: 10 }}>
            <Accordion.Title index = {libro.idLibro} active = {props.activeIndex === libro.idLibro} onClick = {() => props.activeIndex === libro.idLibro ? props.handleClick(null) : props.handleClick(libro.idLibro)}>
                <Icon name='dropdown' />
                {libro.titolo}
            </Accordion.Title>
            <Accordion.Content active = {props.activeIndex === libro.idLibro}>
                <LibroForm
                    libro = {libro}
                    handleChange = {props.handleChange}
                    isUpdate = {true}
                    updateLibro = {props.updateLibro}
                    deleteLibro = {props.deleteLibro}
                />
            </Accordion.Content>
        </Accordion>
    );
}