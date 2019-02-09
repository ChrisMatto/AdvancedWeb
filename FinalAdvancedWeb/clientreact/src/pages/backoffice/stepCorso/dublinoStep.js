import React, { Fragment } from 'react';
import CustomEditor from './customEditor';

export default function DublinoStep(props) {
    return (
        <Fragment>
            <CustomEditor 
                className = {props.className}
                objName = 'knowledge' 
                title = 'Knowledge' 
                placeholder = 'Knowledge Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.dublino.knowledge}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'application' 
                title = 'Application' 
                placeholder = 'Application Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.dublino.application}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'evaluation' 
                title = 'Evaluation' 
                placeholder = 'Evaluation Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.dublino.evaluation}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'communication' 
                title = 'Communication' 
                placeholder = 'Communication Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.dublino.communication}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'lifelong' 
                title = 'Lifelong' 
                placeholder = 'Lifelong Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.dublino.lifelong}
                step = {props.step}
            />
        </Fragment>
    );
}