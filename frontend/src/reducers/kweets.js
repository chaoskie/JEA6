export function kweetsHasErrored(state = false, action) {
    switch (action.type) {
        case 'KWEETS_HAS_ERRORED':
            return action.hasErrored;
        default:
            return state;
    }
}
export function kweetsIsLoading(state = false, action) {
    switch (action.type) {
        case 'KWEETS_IS_LOADING':
            return action.isLoading;
        default:
            return state;
    }
}
export function kweets(state = [], action) {
    switch (action.type) {
        case 'KWEETS_FETCH_DATA_SUCCESS':
            return action.payload;
        default:
            return state;
    }
}