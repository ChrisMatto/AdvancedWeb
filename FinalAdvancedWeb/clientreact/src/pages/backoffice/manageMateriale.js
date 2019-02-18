import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Segment, Header, Form, Button, Loader, Accordion, Icon } from 'semantic-ui-react';
import AnnoCdlStep from './stepCorso/annoCdlStep';
import MaterialeForm from './materialeForm/materialeForm';
import { fileToBase64 } from './../../js/functions';

export default class ManageMateriale extends Component {
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
            materiali: [],
            activeAccordionIndex: null,
            links: {}
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
            fetch('http://localhost:8080/AdvancedWeb/rest/material/course/' + annoCorso + '/' + idCorso)
            .then(res => res.ok ? res.json() : [])
            .then(result => {
                this.setState({ materiali: result })
            });
        }
    }

    handleChange = (e, {name, value}) => {
        let materiali = this.state.materiali;
        let index = materiali.findIndex(l => l.idMateriale === this.state.activeAccordionIndex);
        let canChange = true;
        if (name === 'link') {
            let links = this.state.links;
            e.persist();
            let immagine = e.target.files[0];
            if (value.length > 0) {
                fileToBase64(immagine)
                .then(immagine => {
                    links[index] = immagine;
                    this.setState({ links: links });
                });
            } else {
                canChange = false;
                links[index] = null;
                this.setState({ links: links });
            }
        }
        if (canChange) {
            materiali[index] = {
                ...materiali[index],
                [name]: value
            };
        } else {
            materiali[index] = {
                ...materiali[index],
                [name]: ""
            };
        }
        this.setState({ materiali: materiali });
    }

    updateMateriale = (idMateriale) => {
        this.setState({ loading: true });
        let index = this.state.materiali.findIndex(l => l.idMateriale === idMateriale);
        let materiale = JSON.parse(JSON.stringify(this.state.materiali[index]));
        materiale.link = this.state.links[index];
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        if (this.state.materiali[index].link && this.state.materiali[index].link.trim()) {
            let arr = this.state.materiali[index].link.split(".");
            headers.append("file-type", arr[arr.length - 1]);
        }
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/material/course/' + this.state.annoCorso + '/' + this.state.idCorso + '/' + idMateriale, {
            method: 'PUT',
            headers: headers,
            body: JSON.stringify(materiale)
        })
        .then(res => res.ok ? this.setState({ redirect: true }) : null);
    }

    deleteMateriale = (idMateriale) => {
        this.setState({ loading: true });
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/material/course/' + this.state.annoCorso + '/' + this.state.idCorso + '/' + idMateriale, {
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
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Gestisci Materiale Corso</Header>
                <Form>
                    {
                        this.state.selected
                        ?
                            this.state.materiali.map(materiale => 
                                <CustomAccordion 
                                    key = {materiale.idMateriale} 
                                    activeIndex = {this.state.activeAccordionIndex} 
                                    materiale = {materiale} 
                                    handleChange = {this.handleChange}
                                    handleClick = {(id) => this.setState({ activeAccordionIndex: id })}
                                    updateMateriale = {this.updateMateriale}
                                    deleteMateriale = {this.deleteMateriale}
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
    let keys = Object.keys(props.materiale);
    let materiale = props.materiale;
    keys.forEach(key => {
        if (materiale[key] === null) {
            materiale[key] = "";
        }
    });
    if (!keys.includes("link")) {
        materiale.link = "";
    }
    delete materiale.dimensioni;
    return (
        <Accordion styled style = {{ width: '100%', marginTop: 10 }}>
            <Accordion.Title index = {materiale.idMateriale} active = {props.activeIndex === materiale.idMateriale} onClick = {() => props.activeIndex === materiale.idMateriale ? props.handleClick(null) : props.handleClick(materiale.idMateriale)}>
                <Icon name='dropdown' />
                {materiale.nome}
            </Accordion.Title>
            <Accordion.Content active = {props.activeIndex === materiale.idMateriale}>
                <MaterialeForm
                    materiale = {materiale}
                    handleChange = {props.handleChange}
                    isUpdate = {true}
                    updateMateriale = {props.updateMateriale}
                    deleteMateriale = {props.deleteMateriale}
                />
            </Accordion.Content>
        </Accordion>
    );
}