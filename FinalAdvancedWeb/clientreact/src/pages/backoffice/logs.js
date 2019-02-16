import React, { Component } from 'react';
import { Table, Segment, Header} from 'semantic-ui-react';

export default class Logs extends Component {
    constructor() {
        super();
        this.state = {
            logs: []
        };
    }
    
    componentWillMount() {
        fetch('http://localhost:8080/AdvancedWeb/rest/auth/' + this.props.token + "/logs")
        .then(res => res.ok ? res.json() : [])
        .then(result => this.setState({ logs: result }));
    }

    render() {
        return (
            <Segment className = 'col-md-8' color = 'teal' style = {{ marginTop: 4 }}>
                <Header size='medium' style = {{ textAlign: 'center' }} dividing>Logs</Header>
                <Table celled striped color = 'grey'>
                    <Table.Header>
                        <Table.Row>
                            <Table.HeaderCell>Log</Table.HeaderCell>
                            <Table.HeaderCell>Data</Table.HeaderCell>
                        </Table.Row>
                    </Table.Header>
                    <Table.Body>
                        {
                            this.state.logs.map(log => 
                                <Table.Row key = {log.idLog}>
                                    <Table.Cell>{log.descrizione}</Table.Cell>
                                    <Table.Cell>{new Date(log.data).toLocaleString()}</Table.Cell>
                                </Table.Row>
                            )
                        }
                    </Table.Body>
                </Table>
            </Segment>
        );
    }
}