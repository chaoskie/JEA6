import React, { Component } from 'react';
import Navbar from './components/Navbar';
import CreateKweet from './components/CreateKweet';
import Profile from './components/Profile';
import { connect } from 'react-redux';
import { usersFetchAll, userFetchByUsername, getUsernameFromJwt } from './actions/users';
import { kweetsFetchAll } from './actions/kweets';
import { Route } from 'react-router';

const mapStateToProps = (state, ownProps) => {
  return {...state};
};

class App extends Component {
  componentDidMount() {
    if (this.props.authentication.isAuthenticated) {
      let username = this.props.router.location.pathname.substring(1);
      if (!this.props.users.find(u => u.username === username)) {
        this.props.dispatch(userFetchByUsername(getUsernameFromJwt()));
      }
    }
}
  
  render() {
    return (
      <div>
        <Route path="/" render={() => 
          <div>
            <Navbar />
            <Profile user={this.props.users.find(u => u.username === this.props.router.location.pathname.substring(1))} />
            <CreateKweet />
          </div>
        } />
      </div>
    );
  }
}

export default connect(mapStateToProps, null)(App);