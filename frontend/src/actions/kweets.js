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

export function kweetCreation(kweet){
    //console.log('kweet creation');
    return (dispatch) => {
       // dispatch(kweetsIsLoading(true));
        callApiPost('kweets', true, kweet)
        .then(response => {console.log('response'); /*dispatch(kweetsIsLoading(false)); */return response.json(); })
        .then(json => { dispatch(kweetCreatedSuccess(json))})
        .catch(error => { console.log(error); /*dispatch(kweetsHasErrored(true))*/ });
    };
}

export function kweetsFetchAll() {
    return (dispatch) => {
        dispatch(kweetsIsLoading(true));
        callApiGet('kweets', false)
        .then(response => { dispatch(kweetsIsLoading(false)); return response.json(); })
        .then(json => { dispatch(kweetsFetchDataSuccess(json))})
        .catch(error => { dispatch(kweetsHasErrored(true)) });
        

        // fetch(url)
        //     .then((response) => {
        //         if (!response.ok) {
        //             throw Error(response.statusText);
        //         }
        //         dispatch(kweetsIsLoading(false));
        //         return response;
        //     })
        //     .then((response) => response.json())
        //     .then((kweets) => dispatch(kweetsFetchDataSuccess(kweets)))
        //     .catch(() => dispatch(kweetsHasErrored(true)));
    };
}