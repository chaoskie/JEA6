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

export function kweetsFetchData(url) {
    return (dispatch) => {
        dispatch(kweetsIsLoading(true));
        fetch(url)
            .then((response) => {
                if (!response.ok) {
                    throw Error(response.statusText);
                }
                dispatch(kweetsIsLoading(false));
                return response;
            })
            .then((response) => response.json())
            .then((kweets) => dispatch(kweetsFetchDataSuccess(kweets)))
            .catch(() => dispatch(kweetsHasErrored(true)));
    };
}