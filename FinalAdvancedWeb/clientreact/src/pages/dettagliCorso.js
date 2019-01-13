import React, { Component } from 'react';
import { Link } from 'react-router-dom';

export default class DettagliCorso extends Component {
    constructor() {
        super();
        this.state = {
            corso: {},
            infoBase: {},
            sillabo: {},
            insegnanti: [],
            relazioni: {
                mutuati: [],
                modulo: [],
                propedeudici: [],
                mutua: {}
            },
            links: {},
            storiaCorso: [],
            mustUpdateData: false
        }
    }

    componentWillMount() {
        this.getData();
    }

    getData() {
        let baseLink = 'http://localhost:8080/AdvancedWeb/rest/';
        let corsoLink = baseLink + 'courses/' + this.props.year + '/' + this.props.id;
        let infoBaseLink = baseLink + 'courses/' + this.props.year + '/' + this.props.id + '/basic';
        let sillaboLink = baseLink + 'courses/' + this.props.year + '/' + this.props.id + '/syllabus';
        let insegnantiLink = baseLink + 'courses/' + this.props.year + '/' + this.props.id + '/teachers';
        let relazioniLink = baseLink + 'courses/' + this.props.year + '/' + this.props.id + '/relations';
        let linksLink = baseLink + 'courses/' + this.props.year + '/' + this.props.id + '/links';
        let storiaCorsoLink = baseLink + 'courses/history/' + this.props.id;
        fetch(corsoLink)
        .then(res => res.json())
        .then(result => {
            this.setState({corso: result});
        });
        fetch(infoBaseLink)
        .then(res => res.json())
        .then(result => {
            this.setState({infoBase: result});
        });
        fetch(sillaboLink)
        .then(res => res.json())
        .then(result => {
            this.setState({sillabo: result});
        });
        fetch(insegnantiLink)
        .then(res => res.json())
        .then(result => {
            let teachers = [];
            let teachersPromises = [];
            for (let doc in result) {
                teachersPromises.push(fetch(baseLink + 'teachers/' + result[doc].idDocente));
            }
            Promise.all(teachersPromises).then(responses => {
                var jsonPromises = [];
                for (var res in responses) {
                    jsonPromises.push(responses[res].json());
                }
                return Promise.all(jsonPromises);
            }).then((results) => {
                for (var result in results) {
                    teachers.push(results[result]);
                }
                this.setState({insegnanti: teachers});
            });
        });
        fetch(relazioniLink)
        .then(res => res.json())
        .then(result => {
            let mutuati = [];
            let propedeudici = [];
            let modulo = [];
            var mutuatiPromises = [];
            var propedeudiciPromises = [];
            var moduloPromises = [];

            for (let c in result.mutuati) {
                mutuatiPromises.push(fetch(result.mutuati[c] + "/basic"));
            }
            Promise.all(mutuatiPromises).then(responses => {
                var jsonPromises = [];
                for (var res in responses) {
                    jsonPromises.push(responses[res].json());
                }
                return Promise.all(jsonPromises);
            }).then((results) => {
                for (var result in results) {
                    mutuati.push(results[result]);
                }
                this.setState({
                    relazioni: {
                        mutuati: mutuati
                    }
                });
            });

            for (let c in result.propedeudici) {
                propedeudiciPromises.push(fetch(result.propedeudici[c] + "/basic"));
            }
            Promise.all(propedeudiciPromises).then(responses => {
                var jsonPromises = [];
                for (var res in responses) {
                    jsonPromises.push(responses[res].json());
                }
                return Promise.all(jsonPromises);
            }).then((results) => {
                for (var result in results) {
                    propedeudici.push(results[result]);
                }
                this.setState({
                    relazioni: {
                        propedeudici: propedeudici
                    }
                });
            });

            for (let c in result.modulo) {
                moduloPromises.push(fetch(result.modulo[c] + "/basic"));
            }
            Promise.all(moduloPromises).then(responses => {
                var jsonPromises = [];
                for (var res in responses) {
                    jsonPromises.push(responses[res].json());
                }
                return Promise.all(jsonPromises);
            }).then((results) => {
                for (var result in results) {
                    modulo.push(results[result]);
                }
                this.setState({
                    relazioni: {
                        modulo: modulo
                    }
                });
            });

            if (result.mutua !== null)
            {
                fetch(result.mutua + "/basic")
                .then(res => res.json())
                .then(result => {
                    this.setState({
                        relazioni: {
                            mutua: result
                        }
                    });
                });
            }
        });
        fetch(linksLink)
        .then(res => res.json())
        .then(result => {
            this.setState({links: result});
        });
        fetch(storiaCorsoLink)
        .then(res => res.json())
        .then(result => {
            var storiaPromises = [];
            var storia = [];
            for (var corso in result) {
                storiaPromises.push(fetch(result[corso].url + '/basic'));
            }
            Promise.all(storiaPromises).then(responses => {
                var jsonPromises = [];
                for (var res in responses) {
                    jsonPromises.push(responses[res].json());
                }
                return Promise.all(jsonPromises);
            }).then((results) => {
                for (var result in results) {
                    if (results[result].anno !== this.state.infoBase.anno) {
                        storia.push(results[result]);
                    }
                }
                this.setState({
                    storiaCorso: storia.sort((a, b) => {return (a.anno > b.anno) ? -1 : (a.anno < b.anno) ? 1 : 0;})
                });
            });
        });
    }

