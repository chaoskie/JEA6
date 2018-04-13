import { callApiGet, callApiPut, callApiPost } from '../middleware/api';

export function usersHasErrored(bool) {
    return {
        type: 'USERS_HAS_ERRORED',
        hasErrored: bool
    }
}

export function usersIsLoading(bool) {
    return {
        type: 'USERS_IS_LOADING',
        isLoading: bool
    };
}

export function usersFetchDataSuccess(users) {
    return {
        type: 'USERS_FETCH_DATA_SUCCESS',
        payload: users
    };
}

export function followersIsLoading(bool) {
    return {
        type: 'FOLLOWERS_IS_LOADING',
        isLoading: bool
    }
}

export function followersHasErrored(bool) {
    return {
        type: 'FOLLOWERS_HAS_ERRORED',
        hasErrored: bool
    }
}

export function followersFetchDataSuccess(user, followers) {
    return {
        type: 'FOLLOWERS_FETCH_DATA_SUCCESS',
        payload: { user, followers }
    }
}

export function userUpdateBioSuccess(user) {
    return {
        type: 'USERS_UPDATE_BIO_SUCCESS',
        payload: user
    }
}

export function userUpdateLocationSuccess(user) {
    return {
        type: 'USERS_UPDATE_LOCATION_SUCCESS',
        payload: user
    }
}

export function userUpdateDisplaynameSuccess(user) {
    return {
        type: 'USERS_UPDATE_DISPLAYNAME_SUCCESS',
        payload: user
    }
}

export function userUpdateWebsiteSuccess(user) {
    return {
        type: 'USERS_UPDATE_WEBSITE_SUCCESS',
        payload: user
    }
}

export function userFollowedSuccess(username, userFollowed) {
    return {
        type: 'USER_FOLLOW_SUCCESS',
        payload: { username, userFollowed }
    }
}

export function userFollowedFailed() {
    return {
        type: 'USER_FOLLOW_FAILED'
    }
}

export function userUnfollowedSuccess(username, userUnfollowed) {
    return {
        type: 'USER_UNFOLLOW_SUCCESS',
        payload: { username, userUnfollowed }
    }
}

export function userUnfollowedFailed() {
    return {
        type: 'USER_UNFOLLOW_FAILED'
    }
}

export function userUpdateWebsite(user) {
    return (dispatch) => {
        callApiPut('users/website', true, user.website)
        .then((response) => {
            if (!response.ok) {
                throw Error(response.statusText);
            }

            return response;
        })
        .then((response) => response.json())
        .then((u) => dispatch(userUpdateWebsiteSuccess(u)))
        .catch((error) => { console.log(error); })
    }
}

export function userUpdateLocation(user) {
    return (dispatch) => {
        callApiPut('users/location', true, user.location)
        .then((response) => {
            if (!response.ok) {
                throw Error(response.statusText);
            }

            return response;
        })
        .then((response) => response.json())
        .then((u) => dispatch(userUpdateLocationSuccess(u)))
        .catch((error) => { console.log(error); })
    }
}

export function userUpdateDisplayname(user) {
    return (dispatch) => {
        callApiPut('users/displayname', true, user.displayname)
        .then((response) => {
            if (!response.ok) {
                throw Error(response.statusText);
            }

            return response;
        })
        .then((response) => response.json())
        .then((u) => dispatch(userUpdateDisplaynameSuccess(u)))
        .catch((error) => { console.log(error); })
    }
}

export function userUpdateBio(user) {
    return (dispatch) => {
        callApiPut('users/bio', true, user.bio)
        .then((response) => {
            if (!response.ok) {
                throw Error(response.statusText);
            }

            return response;
        })
        .then((response) => response.json())
        .then((u) => dispatch(userUpdateBioSuccess(u)))
        .catch((error) => { console.log(error); })
    }
}

export function usersFetchAll() {
    return (dispatch) => {
        dispatch(usersIsLoading(true));
        callApiGet('users', false)
            .then((response) => {
                if (!response.ok) {
                    throw Error(response.statusText);
                }
                dispatch(usersIsLoading(false));
                return response;
            })
            .then((response) => response.json())
            .then((users) => dispatch(usersFetchDataSuccess(users)))
            .catch(() => dispatch(usersHasErrored(true)));
    };
}

export function usersFetchFollowers(user) {
    return (dispatch) => {
        dispatch(followersIsLoading(true));
        callApiGet(`users/${user.username}/followers`, false) 
        .then((response) => {
            if (!response.ok) {
                throw Error(response.statusText);
            }

            dispatch(followersIsLoading(false));
            return response;
        })
        .then((response) => response.json())
        .then((followers) => dispatch(followersFetchDataSuccess(user, followers)))
        .catch(() => dispatch(followersHasErrored(true)));
    }
}

export function followUser(userToFollow) {
    return (dispatch) => {
        callApiPost(`users/${userToFollow.username}/follow`, true, null)
        .then((response) => {
            if (!response.ok) {
                throw Error(response.statusText);
            }

            dispatch(userFollowedSuccess(getUsernameFromJwt(), userToFollow));
        })
        .catch(() => dispatch(userFollowedFailed()));
    }
}

export function unfollowUser(userToUnfollow) {
    return (dispatch) => {
        callApiPost(`users/${userToUnfollow.username}/unfollow`, true, null)
        .then((response) => {
            if (!response.ok) {
                throw Error(response.statusText);
            }

            dispatch(userUnfollowedSuccess(getUsernameFromJwt(), userToUnfollow));
        })
        .catch(() => dispatch(userUnfollowedFailed()));
    }
}

export function getUsernameFromJwt() {
    let token = localStorage.getItem('id_token');
    if (!token) { return '' }

    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64)).username;
}