import React from 'react';
import ReactDOM from 'react-dom';
import ListaCorsi from './listacorsi';

class Page extends React.Component {

    constructor() {
        super();
        this.state = {
            page: "home",
        };
        this.changePage = this.changePage.bind(this);
        this.PageBody = this.PageBody.bind(this);
    }

    changePage(page) {
        switch (page) {
            case "listacorsi":
            this.setState({
                page: "listacorsi",
            });
            break;
            case "home":
            this.setState({
                page: "home",
            });
            break;
            case "reload":
            this.setState({
                page: "reload",
            });
            break;
        }
    }

    PageBody() {
        switch (this.state.page) {
            case "home":
                return (
                    <div>
                        <Body/>
                        <Testimonials/>
                    </div>
                );
            case "listacorsi":
                return (
                    <ListaCorsi/>
                );
            case "reload":
                    window.location.reload();
        }
    }

    render() {
        return (
            <div>
                <Header />
                <Nav onClick={this.changePage} />
                <this.PageBody />
                <Footer />
            </div>
        );
        
    }
}

class Header extends React.Component {
    render() {
        return (
            <header>
                <div className="container">
                    <div className="row">
                        <div className="col-md-2 col-sm-2 col-xs-2">
                            <a href="#" id="logo">Learn</a>
                        </div>
                        <div className="col-md-10 col-sm-10 col-xs-10"> 
                            <div className="pull-right">
                                <a href="#" className="button_top" id="login_top">Login</a>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
        );
    }
}

class Nav extends React.Component {
    render() {
        return(
            <nav>
                <div className="container">
                    <div className="row">
                        <div className="col-md-12">
                            <ul className="sf-menu">
                                    <li>
                                        <a onClick={() => this.props.onClick("reload")}>Home</a>
                                    </li>
                                    <li>
                                        <a onClick={() => this.props.onClick("listacorsi")}>Corsi</a>
                                    </li>
                                    <li>
                                        <a /*onClick={}*/>Insegnanti</a>
                                    </li>              
                                    <div className="pull-right">                           
                                                <a href="#" className="lnbar">
                                                    <img alt="flag" id="flag" src="data:image/png;base64,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"
                                                    /> English Version</a>             
                                    </div>               
                            </ul>
                        </div>
                    </div>
                </div>
            </nav>
        );
    }
}

class Body extends React.Component {
    constructor() {
        super();
        this.state = {
            cdl: [],
            cdlm: [],
        };
        fetch('http://localhost:8080/AdvancedWeb/rest/cdl/triennaleRandom')
        .then(res => res.json())
        .then((result) => {
            var array = [];
            for (var c in result) {
                array.push(result[c]);
            }
            this.setState({
                cdl: array,
            });
        });
        fetch('http://localhost:8080/AdvancedWeb/rest/cdl/magistraleRandom')
        .then(res => res.json())
        .then((result) => {
            var array = [];
            for (var c in result) {
                array.push(result[c]);
            }
            this.setState({
                cdlm: array,
            });
        });
    }
    render() {
        var cdl = this.state.cdl.slice();
        var cdlm = this.state.cdlm.slice();
        var cdlRows = [];
        var cdlmRows = [];

        for (var c in cdl) {
            cdlRows.push(Cdl(cdl[c]));
        }
        for (var cm in cdlm) {
            cdlmRows.push(Cdl(cdlm[cm]));
        }

        return (
            <div>
            <Slider />
            <UnderSlider />
            <Section section = {'cdl'} rows = {cdlRows}/>
            <Section section = {'cdlm'} rows = {cdlmRows}/>
            </div>
        );
    }
}

