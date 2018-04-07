import React, { Component } from 'react';
import {Card, CardActions, CardHeader, CardText} from 'material-ui/Card';
import RaisedButton from 'material-ui/RaisedButton';
import { connect } from 'react-redux';
import { usersFetchData } from '../actions/users';


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