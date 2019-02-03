import React, { Component, Fragment } from 'react';
import { Link, Redirect } from 'react-router-dom';
import { Segment, Header, Progress, Form, Button } from 'semantic-ui-react';

export default class CreateCorso extends Component {
    constructor() {
        super();
        this.state = {
            step: 1,
            corso: {}
        };
    }

    handleChange = (e, {name, value}) => {
        this.setState({
            corso: {...this.state.corso, [name]: value}
        });
    }

    firstStep = () => {
        return (
            <React.Fragment>
                <Form.Group widths = 'equal'>
                    <Form.Input fluid name = 'nomeIt' onChange = {this.handleChange} label = 'Nome Del Corso' placeholder = 'Nome Corso' required/>
                    <Form.Input fluid name = 'nomeEn' onChange = {this.handleChange} label = 'Nome Del Corso EN' placeholder = 'Nome Corso EN'/>
                </Form.Group>
                <Form.Group widths = 'equal'>
                    <Form.Input fluid name = 'ssd' onChange = {this.handleChange} label = 'SSD' placeholder = 'SSD'/>
                    <Form.Input fluid name = 'lingua' onChange = {this.handleChange} label = 'Lingua' placeholder = 'Lingua'/>
                </Form.Group>
                <Form.Group widths = 'equal'>
                    <Form.Select fluid name = 'semestre' onChange = {this.handleChange} label = 'Semestre' placeholder = 'Seleziona un semestre...' options = {[{value: 1, text: 1}, {value: 2, text: 2}]}/>
                    <Form.Input fluid name = 'cfu' onChange = {this.handleChange} label = 'CFU' placeholder = 'CFU'/>
                </Form.Group>
                <Form.Group widths = 'two'>
                    <Form.Select fluid name = 'tipologia' onChange = {this.handleChange} label = 'Tipologia CFU' placeholder = 'Seleziona una tipologia...' options = {[{value: 'A', text: 'A'}, {value: 'B', text: 'B'}, {value: 'F', text: 'F'}]}/>
                    <Form.Field/>
                </Form.Group>
            </React.Fragment>
        );
    }

    render() {
        return (
            <Segment className = 'col-md-8' color = 'teal'>
                <Progress value = {this.state.step} total='5' indicating/>
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Crea un Nuovo Corso</Header>
                <Form>
                    <this.firstStep/>
                </Form>
                <div style = {{ textAlign: 'center', paddingTop: 20 }}>
                    <Button.Group>
                        <Button onClick = {() => this.setState({ step: this.state.step - 1 })} disabled = {this.state.step === 1}>Indietro</Button>
                        <Button.Or text = 'O'/>
                        <Button positive onClick = {() => this.setState({ step: this.state.step + 1 })} disabled = {this.state.step === 5}>Avanti</Button>
                    </Button.Group>
                </div>
            </Segment>
        );
    }

}