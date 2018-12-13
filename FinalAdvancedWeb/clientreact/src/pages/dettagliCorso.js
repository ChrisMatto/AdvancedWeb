import React, { Component } from 'react';

export default class DettagliCorso extends Component {
    constructor() {
        super();
        this.state = {
            corso: {},
            infoBase: {},
            sillabo: {},
            insegnanti: [],
            relazioni: {},
            links: {},
            storiaCorso: []
        }
    }

    componentWillMount() {
        let baseLink = 'http://localhost:8080/AdvancedWeb/rest/courses/';
        let corsoLink = baseLink + this.props.year + '/' + this.props.id;
        let infoBaseLink = baseLink + this.props.year + '/' + this.props.id + '/basic';
        let sillaboLink = baseLink + this.props.year + '/' + this.props.id + '/syllabus';
        let insegnantiLink = baseLink + this.props.year + '/' + this.props.id + '/teachers';
        let relazioniLink = baseLink + this.props.year + '/' + this.props.id + '/relations';
        let linksLink = baseLink + this.props.year + '/' + this.props.id + '/links';
        let storiaCorsoLink = baseLink + 'history/' + this.props.id;
        fetch(corsoLink)
        .then(res => res.json())
        .then(result => {
            this.setState({corso: result});
        });
        fetch(infoBaseLink)
        .then(res => res.json())
        .then(result => {
            this.setState({infoBase: result});
        });
        fetch(sillaboLink)
        .then(res => res.json())
        .then(result => {
            this.setState({sillabo: result});
        });
        fetch(insegnantiLink)
        .then(res => res.json())
        .then(result => {
            this.setState({insegnanti: result});
        });
        fetch(relazioniLink)
        .then(res => res.json())
        .then(result => {
            this.setState({relazioni: result});
        });
        fetch(linksLink)
        .then(res => res.json())
        .then(result => {
            this.setState({links: result});
        });
        fetch(storiaCorsoLink)
        .then(res => res.json())
        .then(result => {
            this.setState({storiaCorso: result});
        });
    }

    render() {
        return <div></div>
    }
}