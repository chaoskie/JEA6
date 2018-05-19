export function usersHasErrored(state = false, action) {
    switch (action.type) {
        case 'USERS_HAS_ERRORED':
            return action.hasErrored;
        default:
            return state;
    }
}

export function usersIsLoading(state = false, action) {
    switch (action.type) {
        case 'USERS_IS_LOADING':
            return action.isLoading;
        default:
            return state;
    }
}

export function followingHasErrored(state = false, action) {
    switch (action.type) {
        case 'FOLLOWING_HAS_ERRORED':
            return action.hasErrored;
        default:
            return state;
    }
}

export function followingIsLoading(state = false, action) {
    switch (action.type) {
        case 'FOLLOWING_IS_LOADING':
            return action.isLoading;
        default:
            return state;
    }
}

export function followersHasErrored(state = false, action) {
    switch (action.type) {
        case 'FOLLOWERS_HAS_ERRORED':
            return action.hasErrored;
        default:
            return state;
    }
}

export function followersIsLoading(state = false, action) {
    switch (action.type) {
        case 'FOLLOWERS_IS_LOADING':
            return action.isLoading;
        default:
            return state;
    }
}

export function users(state = [], action) {
    switch (action.type) {
        case 'USERS_FETCH_DATA_SUCCESS':
        {
            return merge(state, action.payload);
        }

        case 'FOLLOWING_FETCH_DATA_SUCCESS':
        {
            let user = {...action.payload.user, following: action.payload.following };
            return state.map(u => u.id === user.id ? user : u);
        }

        case 'FOLLOWERS_FETCH_DATA_SUCCESS':
        {
            let user = {...action.payload.user, followers: action.payload.followers };
            return state.map(u => u.id === user.id ? user : u);
        }

        case 'USER_REGISTER_SUCCESS': {
            return [...state, action.payload];
        }

        case 'USERS_UPDATE_BIO_SUCCESS':
        case 'USERS_UPDATE_LOCATION_SUCCESS':
        case 'USERS_UPDATE_DISPLAYNAME_SUCCESS':
        case 'USERS_UPDATE_WEBSITE_SUCCESS':
        {
            let user = state.find(u => u.id === action.payload.id);
            if (user) {
                // Replace user with payload user which contains the latest data
                return state.map(u => u.id === user.id ? action.payload : u);
            } else {
                // if it doesn't exist already, just append it to the list
                return [...state, user];
            }
        }

        case 'USER_FOLLOW_SUCCESS':
        {
            let user = state.find(u => u.username === action.payload.username);
            let followedUser = action.payload.userFollowed;
            followedUser = {...followedUser, followers: [...followedUser.followers, user]};
            user = {...user, following: [...user.following, action.payload.userFollowed]};

            let newState = state.map(u => u.id === user.id ? user : u);
            return newState.map(u => u.id === followedUser.id ? followedUser : u);
        }

        case 'USER_FOLLOW_FAILED':
            console.log("Follow failed");
            return state;

        case 'USER_UNFOLLOW_SUCCESS':
        {
            let user = state.find(u => u.username === action.payload.username);
            let unfollowedUser = action.payload.userUnfollowed;
            unfollowedUser = {...unfollowedUser, followers: unfollowedUser.followers.filter(u => u.id !== user.id)};
            user = {...user, following: user.following.filter(u => u.id !== action.payload.userUnfollowed.id)}
            
            let newState = state.map(u => u.id === user.id ? user : u);
            return newState.map(u => u.id === unfollowedUser.id ? unfollowedUser : u);
        }
        case 'USER_UNFOLLOW_FAILED':
            console.log("Unfollow failed");
            return state;
    
        default:
            return state;
    }
}

function merge(old,updated) {
    var o = {};
    
    old.forEach(function(v) { 
      o[v.id] = v; 
    })
    
    updated.forEach(function(v) { 
      o[v.id] = v; 
    })
    
    var r = [];
    
    for(var p in o) { 
      if(o.hasOwnProperty(p)) 
        r.push(o[p]); 
    }
    
    return r;
  }