class Testimonials extends React.Component {
    render() {
        return (
            <section id="testimonials">
    <div className="container">
        <div className="row">
            <div className='col-md-offset-2 col-md-8 text-center'>
                <h2>What they say</h2>
            </div>
        </div>
        <div className='row'>
            <div className='col-md-offset-2 col-md-8'>
                <div className="carousel slide" data-ride="carousel" id="quote-carousel">
                    
                    <ol className="carousel-indicators">
                        <li data-target="#quote-carousel" data-slide-to="0" className="active"></li>
                        <li data-target="#quote-carousel" data-slide-to="1"></li>
                        <li data-target="#quote-carousel" data-slide-to="2"></li>
                    </ol>
                    
                    <div className="carousel-inner">
                        
                        <div className="item active">
                            <blockquote>
                                <div className="row">
                                    <div className="col-sm-3 text-center">
                                        <img className="img-circle" src="img/testimonial_1.jpg" alt=""/>
                                    </div>
                                    <div className="col-sm-9">
                                        <p>
                                            Incredibile!
                                        </p>
                                        <small>Qualcuno di Famoso</small>
                                    </div>
                                </div>
                            </blockquote>
                        </div>
                        
                        <div className="item">
                            <blockquote>
                                <div className="row">
                                    <div className="col-sm-3 text-center">
                                        <img className="img-circle" src="img/testimonial_2.jpg" alt=""/>
                                    </div>
                                    <div className="col-sm-9">
                                        <p>
                                            La migliore scelta!
                                        </p>
                                        <small>Carla</small>
                                    </div>
                                </div>
                            </blockquote>
                        </div>
                        
                        <div className="item">
                            <blockquote>
                                <div className="row">
                                    <div className="col-sm-3 text-center">
                                        <img className="img-circle" src="img/testimonial_1.jpg" alt=""/>
                                    </div>
                                    <div className="col-sm-9">
                                        <p>
                                            Che universit&#224; ragazzi!
                                        </p>
                                        <small>Roberta</small>
                                    </div>
                                </div>
                            </blockquote>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
   </section>
        );
    }
}

class Footer extends React.Component {
    render() {
        return (
            <footer>

<hr></hr>

<div className="container" id="nav-footer">
    <div className="row text-left">
        <div className="col-md-4 col-sm-4">
            <h4>Browse</h4>
            <ul>
                <li>
                    <a href="#" /*onclick="bodychange('listcorsi')"*/>Corsi</a>
                </li>
                <li>
                    <a href="#" /*onclick="bodychange('docenti')"*/>Insegnanti</a>
                </li>
                
            </ul>
        </div>
        
        <div className="col-md-4 col-sm-4">
            <h4>Next Courses</h4>
            <ul>
                <li>
                    <a href="#" /*onclick="bodychange('listcorsi')"*/>Tutti i corsi</a>
                </li>
                
            </ul>
        </div>
        
        <div className="col-md-4 col-sm-4">
            <h4>About Learn</h4>
            <ul>
                <li>
                    <a href="#">About Us</a>
                </li>
                <li>
                    <a href="#">Apply</a>
                </li>
                <li>
                    <a href="#">Terms and conditions</a>
                </li>
                <li>
                    <a href="#">Register</a>
                </li>
            </ul>
        </div>
    </div>
    
</div>
<div id="copy_right">Page compiled on </div>
</footer>
        );
    }
}

