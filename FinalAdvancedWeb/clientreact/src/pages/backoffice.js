import React, { Component, Fragment } from 'react';
import { Link, Redirect } from 'react-router-dom';

export default class Backoffice extends Component {
    constructor() {
        super();
        this.state = {

        };
    }

    render() {
        if (!this.props.loggedIn) {
            return <Redirect to = '/Login'/>
        } else {
            return (
                <Fragment>
                    <section id="sub-header">
                        <div class="container">
                            <div class="row">
                                <div class="col-md-10 col-md-offset-1 text-center">
                                    <h1>Backoffice</h1>
                                    <p class="lead"></p>
                                </div>
                            </div>
                        </div>
                        <div class="divider_top"></div>
                    </section>
                </Fragment>
            );
        }
    }
    
}