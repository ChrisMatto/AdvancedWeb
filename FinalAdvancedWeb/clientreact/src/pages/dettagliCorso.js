import React, { Component } from 'react';

export default class DettagliCorso extends Component {
    constructor() {
        super();
        this.state = {
            corso: {},
            storiaCorso: []
        }
    }

    componentWillMount() {
        console.log(this.props.id);
    }

    render() {
        return <div></div>
    }
}