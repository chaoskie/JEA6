import { callApiPost } from '../middleware/api';

export function requestLogin(creds) {
  return {
    type: 'LOGIN_REQUEST',
    isFetching: true,
    isAuthenticated: false,
    creds
  }
}
  
export  function receiveLogin(user) {
  return {
    type: 'LOGIN_SUCCESS',
    isFetching: false,
    isAuthenticated: true,
    id_token: user
  }
}
  
export function loginError(message) {
  return {
    type: 'LOGIN_FAILURE',
    isFetching: false,
    isAuthenticated: false,
    message
  }
}

function requestLogout() {
  return {
    type: 'LOGOUT_REQUEST',
    isFetching: true,
    isAuthenticated: true
  }
}
  
function receiveLogout() {
  return {
    type: 'LOGOUT_SUCCESS',
    isFetching: false,
    isAuthenticated: false
  }
}

export function loginUser(creds) {
  return dispatch => {
    // We dispatch requestLogin to kickoff the call to the API
    dispatch(requestLogin(creds))

    return callApiPost('authentication', false, creds)
    .then(response =>
      response.text().then(token => ({ token, response }))
          ).then(({ token, response }) =>  {
      if (!response.ok) {
        // If there was a problem, we want to
        // dispatch the error condition
        dispatch(loginError(token));
        return Promise.reject(token);
      } else {
        // If login was successful, set the token in local storage
        localStorage.setItem('id_token', token);
        // Dispatch the success action
        dispatch(receiveLogin(token));
      }
    }).catch(err => console.log("Error: ", err))
  }
}

export function logoutUser() {
  return dispatch => {
    dispatch(requestLogout());
    localStorage.removeItem('id_token');
    dispatch(receiveLogout());
  }
}