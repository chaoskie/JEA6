import React, { Component } from 'react';
import { Card, CardActions, CardHeader, CardText } from 'material-ui/Card';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import { connect } from 'react-redux';
import {
    usersFetchFollowers, getUsernameFromJwt, userFetchByUsername,
    userUpdateWebsite, userUpdateLocation, userUpdateBio, userUpdateDisplayname,
    followUser, unfollowUser, isModeratorFromJwt
} from '../actions/users';
import { Kweet } from './Kweet';
import UserList from './UserList';
import KweetList from './KweetList';
import Avatar from 'material-ui/Avatar';
import DeviceGPS from 'material-ui/svg-icons/device/gps-fixed';
import Website from 'material-ui/svg-icons/action/language';
import { Tabs, Tab } from 'material-ui/Tabs';
import Message from 'material-ui/svg-icons/communication/message';
import People from 'material-ui/svg-icons/social/people';
import PeopleOutline from 'material-ui/svg-icons/social/people-outline';
import {likeTheKweet, unlikeTheKweet, deleteTheKweet, kweetsFetchUser} from '../actions/kweets';
import { Link } from 'react-router-dom'


const mapStateToProps = (state, ownProps) => {
    return {
        profileName: state.router.location.pathname.substring(1),
        user: ownProps.user,
        kweets: state.kweets,
        hasErrored: state.usersHasErrored,
        isLoading: state.usersIsLoading,
        followersIsLoading: state.followersIsLoading,
        isAuthenticated: state.authentication.isAuthenticated,
        username: state.authentication.isAuthenticated ? getUsernameFromJwt() : '',
        loggedInUser: state.authentication.isAuthenticated ? state.users.find(u => u.username === getUsernameFromJwt()) : null
    };
};

