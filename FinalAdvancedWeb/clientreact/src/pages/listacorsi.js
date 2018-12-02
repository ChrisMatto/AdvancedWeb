import React from 'react';
import activeFilters from '../js/table_filter';
import { Link } from 'react-router-dom';
import compareStrings from '../js/functions';

class ListaCorsi extends React.Component {
    constructor() {
        super();
        this.state = {
            cdl: [],
            cdlm: [],
            anniCorsi: [],
            corsi: {},
        };
    }

    componentWillMount() {
        var versioneCdl;
        var localVersioneCdl = localStorage.getItem('versioneCdl');
        fetch('http://localhost:8080/AdvancedWeb/rest/cdl/triennale')
            .then(res =>  {versioneCdl = res.headers.get('versione'); return res.json()})
            .then((result) => {
                if (localVersioneCdl != null && versioneCdl === localVersioneCdl && localStorage.getItem('cdlList') != null) {
                    this.setState({
                        cdl: JSON.parse(localStorage.getItem('cdlList')),
                    });
                } else {
                    var cdlList = [];
                    localStorage.setItem('versioneCdl', versioneCdl);
                    var promises = [];
                    for (var c in result) {
                        promises.push(fetch(result[c]));
                    }
                    Promise.all(promises).then(responses => {
                        var jsonPromises = [];
                        for (var res in responses) {
                            jsonPromises.push(responses[res].json());
                        }
                        return Promise.all(jsonPromises);
                    }).then((results) => {
                        for (var result in results) {
                            cdlList.push(results[result]);
                        }
                        localStorage.setItem('cdlList', JSON.stringify(cdlList));
                        this.setState({
                            cdl: cdlList,
                        });
                    });
                }
            });
            fetch('http://localhost:8080/AdvancedWeb/rest/cdl/magistrale')
            .then(res =>  {versioneCdl = res.headers.get('versione'); return res.json()})
            .then((result) => {
                if (localVersioneCdl != null && versioneCdl === localVersioneCdl && localStorage.getItem('cdlmList') != null) {
                    this.setState({
                        cdlm: JSON.parse(localStorage.getItem('cdlmList')),
                    });
                } else {
                    var cdlmList = [];
                    localStorage.setItem('versioneCdl', versioneCdl);
                    var promises = [];
                    for (var c in result) {
                        promises.push(fetch(result[c]));
                    }
                    Promise.all(promises).then(responses => {
                        var jsonPromises = [];
                        for (var res in responses) {
                            jsonPromises.push(responses[res].json());
                        }
                        return Promise.all(jsonPromises);
                    }).then((results) => {
                        for (var result in results) {
                            cdlmList.push(results[result]);
                        }
                        localStorage.setItem('cdlmList', JSON.stringify(cdlmList));
                        this.setState({
                            cdlm: cdlmList,
                        });
                    });
                }
            });
        
        var versioneCorsi;
        var localVersioneCorsi = localStorage.getItem('versioneCorsi');
        fetch('http://localhost:8080/AdvancedWeb/rest/courses/current')
        .then(res => {versioneCorsi = res.headers.get('versione'); return res.json()})
        .then((result) => {
            var corsiList = {};
            if (localVersioneCorsi == null || versioneCorsi !== localVersioneCorsi) {
                localStorage.setItem('versioneCorsi', versioneCorsi);
                var promises = [];
                for (var c in result) {
                    promises.push(fetch([result[c]]));
                }
                var urls = [];
                Promise.all(promises).then(responses => {
                    var jsonPromises = [];
                    for (var res in responses) {
                        urls.push(responses[res].url);
                        jsonPromises.push(responses[res].json());
                    }
                    return Promise.all(jsonPromises);
                }).then((results) => {
                    for (var i = 0; i < results.length; i++) {
                        corsiList[urls[i]] = results[i];
                        localStorage.setItem(urls[i], JSON.stringify(results[i]));
                    }
                    this.setState({
                        corsi: corsiList,
                    });
                });
                fetch('http://localhost:8080/AdvancedWeb/rest/courses/years')
                    .then(res => res.json())
                    .then((result) => {
                        localStorage.setItem('anniCorsi', JSON.stringify(result));
                        this.setState({
                            anniCorsi: result,
                        });
                    });
            } else {
                for (var corso in result) {
                    corsiList[result[corso]] = JSON.parse(localStorage.getItem(result[corso]));
                }
                this.setState({
                    corsi: corsiList,
                    anniCorsi: JSON.parse(localStorage.getItem("anniCorsi")),
                });
            }
        });
    }

    componentDidMount() {
        activeFilters();
    }

    render() {
        return (
            <React.Fragment>
            <Banner lingua = {this.props.lingua}/>
            <BodySection lingua = {this.props.lingua} cdl = {this.state.cdl} cdlm = {this.state.cdlm} anniCorsi = {this.state.anniCorsi} corsi = {this.state.corsi}/>
            </React.Fragment>
        );
    }
}

