import React, { Component } from 'react';
import {Card, CardActions, CardHeader, CardText} from 'material-ui/Card';
import RaisedButton from 'material-ui/RaisedButton';
import { connect } from 'react-redux';
import { usersFetchAll } from '../actions/users';
import Avatar from 'material-ui/Avatar';
import DeviceGPS from 'material-ui/svg-icons/device/gps-fixed';
import Website from 'material-ui/svg-icons/action/language';

export default class UserList extends Component {
    // componentDidMount() {
    //     this.props.dispatch(usersFetchAll());
    // }

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
// export default connect(mapStateToProps, null)(UserList);