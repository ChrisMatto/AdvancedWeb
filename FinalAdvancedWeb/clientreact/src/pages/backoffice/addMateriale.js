import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Segment, Header, Form, Button, Loader } from 'semantic-ui-react';
import AnnoCdlStep from './stepCorso/annoCdlStep';
import MaterialeForm from './materialeForm/materialeForm';
import { fileToBase64 } from './../../js/functions';

export default class AddMateriale extends Component {
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
            materiale: {
                nome: "",
                link: "",
                descrizioneIt: "",
                descrizioneEn: ""
            },
            link: null
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
        let canChange = true;
        if (name === 'link') {
            e.persist();
            let immagine = e.target.files[0];
            if (value.length > 0) {
                fileToBase64(immagine)
                .then(immagine => {
                    this.setState({ link: immagine });
                });
            } else {
                canChange = false;
                this.setState({ link: null });
            }
        }
        if (canChange) {
            this.setState({
                materiale: {
                    ...this.state.materiale,
                    [name]: value
                }
            });
        } else {
            this.setState({
                materiale: {
                    ...this.state.materiale,
                    [name]: ""
                }
            });
        }
    }

    addMateriale = () => {
        this.setState({ loading: true });
        let materiale = JSON.parse(JSON.stringify(this.state.materiale));
        materiale.link = this.state.link;
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        if (this.state.materiale.link && this.state.materiale.link.trim()) {
            let arr = this.state.materiale.link.split(".");
            headers.append("file-type", arr[arr.length - 1]);
        }
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/material/course/' + this.state.annoCorso + '/' + this.state.idCorso, {
            method: 'POST',
            headers: headers,
            body: JSON.stringify(materiale)
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
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Aggiungi Un Materiale</Header>
                <Form>
                    {
                        this.state.selected
                        ?
                            <MaterialeForm
                                handleChange = {this.handleChange}
                                materiale = {this.state.materiale}
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
                    <Button onClick = {() => !this.state.selected ? this.setState({ selected: true }) : this.addMateriale()} color = 'facebook' size = 'large' disabled = {!this.state.selected ? !this.state.idCorso : !this.state.materiale.nome.trim() || !this.state.materiale.link.trim()}>Conferma</Button>
                </div>
            </Segment>
        );
    }
}