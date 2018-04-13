import React, { Component } from 'react';
import UserList from './components/UserList';
import KweetList from './components/KweetList';
import Navbar from './components/Navbar';
import Profile from './components/Profile';
import {BottomNavigation, BottomNavigationItem} from 'material-ui/BottomNavigation';
import { connect } from 'react-redux';
import {
  usersFetchAll,usersFetchFollowers, getUsernameFromJwt,
  userUpdateWebsite, userUpdateLocation, userUpdateBio, userUpdateDisplayname,
  followUser, unfollowUser
} from './actions/users';
import { kweetsFetchUser, kweetsFetchAll } from './actions/kweets';

const mapStateToProps = (state) => {
  return state;
};

class App extends Component {
  componentDidMount() {
    //if (!this.props.users) {
    this.props.dispatch(usersFetchAll());
    this.props.dispatch(kweetsFetchAll());
    
    //}
}
  
  render() {
    return (
      <div>
        <Navbar />
        <Profile user={this.props.users[0]} />
      </div>
    );
  }
}

export default connect(mapStateToProps, null)(App);