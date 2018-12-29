import React, { Component } from 'react';

export default class DettagliCorso extends Component {
    constructor() {
        super();
        this.state = {
            corso: {},
            infoBase: {},
            sillabo: {},
            insegnanti: [],
            relazioni: {},
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
            this.setState({relazioni: result});
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
        var infoBase = this.state.infoBase;
        var descrizione = this.state.corso.descrizioneIt;
        if (descrizione === undefined)
        {
            descrizione = {};
        }
        var propedeudici = [];
        var mutuati = [];
        var moduli = [];
        var mutuato = <span>Mutuato da:</span>;
        var cdlList = [];
        return (
            <section id = "sub-header">
                <div className="container">
                    <div className="row">
                        <div className="col-md-12 text-center">
                            <h1>Dettagli Del Corso</h1>
                            <p className="lead"></p>
                        </div>
                    </div>
                    <div className="row" id="sub-header-features-2">
                        <div className="col-md-4">
                            <h2>{this.props.lingua === "it" ? 'Informazioni Generali' : 'Generics'}</h2>
                            <ul className="list_ok">
                                <li><strong>SSD:</strong> &nbsp;{infoBase.ssd}</li>
                                <li><strong>CDL:</strong>&nbsp;{cdlList}</li>
                                <li><strong>Lingua:</strong>&nbsp;{infoBase.lingua}</li>
                                <li><strong>Semestre:</strong>&nbsp;{infoBase.semestre}</li>
                                <li><strong>CFU (e tipo):</strong>&nbsp;  {infoBase.cfu} / {infoBase.tipologia}</li>
                                <li><strong>Anno Accademico:</strong>&nbsp; {infoBase.anno} / {infoBase.anno + 1}</li>
                            </ul>
                        </div>

                        <div className="col-md-4">
                            <h2>Prerequisiti</h2>
                            <span><p>{descrizione.prerequisiti}</p></span>
                        </div>

                        <div className="col-md-4">
                            <h2>Obiettivi</h2>
                            <span><p>{descrizione.obiettivi}</p></span>
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