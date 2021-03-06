import React from 'react';
import { Link } from 'react-router-dom';

function Header(props) {
    return (
        <header>
            <div className = "container">
                <div className = "row">
                    <div className = "col-md-2 col-sm-2 col-xs-2">
                        <Link id = 'logo' to = '/Home'>Learn</Link>
                    </div>
                    <div className = "col-md-10 col-sm-10 col-xs-10">
                        {props.login 
                            ?
                                <ul className = 'pull-right user_panel'>
                                    <li className = 'dropdown'>
                                    <a href="#" className="dropdown-toggle" data-toggle="dropdown">Welcome, <strong>{props.utente.username}</strong>
                                        <b className="caret"></b>
                                    </a>
                                        <ul className = 'dropdown-menu'>
                                            <li>
                                                <Link to = '/Backoffice/Profile'>
                                                <i className="icon-user"></i>Profilo Utente</Link>
                                            </li>
                                            <li>
                                                <Link to = "/Backoffice">
                                                <i className="icon-cog"></i>Backoffice Area</Link>
                                            </li>
                                            {props.utente.docente 
                                            ?
                                                null
                                            :
                                                <li>
                                                    <Link to = '/Backoffice/Logs'>
                                                        <i className=""></i>Log</Link>
                                                </li>
                                            }
                                            <li className="divider"></li>
                                            <li>
                                                <Link to = "/Logout">
                                                    <i className="icon-off"></i>Logout</Link>
                                            </li>
                                        </ul>
                                    </li>
                                </ul>
                            :
                            <div className = "pull-right">
                                <Link to = '/Login' className = "button_top" id = "login_top">Login</Link>
                            </div>
                        }
                    </div>
                </div>
            </div>
        </header>   
    );
}

function Nav(props) {
    return (
        <nav>
            <div className = "container">
                <div className = "row">
                    <div className = "col-md-12">
                        <ul className = "sf-menu">
                            <li>
                                <PageButton lingua = {props.lingua} path = {'Home'}/>
                            </li>
                            <li>
                                <PageButton lingua = {props.lingua} path = {'Courses'}/>
                            </li>
                            <li>
                                <PageButton lingua = {props.lingua} path = {'Teachers'}/>
                            </li>
                            <div className = "pull-right">
                                <LinguaButton lingua = {props.lingua} onLinguaChange = {props.onLinguaChange}/>
                            </div>
                        </ul>
                    </div>
                </div>
            </div>
        </nav>
    );
}


function PageButton(props) {
    var text;
        switch (props.path) {
            case "Home":
                text = "Home";
                break;
            case "Courses":
                if (props.lingua === "it") {
                    text = "Corsi";
                } else {
                    text = "Courses";
                }
                break;
            case "Teachers":
                if (props.lingua === "it") {
                    text = "Insegnanti";
                } else {
                    text = "Teachers";
                }
                break;
            case "About":
                if (props.lingua === "it") {
                    text = "Su di Noi";
                } else {
                    text = "About Us";
                }
                break;
            case "Apply":
                if (props.lingua === "it") {
                    text = "Segnati";
                } else {
                    text = "Apply";
                }
                break;
            case "Terms_Conditions":
                if (props.lingua === "it") {
                    text = "Termini e Condizioni";
                } else {
                    text = "Terms and Conditions";
                }
                break;
            case "Register":
                if (props.lingua === "it") {
                    text = "Registrati";
                } else {
                    text = "Register";
                }
                break;
            default:
                text = "";
                break;
        }
        return (
            <Link to = {'/' + props.path}>{text}</Link>
        );
}

function LinguaButton(props) {
    if (props.lingua === "it") {
        return (
            // eslint-disable-next-line
            <a className = "lnbar" onClick = {() => props.onLinguaChange("en")}><img alt="flag" id="flag" src="data:image/png;base64,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"/> English Version</a>
        );
    } else {
        return ( 
            // eslint-disable-next-line
            <a className = "lnbar" onClick = {() => props.onLinguaChange("it")}><img alt="flag" id="flag" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEYAAAAvCAYAAABe1bwWAAABlElEQVRoQ+3bzUoCYRTG8efoJilokdGq++gKyk1EgTMFfVgTtOhiWoYFJhSlTR/Qqu7AS0mirbbREzM0Mo0VHJwWLzyuRN8ZeX/856xeBanX1nVQGRZwBGAJwAIASX9vfd9YO7VeYlr/sXtsWv/DYgW0K5COolCfC+vPyZp447WL2lS/VGwC8Cf9pfT1DsBktivtHqb3F8OTfgyzeRNcqmA7T5ToXu7BAKpyVb472xG/dbAMyEveKK7CxA4qK+K3gnsAG4T5JvAYwbwBKBMmLaCvEYz+B4rTjxIwJMwvVRCGMLaBwWJYDIuxCbAYmxdnDIthMTYBFmPz4oxhMSzGJsBibF6cMSyGxdgEWIzNizOGxbAYmwCLsXlxxrAYFmMTYDE2L84YFsNibAIsxubFGcNiWIxN4I9ieAZvDEffxWsfPojqei7MmZu4eM73awvRqU2e8x2LQlDhyfCMyuhkePR59bZaKg5mmyrq5flIOfcoCcKezuyN/kuQYHjtYFUUQerfJxM5OQLTFaAzABrz4flTsuFP9EA6lGvhTBMAAAAASUVORK5CYII="/> Versione Italiana</a>
        );
    }
}

function Footer(props) {
    var browse;
    var courses;
    var about;
    if (props.lingua === "it") {
        browse = "Naviga";
        courses = "Tutti i Corsi";
        about = "Su Learn";
    } else {
        browse = "Browse";
        courses = "All Courses";
        about = "About Learn";
    }
    return (
        <footer>
            <hr/>
            <div className = "container" id = "nav-footer">
                <div className = "row text-left">
                    <div className = "col-md-4 col-sm-4">
                        <h4>{browse}</h4>
                        <ul>
                            <li>
                                <PageButton lingua = {props.lingua} path = {'Home'}/>
                            </li>
                            <li>
                                <PageButton lingua = {props.lingua} path = {'Teachers'}/>
                            </li>
                        </ul>
                    </div>
                    <div className = "col-md-4 col-sm-4">
                        <h4>{courses}</h4>
                        <ul>
                            <li>
                                <PageButton lingua = {props.lingua} path = {'Courses'}/>
                            </li>
                        </ul>
                    </div>
                    <div className = "col-md-4 col-sm-4">
                        <h4>{about}</h4>
                        <ul>
                            <li>
                                <PageButton lingua = {props.lingua} path = {'About'}/>
                            </li>
                            <li>
                                <PageButton lingua = {props.lingua} path = {'Apply'}/>
                            </li>
                            <li>
                                <PageButton lingua = {props.lingua} path = {'Terms_Conditions'}/>
                            </li>
                            <li>
                                <PageButton lingua = {props.lingua} path = {'Register'}/>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </footer>
    );
}

export {Header, Nav, Footer};