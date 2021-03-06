import React, { Component } from 'react';
import { Header, Nav, Footer } from './pages/header_nav_footer';
import Home from './pages/home';
import ListaCorsi from './pages/listacorsi';
import DettagliCorso from './pages/dettagliCorso';
import ListaDocenti from './pages/listaDocenti';
import { Switch, Route, Redirect } from 'react-router-dom';
import DettagliDocente from './pages/dettagliDocente';
import ListaMateriali from './pages/listaMateriali';
import Login from './pages/login';
import Backoffice from './pages/backoffice';
import { Loader } from 'semantic-ui-react';
import 'semantic-ui-css/semantic.min.css';
import './css/index.css';

class App extends Component {
  constructor() {
    super();
    var lingua;
    if (localStorage.getItem('lingua') != null) {
        lingua = localStorage.getItem('lingua');
    } else {
        lingua = "it";
        localStorage.setItem('lingua', lingua);
    }
    this.state = {
        lingua: lingua,
        token: null,
        utente: null,
        isCheckingSession: false
    };
    this.changeLingua = this.changeLingua.bind(this);
    }

    componentWillMount() {
        var token = localStorage.getItem('token');
        if (token) {
            this.loggedIn(token);
        }
    }

    changeLingua(lingua) {
        if (lingua === "it") {
            this.setState({
                lingua: "it",
            });
            localStorage.setItem('lingua', "it");
        } else {
            this.setState({
                lingua: "en",
            });
            localStorage.setItem('lingua', "en");
        }
    }

    loggedIn = (token) => {
        this.setState({ isCheckingSession: true });
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/checkSession', {
            method: 'POST',
            body: token
        })
        .then(res => res.ok ? res.json() : null)
        .then(result => {
            if (result) {
                this.setState({
                    token: token,
                    utente: result,
                    isCheckingSession: false
                });
            } else {
                localStorage.removeItem('token');
                this.setState({ isCheckingSession: false });
            }
        });
    }

    Logout = () => {
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/logout', {
                method: 'POST',
                body: this.state.token
            })
            .then(res => {
                if (res.ok) {
                    this.setState({
                        token: null,
                        utente: null
                    });
                    sessionStorage.removeItem('token');
                }
            });
        return <Redirect to = '/Home'/>
    }

    render() {
        return (
            <React.Fragment>
                <Header login = {this.state.token && this.state.utente ? true : false} lingua = {this.state.lingua} utente = {this.state.utente}/>
                <Nav lingua = {this.state.lingua} onLinguaChange = {this.changeLingua}/>
                <Switch>
                <Route exact path = '/(|Home)' render = {() => <Home lingua = {this.state.lingua}/>}/>
                <Route exact path = '/Courses' render = {() => <ListaCorsi lingua = {this.state.lingua}/>}/>
                <Route exact path = '/Courses/:year(\d{4})/:id(\d+)' render = {({ match }) => <DettagliCorso year = {match.params.year} id = {match.params.id} lingua = {this.state.lingua}/>}/>
                <Route exact path = '/Courses/:year(\d{4})/:id(\d+)/Material' render = {({ match }) => <ListaMateriali year = {match.params.year} id = {match.params.id} lingua = {this.state.lingua}/>}/>
                <Route exact path = '/Teachers' render = {() => <ListaDocenti lingua = {this.state.lingua}/>}/>
                <Route exact path = '/Teachers/:id(\d+)' render = {({ match }) => <DettagliDocente id = {match.params.id} lingua = {this.state.lingua}/>}/>
                <Route exact path = '/Login' render = {() => <Login login = {this.loggedIn} loggedIn = {this.state.token && this.state.utente ? true : false} />}/>
                <Route exact path = '/Logout' render = {() => <this.Logout/>}/>
                <Route path = '/Backoffice' render = {() => 
                    this.state.isCheckingSession 
                    ?  
                        <div className = 'loader-container'>
                            <Loader active size = 'massive'>Loading</Loader>
                        </div>
                    : 
                        <Backoffice token = {this.state.token} utente = {this.state.utente}/>}/>
                </Switch>
                <Footer lingua = {this.state.lingua}/>
            </React.Fragment>
        );
    }
}

export default App;
