import { combineReducers } from 'redux';
import { users, usersHasErrored, usersIsLoading } from './users';
import { kweets, kweetsHasErrored, kweetsIsLoading } from './kweets';
import { authentication } from './authentication';

export default combineReducers({
    users, usersHasErrored, usersIsLoading,
    kweets, kweetsHasErrored, kweetsIsLoading,
    authentication
});