function Section(props) {
    if(props.section === 'cdl') {
        return (
            <section id="main_content_gray">
    <div className="container">
        <div className="row">
        <div className="col-md-12 text-center">
            <h2>I Nostri Corsi di Laurea</h2>
            <p className="lead"> Esplora la nostra offerta formativa </p>
        </div>
    </div>

    {props.rows}
    
         <a href="#" /*onclick="bodychange('listcorsi')"*/ className="button_medium_outline pull-right">Vedi tutti i corsi</a>

        </div>  
    </section>
        );
    } else {
        return (
            <section id="main_content">
    <div className="container">
    <div className="row add_bottom_30">
        <div className="col-md-12 text-center">
        <h2>Visita i nostri corsi Di laurea magistrali</h2>
        
        </div>
    </div>
    
    {props.rows}
    
    
         <a href="#" /*onclick="bodychange('liscorsi')"*/ className="button_medium_outline pull-right">Vedi Tutti I Corsi</a>
 

        </div> 
    
       <hr></hr>

       <div className="container">
      <div className="row">
          <div className="col-md-3 col-sm-6">
            <h4><a href="#" /*onclick="bodychange('docenti')"*/>Docenti</a></h4>
            <p><img src="img/pic_1.jpg" alt="Pic" className="img-responsive"/></p>
            <p>Non c'&#232; molto altro da dire sui nostri docenti, se non che sono incredibili!</p> 
        </div>
        
        <div className="col-md-3 col-sm-6">
            <h4>Classi</h4>
            <p><img src="img/pic_2.jpg" alt="Pic" className="img-responsive"/></p>
            <p>Le nostre aule sono le pi&#249; tecnologiche!</p> 
        </div>
        
        <div className="col-md-3 col-sm-6">
            <h4>Aule studio</h4>
            <p><img src="img/pic_3.jpg" alt="Pic" className="img-responsive"/></p>
            <p>E si, abbiamo anche delle aule dove si pu&#242; studiare in pace.</p> 
        </div>
        
        <div className="col-md-3 col-sm-6">
            <h4>Link rapidi</h4>
            <ul className="list_1">
                  <li><a href="#" /*onclick="bodychange('home')"*/>Homepage</a></li>
                  <li><a href="#" /*onclick="bodychange('listcorsi')"*/>Corsi</a></li>
                  <li><a href="#" /*onclick="bodychange('docenti')"*/>Docenti</a></li>
             </ul>
        </div>
        
    </div>
    
    <hr className="add_bottom_30"></hr>
    <div className="row">
        <div className="col-md-12">
            <p><img src="img/sponsors.jpg" alt="Pic" className="img-responsive"/></p>
       </div>
    </div>
    </div>
</section>
        );
    }
}

function Cdl(cdl) {
    return(
        <div  className="col-lg-3 col-md-6 col-sm-6" key = {cdl["idcdl"]}>
            <div className="col-item">
                <div className="photo">
                    <a href="#" /*onClick="bodychange('listcorsi', {{idcdl}});document.body.scrollTop = 0;document.documentElement.scrollTop = 0;"*/><img src={cdl['immagine']} alt="cdlimmagine"/></a>
                    <div className="cat_row" >{cdl['nomeIt']}<span className="pull-right"><i className="fas fa-university"></i></span></div>
                </div>
                <div className="info">
                    <div className="row">
                        <div className="course_info col-md-12 col-sm-12">
                            
                            <p>{cdl['descrizioneIt']}</p>

                        <div className="price pull-right" id="cfuCdl">{cdl['cfu']} CFU</div>
                        </div>
                    </div>
                    <div className="separator clearfix">
                        <p className="btn-add" id="cdlink"><a href="#" /*onClick="bodychange('listcorsi', {{idcdl}});document.body.scrollTop = 0;document.documentElement.scrollTop = 0;"*/><i className="icon-export-4"></i></a></p>
                    </div>
                </div>
            </div>
        </div>
    );
}

