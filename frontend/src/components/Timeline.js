import React from 'react';
import { Toolbar, ToolbarGroup, ToolbarSeparator } from 'material-ui/Toolbar';
import Dialog from 'material-ui/Dialog';
import FlatButton from 'material-ui/FlatButton';
import RaisedButton from 'material-ui/RaisedButton';
import TextField from 'material-ui/TextField';
import ActionSearch from 'material-ui/svg-icons/action/search';
import Snackbar from 'material-ui/Snackbar';
import { kweetCreation } from '../actions/kweets';
import KweetList from './KweetList';

import {
    usersFetchFollowers, getUsernameFromJwt,
    userUpdateWebsite, userUpdateLocation, userUpdateBio, userUpdateDisplayname,
    followUser, unfollowUser, isModeratorFromJwt
} from '../actions/users';

import { connect } from 'react-redux';

const mapStateToProps = (state) => {
  return {
    isFetching: state.authentication.isFetching,
    isAuthenticated: state.authentication.isAuthenticated,
    errorMessage: state.authentication.errorMessage,
    kweets: state.kweets,
    username: state.authentication.isAuthenticated ? getUsernameFromJwt() : '',
  };
};

class Timeline extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      searchValue: ""
    };
  }

  componentDidMount() {
    this.props.dispatch(getTimeline());
  }

  render() {
    if (!this.props.isAuthenticated) {
        return <h1>You must be logged in order to view the timeline!</h1>
    }

    return (
    <div>
        
    </div>)
  }

}


export default connect(mapStateToProps, null)(Timeline);