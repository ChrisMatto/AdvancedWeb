import React, { Component } from 'react';
import { Header, Nav, Footer } from './pages/header_nav_footer';
import Home from './pages/home';
import ListaCorsi from './pages/listacorsi';
import DettagliCorso from './pages/dettagliCorso';
import ListaDocenti from './pages/listaDocenti';
import { Switch, Route } from 'react-router-dom';
import DettagliDocente from './pages/dettagliDocente';
import ListaMateriali from './pages/listaMateriali';
import Login from './pages/login';

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
        token: null
    };
    this.changeLingua = this.changeLingua.bind(this);
    }

    componentWillMount() {
        var token = localStorage.getItem('token');
        if (token) {
            fetch('http://localhost:8080/AdvancedWeb/rest/auth/checkSession', {
                method: 'POST',
                body: token
            }).then(res => res.ok ? this.setState({ token: token }) : this.setState({ token: null }));
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

    render() {
        return (
            <React.Fragment>
                <Header/>
                <Nav lingua = {this.state.lingua} onLinguaChange = {this.changeLingua}/>
                <Switch>
                <Route exact path = '/(|Home)' render = {() => <Home lingua = {this.state.lingua}/>}/>
                <Route exact path = '/Courses' render = {() => <ListaCorsi lingua = {this.state.lingua}/>}/>
                <Route exact path = '/Courses/:year(\d{4})/:id(\d+)' render = {({ match }) => <DettagliCorso year = {match.params.year} id = {match.params.id} lingua = {this.state.lingua}/>}/>
                <Route exact path = '/Courses/:year(\d{4})/:id(\d+)/Material' render = {({ match }) => <ListaMateriali year = {match.params.year} id = {match.params.id} lingua = {this.state.lingua}/>}/>
                <Route exact path = '/Teachers' render = {() => <ListaDocenti lingua = {this.state.lingua}/>}/>
                <Route exact path = '/Teachers/:id(\d+)' render = {({ match }) => <DettagliDocente id = {match.params.id} lingua = {this.state.lingua}/>}/>
                <Route exact path = '/Login' render = {() => <Login/>}/>
                </Switch>
                <Footer lingua = {this.state.lingua}/>
            </React.Fragment>
        );
    }
}

export default App;
