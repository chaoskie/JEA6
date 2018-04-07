import * as React from "react";
import * as ReactDOM from "react-dom";
import { connect } from "react-redux";
import {Card, CardActions, CardHeader, CardText} from 'material-ui/Card';
import RaisedButton from 'material-ui/RaisedButton';
import ActionFavorite from 'material-ui/svg-icons/action/favorite';
import {pinkA100} from 'material-ui/styles/colors';

export const Kweet = ({kweet}) => (
    <Card style={{width: '500px', marginBottom: 20}}>
                            <CardHeader
                                title={kweet.user.displayname}
                                subtitle={'@'+kweet.user.username}
        />
        <CardText>
            {kweet.message}
        </CardText>
        <CardActions>
<RaisedButton label="Like" secondary={true} icon={<ActionFavorite color={pinkA100}/>} />
        </CardActions>
    </Card>
);