    componentDidUpdate() {
        if (this.state.mustUpdateData) {
            this.setState({mustUpdateData: false});
            this.getData();
        }
    }

    componentWillReceiveProps(nextProps) {
        if (this.props !== nextProps) {
            this.setState({mustUpdateData: true});
        }
    }

    getRelationName(corso) {
        return <strong><Link to = {'/Courses/' + corso.anno + '/' + corso.idCorso} className = "dc">{this.props.lingua === 'it' ? corso.nomeIt || corso.nomeEn : corso.nomeEn || corso.nomeIt}</Link></strong>
    }

    getCorsoPrecedente(corso) {
        return <li key = {corso.anno}><Link to = {'/Courses/' + corso.anno + '/' + corso.idCorso}>{corso.anno === this.state.storiaCorso[0].anno && corso.anno > this.state.infoBase.anno ? this.props.lingua === 'it' ? 'Informazioni Correnti' : 'Current Information' : corso.anno + '/' + (corso.anno + 1)}</Link></li>
    }

    getDocente(docente) {
        return (
        <div key = {docente.idDocente} className = 'media'>
            <div className = 'pull-right'>
                <img id="img-piccola" src={'/' + docente.immagine} className="img-circle" alt=""/>
            </div>
            <div className = 'media-body'>
                <h5 className = 'media-heading'><Link to = {'/Teachers/' + docente.idDocente}>{docente.nome} {docente.cognome}</Link></h5>
                <p>{docente.specializzazione}</p>
            </div>
        </div>
        );
    }

    getLibro(libro) {
        if (libro.link) {
            return <li><a href = {libro.link}>{libro.titolo}</a>, {libro.volume > 0 ? libro.volume + ',' : null} {libro.anno}, {libro.autore}, {libro.editore}</li>
        }
        return <li>{libro.volume > 0 ? libro.volume + ',' : null} {libro.anno}, {libro.autore}, {libro.editore}</li>
    }

