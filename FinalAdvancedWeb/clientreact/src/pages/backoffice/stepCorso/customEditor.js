import React, { Component, Fragment } from 'react';
import { Form, Accordion } from 'semantic-ui-react';
import Editor from 'react-quill';
import 'react-quill/dist/quill.snow.css';

export default class CustomEditor extends Component{

    step;
    
    componentWillMount() {
        this.step = this.props.step;
    }

    render() {
        var panels = [
            {
                key: this.props.objName, 
                title: this.props.title, 
                content: {
                    as: Editor,
                    placeholder: this.props.placeholder,
                    value: this.props.value ? this.props.value : "",
                    onChange: (value) => {
                        if (this.step !== this.props.step) {
                            this.step = this.props.step;
                        } else {
                            this.props.handleEditorChange(this.props.className, this.props.objName, value);
                        }
                    }
                }
            }
        ];
        return (
            <Fragment>
                <Accordion as = {Form.Field} panels = {panels} styled style = {{ width: '100%' }}/>
            </Fragment>
        );
    }
}