import React from 'react';
import {Slider, UnderSlider} from './homeComponents';

class Home extends React.Component {
    constructor() {
        super();
        this.state = {
            cdl: [],
            cdlm: [],
        };
    }

    componentDidMount() {
        if (localStorage.getItem('cdlList') != null) {
            this.setState({
                cdl: JSON.parse(localStorage.getItem('cdlList')),
            });
        } else {
            fetch('http://localhost:8080/AdvancedWeb/rest/cdl/triennale')
                .then(res => res.json())
                .then((result) => {
                    var cdlList = [];
                    for (var c in result) {
                        fetch(result[c])
                            .then(res => res.json())
                            .then((result) => {
                                cdlList.push(result);
                                localStorage.setItem('cdlList', JSON.stringify(cdlList));
                                this.setState({
                                    cdl: cdlList,
                                });
                            });
                    }
                });
        }
        if (localStorage.getItem('cdlmList') != null) {
            this.setState({
                cdlm: JSON.parse(localStorage.getItem('cdlmList')),
            });
        } else {
            fetch('http://localhost:8080/AdvancedWeb/rest/cdl/magistrale')
                .then(res => res.json())
                .then((result) => {
                    var cdlmList = [];
                    for (var c in result) {
                        fetch(result[c])
                            .then(res => res.json())
                            .then((result) => {
                                cdlmList.push(result);
                                localStorage.setItem('cdlmList', JSON.stringify(cdlmList));
                                this.setState({
                                    cdlm: cdlmList,
                                });
                            });
                    }
                });
        }
    }

    render() {
        var cdl = this.state.cdl.slice().sort(() => Math.random - 0.5).slice(0,4);
        var cdlm = this.state.cdlm.slice().sort(() => Math.random - 0.5).slice(0,4);
        var cdlmRows = [];
        for (var cm in cdlm) {
            cdlmRows.push(<Cdl lingua = {this.props.lingua} onPageChange = {this.props.onPageChange} cdl = {cdlm[cm]}/>)
        }
        return (
            <React.Fragment>
                <Slider lingua = {this.props.lingua}/>
                <UnderSlider lingua = {this.props.lingua}/>
                <CdlSection lingua = {this.props.lingua} onPageChange = {this.props.onPageChange} cdlList = {cdl}/>
            </React.Fragment>
        );
    }
}

function CdlSection(props) {
    var cdlList = props.cdlList
    var cdlRows = [];
    for (var c in cdlList) {
        cdlRows.push(<Cdl lingua = {props.lingua} onPageChange = {props.onPageChange} cdl = {cdlList[c]}/>)
    }
    if (props.lingua === "it") {
        return (
            <section id="main_content_gray">
                <div className = "container">
                    <div className = "row">
                        <div className = "col-md-12 text-center">
                            <h2>I Nostri Corsi di Laurea</h2>
                            <p className = "lead"> Esplora La Nostra Offerta Formativa </p>
                        </div>
                    </div>
                    {cdlRows}
                    <a onClick = {() => props.onPageChange("listacorsi")} className = "button_medium_outline pull-right">Vedi Tutti i Corsi</a>
                </div>
            </section>
        );
    } else {
        return (
            <section id="main_content_gray">
                <div className = "container">
                    <div className = "row">
                        <div className = "col-md-12 text-center">
                            <h2>Our Courses</h2>
                            <p className = "lead"> Explore Our Training Offer </p>
                        </div>
                    </div>
                    {cdlRows}
                    <a onClick = {() => props.onPageChange("listacorsi")} className = "button_medium_outline pull-right">See All Courses</a>
                </div>
            </section>
        );
    }
}

function Cdl(props) {
    var cdl = props.cdl;
    var nome;
    var descrizione;
    if (props.lingua === "it") {
        if (cdl['nomeIt'] != null) {
            nome = cdl['nomeIt'];
        } else {
            nome = cdl['nomeEn'];
        }
        if (cdl['descrizioneIt'] != null) {
            descrizione = cdl['descrizioneIt'];
        } else {
            descrizione = cdl['descrizioneEn'];
        }
    } else {
        if (cdl['nomeEn'] != null) {
            nome = cdl['nomeEn'];
        } else {
            nome = cdl['nomeIt'];
        }
        if (cdl['descrizioneEn'] != null) {
            descrizione = cdl['descrizioneEn'];
        } else {
            descrizione = cdl['descrizioneIt'];
        }
    }
    return(
        <div  className="col-lg-3 col-md-6 col-sm-6" key = {cdl["idcdl"]}>
            <div className="col-item">
                <div className="photo">
                    <a onClick = {() => props.onPageChange("listacorsi")}><img src={cdl['immagine']} alt="cdlimmagine"/></a>
                    <div className="cat_row" >{nome}<span className="pull-right"><i className="fas fa-university"></i></span></div>
                </div>
                <div className="info">
                    <div className="row">
                        <div className="course_info col-md-12 col-sm-12">
                            
                            <p>{descrizione}</p>

                        <div className="price pull-right" id="cfuCdl">{cdl['cfu']} CFU</div>
                        </div>
                    </div>
                    <div className="separator clearfix">
                        <p className="btn-add" id="cdlink"><a onClick = {() => props.onPageChange("listacorsi")}><i className="icon-export-4"></i></a></p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Home;