import React, { Component } from 'react';
import Navbar from './components/Navbar';
import Profile from './components/Profile';
import Timeline from './components/Timeline';
import Search from './components/Search';
import { WelcomePage } from './components/WelcomePage';
import { connect } from 'react-redux';
import { userFetchByUsername, getUsernameFromJwt } from './actions/users';
import { Switch, Route } from 'react-router';
import AppBar from 'material-ui/AppBar';
import IconButton from 'material-ui/IconButton';
import Home from 'material-ui/svg-icons/action/home';
import { push } from 'react-router-redux';

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

getPageTitle() {
  if (this.props.router.location.pathname === "/") {
    return this.props.authentication.isAuthenticated ? "Timeline" : "Welcome to Kwetter!"
  } else {
    let username = this.props.router.location.pathname.substring(1);

    if(username.startsWith("search")) {
      return "Search result";
    }

    return `Profile of ${username}`;
  }
}
  
  render() {
    return (
      <div>
        <AppBar 
          title={this.getPageTitle()}
          onLeftIconButtonClick={() => this.props.dispatch(push("/"))}
          iconElementLeft={<IconButton><Home /></IconButton>}
          className="appBar"
        />
        <Navbar />

        <Switch>
          <Route exact path="/" render={() => {
            return this.props.authentication.isAuthenticated
              ? <Timeline />
              : <WelcomePage />
          }}/>
      
          <Route exact path="/search" render={() => {
            return this.props.router.location.search.startsWith("?v=")
            ? <Search query={this.props.router.location.search.substring(3)} />
            : <div>Invalid search query.</div>
          }} />

          <Route path="/:username+" render={() => 
            <div>
              <Profile user={this.props.users.find(u => u.username === this.props.router.location.pathname.substring(1))} />
            </div>}/>
        </Switch>
      </div>
    );
  }
}

export default connect(mapStateToProps, null)(App);