    render() {
        var lingua = this.props.lingua;
        var infoBase = this.state.infoBase;
        var descrizioneIt = this.state.corso.descrizioneIt;
        if (descrizioneIt === undefined)
        {
            descrizioneIt = {};
        }
        var descrizioneEn = this.state.corso.descrizioneEn;
        if (descrizioneEn === undefined)
        {
            descrizioneEn = {};
        }
        var dublinoIt = this.state.corso.dublinoIt;
        if (dublinoIt === undefined) {
            dublinoIt = {};
        }
        var dublinoEn = this.state.corso.dublinoEn;
        if (dublinoEn === undefined) {
            dublinoEn = {};
        }
        var propedeudiciList = this.state.relazioni.propedeudici;
        var mutuatiList = this.state.relazioni.mutuati;
        var moduliList = this.state.relazioni.modulo;
        var mutuato = this.state.relazioni.mutua;
        var cdlList = this.state.corso.cdl;
        var docentiList = this.state.insegnanti;
        var cdlString = null;
        var mutuati = [];
        var propedeudici = [];
        var modulo = [];
        var precedentiList = this.state.storiaCorso;
        var storia = [];
        var docenti = [];
        var libriList = this.state.corso.libri;
        var libri = [];
        var links = this.state.links;

        var abbrCdl;
        for (let cdl in cdlList) {
            abbrCdl = lingua === 'it' ? cdlList[cdl].abbrIt || cdlList[cdl].abbrEn : cdlList[cdl].abbrEn || cdlList[cdl].abbrIt;
            cdlString = cdlString ? cdlString + ', ' + abbrCdl : abbrCdl;
        }

        for (let corso in mutuatiList) {
            mutuati.push(<React.Fragment key = {mutuatiList[corso].idCorso}>{this.getRelationName(mutuatiList[corso])}{(corso + 1) < mutuatiList.length ? ', ' : ""}</React.Fragment>);
        }

        for (let corso in propedeudiciList) {
            propedeudici.push(<React.Fragment>{this.getRelationName(propedeudiciList[corso])}{(corso + 1) < propedeudiciList.length ? ', ' : ""}</React.Fragment>);
        }
        
        for (let corso in moduliList) {
            modulo.push(<React.Fragment>{this.getRelationName(moduliList[corso])}{(corso + 1) < moduliList.length ? ', ' : ""}</React.Fragment>);
        }

        for (let corso in precedentiList) {
            storia.push(this.getCorsoPrecedente(precedentiList[corso]));
        }

        for (let docente in docentiList) {
            docenti.push(this.getDocente(docentiList[docente]));
        }

        for (let libro in libriList) {
            libri.push(this.getLibro(libriList[libro]));
        }

        return (
            <React.Fragment>
            <section id = "sub-header">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12 text-center">
                            <h1>{lingua === "it" ? "Dettagli Del Corso" : "Course Details"}</h1>
                            <p className="lead"></p>
                        </div>
                    </div>
                    <div className="row" id="sub-header-features-2">
                        <div className="col-md-4">
                            <h2>{lingua === "it" ? 'Informazioni Generali' : 'General Informations'}</h2>
                            <ul className="list_ok">
                                <li><strong>{lingua === 'it' ? 'Nome' : 'Name'}:</strong>&nbsp;{lingua === 'it' ? infoBase.nomeIt || infoBase.nomeEn : infoBase.nomeEn || infoBase.nomeIt}</li>
                                <li><strong>SSD:</strong> &nbsp;{infoBase.ssd}</li>
                                <li><strong>CDL:</strong>&nbsp;{cdlString}</li>
                                <li><strong>{lingua === "it" ? 'Lingua' : 'Language'}:</strong>&nbsp;{infoBase.lingua}</li>
                                <li><strong>{lingua === "it" ? 'Semestre' : 'Semester'}:</strong>&nbsp;{infoBase.semestre}</li>
                                <li><strong>CFU ({lingua === "it" ? 'e tipo' : 'and type'}):</strong>&nbsp;  {infoBase.cfu} / {infoBase.tipologia}</li>
                                <li><strong>{lingua === "it" ? 'Anno Accademico' : 'Accademic Year'}:</strong>&nbsp; {infoBase.anno} / {infoBase.anno + 1}</li>
                            </ul>
                        </div>

                        <div className="col-md-4">
                            <h2>{lingua === "it" ? 'Prerequisiti' : 'Prerequisites'}</h2>
                            <span dangerouslySetInnerHTML = {{
                                __html: lingua === 'it' ? descrizioneIt.prerequisiti || descrizioneEn.prerequisiti : descrizioneEn.prerequisiti || descrizioneIt.prerequisiti
                                }}/>
                        </div>

                        <div className="col-md-4">
                            <h2>{lingua === "it" ? 'Obiettivi' : 'Objectives'}</h2>
                            <span dangerouslySetInnerHTML = {{
                                __html: lingua === 'it' ? descrizioneIt.obiettivi || descrizioneEn.obiettivi : descrizioneEn.obiettivi || descrizioneIt.obiettivi
                                }}/>
                        </div>

                            
                    </div>

                    <div id="divider"></div>
                    <div className="row">
                        <div className="col-md-12" id="tipocorso">

                                {propedeudici.length > 0 ? <li><strong>Corsi Propedeudici: </strong> {propedeudici} </li> : null}

                                {mutuati.length > 0 ? <li><strong>Corsi Mutuati: </strong> {mutuati} </li> : null}   
                                
                                {mutuato ? <li><strong>Mutuato da: </strong> {this.getRelationName(mutuato)} </li> : null}
                            
                                {modulo.length > 0 ? <li><strong>Corsi Modulo: </strong> {modulo} </li> : null}

                        </div>
                    </div>
                </div>
                <div className = 'divider_top'/>
            </section>

            <section id = 'main_content'>
                <div className = 'container'>
                    <ol className="breadcrumb">
                        <li><Link to = '/Home'>Home</Link></li>
                        <li><Link to = '/Courses'>Lista Corsi</Link></li>
                        <li className="active">{lingua === 'it' ? infoBase.nomeIt || infoBase.nomeEn : infoBase.nomeEn || infoBase.nomeIt}</li>
                    </ol>
                    <div className = 'row'>
                        <div className = 'col-md-8'>
                        <section id="space">
                            <h3>{lingua === 'it' ? 'Sillabo' : 'Syllabus'}</h3>
                                <div dangerouslySetInnerHTML = {{
                                __html: lingua === 'it' ? descrizioneIt.sillabo || descrizioneEn.sillabo : descrizioneEn.sillabo || descrizioneIt.sillabo
                                }}></div>
                        </section>

                        <h3>{lingua === 'it' ? 'Descrittori di Dublino' : 'Dublin Descriptors'}</h3>
                            <main className="main" id="space">

                                <input className="input" id="tab1" type="radio" name="tabs" defaultChecked/>
                                <label htmlFor="tab1">Knowledge</label>
                                
                                <input className="input" id="tab2" type="radio" name="tabs"/>
                                <label htmlFor="tab2">Application</label>
                                
                                <input className="input" id="tab3" type="radio" name="tabs"/>
                                <label htmlFor="tab3">Evaluation</label>
                                
                                <input className="input" id="tab4" type="radio" name="tabs"/>
                                <label htmlFor="tab4">Communication</label>
                                
                                <input className="input" id="tab5" type="radio" name="tabs"/>
                                <label htmlFor="tab5">Lifelong</label>
                                

                                <section className="section" id="content1" dangerouslySetInnerHTML = {{
                                __html: lingua === 'it' ? dublinoIt.knowledge || dublinoEn.knowledge : dublinoEn.knowledge || dublinoIt.knowledge
                                }}/>
                                
                                <section className="section" id="content2" dangerouslySetInnerHTML = {{
                                    __html: lingua === 'it' ? dublinoIt.application || dublinoEn.application : dublinoEn.application || dublinoIt.application
                                }}/>
                                
                                <section className="section" id="content3" dangerouslySetInnerHTML = {{
                                    __html: lingua === 'it' ? dublinoIt.evaluation || dublinoEn.evaluation : dublinoEn.evaluation || dublinoIt.evaluation
                                }}/>
                                
                                <section className="section" id="content4" dangerouslySetInnerHTML = {{
                                    __html: lingua === 'it' ? dublinoIt.communication || dublinoEn.communication : dublinoEn.communication || dublinoIt.communication
                                }}/>
                                
                                <section className="section" id="content5" dangerouslySetInnerHTML = {{
                                    __html: lingua === 'it' ? dublinoIt.lifelong || dublinoEn.lifelong : dublinoEn.lifelong || dublinoIt.lifelong
                                }}/>
                            </main>

                            <section className = 'space'>
                                <h3>{lingua === 'it' ? "Modalità d'Esame" : "Examination Procedure"}</h3>
                                <div dangerouslySetInnerHTML = {{
                                __html: lingua === 'it' ? descrizioneIt.modEsame || descrizioneEn.modEsame : descrizioneEn.modEsame || descrizioneIt.modEsame
                                }}></div>
                            </section>
                                
                            <section className = 'space'>
                                <h3>{lingua === 'it' ? "Modalità d'Insegnmento" : "Teaching Methods"}</h3>
                                <div dangerouslySetInnerHTML = {{
                                __html: lingua === 'it' ? descrizioneIt.modInsegnamento || descrizioneEn.modInsegnamento : descrizioneEn.modInsegnamento || descrizioneIt.modInsegnamento
                                }}></div>
                            </section>
                            
                            <section className = 'space'>
                                <h3>{lingua === 'it' ? "Note" : "Notes"}</h3>
                                <div dangerouslySetInnerHTML = {{
                                __html: lingua === 'it' ? descrizioneIt.note || descrizioneEn.note : descrizioneEn.note || descrizioneIt.note
                                }}></div>
                            </section>

                            {storia.length > 0 ? 
                            <section id = 'space'>
                                <h3>{lingua === 'it' ? 'Versioni Pagina Corso' : 'Course Page Updates'}</h3>
                                {storia}
                            </section> : null}

                        </div>
                        
                        <aside className = 'col-md-4'>
                            <div className="box_style_1">
                                <h4><Link to = {'/Courses/' + infoBase.anno + '/' + infoBase.idCorso + '/Material'}>{lingua === 'it' ? 'Materiale' : 'Material'}</Link></h4><br/>
                                <h4>{lingua === 'it' ? 'Insegnanti' : 'Teachers'}</h4>
                                    {docenti}
                            </div>

                            <div className="box_style_1">
                                <h4>{lingua === 'it' ? 'Libri di Testo' : 'Books'}</h4>
                                <ul className="legend_course">
                                    {libri}
                                </ul>
                            </div>

                            {
                                links.homepage || links.forum || links.risorseExt || links.elearning 
                                ? 
                                <div class="box_style_1">
                                    <h4>{lingua === 'it' ? 'Link Esterni' : 'External Links'}</h4>
                                    <ul class="list_1">
                                        {links.homepage ? <li><a href = {links.homepage}>Homepage</a></li> : null}
                                        {links.elearning ? <li><a href = {links.elearning}>E-Learning</a></li> : null}
                                        {links.forum ? <li><a href = {links.forum}>Forum</a></li> : null}
                                        {links.risorseExt ? <li><a href = {links.risorseExt}>{lingua === 'it' ? 'Risorse Esterne' : 'External Resources'}</a></li> : null}
                                    </ul>
                                </div>
                                :
                                null
                            }

                        </aside>
    
                    </div>
                </div>
            </section>
            </React.Fragment>
        );
    }
}