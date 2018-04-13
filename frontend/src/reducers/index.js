import { combineReducers } from 'redux';
import { users, usersHasErrored, usersIsLoading, followersHasErrored, followersIsLoading } from './users';
import { kweets, kweetsHasErrored, kweetsIsLoading } from './kweets';
import { authentication } from './authentication';
import { routerReducer } from 'react-router-redux'

export default combineReducers({
    users, usersHasErrored, usersIsLoading, followersHasErrored, followersIsLoading,
    kweets, kweetsHasErrored, kweetsIsLoading,
    authentication,

    // React-redux-router
    router: routerReducer
});