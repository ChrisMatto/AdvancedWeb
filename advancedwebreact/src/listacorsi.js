import React from 'react';
import compareStrings from './js/compareStrings';

class ListaCorsi extends React.Component {
    render() {
        return(
        <div>
            <Banner />
            <Body />
        </div>
        );
    }
}

class Banner extends React.Component {
    render() {
        return(
            <section id="sub-header">
    <div className="container">
        <div className="row">
            <div className="col-md-10 col-md-offset-1 text-center">
                <h1>Lista dei corsi</h1>
                <p className="lead boxed ">Esplora la lista dei nostri fantastici corsi</p>
                <p className="lead">
                     
                </p>
            </div>
        </div>
    </div>
    <div className="divider_top"></div>
    </section>
        );
    }
}

class Body extends React.Component {
    constructor() {
        super();
        this.state = {
            cdl: [],
            cdlm: [],
            corsi: [],
        };
        fetch('http://localhost:8080/AdvancedWeb/rest/cdl/triennale')
        .then(res => res.json())
        .then((result) => {
            var array = [];
            for (var c in result) {
                array.push(result[c]);
            }
            array = array.sort(function(a,b){return compareStrings(a.nomeIt,b.nomeIt);});
            this.setState({
                cdl: array,
            });
        });
        fetch('http://localhost:8080/AdvancedWeb/rest/cdl/magistrale')
        .then(res => res.json())
        .then((result) => {
            var array = [];
            for (var c in result) {
                array.push(result[c]);
            }
            array = array.sort(function(a,b){return compareStrings(a.nomeIt,b.nomeIt);});
            this.setState({
                cdlm: array,
            });
        });
        fetch('http://localhost:8080/AdvancedWeb/rest/courses/current')
        .then(res => res.json())
        .then((result) => {
            var array = [];
            for (var c in result) {
                fetch(result[c])
                .then(res => res.json())
                .then((result) => {
                    array.push(result);
                    array = array.sort(function(a,b){return compareStrings(a.nomeIt,b.nomeIt);});
                    this.setState({
                        corsi: array,
                    });
                });
            }
        });
    }

    render() {
        var cdl = this.state.cdl.slice();
        var cdlm = this.state.cdlm.slice();
        var corsi = this.state.corsi.slice();
        var CdlNames = [];
        var CdlmNames = [];
        var CorsiList = [];

        for (var c in cdl) {
            CdlNames.push(Cdl(cdl[c]));
        }

        for (var c in cdlm) {
            CdlmNames.push(Cdl(cdlm[c]));
        }

        for (var c in corsi) {
            CorsiList.push(CorsoRow(corsi[c]));
        }

        return(
            <section id="main_content" >
    	<div className="container">
        
        <ol className="breadcrumb">
          <li><a /*onclick="bodychange('home')"*/>Home</a></li>
          <li className="active">Lista corsi</li>
        </ol>
        
        <div className="row">
        
        <aside className="col-lg-3 col-md-4 col-sm-4">
            <div className="box_style_1">
                
              
            <ul className="submenu-col">
                <li><a /*onClick="bodychange('listcorsi')">Tutti i corsi*//></li>
                <br></br>
                <h4>Corsi di laurea Triennale</h4>
                    {CdlNames}
                <h4>Corsi di laurea Magistrale</h4>  
                    {CdlmNames}

            </ul>
            
                <hr/>
            </div>
        </aside>
        
        <div className="col-lg-9 col-md-8 col-sm-8">
       
            <h3 align="center">Tutti i corsi</h3>
            <p></p>

                <div className="panel panel-info filterable add_bottom_45">
                    <div className="panel-heading">
                        <h3 className="panel-title">Corsi</h3>
                        <div className="pull-right">
                            <button className="btn-filter"><span className="icon-th-list"></span>Filtri</button>
                        </div>
                    </div>
                    <table className="table table-responsive table-striped">
                        <thead>
                            <tr className="filters">
                                
                                <th><input type="text" className="form-control" placeholder="Nome" disabled/></th>
                                <th><input type="text" className="form-control" placeholder="SSD" disabled /></th>
                                <th><input type="text" className="form-control" placeholder="CFU" disabled /></th>
                                <th><input type="text" className="form-control" placeholder="Lingua" disabled /></th>
                                <th><input type="text" className="form-control" placeholder="Semestre" disabled /></th>
                                <th><input type="text" className="form-control" placeholder="Tipologia" disabled /></th>
                                <th><input type="text" className="form-control" placeholder="CDL" disabled /></th>
                                <th><input type="text" className="form-control" placeholder="Docenti" disabled /></th>
                            </tr>
                        </thead>
                        <tbody>                      
                            {CorsiList}
                        </tbody>
                </table>
                </div>
                    
      
 
        </div>                       
        </div>
        
        <hr/>
       
            	
        </div>
    </section>
        );
    }
}

function Cdl(cdl) {
    return (
         <li key={cdl.idcdl}><a className="uppercase" /*onClick="bodychange('listcorsi', {{idcdl}})"*/>{cdl.nomeIt}</a></li>
    );
}

function CorsoRow(corso) {
    return (
        <tr key = {corso.idCorso}>                          
        <td><strong><a href="DetailsCorso?n=${corso.ID}&lin=it">{corso.nomeIt}</a></strong></td>
        <td>{corso.ssd}</td>
        <td>{corso.cfu}</td>
        <td>{corso.lingua}</td>
        <td>{corso.semestre}</td>
        <td>{corso.tipologia}</td>
        <td>
            /*abbrcdl*/
        </td>
        <td>
            /*cognomedocente*/
        </td>
    </tr>
    );
}

export default ListaCorsi;