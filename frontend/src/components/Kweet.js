import * as React from "react";
import * as ReactDOM from "react-dom";
import { connect } from "react-redux";
import {Card, CardActions, CardHeader, CardText} from 'material-ui/Card';
import RaisedButton from 'material-ui/RaisedButton';
import ActionFavorite from 'material-ui/svg-icons/action/favorite';
import {pinkA100} from 'material-ui/styles/colors';
import Avatar from 'material-ui/Avatar';

let dateDisplay = (date) => {
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString('en');
}

export const Kweet = ({kweet}) => (
    <Card style={{width: '500px', marginBottom: 20}}>
                            <CardHeader
                                avatar={kweet.user.profilePhoto ? <Avatar src={kweet.user.profilePhoto}></Avatar> : <Avatar>{kweet.user.displayname.split(' ').map(s => s[0]).join('')}</Avatar>}
                                title={kweet.user.displayname}
                                subtitle={'@'+kweet.user.username}
        />
        <CardText>
        {kweet.message}
        <div style={{
            position: 'relative',
            top: '-90px',
            right: '-340px',
            color: 'rgba(0, 0, 0, 0.54)'}}>
                {dateDisplay(new Date(kweet.date))}
        </div>
        
        </CardText>
        <CardActions>
            <RaisedButton label="Like" secondary={true} icon={<ActionFavorite color={pinkA100}/>} />
        </CardActions>
    </Card>
);