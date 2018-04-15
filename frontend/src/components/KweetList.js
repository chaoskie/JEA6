import React, { Component } from 'react';
import {Card, CardActions, CardHeader, CardText} from 'material-ui/Card';
import RaisedButton from 'material-ui/RaisedButton';
import { connect } from 'react-redux';
import { kweetsFetchAll, likeTheKweet, unlikeTheKweet, deleteTheKweet } from '../actions/kweets';
import { Kweet } from './Kweet';
import {
    usersFetchFollowers, getUsernameFromJwt,
    userUpdateWebsite, userUpdateLocation, userUpdateBio, userUpdateDisplayname,
    followUser, unfollowUser, isModeratorFromJwt
} from '../actions/users';

const mapStateToProps = (state, ownProps) => {
    return {
        kweets: ownProps.kweets,
        hasErrored: state.kweetsHasErrored,
        isLoading: state.kweetsIsLoading,
        isAuthenticated: state.authentication.isAuthenticated,
        username: state.authentication.isAuthenticated ? getUsernameFromJwt() : '',
        user: state.authentication.isAuthenticated ? state.users.find(u => u.username === getUsernameFromJwt()) : null
    };
};

class KweetList extends Component {
    componentDidMount() { }

    handleLike(kweet) {
        if (this.userAlreadyLike(kweet)) {
            // unlike
            this.props.dispatch(unlikeTheKweet(kweet, this.props.user));
        } else {
            // like
            this.props.dispatch(likeTheKweet(kweet, this.props.user));
        }
    }

    deleteKweet(kweet){
        if(this.userOwnKweet(kweet)){
            this.props.dispatch(deleteTheKweet(kweet));
        }
    }

    userOwnKweet(kweet){
        return (            
            this.props.isAuthenticated && ( kweet.user.username === this.props.username || isModeratorFromJwt())
        );
    }

    userAlreadyLike(kweet) {
        return (
            this.props.isAuthenticated
                && kweet.likes.find(u => u.username === this.props.username)                    
                ? true : false)
    }

    render() {
        if (this.props.hasErrored) {
            return <p>Sorry! There was an error loading the kweets</p>;
        }
        if (this.props.isLoading) {
            return <p>Loading…</p>;
        }
        return (
            <div>
                {this.props.kweets.map((kweet) => (
                    <Kweet
                        key={kweet.id} 
                        kweet={kweet} 
                        loggedIn={this.props.isAuthenticated}
                        likeKweet={() => { this.handleLike(kweet); }}
                        canLike={() => this.userAlreadyLike(kweet)} 
                        deleteKweet={() => { this.deleteKweet(kweet); }}
                    />
                ))}
            </div>
        );
    }
}
export default connect(mapStateToProps, null)(KweetList);