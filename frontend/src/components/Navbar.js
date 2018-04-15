import React from 'react';
import { Toolbar, ToolbarGroup, ToolbarSeparator } from 'material-ui/Toolbar';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import ActionSearch from 'material-ui/svg-icons/action/search';
import Snackbar from 'material-ui/Snackbar';
import { kweetCreation, findKweet } from '../actions/kweets';

import { loginUser, logoutUser } from '../actions/authentication';
import { registerUser, getUsernameFromJwt } from '../actions/users';
import { push } from 'react-router-redux';
import { connect } from 'react-redux';

const mapStateToProps = (state) => {
  return {
    isFetching: state.authentication.isFetching,
    isAuthenticated: state.authentication.isAuthenticated,
    errorMessage: state.authentication.errorMessage
  };
};

class Navbar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
     // kweets: state.kweets,
      searchValue: "",
      showLoginDialog: false,
      showRegisterDialog: false,
      credentials: { username: "", password: "" },
      registerCredentials: { username: "", password: "", email: "" }
    };

    this.handleLoginClose = this.handleLoginClose.bind(this);
    this.handleRegisterClose = this.handleRegisterClose.bind(this);
  }

  handleChange = (event, index, value) => this.setState({ value });

  handleLoginOpen() {
    this.setState({ showLoginDialog: true });
  };

  handleLoginClose() {
    this.setState({ showLoginDialog: false });
  };

  handleRegisterOpen() {
    this.setState({ showRegisterDialog: true });
  };

  handleRegisterClose() {
    this.setState({ showRegisterDialog: false });
  };

  componentWillReceiveProps(props) {
    if (props.isAuthenticated) {
      this.handleLoginClose();
      this.handleRegisterClose();
    }
  }

 /* createKweet() {
    let kweet = { message: this.state.searchValue };
    this.props.dispatch(kweetCreation(kweet));
  }*/

  searchKweets(s){
    //this.props.dispatch(findKweet(this.state.searchValue));
  }

  render() {
    const loginActions = [
      <FlatButton
        label="Cancel"
        primary={true}
        onClick={this.handleLoginClose}
      />,
      <FlatButton
        label="Submit"
        primary={true}
        disabled={!(this.state.credentials.username && this.state.credentials.password)}
        onClick={(e) => {
          this.setState({ credentials: { username: "", password: "" } });
          this.props.dispatch(loginUser(this.state.credentials));
        }}
      />,
    ];

    const registerActions = [
      <FlatButton
        label="Cancel"
        primary={true}
        onClick={this.handleRegisterClose}
      />,
      <FlatButton
        label="Register"
        primary={true}
        disabled={!(this.state.registerCredentials.username && this.state.registerCredentials.password && this.state.registerCredentials.email)}
        onClick={(e) => {
          this.props.dispatch(registerUser(this.state.registerCredentials));
          this.setState({ registerCredentials: { username: "", password: "", email: "" } });          
        }}
      />,
    ];

    return (
      <Toolbar className="navbar">
        <ToolbarGroup className="navbarSearch" firstChild={true}>
          <TextField hintText="Search kweets" fullWidth={true} value={this.state.searchValue} onChange={(e, v) => this.setState({ searchValue: v })} />
          <FlatButton icon={<ActionSearch />} onClick={this.searchKweets(this.state.searchValue) /*=> console.log(this.state.searchValue)*/} />
        </ToolbarGroup>
        <ToolbarGroup>
          <ToolbarSeparator />
          {!this.props.isAuthenticated
            ? <div className="navbarButtons">
                <RaisedButton label="Login" primary={true} onClick={() => this.handleLoginOpen()} />
                <RaisedButton label="Register" onClick={() => this.handleRegisterOpen()} />
              </div>
            : <div className="navbarButtons">
                <RaisedButton label="Profile" primary={true} onClick={() => this.props.dispatch(push("/" + getUsernameFromJwt()))} />
                <RaisedButton label="Logout" onClick={() => this.props.dispatch(logoutUser())} />
              </div>
          }
        </ToolbarGroup>

        <Dialog title="Login" actions={loginActions} modal={true} open={this.state.showLoginDialog}>
          <TextField
            floatingLabelText="Username"
            onChange={(e, v) => { let creds = { ...this.state.credentials, username: v }; this.setState({ credentials: creds }) }}
            value={this.state.credentials.username}
          /> <br />

          <TextField
            hintText="Password"
            floatingLabelText="Password"
            type="password"
            onChange={(e, v) => { let creds = { ...this.state.credentials, password: v }; this.setState({ credentials: creds }) }}
            value={this.state.credentials.password}
          />
        </Dialog>
        <Dialog className="registerDialog" title="Register" actions={registerActions} modal={true} open={this.state.showRegisterDialog}>
          <TextField
            floatingLabelText="Username"
            onChange={(e, v) => { let creds = { ...this.state.registerCredentials, username: v }; this.setState({ registerCredentials: creds }) }}
            value={this.state.registerCredentials.username}
          /> <br />

          <TextField
            hintText="Password"
            floatingLabelText="Password"
            type="password"
            onChange={(e, v) => { let creds = { ...this.state.registerCredentials, password: v }; this.setState({ registerCredentials: creds }) }}
            value={this.state.registerCredentials.password}
          /> <br />

          <TextField
            floatingLabelText="Email"
            onChange={(e, v) => { let creds = { ...this.state.registerCredentials, email: v }; this.setState({ registerCredentials: creds }) }}
            value={this.state.registerCredentials.email}
          />
        </Dialog>
        <Snackbar
          open={this.props.errorMessage !== undefined && this.props.errorMessage.length > 0 && !this.state.credentials.username && !this.state.credentials.password}
          message={this.props.errorMessage ? this.props.errorMessage : ''}
          autoHideDuration={4000}
        />
      </Toolbar>
    );
  }

}


export default connect(mapStateToProps, null)(Navbar);