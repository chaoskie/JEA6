import { callApiGet, callApiPost } from '../middleware/api';

export function kweetsHasErrored(bool) {
    return {
        type: 'KWEETS_HAS_ERRORED',
        hasErrored: bool
    }
}

export function kweetsIsLoading(bool) {
    return {
        type: 'KWEETS_IS_LOADING',
        isLoading: bool
    };
}

export function kweetsFetchDataSuccess(kweets) {
    return {
        type: 'KWEETS_FETCH_DATA_SUCCESS',
        payload: kweets
    };
}

export function kweetCreatedSuccess(kweet) {
    return {
        type: 'KWEET_CREATED_SUCCESS',
        payload: kweet
    };
}

export function kweetUnlikedSuccess(kweet, user) {
    return {
        type: 'KWEET_UNLIKED_SUCCESS',
        payload: { kweet, user }
    };
}

export function kweetLikedSuccess(kweet, user) {
    return {
        type: 'KWEET_LIKED_SUCCESS',
        payload: { kweet, user }
    };
}

export function kweetDeleteSuccess(kweet, user) {
    return {
        type: 'KWEET_DELETE_SUCCESS',
        payload: { kweet, user }
    };
}


export function kweetCreation(kweet, socket) {
    var obj = {
        type: "createKweet",
        kweet: kweet
    };

    socket.send(JSON.stringify(obj));
}

export function deleteTheKweet(kweet, socket) {

    var obj = {
        type: "deleteKweet",
        kweet: kweet,
    };

    socket.send(JSON.stringify(obj));

    console.log('action delete');
}

export function unlikeTheKweet(kweet, user) {
    return (dispatch) => {
        callApiPost(`kweets/${kweet.id}/unlike`, true, null)
            .then(response => { return response.json(); })
            .then(json => { dispatch(kweetUnlikedSuccess(json, user)) })
            .catch(error => { console.log(error); });
    };
}

export function likeTheKweet(kweet, user) {
    return (dispatch) => {
        callApiPost(`kweets/${kweet.id}/like`, true, null)
            .then(response => { return response.json(); })
            .then(json => { dispatch(kweetLikedSuccess(json, user)) })
            .catch(error => { console.log(error); });
    };
}

export function kweetsFetchAll() {
    return (dispatch) => {
        dispatch(kweetsIsLoading(true));
        callApiGet('kweets', false)
            .then(response => { return response.json(); })
            .then(json => { dispatch(kweetsFetchDataSuccess(json)); dispatch(kweetsIsLoading(false)); })
            .catch(error => { dispatch(kweetsHasErrored(true)) });
    };
}

export function searchKweets(s) {
    return (dispatch) => {
        dispatch(kweetsIsLoading(true));
        callApiGet('kweets/search?v=' + s, false)
            .then(response => { return response.json(); })
            .then(json => { dispatch(kweetsFetchDataSuccess(json)); dispatch(kweetsIsLoading(false)); })
            .catch(error => { dispatch(kweetsHasErrored(true)) });
    };
}

export function kweetsFetchUser(user) {
    return (dispatch) => {
        dispatch(kweetsIsLoading(true));
        callApiGet('kweets/' + user.username, false)
            .then(response => { return response.json(); })
            .then(json => { dispatch(kweetsFetchDataSuccess(json)); dispatch(kweetsIsLoading(false)); })
            .catch(error => { dispatch(kweetsHasErrored(true)) });
    }
}

export function kweetsFetchTimeline(username) {
    return (dispatch) => {
        dispatch(kweetsIsLoading(true));
        callApiGet('kweets/' + username + '/timeline', false)
            .then(response => { return response.json(); })
            .then(json => { dispatch(kweetsFetchDataSuccess(json)); dispatch(kweetsIsLoading(false)); })
            .catch(error => { dispatch(kweetsHasErrored(true)) });
    }
}