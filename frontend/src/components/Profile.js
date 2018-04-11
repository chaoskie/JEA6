import React, { Component } from 'react';
import { Card, CardActions, CardHeader, CardText } from 'material-ui/Card';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import { connect } from 'react-redux';
import { usersFetchAll, getUsernameFromJwt, userUpdateWebsite, userUpdateLocation, userUpdateBio, userUpdateDisplayname } from '../actions/users';
import Avatar from 'material-ui/Avatar';
import DeviceGPS from 'material-ui/svg-icons/device/gps-fixed';
import Website from 'material-ui/svg-icons/action/language';


const mapStateToProps = (state) => {
    return {
        users: state.users,
        user: state.users[1],
        hasErrored: state.usersHasErrored,
        isLoading: state.usersIsLoading,
        isAuthenticated: state.authentication.isAuthenticated,
        username: state.authentication.isAuthenticated ? getUsernameFromJwt() : '',
    };
};

class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = {
          editing: false,
          editingValues: {displayName: '', bio: '', website: '', location: ''}
        };

        this.handleEditProfile = this.handleEditProfile.bind(this);
      }
    
    componentDidMount() {
        if (!this.props.users) {
            this.props.dispatch(usersFetchAll());
        }
    }

    userIsFollowing() {
        return (
            this.props.isAuthenticated 
            && this.props.users.find(u => u.username === this.props.username)
               .following.find(f => f.username === this.props.user.username)
            ? true : false)
    }

    userIsMyself() {
        return this.props.isAuthenticated && this.props.username === this.props.user.username;
    }

    handleEditProfile() {
        if (!this.state.editing) {
            let editingValues = {
                displayname: this.props.user.displayname,
                bio: this.props.user.bio,
                website: this.props.user.website,
                location: this.props.user.location
            };

            this.setState({editing: true, editingValues});
        } else {
            if (this.state.editingValues.displayname !== this.props.user.displayname) {
                console.log("Display name changed");
                let user = { ...this.props.user, location: this.state.editingValues.displayname };
                this.props.dispatch(userUpdateDisplayname(user))
            }

            if (this.state.editingValues.bio !== this.props.user.bio) {
                console.log("Bio changed");
                let user = { ...this.props.user, location: this.state.editingValues.bio };
                this.props.dispatch(userUpdateBio(user))
            }

            if (this.state.editingValues.location !== this.props.user.location) {
                console.log("Location changed");
                let user = { ...this.props.user, location: this.state.editingValues.location };
                this.props.dispatch(userUpdateLocation(user))
            }

            if (this.state.editingValues.website !== this.props.user.website) {
                console.log("Website changed");
                let user = { ...this.props.user, website: this.state.editingValues.website };
                this.props.dispatch(userUpdateWebsite(user))
            }

            this.setState({editing: false});
        }
    }

    render() {
        if (this.props.hasErrored || !this.props.user) {
            return <p>Sorry! There was an error loading the profile</p>;
        }
        if (this.props.isLoading) {
            return <p>Loading…</p>;
        }
        return (

            <div key={this.props.user.id}>
                <Card style={{ width: '500px', marginBottom: 20 }}>
                    <CardHeader
                        avatar={this.props.user.profilePhoto ? <Avatar src={this.props.user.profilePhoto}></Avatar> : <Avatar>{this.props.user.displayname.split(' ').map(s => s[0]).join('')}</Avatar>}
                        title={this.props.user.displayname}
                        subtitle={'@' + this.props.user.username}
                    />
                    <CardActions>
                        <RaisedButton label={this.userIsFollowing() ? "Unfollow" : "Follow" } disabled={
                            !this.props.isAuthenticated 
                            || this.userIsMyself()
                            || this.userIsFollowing()
                            } />
                        {this.userIsMyself() &&
                            <RaisedButton label={this.state.editing ? "Save profile" : "Edit profile"} onClick={this.handleEditProfile} />
                        }
                    </CardActions>
                    <CardText>
                        {this.state.editing
                        ? <TextField
                            id={'edit-bio-field'}
                            multiLine={true}
                            value={this.state.editingValues.bio}
                            onChange={(e, v) => { let editingValues = {...this.state.editingValues, bio: v}; this.setState({editingValues}); }}
                          />
                        : <p>{this.props.user.bio}</p>
                        }
                        
                        {(this.props.user.location || this.userIsMyself()) &&
                            <div>
                                <DeviceGPS style={{ color: 'rgba(0, 0, 0, 0.54)' }} />
                                {this.state.editing
                                ? <TextField
                                    id={'edit-location-field'}
                                    style={{marginLeft: '5px'}}
                                    value={this.state.editingValues.location}
                                    onChange={(e, v) => {let editingValues = {...this.state.editingValues, location: v }; this.setState({editingValues}); }}
                                  />
                                : <p style={{ display: 'inline', verticalAlign: 'super', marginLeft: '5px' }}>
                                    {this.props.user.location}
                                  </p>
                                }
                                
                            </div>
                        }
                        {(this.props.user.website || this.userIsMyself()) &&
                            <div>
                                <Website style={{ color: 'rgba(0,0,0, 0.54' }} />
                                {this.state.editing
                                ? <TextField
                                    id={'edit-website-field'}
                                    style={{marginLeft: '5px'}}
                                    value={this.state.editingValues.website}
                                    onChange={(e, v) => {let editingValues = {...this.state.editingValues, website: v }; this.setState({editingValues}); }}
                                  />
                                : <p style={{ display: 'inline', verticalAlign: 'super', marginLeft: '5px' }}>
                                    {this.props.user.website}
                                  </p>
                                }
                            </div>
                        }
                    </CardText>
                </Card>

            </div>

        );
    }
}
export default connect(mapStateToProps, null)(Profile);