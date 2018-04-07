import React from 'react';
import {Toolbar, ToolbarGroup, ToolbarSeparator} from 'material-ui/Toolbar';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import ActionSearch from 'material-ui/svg-icons/action/search';

import { connect } from 'react-redux';

const mapStateToProps = (state) => {
    return {
        isFetching: state.isFetching,
        isAuthenticated: state.isAuthenticated
    };
};

const mapDispatchToProps = (dispatch) => {
    return {
        //fetchData: (url) => dispatch(usersFetchData(url))
    };
}

class Navbar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          value: 3,
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
                  console.log(this.state.credentials);
                  this.setState({credentials: {username: "", password: ""}})
                  this.handleClose();
                }}
            />,
          ];

        return (
          <Toolbar>
            <ToolbarGroup firstChild={true} style={{width: '100%'}}>
                <TextField hintText="Search kweets" style={{paddingLeft: 10}} fullWidth={true} value={this.state.searchValue} onChange={(e, v) => this.setState({searchValue: v})} />
                <FlatButton icon={<ActionSearch />} onClick={() => console.log(this.state.searchValue)}/>
            </ToolbarGroup>
            <ToolbarGroup>
              <ToolbarSeparator />
              <RaisedButton label="Login" primary={true} onClick={() => this.handleOpen() } />
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

          </Toolbar>
        );
      }
    
}


export default connect(mapStateToProps, mapDispatchToProps)(Navbar);