import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { compareStrings, formatBytes } from '../js/functions';

export default class ListaMateriali extends Component {
    constructor() {
        super();
        this.state = {
            corso: {},
            materiale: []
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
            this.setState({materiale: result});
        });
    }

    render() {
        var lingua = this.props.lingua;
        var corso = this.state.corso;
        var materiale = this.state.materiale.slice().sort((a, b) => compareStrings(a, b));
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

                        <div className="row">
                            <div className="col-md-12">
                                <h3>{lingua === 'it' ? 'Materiale' : 'Material'}</h3>
                                <table className="table table-striped">
                                    <thead>
                                        <tr>
                                          <th>{lingua === 'it' ? 'Nome' : 'Name'}</th>
                                          <th>{lingua === 'it' ? 'Descrizione' : 'Description'}</th>
                                          <th>{lingua === 'it' ? 'Dimensioni' : 'Size'}</th>
                                          <th>Download</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {materiale ? materiale.map((mat) => (
                                            <tr key = {mat.idMateriale}>
                                                <th scope = 'row'>{mat.nome}</th>
                                                <td>{lingua === 'it' ? mat.descrizioneIt || mat.descrizioneEn : mat.descrizioneEn || mat.descrizioneIt}</td>
                                                <td>{formatBytes(mat.dimensioni)}</td>
                                                <td className = 'icon-download'><a href = {'http://localhost:8080/AdvancedWeb/rest/courses/material/' + mat.idMateriale}> {lingua === 'it' ? 'Scarica Ora' : 'Download Now'} </a></td>
                                            </tr>
                                        )): null}
                                    </tbody>
                                </table>       
                            </div>                     
                        </div>
                    </div>
                </section>
            </React.Fragment>
        );
    }

}