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
    }

    render() {
        return <div></div>
    }
}