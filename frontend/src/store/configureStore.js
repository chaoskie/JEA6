import { createStore, applyMiddleware } from 'redux';
import thunk from 'redux-thunk';
import { routerMiddleware } from 'react-router-redux';
import rootReducer from '../reducers';
export default function configureStore(initialState, history) {
    return createStore(
        rootReducer,
        initialState,
        applyMiddleware(thunk, routerMiddleware(history))
    );
}