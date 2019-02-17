import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Segment, Header, Form, Button, Loader } from 'semantic-ui-react';
import { validateImg, fileToBase64 } from '../../js/functions';
import { Switch } from '@progress/kendo-react-inputs';
import '@progress/kendo-theme-default';

export default class CreateCdl extends Component {
    constructor() {
        super();
        this.state = {
            cdl: {
                nomeIt: "",
                nomeEn: "",
                cfu: "",
                magistrale: false,
                immagine: "",
                descrizioneIt: "",
                descrizioneEn: "",
                abbrIt: "",
                abbrEn: ""
            },
            immagine: null,
            loading: false,
            redirect: false
        };
    }

    componentWillMount() {
        let cdl = this.props.cdl;
        if (cdl) {
            cdl.immagine = "";
            delete cdl.corsi;
            var keys = Object.keys(cdl);
            keys.forEach(key => {
                if (key !== "magistrale" && !cdl[key]) {
                    cdl[key] = "";
                }
            });
            this.setState({ cdl: cdl });
        }
    }

    handleCdlChange = (e, {name, value}) => {
        var canChange = true;
        switch (name) {
            default:
                break;
            case 'immagine':
                e.persist();
                canChange = validateImg(value);
                let immagine = e.target.files[0];
                if (canChange && value.length > 0) {
                    fileToBase64(immagine)
                    .then(immagine => {
                        this.setState({ immagine: immagine });
                    });
                } else {
                    this.setState({ immagine: null });
                }
                break;
        }
        if (canChange) {
            this.setState({
                cdl: {
                    ...this.state.cdl,
                    [name]: value
                }
            });
        } else {
            this.setState({
                cdl: {
                    ...this.state.cdl,
                    [name]: ""
                }
            });
        }
    }

    creaCdl = () => {
        this.setState({ loading: true });
        let cdl = JSON.parse(JSON.stringify(this.state.cdl));
        cdl.immagine = this.state.immagine;
        let headers = new Headers();
        headers.append("Content-Type", "application/json");
        if (this.state.cdl.immagine && this.state.cdl.immagine.trim()) {
            let arr = this.state.cdl.immagine.split(".");
            headers.append("image-type", arr[arr.length - 1]);
        }
        if (!this.props.cdl) {
            fetch("http://localhost:8080/AdvancedWeb/rest/auth/" + this.props.token + "/cdl", {
                method: 'POST',
                headers: headers,
                body: JSON.stringify(cdl)
            })
            .then(res => res.ok ? this.setState({ redirect: true }) : null);
        } else {
            fetch("http://localhost:8080/AdvancedWeb/rest/auth/" + this.props.token + "/cdl/" + cdl.idcdl, {
                method: 'PUT',
                headers: headers,
                body: JSON.stringify(cdl)
            })
            .then(res => res.ok ? this.setState({ redirect: true }) : null);
        }
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
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>{!this.props.cdl ? 'Crea Un Nuovo Corso Di Laurea' : 'Modifica Corso Di Laurea'}</Header>
                <Form>
                    <Form.Group widths = 'equal'>
                        <Form.Input fluid name = 'nomeIt' value = {this.state.cdl.nomeIt} onChange = {this.handleCdlChange} label = 'Nome Italiano' placeholder = 'Nome It' required/>
                        <Form.Input fluid name = 'nomeEn' value = {this.state.cdl.nomeEn} onChange = {this.handleCdlChange} label = 'Nome Inglese' placeholder = 'Nome En'/>
                    </Form.Group>
                    <Form.Group widths = 'equal'>
                        <Form.Input maxLength = "5" fluid name = 'abbrIt' value = {this.state.cdl.abbrIt} onChange = {this.handleCdlChange} label = 'Sigla Corso Di Laurea It' placeholder = 'Sigla It' required/>
                        <Form.Input maxLength = "5" fluid name = 'abbrEn' value = {this.state.cdl.abbrEn} onChange = {this.handleCdlChange} label = 'Sigla Corso Di Laurea En' placeholder = 'Sigla En'/>
                    </Form.Group>
                    <Form.Group widths = 'equal'>
                        <Form.Input fluid name = 'cfu' value = {this.state.cdl.cfu} onChange = {this.handleCdlChange} label = 'CFU' placeholder = 'CFU' type = 'number' min = '0'/>
                        <Form.Field>
                            <label>Corso Di Laurea Magistrale?</label>
                            <Switch onLabel = 'Si' offLabel = 'No' checked = {this.state.cdl.magistrale} onChange = {(event) => this.setState({ cdl: { ...this.state.cdl, magistrale: event.target.value } })}/>
                        </Form.Field>
                    </Form.Group>
                    <Form.Group widths = 'equal'>
                        <Form.TextArea name = 'descrizioneIt' label = 'Descrizione Corsi Di Laurea It' value = {this.state.cdl.descrizioneIt} onChange = {this.handleCdlChange} placeholder = 'Descrizione Italiano'/>
                        <Form.TextArea name = 'descrizioneEn' label = 'Descrizione Corsi Di Laurea En' value = {this.state.cdl.descrizioneEn} onChange = {this.handleCdlChange} placeholder = 'Descrizione Inglese'/>
                    </Form.Group>
                    <Form.Group widths = 'equal'>
                        <Form.Input name = 'immagine' type = 'file' value = {this.state.cdl.immagine} onChange = {this.handleCdlChange} label = 'Immagine CDL'/>
                        <Form.Field/>
                    </Form.Group>
                </Form>
                <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                    <Button onClick = {this.creaCdl} color = 'teal' size = 'large' disabled = {!this.state.cdl.nomeIt || !this.state.cdl.abbrIt}>Conferma</Button>
                </div>
            </Segment>
        );
    }
}