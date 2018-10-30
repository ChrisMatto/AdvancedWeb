import React from 'react';

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
    render() {
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


                    <li><a className="uppercase" /*onClick="bodychange('listcorsi', {{idcdl}})"*/>/*nomeIt*/</a></li>
                    

                
                <h4>Corsi di laurea Magistrale</h4>  
                    
                    <li><a className="uppercase" /*onClick="bodychange('listcorsi', {{idcdl}})"*/>/*nome*/</a></li>

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
                            <button className="btn-filter"><span class="icon-th-list"></span>Filtri</button>
                        </div>
                    </div>
                    <table className="table table-responsive table-striped">
                        <thead>
                            <tr className="filters">
                                
                                <th><input type="text" class="form-control" placeholder="Nome" disabled/></th>
                                <th><input type="text" class="form-control" placeholder="SSD" disabled /></th>
                                <th><input type="text" class="form-control" placeholder="CFU" disabled /></th>
                                <th><input type="text" class="form-control" placeholder="Lingua" disabled /></th>
                                <th><input type="text" class="form-control" placeholder="Semestre" disabled /></th>
                                <th><input type="text" class="form-control" placeholder="Tipologia" disabled /></th>
                                <th><input type="text" class="form-control" placeholder="CDL" disabled /></th>
                                <th><input type="text" class="form-control" placeholder="Docenti" disabled /></th>
                            </tr>
                        </thead>
                        <tbody id="courses">           
                            <tr>
                                
                                <td><strong><a href="DetailsCorso?n=${corso.ID}&lin=it">/*nome*/</a></strong></td>
                                <td>/*ssd*/</td>
                                <td>/*cfu*/</td>
                                <td>/*lingua*/</td>
                                <td>/*semestre*/</td>
                                <td>/*tipologia*/</td>
                                <td>
                                    /*abbrcdl*/
                                </td>
                                <td>
                                    /*cognomedocente*/
                                </td>
                            </tr>
                           
                        
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


export default ListaCorsi;