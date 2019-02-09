import React, { Fragment } from 'react';
import { Form } from 'semantic-ui-react';

export default function LinkStep(props) {
    return (
        <Fragment>
            <Form.Group widths = 'equal'>
                <Form.Input fluid name = 'homepage' value = {props.links.homepage ? props.links.homepage : ""} onChange = {(e, {name, value}) => props.handleEditorChange('links', name, value)} label = 'Homepage' placeholder = 'Homepage'/>
                <Form.Input fluid name = 'forum' value = {props.links.forum ? props.links.forum : ""} onChange = {(e, {name, value}) => props.handleEditorChange('links', name, value)} label = 'Forum' placeholder = 'Forum'/>
            </Form.Group>
            <Form.Group widths = 'equal'>
                <Form.Input fluid name = 'elearning' value = {props.links.elearning ? props.links.elearning : ""} onChange = {(e, {name, value}) => props.handleEditorChange('links', name, value)} label = 'E-Learning' placeholder = 'E-Learning'/>
                <Form.Input fluid name = 'risorseExt' value = {props.links.risorseExt ? props.links.risorseExt : ""} onChange = {(e, {name, value}) => props.handleEditorChange('links', name, value)} label = 'Risorse Esterne' placeholder = 'Risorse Esterne'/>
            </Form.Group>
        </Fragment>
    );
}