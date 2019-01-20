import React, { Component, Fragment } from 'react';
import { Redirect } from 'react-router-dom';

export default class Login extends Component {
    constructor() {
        super();
        this.state = {
            token: null,
            error: false
        };
    }

    login = (username, password) => {
        var credentials = {
            username: username,
            password: password
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
        });
    }

    render() {
        if (this.state.token) {
            return <Redirect to = ''/>
        } else {
            return (
                <a onClick = {() => this.login('admin', 'password')}>Login</a>
            );
        }
    }
}