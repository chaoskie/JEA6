import React from 'react';
import FlatButton from 'material-ui/FlatButton';
import TextField from 'material-ui/TextField';
import ActionSend from 'material-ui/svg-icons/content/send';
import {kweetCreation} from '../actions/kweets';

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
          kweetValue: ""
        };
      }
    
      componentWillReceiveProps(props) { }

      createKweet() {
        let kweet= {message:this.state.kweetValue};
        this.props.dispatch(kweetCreation(kweet));
        this.setState({kweetValue:""});
        }

  render() {
        return (
          <div className='kweetCreate'>
                <TextField
                hintText={"Share what's on your mind!"} 
                errorText={this.state.kweetValue.length > 140 ? "Maximum length is 140 characters!" : ""}
                style={{paddingLeft: 10}}
                fullWidth={false}
                value={this.state.kweetValue} 
                onChange={(e, v) => this.setState({kweetValue: v})} />

                <FlatButton icon={<ActionSend/>} onClick={() => this.createKweet()} disabled={this.state.kweetValue.length > 140 || !this.state.kweetValue} />
          </div>
        );
      }    
}


export default connect(mapStateToProps, null)(CreateKweet);