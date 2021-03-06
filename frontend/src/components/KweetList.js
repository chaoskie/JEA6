import React, { Component } from 'react';
import { connect } from 'react-redux';
import { likeTheKweet, unlikeTheKweet, deleteTheKweet } from '../actions/kweets';
import { Kweet } from './Kweet';
import {
    getUsernameFromJwt, isModeratorFromJwt
} from '../actions/users';

const mapStateToProps = (state, ownProps) => {
    return {
        kweets: ownProps.kweets,
        socket: ownProps.socket,
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

    deleteKweet(kweet) {
        if (this.userOwnKweet(kweet)) {
            deleteTheKweet(kweet, this.props.socket)            
        }
    }

    userOwnKweet(kweet) {
        return (
            this.props.isAuthenticated && (kweet.user.username === this.props.username || isModeratorFromJwt())
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