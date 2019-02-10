import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Segment, Header, Progress, Form, Button, Loader } from 'semantic-ui-react';
import AnnoCdlStep from './stepCorso/annoCdlStep';

export default class UpdateCorso extends Component {
    constructor() {
        super();
        this.state = {
            step: 0,
            corso: {},
            docenti: [],
            cdl: [],
            corsi: [],
            anniCorsi: [],
            idCorso: null,
            annoCorso: null,
            cdlCorso: null,
            formError: false,
            loading: false,
            redirect: false
        };
    }

    componentWillMount() {
        if (!this.props.utente.docente) {
            fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/teachers')
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
                    var teachers = [];
                    results.forEach(teacher => {
                        teachers.push(teacher);
                    });
                    this.setState({ docenti: teachers });
                });
            });

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
                this.setState({ anniCorsi: result });
            });
        }
    }

    selectedCorso = (idCorso, annoCorso, cdlCorso) => {
        this.setState({
            annoCorso: annoCorso,
            cdlCorso: cdlCorso,
            idCorso: idCorso
        });
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/courses/' + annoCorso + '?cdl=' + cdlCorso)
        .then(res => res.ok ? res.json() : null)
        .then(result => {
            this.setState({
                corso: result
            });
        });
    }

    aheadButtonClick = () => {
        if (this.state.step === 0 && this.state.annoCorso && this.state.cdlCorso && this.state.idCorso) {
            this.setState({ step: this.state.step + 1, formError: false });
        } else {
            this.setState({ formError: true });
        }
        document.documentElement.scrollTop = 200;
    }
    
    render() {
        if (this.state.redirect) {
            return <Redirect to = '/Backoffice'/>
        }
        var title;
        var Step;
        switch (this.state.step) {
            default:
                title = 'Scegli Anno e CDL Del Corso Da Modificare';
                Step = <AnnoCdlStep 
                            annoCorso = {this.state.annoCorso}
                            cdlCorso = {this.state.cdlCorso}
                            corso = {this.state.corso}
                            token = {this.props.token}
                            formError = {this.state.formError}
                            cdl = {this.state.cdl}
                            anniCorsi = {this.state.anniCorsi}
                            selectedCorso = {this.selectedCorso}
                        />
                break;
        }
        return (
            <Segment className = 'col-md-8' color = 'teal' style = {{ marginTop: 4 }}>
                <div hidden = {!this.state.loading} className = 'loader-container'>
                    <Loader active size = 'massive'>Inviando Informazioni</Loader>
                </div>
                <Progress value = {this.state.step} total='6' indicating/>
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>{title}</Header>
                <Form>
                    {Step}
                </Form>
                <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                    <Button.Group>
                        <Button onClick = {() => {this.setState({ step: this.state.step - 1 }); document.documentElement.scrollTop = 200}} disabled = {this.state.step === 0}>Indietro</Button>
                        <Button.Or text = 'O'/>
                        <Button positive onClick = {this.aheadButtonClick} disabled = {this.state.step === 6}>Avanti</Button>
                    </Button.Group>
                </div>
            </Segment>
        );
    }

}