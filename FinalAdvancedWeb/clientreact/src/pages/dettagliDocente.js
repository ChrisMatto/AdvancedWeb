import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import compareStrings from '../js/functions';

export default class DettagliDocente extends Component {
    constructor() {
        super();
        this.state = {
            docente: {}
        };
    }

    componentWillMount() {
        var link = 'http://localhost:8080/AdvancedWeb/rest/teachers/' + this.props.id;
        var docente = localStorage.getItem(link);
        if (docente) {
            this.setState({docente: JSON.parse(docente)});
        } else {
            fetch(link)
            .then(res => res.json())
            .then(result => {
                this.setState({docente: result});
            });
        }
    }

    render() {
        var lingua = this.props.lingua;
        var docente = this.state.docente;
        var sortCorsiFunc = lingua === 'it' ? (a, b) => {return compareStrings(a.nomeIt, b.nomeIt);} : (a, b) => {return compareStrings(a.nomeEn, b.nomeEn);};
        var corsi = this.state.docente.corsi;
        if (corsi) {
            corsi.sort(sortCorsiFunc);
        }
        return (
            <React.Fragment>
                <section id="sub-header">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-10 col-md-offset-1 text-center">
                                <h1>{lingua === 'it' ? 'Insegnanti' : 'Teachers'}</h1>
                                <p className="lead"></p>
                            </div>
                        </div>
                    </div>
                    <div className="divider_top"></div>
                </section>

                <section id = 'main_content'>
                    <div className = 'container'>
                        <ol className="breadcrumb">
                            <li><Link to = '/Home'>Home</Link></li>
                            <li><Link to = '/Teachers'>{lingua === 'it' ? 'Lista Docenti' : 'Teachers List'}</Link></li>
                            <li className="active">{docente.nome} {docente.cognome}</li>
                        </ol>
                        <div className = 'row'>
                            <aside className="col-md-4">
                                <div className="box_style_1 profile">
                                <p className="text-center"><img id="img-docente" src={'/' + docente.immagine} alt="Teacher" className="img-circle styled"/></p> 
                                        <ul>
                                            <li>{lingua === 'it' ? 'Nome' : 'Name'}<strong className="pull-right">{docente.nome} {docente.cognome}</strong> </li>
                                            <li>{lingua === 'it' ? 'Specializzazione' : 'Specialization'}<strong className="pull-right">{docente.specializzazione}</strong></li>
                                            <li>Email<a href={"mailto:" + docente.email} target="top"> <strong className="pull-right">{docente.email}</strong></a></li>
                                            <li>{lingua === 'it' ? 'Ufficio' : 'Office'}<strong className="pull-right">{docente.ufficio}</strong></li>
                                            <li>{lingua === 'it' ? 'Telefono' : 'Telephone'}<strong className="pull-right">{docente.telefono}</strong></li>
                                            <li>{lingua === 'it' ? 'Ricevimento' : 'Receipt'}<strong className="pull-right">{docente.ricevimento}</strong></li>
                                            <li>Curriculum<strong className="pull-right"><a href = {"http://localhost:8080/AdvancedWeb/rest/teachers/" + docente.idDocente + "/curriculum"} target="_self" download><i className="fa fa-arrow-circle-down" aria-hidden="true"></i></a></strong></li>
                                        </ul>
                                    </div>
                            </aside>
                            <div className = 'col-md-8'>
                                <main className = 'main'>
                                    
                                    <input className = 'input' id = 'tab6' type = 'radio' name = 'tabs' defaultChecked/>
                                    <label htmlFor = 'tab6'>{lingua === 'it' ? 'Ricerche' : 'Researces'}</label>

                                    <input className = 'input' id = 'tab7' type = 'radio' name = 'tabs'/>
                                    <label htmlFor = 'tab7'>{lingua === 'it' ? 'Pubblicazioni' : 'Publications'}</label>

                                    <input className = 'input' id = 'tab8' type = 'radio' name = 'tabs'/>
                                    <label htmlFor = 'tab8'>{lingua === 'it' ? 'Corsi Attivi' : 'Active Courses'}</label>

                                    <section className = 'section' id = 'content6' dangerouslySetInnerHTML = {{__html: docente.ricerche}}/>

                                    <section className = 'section' id = 'content7' dangerouslySetInnerHTML = {{__html: docente.pubblicazioni}}/>

                                    <section className = 'section' id = 'content8'>
                                        <div className ='tab-pane fade in' id = 'courses'>
                                            <div className = 'table-responsive'>
                                                <table className = 'table table-striped'>
                                                    <thead>
                                                        <tr>
                                                            <th>{lingua === 'it' ? 'Nome' : 'Name'}</th>
                                                            <th>SSD</th>
                                                            <th>CFU</th>
                                                            <th>{lingua === 'it' ? 'Lingua' : 'Language'}</th>
                                                            <th>{lingua === 'it' ? 'Semestre' : 'Semester'}</th>
                                                            <th>CDL</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        {corsi ? corsi.map((corso) => (
                                                            <tr>
                                                                <td><Link to = {"/Courses/" + corso.anno + '/' + corso.idCorso}>{lingua === 'it' ? corso.nomeIt || corso.nomeEn : corso.nomeEn || corso.nomeIt}</Link></td>
                                                                <td>{corso.ssd}</td>
                                                                <td>{corso.cfu}</td>
                                                                <td>{corso.lingua}</td>
                                                                <td>{corso.semestre}</td>
                                                                <td>
                                                                    {corso.cdl.map((cdl, index) => {
                                                                        if (index > 0) {
                                                                            return ", " + lingua === 'it' ? cdl.abbrIt || cdl.abbrEn : cdl.abbrEn || cdl.abbrIt;
                                                                        } else {
                                                                            return lingua === 'it' ? cdl.abbrIt || cdl.abbrEn : cdl.abbrEn || cdl.abbrIt;
                                                                        }
                                                                    })}
                                                                </td>
                                                            </tr>
                                                        )) : null}
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </section>
                                </main>
                            </div>
                        </div>
                    </div>
                </section>
            </React.Fragment>
        );
    }
}