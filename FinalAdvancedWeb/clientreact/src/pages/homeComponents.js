import React from 'react';
import { Link } from 'react-router-dom';

function Slider(props) {
    if (props.lingua === "it") {
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
    } else {
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
                            style={{zIndex: 6}}>Welcome
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
                            style={{zIndex: 6}}>in the
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
                            style={{zIndex: 6}}>best
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
                            style={{zIndex: 8}}>University
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
                            style={{zIndex: 8}}>of the
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
                            style={{zIndex: 6}}>future!
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
                            style={{zIndex: 7}}>Share your skills
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
                            style={{zIndex: 7}}> Improve your skills!
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
}

function UnderSlider(props) {
    if (props.lingua === "it") {
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
    } else {
        return (
            <section id="main-features">
                <div className="divider_top_black"></div>
                <div className="container">
                    <div className="row">
                        <div className=" col-md-10 col-md-offset-1 text-center">
                            <h2>Why Join Us?</h2>
                            <p className="lead">
                                Easy! Because we are the most important university of last years!
                            </p>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-6">
                            <div className="feature">
                                <i className="icon-trophy"></i>  
                                <h3>Expert teachers</h3>
                                <p>
                                    Our teachers got extraordinary curruculum!
                                </p>
                            </div>
                        </div>
                        <div className="col-md-6">
                            <div className="feature">
                                <i className=" icon-ok-4"></i>
                                <h3>Our Certifications</h3>
                                <p>
                                    Our certifications will allow you to work all around the world!
                                </p>
                            </div>
                        </div>
                        <div className="col-md-6">
                            <div className="feature">
                                <i className="icon-mic"></i>
                                <h3>+500 Audio lessons</h3>
                                <p>
                                    Everyone can follow our lessons!
                                </p>
                            </div>
                        </div>
                        <div className="col-md-6">
                            <div className="feature">
                                <i className="icon-video"></i>
                                <h3>+1.200 Video lessons</h3>
                                <p>
                                    Everyone needs a good teaching!
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        );
    }
}

function VariousThings(props) {
    if (props.lingua === "it") {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-3 col-sm-6">
                        <h4><Link to = 'Teachers'>Docenti</Link></h4>
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
                        <h4>Link Rapidi</h4>
                        <ul className="list_1">
                            <li><Link to = '/Home'>Homepage</Link></li>
                            <li><Link to = '/Courses'>Corsi</Link></li>
                            <li><Link to = '/Teachers'>Docenti</Link></li>
                        </ul>
                    </div>
                    
                </div>
                
                <hr className="add_bottom_30"/>
                <div className="row">
                    <div className="col-md-12">
                        <p><img src="img/sponsors.jpg" alt="Pic" className="img-responsive"/></p>
                    </div>
                </div>
            </div>
        );
    } else {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-3 col-sm-6">
                        <h4><Link to = 'Teachers'>Teachers</Link></h4>
                        <p><img src="img/pic_1.jpg" alt="Pic" className="img-responsive"/></p>
                        <p>We can't say other things on our teachers; they are incredibile!</p>
                    </div>
                    
                    <div className="col-md-3 col-sm-6">
                        <h4>Classrooms</h4>
                        <p><img src="img/pic_2.jpg" alt="Pic" className="img-responsive"/></p>
                        <p>We got the most advanced classrooms!</p>
                    </div>
                    
                    <div className="col-md-3 col-sm-6">
                        <h4>Study Rooms</h4>
                        <p><img src="img/pic_3.jpg" alt="Pic" className="img-responsive"/></p>
                        <p>And yeah, we also got some classrooms where you can study in peace</p>
                    </div>
                    
                    <div className="col-md-3 col-sm-6">
                        <h4>Quick Links</h4>
                        <ul className="list_1">
                            <li><Link to = '/Home'>Homepage</Link></li>
                            <li><Link to = '/Courses'>Courses</Link></li>
                            <li><Link to = '/Teachers'>Teachers</Link></li>
                        </ul>
                    </div>
                    
                </div>
                
                <hr className="add_bottom_30"/>
                <div className="row">
                    <div className="col-md-12">
                        <p><img src="img/sponsors.jpg" alt="Pic" className="img-responsive"/></p>
                    </div>
                </div>
            </div>
        );
    }
}

function Testimonials(props) {
    if (props.lingua === "it") {
        return (
            <section id="testimonials">
                <div className="container">
                    <div className="row">
                        <div className='col-md-offset-2 col-md-8 text-center'>
                            <h2>Coda dicono di noi</h2>
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
    } else {
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
                                                        Incredible!
                                                    </p>
                                                    <small>Someone Famous</small>
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
                                                        The Best Choice!
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
                                                        What a University Boys!
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

export {Slider, UnderSlider, VariousThings, Testimonials};