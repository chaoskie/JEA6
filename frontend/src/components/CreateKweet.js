import React from 'react';
import {Toolbar, ToolbarGroup, ToolbarSeparator} from 'material-ui/Toolbar';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import ActionSend from 'material-ui/svg-icons/content/send'
//import ActionSearch from 'material-ui/svg-icons/action/search';
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

class CreateKweet extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
          open:false,
          kweetValue: ""
         // showLoginDialog: false,
         // credentials: {username: "", password: ""}
        };
      }
    
      handleChange = (event, index, value) => this.setState({value});

     /* handleOpen = () => {
        this.setState({showLoginDialog: true});
      };
    
      handleClose = () => {
        this.setState({showLoginDialog: false});
      };*/

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
       // console.log("woop");
       
        if(this.state.kweetValue.length<=140){ 
        let kweet= {message:this.state.kweetValue};
        this.props.dispatch(kweetCreation(kweet));
        }else{
          alert("we currently do not support messages longer as 140 characters. Don't blame the developers, we didn't ask for this ristriction.");
        }
      }


  render() {
        const actions = [
            <FlatButton
              label="Send"
              primary={true}
              onClick={this.handleClose}
            />,
            <TextField
            hintText="Share what's on your mind!"
          />
          ];

        return (
          <div class='kweetCreate'>
                <TextField hintText={"Share what's on your mind!"} 
                style={{paddingLeft: 10}}
                fullWidth={false}
                value={this.state.kweetValue} 
                onChange={(e, v) => this.setState({kweetValue: v})} />

                <FlatButton icon={<ActionSend/>} onClick={() => this.createKweet()}/>
          </div>
        );
      }    
}


export default connect(mapStateToProps, null)(CreateKweet);