import { combineReducers } from 'redux';
import { users, usersHasErrored, usersIsLoading, followersHasErrored, followersIsLoading } from './users';
import { kweets, kweetsHasErrored, kweetsIsLoading } from './kweets';
import { authentication } from './authentication';

export default combineReducers({
    users, usersHasErrored, usersIsLoading, followersHasErrored, followersIsLoading,
    kweets, kweetsHasErrored, kweetsIsLoading,
    authentication
});