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
            return action.payload;
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
            user = {...user, following: [...user.following, action.payload.userFollowed]};
                    
            return state.map(u => u.id === user.id ? user : u);
        }

        case 'USER_FOLLOW_FAILED':
            console.log("Follow failed");
            return state;

        case 'USER_UNFOLLOW_SUCCESS':
        {
            let user = state.find(u => u.username === action.payload.username);
            user = {...user, following: user.following.filter(u => u.id !== action.payload.userUnfollowed.id)}

            return state.map(u => u.id === user.id ? user : u);
        }
        case 'USER_UNFOLLOW_FAILED':
            console.log("Unfollow failed");
            return state;
    
        default:
            return state;
    }
}