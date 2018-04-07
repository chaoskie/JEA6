import { combineReducers } from 'redux';
import { users, usersHasErrored, usersIsLoading } from './users';
import { kweets, kweetsHasErrored, kweetsIsLoading } from './kweets';

export default combineReducers({
    users, usersHasErrored, usersIsLoading,
    kweets, kweetsHasErrored, kweetsIsLoading
});