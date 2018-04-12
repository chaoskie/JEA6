import React, { Component } from 'react';
import UserList from './components/UserList';
import KweetList from './components/KweetList';
import Navbar from './components/Navbar';
import CreateKweet from './components/CreateKweet';
import {BottomNavigation, BottomNavigationItem} from 'material-ui/BottomNavigation';


class App extends Component {
  render() {
    return (
      <div>
        <Navbar />
        <UserList />
        <KweetList />
        <CreateKweet />
      </div>
    );
  }
}

export default App;
