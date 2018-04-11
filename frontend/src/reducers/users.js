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
export function users(state = [], action) {
    switch (action.type) {
        case 'USERS_FETCH_DATA_SUCCESS':
            return action.payload;
        case 'USERS_UPDATE_BIO_SUCCESS':
        case 'USERS_UPDATE_LOCATION_SUCCESS':
        case 'USERS_UPDATE_DISPLAYNAME_SUCCESS':
        case 'USERS_UPDATE_WEBSITE_SUCCESS':
            let user = state.find(u => u.id === action.payload.id);
            if (user) {
                // Replace user with payload user which contains the latest data
                return state.map(u => u.id === user.id ? action.payload : u);
            } else {
                // if it doesn't exist already, just append it to the list
                return [...state, user];
            }
        default:
            return state;
    }
}