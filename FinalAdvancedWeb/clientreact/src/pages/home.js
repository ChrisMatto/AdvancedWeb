import React from 'react';

class Home extends React.Component {
    constructor() {
        super();
        this.state = {
            cdl: [],
            cdlm: [],
        };
        if (localStorage.getItem('cdlList') != null) {
            this.setState({
                cdl: JSON.parse(localStorage.getItem('cdlList')),
            });
        } else {
            fetch('http://localhost:8080/AdvancedWeb/rest/cdl/triennale')
                .then(res => res.json())
                .then((result) => {
                    var cdlList = [];
                    for (var c in result) {
                        fetch(result[c])
                            .then(res => res.json())
                            .then((result) => {
                                cdlList.push(result);
                                localStorage.setItem('cdlList', JSON.stringify(cdlList));
                                this.setState({
                                    cdl: cdlList,
                                });
                            });
                    }
                });
        }
        if (localStorage.getItem('cdlmList') != null) {
            this.setState({
                cdlm: JSON.parse(localStorage.getItem('cdlmList')),
            });
        } else {
            fetch('http://localhost:8080/AdvancedWeb/rest/cdl/magistrale')
                .then(res => res.json())
                .then((result) => {
                    var cdlmList = [];
                    for (var c in result) {
                        fetch(result[c])
                            .then(res => res.json())
                            .then((result) => {
                                cdlmList.push(result);
                                localStorage.setItem('cdlmList', JSON.stringify(cdlmList));
                                this.setState({
                                    cdlm: cdlmList,
                                });
                            });
                    }
                });
        }
    }
    render() {
        var cdl = this.state.cdl.slice().sort(function() {return 0.5 - Math.random()});
        var cdlm = this.state.cdlm.slice().sort(function() {return 0.5 - Math.random()});
        return <div></div>;
    }
}

function Cdl(props) {
    
}

export default Home;