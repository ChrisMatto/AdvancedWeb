import React from 'react';
import ReactDOM from 'react-dom';
import {Header, Nav, Footer} from './pages/header_nav_footer';
import Home from './pages/home';
import ListaCorsi from './pages/listacorsi';

class Page extends React.Component {

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
            case "reload":
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
                return <ListaCorsi/>;
            case "reload":
                window.location.reload();
        }
    }

    render() {
        return (
            <React.Fragment>
                <Header onClick = {this.changePage}/>
                <Nav lingua = {this.state.lingua} onPageChange = {this.changePage} onLinguaChange = {this.changeLingua}/>
                <this.getBody/>
                <Footer lingua = {this.state.lingua} onPageChange = {this.changePage}/>
            </React.Fragment>
        );
    }
}

ReactDOM.render(
    <Page/>,
    document.getElementById('root')
);