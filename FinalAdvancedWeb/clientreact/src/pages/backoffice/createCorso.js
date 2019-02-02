import React, { Component, Fragment } from 'react';
import { Link, Redirect } from 'react-router-dom';
import { Segment, Header, Progress } from 'semantic-ui-react';

export default class CreateCorso extends Component {
    constructor() {
        super();
        this.state = {
            
        };
    }

    render() {
        return (
            <Segment className = 'col-md-8' color = 'teal'>
                <Progress value='1' total='5'/>
                <Header size='medium'>Crea un Nuovo Corso</Header>
            </Segment>
        );
    }

}