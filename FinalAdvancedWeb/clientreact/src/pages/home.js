import React from 'react';
import {Slider, UnderSlider, VariousThings, Testimonials} from './homeComponents';
import { Link } from 'react-router-dom';

class Home extends React.Component {
    constructor() {
        super();
        this.state = {
            cdl: [],
            cdlm: [],
        };
    }

    componentWillMount() {
        var versione;
        var localVersione = localStorage.getItem('versioneCdl');
        fetch('http://localhost:8080/AdvancedWeb/rest/cdl/triennale')
                .then(res =>  {versione = res.headers.get('versione'); return res.json()})
                .then((result) => {
                    if (localVersione != null && versione === localVersione && localStorage.getItem('cdlList') != null) {
                        this.setState({
                            cdl: JSON.parse(localStorage.getItem('cdlList')),
                        });
                    } else {
                        var cdlList = [];
                        localStorage.setItem('versioneCdl', versione);
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
        .then(res =>  {versione = res.headers.get('versione'); return res.json()})
        .then((result) => {
            if (localVersione != null && versione === localVersione && localStorage.getItem('cdlmList') != null) {
                this.setState({
                    cdlm: JSON.parse(localStorage.getItem('cdlmList')),
                });
            } else {
                var cdlmList = [];
                localStorage.setItem('versioneCdl', versione);
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
    }

    componentDidMount() {
        window.jQuery(document).ready(function () {

            window.jQuery('.tp-banner').revolution({
                delay: 9000,
                startwidth: 1700,
                startheight: 600,
                hideThumbs: true,
                navigationType: "none",
                fullWidth: "on",
                forceFullWidth: "on"
            });

        });
    }


    render() {
        var cdl = this.state.cdl.slice().sort(() => Math.random() - 0.5).slice(0,4);
        var cdlm = this.state.cdlm.slice().sort(() => Math.random() - 0.5).slice(0,4);
        return (
            <div>
                <Slider lingua = {this.props.lingua}/>
                <UnderSlider lingua = {this.props.lingua}/>
                <CdlSection lingua = {this.props.lingua} cdlList = {cdl} isCdlm = {false}/>
                <CdlSection lingua = {this.props.lingua} cdlList = {cdlm} isCdlm = {true}/>
                <VariousThings lingua = {this.props.lingua}/>
                <Testimonials lingua = {this.props.lingua}/>
            </div>
        );
    }
}

function CdlSection(props) {
    var cdlList = props.cdlList
    var cdlRows = [];
    for (var c in cdlList) {
        cdlRows.push(<Cdl lingua = {props.lingua} cdl = {cdlList[c]} key = {cdlList[c]["idcdl"]}/>)
    }
    var title;
    var secondTitle = null;
    var allCourses;
    if (props.lingua === "it") {
        if (props.isCdlm) {
            title = "I Nostri Corsi di Laurea Magistrali";
        } else {
            title = "I Nostri Corsi di Laurea";
            secondTitle = <p className = "lead"> Esplora La Nostra Offerta Formativa </p>;
        }
        allCourses = "Vedi Tutti i Corsi";
    } else {
        if (props.isCdlm) {
            title = "Our Master's Degree Courses"
        } else {
            title = "Our Bachelor's Degree Courses";
            secondTitle = <p className = "lead"> Explore Our Training Offer </p>;
        }
        allCourses = "See All Courses";
    }

    return (
        <section id="main_content_gray">
            <div className = "container">
                <div className = "row">
                    <div className = "col-md-12 text-center">
                        <h2>{title}</h2>
                        {secondTitle}
                    </div>
                </div>
                {cdlRows}
                <Link to = '/Courses' className = "button_medium_outline pull-right">{allCourses}</Link>
            </div>
        </section>
    );
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
        <div  className="col-lg-3 col-md-6 col-sm-6">
            <div className="col-item">
                <div className="photo">
                    <Link to = {'/Courses?cdl=' + cdl['idcdl']}><img src={cdl['immagine']} alt="cdlimmagine"/></Link>
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
                        <p className="btn-add" id="cdlink"><Link to = {'/Courses?cdl=' + cdl['idcdl']}><i className="icon-export-4"></i></Link></p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Home;