import React, { Component, Fragment } from 'react';
import { Link, Redirect } from 'react-router-dom';
import { BackPages } from '../js/enums';

export default class Backoffice extends Component {
    constructor(props) {
        super(props);
        this.state = {
            token: props.token,
            utente: props.utente,
            backPage: null
        };
    }

    AdminMenu = () => {
        return (
            <div></div>
        );
    }

    TeachersMenu = () => {
        return (
            <div></div>
        );
    }

    render() {
        if (!this.state.token || !this.state.utente) {
            return <Redirect to = '/Login'/>
        } else {
            return (
                <Fragment>
                    <section id="sub-header">
                        <div className="container">
                            <div className="row">
                                <div className="col-md-10 col-md-offset-1 text-center">
                                    <h1>Backoffice</h1>
                                    <p className="lead"></p>
                                </div>
                            </div>
                        </div>
                        <div className="divider_top"></div>
                    </section>
                    
                    <section id="main_content">
                        <div className="container">
                            <ol className="breadcrumb">
                                <li className="active">
                                    <Link to = '/Backoffice'>Backoffice</Link>
                                </li>
                            </ol>
                            <div className = 'row'>
                            {
                                this.state.utente.docente ?
                                    <this.TeachersMenu/>
                                :
                                    <this.AdminMenu/>
                            }
                            </div>
                        </div>
                    </section>                    

                </Fragment>
            );
        }
    }
}
