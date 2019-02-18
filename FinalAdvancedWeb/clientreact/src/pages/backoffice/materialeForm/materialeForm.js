import React, { Fragment } from 'react';
import { Form, Button } from 'semantic-ui-react';

export default function MaterialeForm(props) {
    return (
        <Fragment>
            <Form.Group widths = 'equal'>
                <Form.Input fluid name = 'nome' value = {props.materiale.nome} onChange = {props.handleChange} label = 'Nome Materiale' placeholder = 'Nome' required/>
                <Form.Input name = 'link' type = 'file' value = {props.materiale.link} onChange = {props.handleChange} label = 'File' required = {!props.isUpdate}/>
            </Form.Group>
            <Form.Group widths = 'equal'>
                <Form.TextArea name = 'descrizioneIt' label = 'Descrizione Materiale It' value = {props.materiale.descrizioneIt} onChange = {props.handleChange} placeholder = 'Descrizione Italiano'/>
                <Form.TextArea name = 'descrizioneEn' label = 'Descrizione Materiale En' value = {props.materiale.descrizioneEn} onChange = {props.handleChange} placeholder = 'Descrizione Inglese'/>
            </Form.Group>
            {
                props.isUpdate
                ?
                    <Form.Group widths = 'equal'>
                        <Form.Field>
                            <Button floated = 'left' onClick = {() => props.updateMateriale(props.materiale.idMateriale)} color = 'green' size = 'large' disabled = {!props.materiale.nome.trim()}>Conferma Modifica</Button>
                        </Form.Field>
                        <Form.Field>
                            <Button floated = 'right' onClick = {() => props.deleteMateriale(props.materiale.idMateriale)} negative size = 'large'>Cancella Materiale</Button>
                        </Form.Field>
                    </Form.Group>
                :
                    null
            }
        </Fragment>
    );
}