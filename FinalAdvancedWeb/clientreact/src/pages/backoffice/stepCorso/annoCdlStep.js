import React, { Fragment, Component } from 'react';
import { Form } from 'semantic-ui-react';

export default class AnnoCdlStep extends Component{
    _anno = null;
    _cdl = null;
    constructor() {
        super();
        this.state = {
            anno: null,
            cdl: null,
            idCorso: null,
            corsi: []
        };
    }
    
    handleChange = (e, {name, value}) => {
        this.setState({ [name]: value });
    }

    componentDidMount() {
        this.setState({
            anno: this.props.annoCorso,
            cdl: this.props.cdlCorso,
            idCorso: this.props.idCorso
        });
        this.loadCorsi();
    }

    loadCorsi = () => {
        let canLoad = false;
        if (this.props.utente.docente) {
            if (this.state.anno && this.state.anno !== this._anno) {
                canLoad = true;
            }
        } else {
            if (this.state.anno && this.state.cdl && (this.state.anno !== this._anno || this.state.cdl !== this._cdl)) {
                canLoad = true;
            }
        }
        if (canLoad) {
            this.setState({ corsi: [], idCorso: null });
            this.props.selectedCorso(null, this.state.anno, this.state.cdl);
            this._anno = this.state.anno;
            this._cdl = this.state.cdl;
            let link = 'http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + '/courses/' + this.state.anno;
            if (this.props.utente.docente) {
                link += '?teacher=' + this.props.utente.docente;
            } else {
                link += '?cdl=' + this.state.cdl;
            }
            fetch(link)
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
                    var corsi = [];
                    results.forEach(corso => {
                        corsi.push(corso);
                    });
                    this.setState({ corsi: corsi });
                });
            });
        }
    }

    componentDidUpdate() {
        this.loadCorsi();
    }

    render() {
        var anniAccademici = [];
        this.props.anniCorsi.forEach(anno => {
            let obj = {
                key: anno,
                text: anno + '/' + (anno + 1),
                value: anno
            };
            anniAccademici.push(obj);
        });

        if (!this.props.utente.docente) {
            var cdl = [];
            this.props.cdl.forEach(c => {
                let obj = {
                    key: c.idcdl,
                    text: c.nomeIt,
                    value: c.idcdl
                };
                cdl.push(obj);
            });
        }

        var corsi = [];
        this.state.corsi.forEach(corso => {
            let obj = {
                key: corso.idCorso,
                text: corso.nomeIt,
                value: corso.idCorso
            };
            corsi.push(obj);
        });

        if (this.props.utente.docente) {
            return (
                <Fragment>
                    <Form.Group widths = 'equal'>
                        <Form.Dropdown 
                            fluid
                            scrolling
                            search
                            selection
                            label = 'Seleziona Un Anno Accademico'
                            onChange = {this.handleChange}
                            name = 'anno'
                            options = {anniAccademici}
                            placeholder = 'Seleziona Anno...'
                            closeOnBlur
                            required
                            value = {this.state.anno}
                            error = {this.props.formError && !this.state.anno}
                        />
                        <Form.Dropdown
                            fluid
                            scrolling
                            search
                            selection
                            label = 'Seleziona Corso Da Modificare'
                            onChange = {(e, {name, value}) => {
                                this.setState({ idCorso: value });
                                this.props.selectedCorso(value, this.state.anno, this.state.cdl);
                            }}
                            name = 'corso'
                            options = {corsi}
                            placeholder = 'Seleziona Corso...'
                            closeOnBlur
                            required
                            value = {this.state.idCorso}
                            disabled = {!this.state.anno}
                            error = {this.props.formError && !this.state.idCorso}
                        />
                    </Form.Group>
                </Fragment>
            );
        } else {
            return (
                <Fragment>
                    <Form.Group widths = 'equal'>
                        <Form.Dropdown 
                            fluid
                            scrolling
                            search
                            selection
                            label = 'Corso Di Laurea'
                            onChange = {this.handleChange}
                            name = 'cdl'
                            options = {cdl}
                            placeholder = 'Seleziona CDL...'
                            closeOnBlur
                            required
                            value = {this.state.cdl}
                            error = {this.props.formError && !this.state.cdl}
                        />
                        <Form.Dropdown 
                            fluid
                            scrolling
                            search
                            selection
                            label = 'Anno Accademico'
                            onChange = {this.handleChange}
                            name = 'anno'
                            options = {anniAccademici}
                            placeholder = 'Seleziona Anno...'
                            closeOnBlur
                            required
                            value = {this.state.anno}
                            error = {this.props.formError && !this.state.anno}
                        />
                    </Form.Group>
                    <Form.Group>
                        <Form.Field width = {4}/>
                        <Form.Dropdown
                            width = {10}
                            fluid
                            scrolling
                            search
                            selection
                            label = 'Seleziona Corso Da Modificare'
                            onChange = {(e, {name, value}) => {
                                this.setState({ idCorso: value });
                                this.props.selectedCorso(value, this.state.anno, this.state.cdl);
                            }}
                            name = 'corso'
                            options = {corsi}
                            placeholder = 'Seleziona Corso...'
                            closeOnBlur
                            required
                            value = {this.state.idCorso}
                            disabled = {!this.state.anno || !this.state.cdl}
                            error = {this.props.formError && !this.state.idCorso}
                        />
                        <Form.Field width = {4}/>
                    </Form.Group>
                </Fragment>
            );
        }
    }
}