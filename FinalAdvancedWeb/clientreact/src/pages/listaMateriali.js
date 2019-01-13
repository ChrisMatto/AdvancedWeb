import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import compareStrings from '../js/functions';

export default class ListaMateriali extends Component {
    constructor() {
        super();
        this.state = {
            corso: {},
            data: []
        };
    }

    componentWillMount() {
        fetch('http://localhost:8080/AdvancedWeb/rest/courses/' + this.props.year + '/' + this.props.id + '/basic')
        .then(res => res.json())
        .then(result => {
            this.setState({corso: result});
        });
        fetch('http://localhost:8080/AdvancedWeb/rest/courses/' + this.props.year + '/' + this.props.id + '/material')
        .then(res => res.json())
        .then(result => {
            this.setState({data: result});
        });
    }

    render() {
        var lingua = this.props.lingua;
        var corso = this.state.corso;
        return (
            <React.Fragment>
                <section id="sub-header">
                    <div className="container">
                        <div className="row">
                            <div className="col-md-10 col-md-offset-1 text-center">
                                <h1>{lingua === 'it' ? 'Materiale' : 'Material'}</h1>
                            </div>
                        </div>
                    </div>
                    <div className="divider_top"></div>
                </section>

                <section id = 'main_content'>
                    <div className = 'container'>
                        <ol className="breadcrumb">
                            <li><Link to = '/Home'>Home</Link></li>
                            <li><Link to = '/Courses'>{lingua === 'it' ? 'Lista Corsi' : 'Courses List'}</Link></li>
                            <li><Link to = {'/Courses/' + this.props.year + '/' + this.props.id}>{lingua === 'it' ? corso.nomeIt : corso.nomeEn}</Link></li>
                            <li className="active">{lingua === 'it' ? 'Materiale' : 'Material'}</li>
                        </ol>
                    </div>
                </section>
            </React.Fragment>
        );
    }

}