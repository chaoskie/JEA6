import * as React from "react";
import {Card, CardActions, CardHeader, CardText} from 'material-ui/Card';
import RaisedButton from 'material-ui/RaisedButton';
import ActionFavorite from 'material-ui/svg-icons/action/favorite';
import {pinkA100} from 'material-ui/styles/colors';
import Avatar from 'material-ui/Avatar';
//import { getUernameFromJwt } from '../actions/users';
import { Link } from 'react-router-dom';
import ActionDelete from 'material-ui/svg-icons/action/delete';


let dateDisplay = (date) => {
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString('en');
} 

export const Kweet = ({kweet, likeKweet, canLike, loggedIn, deleteKweet}) => (
    <Card className="kweetCard">
        <CardHeader
            avatar={kweet.user.profilePhoto
                ? <Avatar src={kweet.user.profilePhoto}></Avatar>
                : <Avatar>{kweet.user.displayname.split(' ').map(s => s[0]).join('')}</Avatar>}
            title={kweet.user.displayname}
            subtitle={'@'+kweet.user.username}
        />
        <CardText>

            
        { kweet.message.replace(/#(\S*)/g, <Link to={`#$1`} activeClassName="active">$1</Link>)
            /*kweet.message  + '  ' + <link to={'https://www.google.com'}> 'crap' </link>*/ } 
        <div className="kweetDate">
            {dateDisplay(new Date(kweet.date))}
        </div>

        
        </CardText>
        <CardActions>
            <RaisedButton label={canLike() ? "Unlike":"Like"} secondary={true} icon={<ActionFavorite color={pinkA100}/>} onClick={likeKweet} disabled={!loggedIn}  />
            <RaisedButton label={"Remove"} secondary={true} icon={<ActionDelete/>} onClick={deleteKweet} disabled={!loggedIn}  />
        </CardActions>
    </Card>
);

