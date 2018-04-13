import * as React from "react";
import * as ReactDOM from "react-dom";

export const Kweet = ({user}) => (
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
        <div className="kweetDate">
            {dateDisplay(new Date(kweet.date))}
        </div>
        
        </CardText>
        <CardActions>
            <RaisedButton label="Like" secondary={true} icon={<ActionFavorite color={pinkA100}/>} />
        </CardActions>
    </Card>
);