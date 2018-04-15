import React from 'react';
import { kweetsFetchTimeline } from '../actions/kweets';
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
    this.interval = setInterval(() => this.props.dispatch(kweetsFetchTimeline(this.props.username)), 5000);
  }
  componentWillUnmount() {
    clearInterval(this.interval);
  }
  
  static getDerivedStateFromProps(nextProps, prevState) {
    if (!nextProps) { return prevState; }

    if (nextProps.username && nextProps.isAuthenticated && !prevState.loadedKweets && !nextProps.kweets.filter(kweet => kweet.user.username === nextProps.username).length) {
        nextProps.dispatch(kweetsFetchTimeline(nextProps.username));
        return {...prevState, loadedKweets: true};
    }

    return prevState;
  }

  getTimelineKweets() {
    if (!this.props.loggedInUser) { return []; }
    
    let kweets = this.props.kweets.filter(kweet => kweet.user.username === this.props.username || this.props.loggedInUser.following.find(f => f.id === kweet.user.id));
    return kweets;
  }

  render() {
    if (!this.props.isAuthenticated) {
        return <h1>You must be logged in in order to view your timeline!</h1>
    }

    return (
    <div className="timeline">
        <CreateKweet />
        <KweetList kweets={this.getTimelineKweets()} />
    </div>)
  }

}


export default connect(mapStateToProps, null)(Timeline);