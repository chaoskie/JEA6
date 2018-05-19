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
  }

  componentDidMount() {
    //this.interval = setInterval(() => this.props.dispatch(kweetsFetchTimeline(this.props.username)), 5000);
    this.websocket = new WebSocket("ws://localhost:8080/Kwetter-SNAPSHOT_Gamma/ws/timeline?" + localStorage["id_token"]);
    this.websocket.onopen = (ev) => {
      //this.websocket.send("Hello world");
      console.log('socket mount');
    };

    this.websocket.addEventListener("message", function (event) {
      var data = JSON.parse(event.data);
      console.log(data);
      switch (data.type) {
        case "createKweet":
          {
            this.props.dispatch(kweetCreatedSuccess(data.kweet));
          }
          break;
        case "deleteKweet":
          {
            console.log(data);

            this.props.dispatch(kweetDeleteSuccess(data.kweet, data.user));
          }
          break;
          case "fetching following":
          {
            this.props.dispatch(usersFetchFollowing(this.props.username));
          }
          break;
      }
    }.bind(this));

  }
  componentWillUnmount() {
    console.log('socket dead');
    //clearInterval(this.interval);
    this.websocket.close();
  }

  static getDerivedStateFromProps(nextProps, prevState) {
    if (!nextProps) { return prevState; }

    if (nextProps.loggedInUser && !nextProps.loggedInUser.following && !nextProps.followingIsLoading && !prevState.loadedFollowing) {
      console.log('fetching following '+ nextProps.username);
      nextProps.dispatch(usersFetchFollowing(nextProps.username));
      return { ...prevState, loadedFollowing: true };
  }

    console.log("username " + nextProps.username + " authenticated " + nextProps.isAuthenticated + " previous state following loaded " + !prevState.loadedFollowing + " previous state is not loaded kweets " + !prevState.loadedKweets + " searching something" + !nextProps.kweets.filter(kweet => kweet.user.username === nextProps.username).length )  
    console.log("entering kweets fetch check");
    if (nextProps.username && nextProps.isAuthenticated && prevState.loadedFollowing && !prevState.loadedKweets && !nextProps.kweets.filter(kweet => kweet.user.username === nextProps.username).length) {
      console.log('fetching kweets '+ nextProps.username);
      nextProps.dispatch(kweetsFetchTimeline(nextProps.username));
      return { ...prevState, loadedKweets: true };
    }
    console.log("kweets fetch check ended");

    return prevState;
  }

  getTimelineKweets() {
    if (!this.props.loggedInUser) { return []; }
    console.log(this.props.kweets);
    console.log(this.props.loggedInUser);

    let allKweets = this.props.kweets.filter(kweet => kweet.user.username === this.props.username || this.props.loggedInUser.following.filter(f => f === kweet.user.id));
    console.log(allKweets);
    return allKweets;
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