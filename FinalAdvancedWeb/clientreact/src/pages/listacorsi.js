import React from 'react';

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

    render() {
        return (
            <Banner/>
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

export default ListaCorsi;