class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            editing: false,
            editingValues: { displayName: '', bio: '', website: '', location: '' }
        };

        this.handleEditProfile = this.handleEditProfile.bind(this);
        this.handleFollow = this.handleFollow.bind(this);
    }

    componentDidMount() { }

    static getDerivedStateFromProps(nextProps, prevState) {        
        if (!nextProps) { return null; }

        if (!nextProps.user && nextProps.profileName && !nextProps.isLoading && !nextProps.hasErrored) {
            nextProps.dispatch(userFetchByUsername(nextProps.profileName));
            return null;
        }

        if (nextProps.user && !nextProps.user.followers && !nextProps.followersIsLoading) {
            nextProps.dispatch(usersFetchFollowers(nextProps.user));
            return null;
        }

        return null;
      }

    userIsFollowing(user) {
        return (
            this.props.isAuthenticated
            && this.props.loggedInUser
            && this.props.loggedInUser.following.find(f => f.username === user.username)
                ? true : false);
    }

    userIsMyself(user) {
        return this.props.isAuthenticated && this.props.username === user.username;
    }

    getUserKweets() {
        let kweets = this.props.kweets.filter(kweet => kweet.user.id === this.props.user.id);
        return kweets;
    }

    handleEditProfile() {
        if (!this.state.editing) {
            let editingValues = {
                displayname: this.props.user.displayname,
                bio: this.props.user.bio,
                website: this.props.user.website,
                location: this.props.user.location
            };

            this.setState({ editing: true, editingValues });
        } else {
            if (this.state.editingValues.displayname !== this.props.user.displayname) {
                console.log("Display name changed");
                let user = { ...this.props.user, displayname: this.state.editingValues.displayname };
                this.props.dispatch(userUpdateDisplayname(user))
            }

            if (this.state.editingValues.bio !== this.props.user.bio) {
                console.log("Bio changed");
                let user = { ...this.props.user, bio: this.state.editingValues.bio };
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

            this.setState({ editing: false });
        }
    }

    handleFollow(user) {
        if (this.userIsFollowing()) {
            // Unfollow
            this.props.dispatch(unfollowUser(user));
        } else {
            // Follow
            this.props.dispatch(followUser(user));
        }
    }

    render() {
        if (this.props.hasErrored) {
            return <p>Sorry! There was an error loading the profile</p>;
        }

        if (this.props.isLoading || !this.props.user) {
            return <p>Loading…</p>;
        }
        
        return (
            <div key={this.props.user.id}>
                <Card className="profileCard">
                    <CardHeader
                        avatar={this.props.user.profilePhoto ? <Avatar src={this.props.user.profilePhoto}></Avatar> : <Avatar>{this.props.user.displayname.split(' ').map(s => s[0]).join('')}</Avatar>}
                        title={this.state.editing
                            ? <TextField
                                id={'edit-displayname-field'}
                                value={this.state.editingValues.displayname}
                                onChange={(e, v) => { let editingValues = { ...this.state.editingValues, displayname: v }; this.setState({ editingValues }); }} />
                            : <Link className="profileLink" to={`/${this.props.user.username}`}>{this.props.user.displayname}</Link>}
                        subtitle={'@' + this.props.user.username}
                    />
                    <CardActions>
                        <RaisedButton
                            label={this.userIsFollowing(this.props.user) ? "Unfollow" : "Follow"}
                            disabled={!this.props.isAuthenticated || this.userIsMyself(this.props.user)}
                            onClick={() => this.handleFollow(this.props.user)}
                        />

                        {this.userIsMyself(this.props.user) &&
                            <RaisedButton label={this.state.editing ? "Save profile" : "Edit profile"} onClick={this.handleEditProfile} />
                        }
                    </CardActions>
                    <CardText>
                        {this.state.editing
                            ? <TextField
                                id={'edit-bio-field'}
                                multiLine={true}
                                value={this.state.editingValues.bio}
                                onChange={(e, v) => { let editingValues = { ...this.state.editingValues, bio: v }; this.setState({ editingValues }); }}
                            />
                            : <p>{this.props.user.bio}</p>
                        }

                        {(this.props.user.location || this.userIsMyself(this.props.user)) &&
                            <div>
                                <DeviceGPS style={{ color: 'rgba(0, 0, 0, 0.54)' }} />
                                {this.state.editing
                                    ? <TextField
                                        id={'edit-location-field'}
                                        className="profileEditField"
                                        value={this.state.editingValues.location}
                                        onChange={(e, v) => { let editingValues = { ...this.state.editingValues, location: v }; this.setState({ editingValues }); }}
                                    />
                                    : <p className="profileField">
                                        {this.props.user.location}
                                    </p>
                                }

                            </div>
                        }
                        {(this.props.user.website || this.userIsMyself(this.props.user)) &&
                            <div>
                                <Website style={{ color: 'rgba(0,0,0, 0.54' }} />
                                {this.state.editing
                                    ? <TextField
                                        id={'edit-website-field'}
                                        className="profileEditField"
                                        value={this.state.editingValues.website}
                                        onChange={(e, v) => { let editingValues = { ...this.state.editingValues, website: v }; this.setState({ editingValues }); }}
                                    />
                                    : <p className="profileField">
                                        {this.props.user.website}
                                    </p>
                                }
                            </div>
                        }
                    </CardText>
                </Card>
                <Tabs className="profileTabs">
                    <Tab
                        icon={<Message />}
                        label={`Kweets (${this.getUserKweets().length})`}>
                        <KweetList kweets={this.getUserKweets()} />
                    </Tab>
                    <Tab
                        icon={<People />}
                        label={`Following (${this.props.user.following.length})`}>
                        {this.props.user.following.length > 0
                        ? <UserList users={this.props.user.following} />
                        : <h1>This user does not follow anyone!</h1> }
                        </Tab>
                    <Tab
                        icon={<PeopleOutline />}
                        label={`Followers (${this.props.user.followers ? this.props.user.followers.length : 0})`}>
                        {(this.props.user.followers && this.props.user.followers.length > 0)
                        ? <UserList users={this.props.user.followers} />
                        : <h1>This user has no followers!</h1> }
                        </Tab>
                    
                </Tabs>
            </div>

        );
    }
}
export default connect(mapStateToProps, null)(Profile);