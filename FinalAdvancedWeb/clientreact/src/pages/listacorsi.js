import React from 'react';
import activeFilters from '../js/table_filter';

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
            <BodySection lingua = {this.props.lingua} onPageChange = {this.props.onPageChange} cdl = {this.state.cdl} cdlm = {this.state.cdlm} anniCorsi = {this.state.anniCorsi} corsi = {this.state.corsi}/>
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
    var cdlNames = [];
    var cdlmNames = [];
    var anniList = [];
    var corsiRows = [];

    for (var cdl in props.cdl) {
        cdlNames.push(<CdlName lingua = {props.lingua} tipo = {'cdl'} cdl = {props.cdl[cdl]} key = {props.cdl[cdl].idcdl}/>)
    }

    if (props.lingua === "it") {
        return (
            <section id="main_content" >
                <div className="container">
                    <ol className="breadcrumb">
                        <li><a onClick = {() => props.onPageChange("home")}>Home</a></li>
                        <li className="active">Lista corsi</li>
                    </ol>
                    <div className="row">
                
                        <aside className="col-lg-3 col-md-4 col-sm-4">
                            <div className="box_style_1">
                                
                            
                                <ul className="submenu-col">
                                    <li><a onClick = {() => props.onPageChange("listacorsi")}>Tutti i corsi</a></li>
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
    }
}

function CdlName(props) {
    var text;
    switch (props.tipo) {
        case 'cdl':
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
        default:
            text = "";
            break;
    }
    return <li><a className = "uppercase" onClick = {() => {}}>{text}</a></li>;
}

export default ListaCorsi;