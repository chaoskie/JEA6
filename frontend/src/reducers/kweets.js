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
            return merge(state, action.payload);
        case 'KWEET_CREATED_SUCCESS':        
            return [action.payload, ...state];
        case 'KWEET_LIKED_SUCCESS':
        case 'KWEET_UNLIKED_SUCCESS':
        {
            return state.map(k => k.id === action.payload.kweet.id ? action.payload.kweet : k);
        }        
        case 'KWEET_DELETE_SUCCESS':
            return state.filter(k => k.id !== action.payload.kweet.id);
        default:
            return state;
    }
}


function merge(old,updated) {
    var o = {};
    
    old.forEach(function(v) { 
      o[v.id] = v; 
    })
    
    updated.forEach(function(v) { 
      o[v.id] = v; 
    })
    
    var r = [];
    
    for(var p in o) { 
      if(o.hasOwnProperty(p)) 
        r.push(o[p]); 
    }
    
    return r;
  }