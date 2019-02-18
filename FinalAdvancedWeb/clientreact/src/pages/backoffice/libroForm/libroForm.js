import React, { Fragment } from 'react';
import { Form, Button } from 'semantic-ui-react';

export default function LibroForm(props) {
    return (
        <Fragment>
            <Form.Group widths = 'equal'>
                <Form.Input fluid name = 'titolo' value = {props.libro.titolo} onChange = {props.handleChange} label = 'Titolo Libro' placeholder = 'Titolo' required/>
                <Form.Input fluid name = 'autore' value = {props.libro.autore} onChange = {props.handleChange} label = 'Autore Libro' placeholder = 'Autore' required/>
            </Form.Group>
            <Form.Group widths = 'equal'>
                <Form.Input type = 'number' fluid name = 'anno' value = {props.libro.anno} onChange = {props.handleChange} label = 'Anno Pubblicazione Libro' placeholder = 'Anno' required/>
                <Form.Input type = 'number' fluid name = 'volume' value = {props.libro.volume} onChange = {props.handleChange} label = 'Volume Libro' placeholder = 'Volume'/>
            </Form.Group>
            <Form.Group widths = 'equal'>
                <Form.Input fluid name = 'editore' value = {props.libro.editore} onChange = {props.handleChange} label = 'Editore Libro' placeholder = 'Editore'/>
                <Form.Input fluid name = 'link' value = {props.libro.link} onChange = {props.handleChange} label = 'Link Libro' placeholder = 'Link'/>
            </Form.Group>
            {
                props.isUpdate
                ?
                    <Form.Group widths = 'equal'>
                        <Form.Field>
                            <Button floated = 'left' onClick = {() => props.updateLibro(props.libro.idLibro)} color = 'green' size = 'large' disabled = {!props.libro.titolo.trim() || !props.libro.autore.trim() || !props.libro.anno}>Conferma Modifica</Button>
                        </Form.Field>
                        <Form.Field>
                            <Button floated = 'right' onClick = {() => props.deleteLibro(props.libro.idLibro)} negative size = 'large'>Cancella Libro</Button>
                        </Form.Field>
                    </Form.Group>
                :
                    null
            }
        </Fragment>
    );
}