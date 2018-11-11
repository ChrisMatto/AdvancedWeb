import React from 'react';
import ReactDOM from 'react-dom';
import {Header, Nav, Footer} from './pages/header_nav_footer';
import Home from './pages/home';

class Page extends React.Component {

    constructor() {
        super();
        this.state = {
            page: "home",
            lingua: "it",
        };
        let data = new Date();
        var oldData = localStorage.getItem('data');
        if (oldData != null) {
            oldData = new Date(oldData);
            if (oldData.getFullYear() !== data.getFullYear() || oldData.getMonth() !== data.getMonth() || oldData.getDate() !== data.getDate()) {
                localStorage.clear();
                localStorage.setItem('data', data);
            }
        } else {
            localStorage.clear();
            localStorage.setItem('data', data);
        }
        this.changePage = this.changePage.bind(this);
        this.changeLingua = this.changeLingua.bind(this);
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
        } else {
            this.setState({
                lingua: "en",
            });
        }
    }

    render() {
        return (
            <div>
                <Header onClick = {this.changePage}/>
                <Nav lingua = {this.state.lingua} onPageChange = {this.changePage} onLinguaChange = {this.changeLingua}/>
                <Home lingua = {this.state.lingua} onPageChange = {this.changePage}/>
                <Footer lingua = {this.state.lingua} onPageChange = {this.changePage}/>
            </div>
        );
    }
}

ReactDOM.render(
    <Page/>,
    document.getElementById('root')
);