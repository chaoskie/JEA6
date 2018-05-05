export function authentication(state = {
    isFetching: false,
    isAuthenticated: localStorage.getItem('id_token') ? true : false
  }, action) {
  switch (action.type) {
    case 'LOGIN_REQUEST':
      return { ...state, 
        isFetching: true,
        isAuthenticated: false,
        username: action.creds.username,
        errorMessage: ''
      }
    case 'LOGIN_SUCCESS':
      return {...state, 
        isFetching: false,
        isAuthenticated: true,
        errorMessage: ''
      }
    case 'LOGIN_FAILURE':
      return { ...state, 
        isFetching: false,
        isAuthenticated: false,
        errorMessage: action.message
      }
    case 'LOGOUT_SUCCESS':
      return {...state,
        isFetching: true,
        isAuthenticated: false,
        errorMessage: ''
      }
    default:
      return state
  }
}