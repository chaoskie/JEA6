import React, { Component } from 'react';
import {Card, CardActions, CardHeader, CardText} from 'material-ui/Card';
import RaisedButton from 'material-ui/RaisedButton';
import { connect } from 'react-redux';
import { usersFetchData } from '../actions/users';
import Avatar from 'material-ui/Avatar';
import DeviceGPS from 'material-ui/svg-icons/device/gps-fixed';
import Website from 'material-ui/svg-icons/action/language';


const styles = theme => ({
    root: theme.mixins.gutters({
        paddingTop: 16,
        paddingBottom: 16,
        marginTop: theme.spacing.unit * 3,
    }),
});

const mapStateToProps = (state) => {
    return {
        users: state.users,
        hasErrored: state.usersHasErrored,
        isLoading: state.usersIsLoading
    };
};

const mapDispatchToProps = (dispatch) => {
    return {
        fetchData: (url) => dispatch(usersFetchData(url))
    };
}


class UserList extends Component {
    componentDidMount() {
        this.props.fetchData('http://localhost:8080/Kwetter-Beta/api/v1/users');
    }

    render() {
        if (this.props.hasErrored) {
            return <p>Sorry! There was an error loading the users</p>;
        }
        if (this.props.isLoading) {
            return <p>Loading…</p>;
        }
        return (
            <div>
                {this.props.users.map((user) => (
                    <div key={user.id}>
                        <Card style={{width: '500px', marginBottom: 20}}>
                            <CardHeader
                                avatar={<Avatar>{user.displayname.split(' ').map(s => s[0]).join('')}</Avatar>}
                                title={user.displayname}
                                subtitle={'@'+user.username}
                                actAsExpander={true}
                                showExpandableButton={true}
                            />
                            <CardActions>
                                <RaisedButton label="Follow" />
                                <RaisedButton label="Action2" />
                            </CardActions>
                            <CardText expandable={true}>
                                <p>{user.bio}</p>
                                {user.location &&
                                    <div>
                                        <DeviceGPS style={{color: 'rgba(0, 0, 0, 0.54)'}} />
                                        <p style={{display: 'inline', verticalAlign: 'super', marginLeft: '5px'}}>
                                            {user.location}
                                        </p>
                                    </div>
                                }
                                {user.website &&
                                    <div>
                                        <Website style={{color: 'rgba(0,0,0, 0.54'}} />
                                        <p style={{display: 'inline', verticalAlign: 'super', marginLeft: '5px'}}>
                                            {user.website}
                                        </p>
                                    </div>
                                }
                                <pre>{JSON.stringify({...user, following: []}, null, 2)}</pre>
                            </CardText>
                        </Card>

                    </div>
                ))}
            </div>
        );
    }
}
export default connect(mapStateToProps, mapDispatchToProps)(UserList);