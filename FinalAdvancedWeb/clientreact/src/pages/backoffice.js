import React, { Component, Fragment } from 'react';
import { Switch, Route, Redirect, Link } from 'react-router-dom';
import CreateCorso from './backoffice/createCorso';
import UpdateCorso from './backoffice/updateCorso';

export default class Backoffice extends Component {
    constructor(props) {
        super(props);
        this.state = {
            token: props.token,
            utente: props.utente,
            corsi: [],
            backPage: null
        };
    }

    componentWillMount() {
        if (this.state.token && this.props.utente) {
            if (this.props.utente.docente) {
                fetch('http://localhost:8080/AdvancedWeb/rest/teachers/' + this.state.utente.docente + '/courses')
                .then(res => res.ok ? res.json() : [])
                .then(result => this.setState({ corsi: result }));
            }
        }
    }

    AdminMenu = () => {
        return (
            <aside className="col-md-4">
                <div className="box_style_1 profile">
                    <h4>Funzioni creazione</h4>

                    <ul>
                        <li><Link to = '/Backoffice/CreateCDL'>Crea Un Nuovo Cdl</Link></li>
                        <li><Link to = '/Backoffice/CreateCourse'>Crea Un Nuovo Corso</Link></li>
                        <li><Link to = '/Backoffice/RegisterDocente'>Registra Un Nuovo Docente</Link></li>
                        <li><Link to = '/Backoffice/CreateAdmin'>Registra Un Nuovo Amministratore</Link></li>
                        <li><Link to = '/Backoffice/NewMateriale'>Aggiungi Materiale</Link></li>
                        <li><Link to = '/Backoffice/NewLibro'>Aggiungi Libro</Link></li>
                    </ul>

                    <h4>Funzioni modifica</h4>
                    <ul>
                        <li><Link to = '/Backoffice/UpdateCourse'>Modifica Corsi</Link></li>
                        <li><Link to = '/Backoffice/UpdateCDL'>Modifica Cdl</Link></li>
                        <li><Link to = '/Backoffice/UpdateDocente'>Modifica Docente</Link></li>
                    </ul>

                    <h4>Funzioni Upload</h4>
                    <ul>
                        <li><Link to = '/Backoffice/ManageMateriale'>Materiale Gestione</Link></li>
                        <li><Link to = '/Backoffice/ManageLibri'>Libri Gestione</Link></li>
                    </ul>

                </div>
            </aside>
        );
    }

    TeachersMenu = () => {
        return (
            <aside className="col-md-4">
                <div className="box_style_1 profile">
                    {
                        this.state.corsi.length > 0 ?
                            <Fragment>
                                <h4>Funzioni Modifica</h4>
                                <ul>
                                    <li><Link to = '/Backoffice/UpdateCourse'>Modifica Un Corso</Link></li>
                                </ul>
                            </Fragment>
                        :
                            null
                    }
                    

                    <h4>Aggiunta Documenti</h4>
                    <ul>
                        <li><Link to = '/Backoffice/NewMateriale'>Aggiungi Materiale</Link></li>
                        <li><Link to = '/Backoffice/NewLibro'>Aggiungi Libro</Link></li>
                    </ul>

                    <h4>Gestione Documenti</h4>
                    <ul>
                        <li><Link to = '/Backoffice/ManageMateriale'>Materiale Gestione</Link></li>
                        <li><Link to = '/Backoffice/ManageLibri'>Libri Gestione</Link></li>
                    </ul>
                </div>
            </aside>
        );
    }

    render() {
        if (!this.state.token || !this.state.utente) {
            return <Redirect to = '/Login'/>
        } else {
            return (
                <Fragment>
                    <section id="sub-header">
                        <div className="container">
                            <div className="row">
                                <div className="col-md-10 col-md-offset-1 text-center">
                                    <h1>Backoffice</h1>
                                    <p className="lead"></p>
                                </div>
                            </div>
                        </div>
                        <div className="divider_top"></div>
                    </section>
                    
                    <section id="main_content">
                        <div className="container" style = {{ padding: 0 }}>
                            <div className = 'row'>
                            {
                                this.state.utente.docente ?
                                    <this.TeachersMenu/>
                                :
                                    <this.AdminMenu/>
                            }
                            <Switch>
                                <Route exact path = '/Backoffice/CreateCourse' render = {() => !this.state.utente.docente ? <CreateCorso utente = {this.state.utente} token = {this.state.token}/> : <Redirect to = '/Backoffice'/>}/>          
                                <Route exact path = '/Backoffice/UpdateCourse' render = {() => <UpdateCorso utente = {this.state.utente} token = {this.state.token}/>}/>                      
                            </Switch>
                            </div>
                        </div>
                    </section>                    

                </Fragment>
            );
        }
    }
}
