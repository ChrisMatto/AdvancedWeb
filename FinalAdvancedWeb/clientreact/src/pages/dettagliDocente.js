import React, { Component } from 'react';

export default class DettagliDocente extends Component {
    constructor() {
        super();
        this.state = {
            docente: {}
        };
    }

    componentWillMount() {
        var link = 'http://localhost:8080/AdvancedWeb/rest/teachers/' + this.props.id;
        var docente = localStorage.getItem(link);
        if (docente) {
            this.setState({docente: JSON.parse(docente)});
        } else {
            fetch(link)
            .then(res => res.json())
            .then(result => {
                this.setState({docente: result});
            });
        }
    }

    render() {
        return (
            <div>ciao</div>
        );
    }
}