function Banner(props) {
    if (props.lingua === "it") {
        return (
            <section id = "sub-header">
                <div className = "container">
                    <div className = "row">
                        <div className = "col-md-10 col-md-offset-1 text-center">
                            <h1>Lista dei corsi</h1>
                            <p className = "lead boxed ">Esplora la lista dei nostri fantastici corsi</p>
                            <p className = "lead"/> 
                        </div>
                    </div>
                </div>
                <div className = "divider_top"/>
            </section>
        );
    } else {
        return (
            <section id = "sub-header">
                <div className = "container">
                    <div className = "row">
                        <div className = "col-md-10 col-md-offset-1 text-center">
                            <h1>Courses List</h1>
                            <p className = "lead boxed ">Explore ours amazing courses</p>
                            <p className = "lead"/> 
                        </div>
                    </div>
                </div>
                <div className = "divider_top"/>
            </section>
        );
    }
}

function BodySection(props) {
    var sortCorsiFunc;
    var sortCdlFunc;
    if (props.lingua === 'it') {
        sortCorsiFunc = (a, b) => {return compareStrings(props.corsi[a].nomeIt, props.corsi[b].nomeIt);}
        sortCdlFunc = (a, b) => {return compareStrings(a.nomeIt, b.nomeIt);}
    } else {
        sortCorsiFunc = (a, b) => {return compareStrings(props.corsi[a].nomeEn, props.corsi[b].nomeEn);}
        sortCdlFunc = (a, b) => {return compareStrings(a.nomeEn, b.nomeEn);}
    }
    var corsiKeys = Object.keys(props.corsi).sort(sortCorsiFunc);
    var cdl = props.cdl.sort(sortCdlFunc);
    var cdlm = props.cdlm.sort(sortCdlFunc);
    var anni = props.anniCorsi.sort((a, b) => {return (a > b) ? -1 : (a < b) ? 1 : 0;});
    var cdlNames = [];
    var cdlmNames = [];
    var anniList = [];
    var corsiRows = [];

    for (let c in cdl) {
        cdlNames.push(<LeftRowName lingua = {props.lingua} tipo = {'cdl'} cdl = {props.cdl[c]} key = {props.cdl[c].idcdl}/>)
    }

    for (let c in cdlm) {
        cdlmNames.push(<LeftRowName lingua = {props.lingua} tipo = {'cdl'} cdl = {props.cdlm[c]} key = {props.cdlm[c].idcdl}/>)
    }

    for (var year in anni) {
        anniList.push(<LeftRowName tipo = {'year'} year = {props.anniCorsi[year]} key = {props.anniCorsi[year]}/>)
    }

    for (var key in corsiKeys) {
        corsiRows.push(<TableRow lingua = {props.lingua} corso = {props.corsi[corsiKeys[key]]} key = {props.corsi[corsiKeys[key]]['idCorso']}/>)
    }

    if (props.lingua === "it") {
        return (
            <section id="main_content" >
                <div className="container">
                    <ol className="breadcrumb">
                        <li><Link to = '/Home'>Home</Link></li>
                        <li className="active">Lista corsi</li>
                    </ol>
                    <div className="row">
                
                        <aside className="col-lg-3 col-md-4 col-sm-4">
                            <div className="box_style_1">
                                
                            
                                <ul className="submenu-col">
                                    <li><Link to = '/Courses'>Tutti i corsi</Link></li>
                                    <br></br>
                                    <h4>Anni Accademici</h4>
                                        {anniList}
                                    <h4>Corsi di laurea Triennale</h4>
                                        {cdlNames}
                                    <h4>Corsi di laurea Magistrale</h4>  
                                        {cdlmNames}
                                </ul>                 
                                <hr/>
                            </div>
                        </aside>
                
                        <div className="col-lg-9 col-md-8 col-sm-8">
                    
                            <h3 align="center">Tutti i corsi</h3>
                            <p></p>

                            <div className="panel panel-info filterable add_bottom_45">
                                <div className="panel-heading">
                                    <h3 className="panel-title">Corsi</h3>
                                    <div className="pull-right">
                                        <button className="btn-filter"><span className="icon-th-list"></span>Filtri</button>
                                    </div>
                                </div>
                                <table className="table table-responsive table-striped">
                                    <thead>
                                        <tr className="filters">
                                            
                                            <th><input type="text" className="form-control" placeholder="Nome" disabled/></th>
                                            <th><input type="text" className="form-control" placeholder="SSD" disabled /></th>
                                            <th><input type="text" className="form-control" placeholder="CFU" disabled /></th>
                                            <th><input type="text" className="form-control" placeholder="Lingua" disabled /></th>
                                            <th><input type="text" className="form-control" placeholder="Semestre" disabled /></th>
                                            <th><input type="text" className="form-control" placeholder="Tipologia" disabled /></th>
                                            <th><input type="text" className="form-control" placeholder="CDL" disabled /></th>
                                            <th><input type="text" className="form-control" placeholder="Docenti" disabled /></th>
                                        </tr>
                                    </thead>
                                    <tbody>                      
                                        {corsiRows}
                                    </tbody>
                                </table>
                            </div>
                        </div>                       
                    </div>
                    <hr/>     
                </div>
            </section>
        );
    } else {
        return (
            <section id="main_content" >
                <div className="container">
                    <ol className="breadcrumb">
                        <li><Link to = '/Home'>Home</Link></li>
                        <li className="active">Courses</li>
                    </ol>
                    <div className="row">
                
                        <aside className="col-lg-3 col-md-4 col-sm-4">
                            <div className="box_style_1">
                                
                            
                                <ul className="submenu-col">
                                    <li><Link to = '/Courses'>All Courses</Link></li>
                                    <br></br>
                                    <h4>Academic Years</h4>
                                        {anniList}
                                    <h4>Bachelor's Degree Courses</h4>
                                        {cdlNames}
                                    <h4>Master's Degree Courses</h4>  
                                        {cdlmNames}
                                </ul>                 
                                <hr/>
                            </div>
                        </aside>
                
                        <div className="col-lg-9 col-md-8 col-sm-8">
                    
                            <h3 align="center">All Courses</h3>
                            <p></p>

                            <div className="panel panel-info filterable add_bottom_45">
                                <div className="panel-heading">
                                    <h3 className="panel-title">Courses</h3>
                                    <div className="pull-right">
                                        <button className="btn-filter"><span className="icon-th-list"></span>Filters</button>
                                    </div>
                                </div>
                                <table className="table table-responsive table-striped">
                                    <thead>
                                        <tr className="filters">
                                            
                                            <th><input type="text" className="form-control" placeholder="Name" disabled/></th>
                                            <th><input type="text" className="form-control" placeholder="SSD" disabled /></th>
                                            <th><input type="text" className="form-control" placeholder="CFU" disabled /></th>
                                            <th><input type="text" className="form-control" placeholder="Language" disabled /></th>
                                            <th><input type="text" className="form-control" placeholder="Semester" disabled /></th>
                                            <th><input type="text" className="form-control" placeholder="Type" disabled /></th>
                                            <th><input type="text" className="form-control" placeholder="CDL" disabled /></th>
                                            <th><input type="text" className="form-control" placeholder="Teachers" disabled /></th>
                                        </tr>
                                    </thead>
                                    <tbody>                      
                                        {corsiRows}
                                    </tbody>
                                </table>
                            </div>
                        </div>                       
                    </div>
                    <hr/>     
                </div>
            </section>
        );
    }
}

