import React, { Component } from 'react';
import Navbar from './components/Navbar';
import CreateKweet from './components/CreateKweet';
import Profile from './components/Profile';
import { connect } from 'react-redux';
import { usersFetchAll } from './actions/users';
import { kweetsFetchAll } from './actions/kweets';

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
        <CreateKweet />
      </div>
    );
  }
}

export default connect(mapStateToProps, null)(App);