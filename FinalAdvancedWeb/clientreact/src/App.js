import React, { Component } from 'react';
import {Header, Nav, Footer} from './pages/header_nav_footer';
import Home from './pages/home';
import ListaCorsi from './pages/listacorsi';
import DettagliCorso from './pages/dettagliCorso';
import { Switch, Route} from 'react-router-dom';

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
    };
    this.changeLingua = this.changeLingua.bind(this);
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
            </Switch>
            <Footer lingua = {this.state.lingua}/>
        </React.Fragment>
    );
}
}

export default App;