function LeftRowName(props) {
    var text;
    var link = '/Courses?';
    switch (props.tipo) {
        case 'cdl':
            link += 'cdl=' + props.cdl.idcdl;
            if (props.lingua === "it") {
                if (props.cdl.nomeIt != null) {
                    text = props.cdl.nomeIt;
                } else {
                    text = props.cdl.nomeEn;
                }
            } else {
                if (props.cdl.nomeEn != null) {
                    text = props.cdl.nomeEn;
                } else {
                    text = props.cdl.nomeIt;
                }
            }
            break;
        case 'year':
            link += 'year=' + props.year;
            text = props.year + " / " + (props.year + 1);
            break;
        default:
            text = "";
            break;
    }
    return <li><Link className = "uppercase" to = {link}>{text}</Link></li>
}

function TableRow(props) {
    let corso = props.corso
    let nomeCorso;
    let cdlCorso;
    let docentiCorso;
    if (props.lingua === "it") {
        if (corso['nomeIt'] != null) {
            nomeCorso = corso['nomeIt'];
        } else {
            nomeCorso = corso['nomeEn'];
        }
    } else {
        if (corso['nomeEn'] != null) {
            nomeCorso = corso['nomeEn'];
        } else {
            nomeCorso = corso['nomeIt'];
        }
    }
    let cdlList = corso.cdl;
    for (let cdl in cdlList) {
        let abbrCdl;
        if (props.lingua === 'it') {
            if (cdlList[cdl]['abbrIt'] != null) {
                abbrCdl = cdlList[cdl]['abbrIt'];
            } else {
                abbrCdl = cdlList[cdl]['abbrEn'];
            }
        } else {
            if (cdlList[cdl]['abbrEn'] != null) {
                abbrCdl = cdlList[cdl]['abbrEn'];
            } else {
                abbrCdl = cdlList[cdl]['abbrIt'];
            }
        }
        if (cdlCorso) {
            cdlCorso += ", " + abbrCdl;
        } else {
            cdlCorso = abbrCdl;
        }
    }
    let docentiList = corso.docenti;
    for (let docente in docentiList) {
        if (docentiCorso) {
            docentiCorso += ", " + docentiList[docente]['cognome'];
        } else {
            docentiCorso = docentiList[docente]['cognome'];
        }
    }
    return (
        <tr>
            <td><strong><Link to = "/DetailsCourse">{nomeCorso}</Link></strong></td>
            <td>{corso.ssd}</td>
            <td>{corso.cfu}</td>
            <td>{corso.lingua}</td>
            <td>{corso.semestre}</td>
            <td>{corso.tipologia}</td>
            <td>{cdlCorso}</td>
            <td>{docentiCorso}</td>
        </tr>
    );
}

export default ListaCorsi;