import React from 'react';
import {Toolbar, ToolbarGroup, ToolbarSeparator} from 'material-ui/Toolbar';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import ActionSearch from 'material-ui/svg-icons/action/search';
import Snackbar from 'material-ui/Snackbar';
import {kweetCreation} from '../actions/kweets';

import {loginUser, logoutUser} from '../actions/authentication.js'

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
          searchValue: "",
          showLoginDialog: false,
          credentials: {username: "", password: ""}
        };
      }
    
      handleChange = (event, index, value) => this.setState({value});

      handleOpen = () => {
        this.setState({showLoginDialog: true});
      };
    
      handleClose = () => {
        this.setState({showLoginDialog: false});
      };

      componentWillReceiveProps(props) {
          // if (!props.isAuthenticated && props.errorMessage) {
          // Display error message
          //   console.log(props.errorMessage);
          // }

          if (props.isAuthenticated) {
            this.handleClose();
          }
      }

      createKweet() {
        console.log("woop");
        let kweet= {message:this.state.searchValue};
        this.props.dispatch(kweetCreation(kweet));
        
      }
    
      render() {
        const actions = [
            <FlatButton
              label="Cancel"
              primary={true}
              onClick={this.handleClose}
            />,
            <FlatButton
              label="Submit"
              primary={true}
              disabled={!(this.state.credentials.username && this.state.credentials.password)}
              onClick={(e) => { 
                  this.setState({credentials: {username: "", password: ""}});
                  this.props.dispatch(loginUser(this.state.credentials));
                }}
            />,
          ];

        return (
          /*<Toolbar>
            <ToolbarGroup firstChild={true} style={{width: '100%'}}>
                <TextField hintText="Search kweets" style={{paddingLeft: 10}} fullWidth={true} value={this.state.searchValue} onChange={(e, v) => this.setState({searchValue: v})} />
                <FlatButton icon={<ActionSearch />} onClick={() => this.createKweet()}/>*/
          <Toolbar className="navbar">
            <ToolbarGroup className="navbarSearch" firstChild={true}>
                <TextField hintText="Search kweets" fullWidth={true} value={this.state.searchValue} onChange={(e, v) => this.setState({searchValue: v})} />
                <FlatButton icon={<ActionSearch />} onClick={() => console.log(this.state.searchValue)}/>
            </ToolbarGroup>
            <ToolbarGroup>
              <ToolbarSeparator />
              {!this.props.isAuthenticated
                ? <RaisedButton label="Login" primary={true} onClick={() => this.handleOpen() } />
                : <RaisedButton label="Logout" primary={true} onClick={() => this.props.dispatch(logoutUser()) } />
              }
            </ToolbarGroup>

            <Dialog title="Login" actions={actions} modal={true} open={this.state.showLoginDialog}>
            <TextField
                floatingLabelText="Username"
                onChange={(e,v) => {let creds = {...this.state.credentials, username: v}; this.setState({credentials: creds})}}
                value={this.state.credentials.username}
            /> <br />
            
            <TextField
                hintText="Password"
                floatingLabelText="Password"
                type="password"
                onChange={(e,v) => {let creds = {...this.state.credentials, password: v}; this.setState({credentials: creds})}}
                value={this.state.credentials.password}
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