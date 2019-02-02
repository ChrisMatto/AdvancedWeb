import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';

export default class Login extends Component {
    constructor() {
        super();
        this.state = {
            token: null,
            error: false,
            username: '',
            password: ''
        };
    }

    login = (event) => {
        event.preventDefault();
        var credentials = {
            username: this.state.username,
            password: this.state.password
        }
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/login', {
            method: 'POST',
            headers: headers,
            body: JSON.stringify(credentials)
        })
        .then(res => res.ok ? res.text() : null)
        .then(result => {
            localStorage.setItem('token', result);
            this.setState({
                token: result,
                error: result ? false : true
            });
            if (result) {
                this.props.login(result);
            }
        });
    }

    onInputChange = (event) => {
        this.setState({
            [event.target.name]: event.target.value
        });
    }

    render() {
        if (this.props.loggedIn || this.state.token) {
            return <Redirect to = '/Backoffice'/>
        } else {
            return (
                <section id="login_bg">
                    <div  className="container">
                        <div className="row">
                            <div className="col-md-4 col-md-offset-4 col-sm-6 col-sm-offset-3">
                                <div id="login">
                                    <p className="text-center">
                                        <img src="img/login_logo.png" alt="Login"/>
                                    </p>
                                    <hr/>
                                    <form onSubmitCapture = {this.login}>
                            
                                        <div className="form-group" style={{marginBottom:'-1em'}}>
                                            <input type="text" className="form-control" placeholder="Username" name = 'username' value = {this.state.username} onChange = {this.onInputChange}/>
                                                <span className="input-icon"><i className="icon-user"></i></span>
                                        </div>
                                        <div className="form-group">
                                            <input type="password" className="form-control" placeholder="Password" style={{marginBottom:5}} name="password" value = {this.state.password} onChange = {this.onInputChange}/>
                                                <span className="input-icon"><i className="icon-lock"></i></span>
                                        </div>
                                        <input type="submit" className="button_fullwidth"/>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            );
        }
    }
}