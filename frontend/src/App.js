import React, { Component } from 'react';
import Navbar from './components/Navbar';
import CreateKweet from './components/CreateKweet';
import Profile from './components/Profile';
import { connect } from 'react-redux';
import { usersFetchAll } from './actions/users';
import { kweetsFetchAll } from './actions/kweets';

const mapStateToProps = (state, ownProps) => {
  return {...state};
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
        <Profile user={this.props.users.find(u => u.username === this.props.router.location.pathname.substring(1))} />
        <CreateKweet />
      </div>
    );
  }
}

export default connect(mapStateToProps, null)(App);