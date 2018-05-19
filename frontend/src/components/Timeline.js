import React from 'react';
import { usersFetchFollowing } from '../actions/users';
import { kweetsFetchTimeline, kweetCreatedSuccess, kweetDeleteSuccess } from '../actions/kweets';
import KweetList from './KweetList';
import CreateKweet from './CreateKweet';

import {
  getUsernameFromJwt
} from '../actions/users';

import { connect } from 'react-redux';

const mapStateToProps = (state) => {
  return {
    isAuthenticated: state.authentication.isAuthenticated,
    kweets: state.kweets,
    username: state.authentication.isAuthenticated ? getUsernameFromJwt() : '',
    loggedInUser: state.authentication.isAuthenticated ? state.users.find(u => u.username === getUsernameFromJwt()) : null
  };
};

class Timeline extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      searchValue: ""
    };

    this.websocket = new WebSocket("ws://localhost:8080/Kwetter-SNAPSHOT_Gamma/ws/timeline?" + localStorage["id_token"]);
    this.websocket.onopen = (ev) => { };
    console.log('constructor');
    this.websocket.onmessage = (event) => {
      console.log(event);
      let data = JSON.parse(event.data);
      switch (data.type) {
        case "createKweet":
          {
            this.props.dispatch(kweetCreatedSuccess(data.kweet));
            break;
          }
        case "deleteKweet":
          {
            this.props.dispatch(kweetDeleteSuccess(data.kweet, data.user));
            break;
          }
        default:
          break;
      }
    }
  }

  componentWillUnmount() {
    this.websocket.close();
  }

  static getDerivedStateFromProps(nextProps, prevState) {
    if (!nextProps) { return prevState; }

    if (nextProps.loggedInUser && !nextProps.loggedInUser.following && !nextProps.followingIsLoading && !prevState.loadedFollowing) {
      nextProps.dispatch(usersFetchFollowing(nextProps.loggedInUser));
      return { ...prevState, loadedFollowing: true };
    }


    if (nextProps.username && nextProps.isAuthenticated && prevState.loadedFollowing && !prevState.loadedKweets && !nextProps.kweets.filter(kweet => kweet.user.username === nextProps.username).length) {
      nextProps.dispatch(kweetsFetchTimeline(nextProps.username));
      return { ...prevState, loadedKweets: true };
    }

    return prevState;
  }

  getTimelineKweets() {
    return this.props.kweets.filter(kweet =>
      kweet.user.username === this.props.username
      || (this.props.loggedInUser.following && this.props.loggedInUser.following.filter(f => f === kweet.user.id)));
  }

  render() {
    if (!this.props.isAuthenticated) {
      return <h1>You must be logged in in order to view your timeline!</h1>
    }

    return (
      <div className="timeline">
        <CreateKweet socket={this.websocket} />
        <KweetList kweets={this.getTimelineKweets()} socket={this.websocket} />
      </div>)
  }

}


export default connect(mapStateToProps, null)(Timeline);