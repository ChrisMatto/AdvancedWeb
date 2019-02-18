import React, { Component, Fragment } from 'react';
import { Switch, Route, Redirect, Link } from 'react-router-dom';
import CreateCorso from './backoffice/createCorso';
import UpdateCorso from './backoffice/updateCorso';
import DeleteCorso from './backoffice/deleteCorso';
import MostraUtenti from './backoffice/mostraUtenti';
import RegistraAmministratore from './backoffice/registraAmministratore';
import RegistraDocente from './backoffice/registraDocente';
import UpdateProfilo from './backoffice/profile';
import UpdateDocente from './backoffice/updateDocente';
import Logs from './backoffice/logs';
import CreateCdl from './backoffice/createCdl';
import UpdateCdl from './backoffice/updateCdl';
import AddLibro from './backoffice/addLibro';
import ManageLibri from './backoffice/manageLibri';

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

                    <h4>Funzioni Utenti</h4>
                    <ul>
                        <li><Link to = '/Backoffice/Users'>Mostra Utenti</Link></li>
                    </ul>

                    <h4>Funzioni Creazione</h4>
                    <ul>
                        <li><Link to = '/Backoffice/CreateCdl'>Crea Un Nuovo Cdl</Link></li>
                        <li><Link to = '/Backoffice/CreateCourse'>Crea Un Nuovo Corso</Link></li>
                        <li><Link to = '/Backoffice/RegisterTeacher'>Registra Un Nuovo Docente</Link></li>
                        <li><Link to = '/Backoffice/RegisterAdmin'>Registra Un Nuovo Amministratore</Link></li>
                        <li><Link to = '/Backoffice/NewMateriale'>Aggiungi Materiale</Link></li>
                        <li><Link to = '/Backoffice/AddBook'>Aggiungi Libro</Link></li>
                    </ul>

                    <h4>Funzioni Modifica</h4>
                    <ul>
                        <li><Link to = '/Backoffice/UpdateCourse'>Modifica Corsi</Link></li>
                        <li><Link to = '/Backoffice/UpdateCdl'>Modifica Cdl</Link></li>
                        <li><Link to = '/Backoffice/UpdateTeacher'>Modifica Docente</Link></li>
                    </ul>

                    <h4>Funzioni Eliminazione</h4>
                    <ul>
                        <li><Link to = '/Backoffice/DeleteCourse'>Elimina Un Corso</Link></li>
                        <li><Link to = '/Backoffice/DeleteCdl'>Elimina Un Cdl</Link></li>
                    </ul>

                    <h4>Funzioni Upload</h4>
                    <ul>
                        <li><Link to = '/Backoffice/ManageMateriale'>Materiale Gestione</Link></li>
                        <li><Link to = '/Backoffice/ManageBooks'>Gestione Libri</Link></li>
                    </ul>

                </div>
            </aside>
        );
    }

    TeachersMenu = () => {
        return (
            <aside className="col-md-4">
                <div className="box_style_1 profile">
                    <h4>Funzioni Modifica</h4>
                    <ul>
                        <li><Link to = '/Backoffice/UpdateTeacher'>Modifica Profilo Docente</Link></li>
                    {
                        this.state.corsi.length > 0 ?
                            <li><Link to = '/Backoffice/UpdateCourse'>Modifica Un Corso</Link></li>
                        :
                            null
                    }
                    </ul>
                    

                    <h4>Aggiunta Documenti</h4>
                    <ul>
                        <li><Link to = '/Backoffice/NewMateriale'>Aggiungi Materiale</Link></li>
                        <li><Link to = '/Backoffice/AddBook'>Aggiungi Libro</Link></li>
                    </ul>

                    <h4>Gestione Documenti</h4>
                    <ul>
                        <li><Link to = '/Backoffice/ManageMateriale'>Materiale Gestione</Link></li>
                        <li><Link to = '/Backoffice/ManageBooks'>Gestione Libri</Link></li>
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
                                <Route exact path = '/Backoffice/DeleteCourse' render = {() => !this.state.utente.docente ? <DeleteCorso utente = {this.state.utente} token = {this.state.token}/> : <Redirect to = '/Backoffice'/>}/>
                                <Route exact path = '/Backoffice/Users' render = {() => !this.state.utente.docente ? <MostraUtenti utente = {this.state.utente} token = {this.state.token}/> : <Redirect to = '/Backoffice'/>}/>
                                <Route exact path = '/Backoffice/RegisterAdmin' render = {() => !this.state.utente.docente ? <RegistraAmministratore utente = {this.state.utente} token = {this.state.token}/> : <Redirect to = '/Backoffice'/>}/>
                                <Route exact path = '/Backoffice/RegisterTeacher' render = {() => !this.state.utente.docente ? <RegistraDocente utente = {this.state.utente} token = {this.state.token}/> : <Redirect to = '/Backoffice'/>}/>
                                <Route exact path = '/Backoffice/Profile' render = {() => <UpdateProfilo utente = {this.state.utente} token = {this.state.token}/>}/>
                                <Route exact path = '/Backoffice/UpdateTeacher' render = {() => <UpdateDocente utente = {this.state.utente} token = {this.state.token}/>}/>
                                <Route exact path = '/Backoffice/Logs' render = {() => !this.state.utente.docente ? <Logs utente = {this.state.utente} token = {this.state.token}/> : <Redirect to = '/Backoffice'/>}/>
                                <Route exact path = '/Backoffice/CreateCdl' render = {() => !this.state.utente.docente ? <CreateCdl utente = {this.state.utente} token = {this.state.token}/> : <Redirect to = '/Backoffice'/>}/>
                                <Route exact path = '/Backoffice/UpdateCdl' render = {() => !this.state.utente.docente ? <UpdateCdl utente = {this.state.utente} token = {this.state.token}/> : <Redirect to = '/Backoffice'/>}/>
                                <Route exact path = '/Backoffice/DeleteCdl' render = {() => !this.state.utente.docente ? <UpdateCdl isDelete = {true} utente = {this.state.utente} token = {this.state.token}/> : <Redirect to = '/Backoffice'/>}/>
                                <Route exact path = '/Backoffice/AddBook' render = {() => <AddLibro utente = {this.state.utente} token = {this.state.token}/>}/>
                                <Route exact path = '/Backoffice/ManageBooks' render = {() => <ManageLibri utente = {this.state.utente} token = {this.state.token}/>}/>
                            </Switch>
                            </div>
                        </div>
                    </section>                    

                </Fragment>
            );
        }
    }
}
