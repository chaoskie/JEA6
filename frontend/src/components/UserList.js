import React, { Component } from 'react';
import {Card, CardHeader} from 'material-ui/Card';
import Avatar from 'material-ui/Avatar';

export default class UserList extends Component {
    render() {
        if (this.props.hasErrored) {
            return <p>Sorry! There was an error loading the users</p>;
        }
        if (this.props.isLoading) {
            return <p>Loading…</p>;
        }
        return (
            <div>
                {this.props.users.map((u) => (
                    <div key={u.id}>
                        <Card>
                            <CardHeader
                                avatar={u.profilePhoto ? <Avatar src={u.profilePhoto}></Avatar> : <Avatar>{u.displayname.split(' ').map(s => s[0]).join('')}</Avatar>}
                                title={u.displayname}
                                subtitle={'@'+u.username}
                            />
                        </Card>
                    </div>
                ))}
            </div>
        );
    }
}