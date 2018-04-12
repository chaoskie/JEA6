import { callApiGet } from '../middleware/api';

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

export function getUsernameFromJwt() {
    let token = localStorage.getItem('id_token');
    if (!token) { return '' }

    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64)).username;
}

export function isModeratorFromJwt() {
    let token = localStorage.getItem('id_token');
    if (!token) { return '' }

    let base64Url = token.split('.')[1];
    let base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64)).moderator ? true : false;
}