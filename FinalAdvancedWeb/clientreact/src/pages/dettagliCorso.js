import React, { Component } from 'react';

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
            storiaCorso: []
        }
    }

    componentWillMount() {
        let baseLink = 'http://localhost:8080/AdvancedWeb/rest/courses/';
        let corsoLink = baseLink + this.props.year + '/' + this.props.id;
        let infoBaseLink = baseLink + this.props.year + '/' + this.props.id + '/basic';
        let sillaboLink = baseLink + this.props.year + '/' + this.props.id + '/syllabus';
        let insegnantiLink = baseLink + this.props.year + '/' + this.props.id + '/teachers';
        let relazioniLink = baseLink + this.props.year + '/' + this.props.id + '/relations';
        let linksLink = baseLink + this.props.year + '/' + this.props.id + '/links';
        let storiaCorsoLink = baseLink + 'history/' + this.props.id;
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
            this.setState({insegnanti: result});
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
            this.setState({storiaCorso: result});
        });
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
        var propedeudici = [];
        var mutuati = [];
        var moduli = [];
        var mutuato = [];
        var cdlList = this.state.corso.cdl;
        var cdlString = null;

        var abbrCdl;
        for (let cdl in cdlList)
        {
            abbrCdl = lingua === 'it' ? cdlList[cdl].abbrIt || cdlList[cdl].abbrEn : cdlList[cdl].abbrEn || cdlList[cdl].abbrIt;
            cdlString = cdlString ? cdlString + ', ' + abbrCdl : abbrCdl;
        }
        return (
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


                                {propedeudici}


                                {mutuati}   
                                
                                {mutuato}
                                
                            
                                {moduli}

                            

                        </div>
                    </div>


                </div>
            </section>
        );
    }
}