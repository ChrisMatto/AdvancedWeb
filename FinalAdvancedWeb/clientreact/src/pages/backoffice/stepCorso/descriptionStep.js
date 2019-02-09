import React, { Fragment } from 'react';
import CustomEditor from './customEditor';

export default function DescriptionStep(props) {
    return (
        <Fragment>
            <CustomEditor 
                className = {props.className}
                objName = 'prerequisiti' 
                title = 'Prerequisiti Del Corso' 
                placeholder = 'Scrivi I Prerequisiti Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.descrizione.prerequisiti}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'obiettivi' 
                title = 'Obiettivi Del Corso' 
                placeholder = 'Scrivi Gli Obiettivi Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.descrizione.obiettivi}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'modEsame' 
                title = "Modalità D'Esame Del Corso" 
                placeholder = "Scrivi le Modalità D'Esame Qui"
                handleEditorChange = {props.handleEditorChange}
                value = {props.descrizione.modEsame}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'modInsegnamento' 
                title = "Modalità D'Insegnamento Del Corso" 
                placeholder = "Scrivi Le Modalità D'Insegnamento Qui"
                handleEditorChange = {props.handleEditorChange}
                value = {props.descrizione.modInsegnamento}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'sillabo' 
                title = 'Sillabo Del Corso' 
                placeholder = 'Scrivi Il Sillabo Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.descrizione.sillabo}
                step = {props.step}
            />
            <CustomEditor 
                className = {props.className}
                objName = 'note' 
                title = 'Note Extra Sul Corso' 
                placeholder = 'Scrivi Le Note Qui'
                handleEditorChange = {props.handleEditorChange}
                value = {props.descrizione.note}
                step = {props.step}
            />
        </Fragment>
    );
}