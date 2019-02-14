import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { compareStrings } from '../js/functions';
import filterTeachers from '../js/teachers_filter';

export default class ListaDocenti extends Component {
    constructor() {
        super();
        this.state = {
            docenti: {}
        };
    }

    componentWillMount() {
        var versioneDocenti;
        var localVersioneDocenti = localStorage.getItem('versioneDocenti');
        fetch('http://localhost:8080/AdvancedWeb/rest/teachers')
        .then(res =>  {versioneDocenti = res.headers.get('versione'); return res.json()})
        .then((result) => {
            var docentiList = {};
            if (localVersioneDocenti == null || versioneDocenti !== localVersioneDocenti) {
                localStorage.setItem('versioneDocenti', versioneDocenti);
                let promises = [];
                for (let c in result) {
                    promises.push(fetch([result[c]]));
                }
                let urls = [];
                Promise.all(promises).then(responses => {
                    var jsonPromises = [];
                    for (var res in responses) {
                        urls.push(responses[res].url);
                        jsonPromises.push(responses[res].json());
                    }
                    return Promise.all(jsonPromises);
                }).then((results) => {
                    for (var i = 0; i < results.length; i++) {
                        docentiList[urls[i]] = results[i];
                        localStorage.setItem(urls[i], JSON.stringify(results[i]));
                    }
                    this.setState({docenti: docentiList})
                });
            } else {
                let promises = [];
                for (let c in result) {
                    let corso = JSON.parse(localStorage.getItem(result[c]));
                    if (corso) {
                        docentiList[result[c]] = JSON.parse(localStorage.getItem(result[c]));
                    } else {
                        promises.push(fetch([result[c]]));
                    }
                }
                let urls = [];
                Promise.all(promises).then(responses => {
                    var jsonPromises = [];
                    for (var res in responses) {
                        urls.push(responses[res].url);
                        jsonPromises.push(responses[res].json());
                    }
                    return Promise.all(jsonPromises);
                }).then((results) => {
                    for (var i = 0; i < results.length; i++) {
                        docentiList[urls[i]] = results[i];
                        localStorage.setItem(urls[i], JSON.stringify(results[i]));
                    }
                    this.setState({
                        docenti: docentiList,
                    });
                });
            }
        });
    }

    keyUpFunction() {
        filterTeachers();
    }
    
    getDocenteSpace(docente) {
        return (
            <div key = {docente.idDocente} className="col-md-6 col-sm-6">
                <div className="box_style_3">
                    <p><img id="img-docente" src={'data:image;base64,' + docente.immagine} alt="Teacher" className="img-circle styled"/></p>
                    <h4 className="p-title"> {docente.nome} {docente.cognome} <br/> <small>{docente.specializzazione}</small></h4>
                    <p></p>
                    <ul className="social_team">
                        <li><a href="#"><i className="icon-facebook"></i></a></li>
                        <li><a href="#"><i className="icon-twitter"></i></a></li>
                        <li><a href="#"><i className=" icon-google"></i></a></li>
                </ul>    
                <hr/>
                    <Link to = {'/Teachers/' + docente.idDocente} className="button_medium_outline">{this.props.lingua === 'it' ? 'Profilo' : 'Profile'}</Link>           
                </div>
            </div>
        );
    }

    render() {
        var lingua = this.props.lingua;
        var docentiList = this.state.docenti;
        var docentiKeys = Object.keys(docentiList).sort((a, b) => {return compareStrings(docentiList[a].nome + ' ' + docentiList[a].cognome, docentiList[b].nome + ' ' + docentiList[b].cognome);});
        var docenti = [];
        for (let key in docentiKeys) {
            docenti.push(this.getDocenteSpace(docentiList[docentiKeys[key]]));
        }

        return (
            <React.Fragment>
                <section id="sub-header">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-10 col-md-offset-1 text-center">
                                <h1>{lingua === 'it' ? 'Insegnanti' : 'Teachers'}</h1>
                                <p className="lead boxed ">{lingua === 'it' ? 'Esplora i nostri magnifici insegnanti' : 'Explore our amazing teachers'}</p>
                                <p className="lead"></p>
                            </div>
                        </div>
                    </div>
                    <div className="divider_top"></div>
                </section>

                <section id="main_content">
                    <div className="container">
                        <ol className="breadcrumb">
                            <li><Link to = '/Home'>Home</Link></li>
                            <li className="active">{lingua === 'it' ? 'Lista Docenti' : 'Teachers List'}</li>
                        </ol>
                        <div className="divider"></div>
                        <div className="row">
                            <div className="col-md-12">
                                <input onKeyUp = {() => this.keyUpFunction()} id="ricerca" type="text" name="search" placeholder={lingua === 'it' ? 'Cerca...' : 'Search...'}/> 
                            </div> 
                        </div>
                        <div id="table" className="row">
                            {docenti}
                        </div>     
                    </div>
                </section>
            </React.Fragment>
        );
    }
}