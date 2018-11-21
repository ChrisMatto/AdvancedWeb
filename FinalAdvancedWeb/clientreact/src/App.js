import React, { Component } from 'react';
import {Header, Nav, Footer} from './pages/header_nav_footer';
import Home from './pages/home';
import ListaCorsi from './pages/listacorsi';

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
        page: "home",
        lingua: lingua,
    };
    this.changePage = this.changePage.bind(this);
    this.changeLingua = this.changeLingua.bind(this);
    this.getBody = this.getBody.bind(this);
}

changePage(page) {
    switch (page) {
        case "home":
            this.setState({
                page: "home",
            });
            break;
        case "listacorsi":
            this.setState({
                page: "listacorsi",
            });
            break;
        case "insegnanti":
            this.setState({
                page: "insegnanti",
            });
            break;
        default:
            this.setState({
                page: "reload",
            });
            break;
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

getBody() {
    switch (this.state.page) {
        case "home":
            return <Home lingua = {this.state.lingua} onPageChange = {this.changePage}/>;
        case "listacorsi":
            return <ListaCorsi lingua = {this.state.lingua} onPageChange = {this.changePage}/>;
        default:
            window.location.reload();
    }
}

render() {
    var body = this.getBody();
    return (
        <React.Fragment>
            <Header onClick = {this.changePage}/>
            <Nav lingua = {this.state.lingua} onPageChange = {this.changePage} onLinguaChange = {this.changeLingua}/>
            {body}
            <Footer lingua = {this.state.lingua} onPageChange = {this.changePage}/>
        </React.Fragment>
    );
}
}

export default App;
