import React, { Component } from 'react';
import UserList from './components/UserList';
import KweetList from './components/KweetList';
import Navbar from './components/Navbar';
import Profile from './components/Profile';
import {BottomNavigation, BottomNavigationItem} from 'material-ui/BottomNavigation';


class App extends Component {
  render() {
    return (
      <div>
        <Navbar />
        <Profile />
        <UserList />
        <KweetList />
        
      </div>
    );
  }
}

export default App;
