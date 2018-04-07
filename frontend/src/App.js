import React, { Component } from 'react';
import UserList from './components/UserList';
import KweetList from './components/KweetList';
import Navbar from './components/Navbar';


class App extends Component {
  render() {
    return (
      <div>
        <Navbar />
        <UserList />
        <KweetList />
      </div>
    );
  }
}

export default App;