function Slider() {
    return (
        <section className="tp-banner-container">
        <div className="tp-banner" >
            <ul className="sliderwrapper">
                <li data-transition="fade" data-slotamount="4" data-masterspeed="1500" >
                    
                    <img src="sliderimages/slide_5.jpg" alt="slidebg1"  data-bgfit="cover" data-bgposition="left top" data-bgrepeat="no-repeat"/>
                    
    
                    
                    <div className="tp-caption skewfromrightshort customout"
                        data-x="center"
                        data-y="center"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="500"
                        data-start="1200"
                        data-easing="Power4.easeOut"
                        data-endspeed="500"
                        data-endeasing="Power4.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 2}}><img src="sliderimages/logo.png" alt=""/>
                    </div>
                    
                    
                    <div className="tp-caption medium_bg_darkblue skewfromleft customout"
                        data-x="left"
                        data-y="190"
                        data-hoffset="30"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="800"
                        data-start="1500"
                        data-easing="Power4.easeOut"
                        data-endspeed="300"
                        data-endeasing="Power1.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 6}}>Benvenuto
                    </div>
    
                    
                    <div className="tp-caption medium_bg_darkblue skewfromleft customout"
                        data-x="left"
                        data-y="245"
                        data-hoffset="30"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="800"
                        data-start="1500"
                        data-easing="Power4.easeOut"
                        data-endspeed="300"
                        data-endeasing="Power1.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 6}}>Nella
                    </div>
                    
                    
                    <div className="tp-caption medium_bg_darkblue skewfromleft customout"
                        data-x="left"
                        data-y="300"
                        data-hoffset="30"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="800"
                        data-start="1500"
                        data-easing="Power4.easeOut"
                        data-endspeed="300"
                        data-endeasing="Power1.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 6}}>Migliore
                    </div>
    
                    
                    <div className="tp-caption medium_bg_darkblue skewfromright customout"
                        data-x="right"
                        data-y="190"
                        data-hoffset="-30"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="800"
                        data-start="1800"
                        data-easing="Power4.easeOut"
                        data-endspeed="300"
                        data-endeasing="Power1.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 8}}>Universit&#224;
                    </div>
    
                    
                    <div className="tp-caption medium_bg_darkblue skewfromright customout"
                        data-x="right"
                        data-y="245"
                        data-hoffset="-30"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="800"
                        data-start="1800"
                        data-easing="Power4.easeOut"
                        data-endspeed="300"
                        data-endeasing="Power1.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 8}}>Del
                    </div>
                    
                    
                    <div className="tp-caption medium_bg_darkblue skewfromleft customout"
                        data-x="right"
                        data-y="300"
                        data-hoffset="-30"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="800"
                        data-start="1800"
                        data-easing="Power4.easeOut"
                        data-endspeed="300"
                        data-endeasing="Power1.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 6}}>Futuro!
                    </div>
                </li>
                
                
                <li data-transition="zoomout" data-slotamount="4" data-masterspeed="1000" >
                    
                    <img src="sliderimages/slide_6.jpg" alt="slidebg2"  data-bgfit="cover" data-bgposition="left top" data-bgrepeat="no-repeat"/>
                    
    
                   
                    <div className="tp-caption medium_light_white skewfromrightshort customout"
                        data-x="80"
                        data-y="96"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="500"
                        data-start="800"
                        data-easing="Back.easeOut"
                        data-endspeed="500"
                        data-endeasing="Power4.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 4}}>Enjoy
                    </div>
    
                   
                    <div className="tp-caption medium_thin_white skewfromleftshort customout"
                        data-x="235"
                        data-y="110"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="500"
                        data-start="900"
                        data-easing="Back.easeOut"
                        data-endspeed="500"
                        data-endeasing="Power4.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 5}}>&
                    </div>
    
                    
                    <div className="tp-caption large_bold_white skewfromleftshort customout"
                        data-x="80"
                        data-y="152"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="300"
                        data-start="1100"
                        data-easing="Back.easeOut"
                        data-endspeed="500"
                        data-endeasing="Power4.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 7}}>Condividi le tue esperienze
                    </div>
    
                    
                    <div className="tp-caption small_thin_white  customin customout"
                        data-x="80"
                        data-y="240"
                        data-customin="x:0;y:100;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:1;scaleY:3;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:0% 0%;"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="500"
                        data-start="1300"
                        data-easing="Power4.easeOut"
                        data-endspeed="500"
                        data-endeasing="Power4.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 8}}>
                    </div>
                </li>
                
                
                <li data-transition="cube-horizontal" data-slotamount="4" data-masterspeed="1000" >
                   
                    <img src="sliderimages/slide_1.jpg" alt="slidebg3"  data-bgfit="cover" data-bgposition="left top" data-bgrepeat="no-repeat"/>
                    
                    <div className="tp-caption medium_light_white skewfromrightshort customout"
                        data-x="80"
                        data-y="96"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="500"
                        data-start="800"
                        data-easing="Back.easeOut"
                        data-endspeed="500"
                        data-endeasing="Power4.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 4}}>Divertiti
                    </div>
    
                    
                    <div className="tp-caption medium_thin_white skewfromleftshort customout"
                        data-x="235"
                        data-y="110"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="500"
                        data-start="900"
                        data-easing="Back.easeOut"
                        data-endspeed="500"
                        data-endeasing="Power4.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 5}}>
                    </div>
    
                 
                    <div className="tp-caption large_bold_white skewfromleftshort customout"
                        data-x="80"
                        data-y="152"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="300"
                        data-start="1100"
                        data-easing="Back.easeOut"
                        data-endspeed="500"
                        data-endeasing="Power4.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 7}}> e migliora le tue abilit&#224;!
                    </div>
    
                    
                    <div className="tp-caption small_thin_white  customin customout"
                        data-x="80"
                        data-y="240"
                        data-customin="x:0;y:100;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:1;scaleY:3;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:0% 0%;"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="500"
                        data-start="1300"
                        data-easing="Power4.easeOut"
                        data-endspeed="500"
                        data-endeasing="Power4.easeIn"
                        data-captionhidden="on"
                        style={{zIndex: 8}}>
                    </div>
                </li>
                
                 
                <li data-transition="zoomin" data-slotamount="4" data-masterspeed="1000" >
                    
                    <img src="sliderimages/slide_7.jpg" alt="slidebg4"  data-bgfit="cover" data-bgposition="left top" data-bgrepeat="no-repeat"/>

                    <div className="tp-caption large_bold_white customin customout"
                        data-x="center" data-hoffset="0"
                        data-y="160"
                        data-customin="x:0;y:0;z:0;rotationX:90;rotationY:0;rotationZ:0;scaleX:1;scaleY:1;skewX:0;skewY:0;opacity:0;transformPerspective:200;transformOrigin:50% 0%;"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="1000"
                        data-start="1700"
                        data-easing="Back.easeInOut"
                        data-endspeed="300"
                        style={{zIndex: 11}}>
                    </div>
    
                    <div className="tp-caption mediumlarge_light_white_center customin customout"
                        data-x="center" data-hoffset="0"
                        data-y="bottom" data-voffset="-150"
                        data-customin="x:0;y:0;z:0;rotationX:90;rotationY:0;rotationZ:0;scaleX:1;scaleY:1;skewX:0;skewY:0;opacity:0;transformPerspective:200;transformOrigin:50% 0%;"
                        data-customout="x:0;y:0;z:0;rotationX:0;rotationY:0;rotationZ:0;scaleX:0.75;scaleY:0.75;skewX:0;skewY:0;opacity:0;transformPerspective:600;transformOrigin:50% 50%;"
                        data-speed="1000"
                        data-start="1900"
                        data-easing="Back.easeInOut"
                        data-endspeed="300"
                        style={{zIndex: 12}}>
                    </div>
                </li>
                
            </ul>
        </div>
    </section>
    );
}

