import * as React from "react";
import * as ReactDOM from "react-dom";
import { connect } from "react-redux";
import {Card, CardActions, CardHeader, CardText} from 'material-ui/Card';
import RaisedButton from 'material-ui/RaisedButton';
import ActionFavorite from 'material-ui/svg-icons/action/favorite';
import {pinkA100} from 'material-ui/styles/colors';
import Avatar from 'material-ui/Avatar';
import { getUsernameFromJwt } from '../actions/users';

let dateDisplay = (date) => {
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString('en');
}

export const Kweet = ({kweet}) => (
    <Card className="kweetCard">
        <CardHeader
            avatar={kweet.user.profilePhoto
                ? <Avatar src={kweet.user.profilePhoto}></Avatar>
                : <Avatar>{kweet.user.displayname.split(' ').map(s => s[0]).join('')}</Avatar>}
            title={kweet.user.displayname}
            subtitle={'@'+kweet.user.username}
        />
        <CardText>
        {kweet.message}
        <div class="kweetDate">
            {dateDisplay(new Date(kweet.date))}
        </div>
        
        </CardText>
        <CardActions>
            <RaisedButton label="Like" secondary={true} icon={<ActionFavorite color={pinkA100}/>} />
        </CardActions>
    </Card>
);