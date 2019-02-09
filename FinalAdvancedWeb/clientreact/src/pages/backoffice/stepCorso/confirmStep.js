import React from 'react';
import { Icon, Button } from 'semantic-ui-react';

export default function ConfirmStep(props) {
    return (
        <div style = {{ textAlign: 'center', paddingTop: 30, paddingBottom: 50 }}>
            <Icon name = 'share square' size = 'massive' className = 'confirmIcon'/>
            <Button inverted color = 'green' size = 'massive' onClick = {props.onClick}>Completa La Registazione Del Corso</Button>
        </div>
    );
}