function UnderSlider() {
    return (
        <section id="main-features">
    <div className="divider_top_black"></div>
    <div className="container">
        <div className="row">
            <div className=" col-md-10 col-md-offset-1 text-center">
                <h2>Perch&#233; unirsi a noi?</h2>
                <p className="lead">
                   Semplice! Perch&#233; rappresentiamo la pi&#249; importante universit&#224; degli ultimi tempi!
                </p>
            </div>
        </div>
        <div className="row">
            <div className="col-md-6">
                <div className="feature">
                    <i className="icon-trophy"></i>  
                    <h3>Insegnanti Esperti</h3>
                    <p>
                        I nostri insegnanti vantano un curriculum davvero straordinario! Pensate che alcuni hanno lavorato per la NASA!
                    </p>
                </div>
            </div>
            <div className="col-md-6">
                <div className="feature">
                    <i className=" icon-ok-4"></i>
                    <h3>Le Nostre Certificazioni</h3>
                    <p>
                        Chiunque ottenga una certificazione nella nostra universit&#224 diventa automaticamente ricercato in tutto il mondo per il suo lavoro!
                    </p>
                </div>
            </div>
            <div className="col-md-6">
                <div className="feature">
                    <i className="icon-mic"></i>
                    <h3>+500 Lezioni Audio</h3>
                    <p>
                        Permettiamo a chiunque di seguire le nostre lezioni, anche se &#232; a miglia di distanza!
                    </p>
                </div>
            </div>
            <div className="col-md-6">
                <div className="feature">
                    <i className="icon-video"></i>
                    <h3>+1.200 Lezioni Video</h3>
                    <p>
                        Le nostre lezioni audio e video permettono a chiunque di poter avere un'adeguata spiegazione! Anche per coloro che non hanno la possibilit&#224; di seguire le lezioni!
                    </p>
                </div>
            </div>
        </div>
        </div>
    </section>
    );
}

ReactDOM.render(
    <Page />,
    document.getElementById('root')
);
