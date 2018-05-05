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
            return merge(state, action.payload).sort((a, b) => new Date(b.date) - new Date(a.date));
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


function merge(old, updated) {
    let o = {};
    let copyOfOld = [...old];

    if (old.length > updated.length) {
        // basically removes deleted kweets if they are found
        copyOfOld = old.filter(ol => updated.map(up => up.id).includes(ol.id));
    }

    copyOfOld.forEach(function(v) { 
      o[v.id] = v; 
    });
    
    updated.forEach(function(v) { 
      o[v.id] = v; 
    });
    
    
    let r = [];
    
    for(let p in o) { 
      if(o.hasOwnProperty(p))
        r.push(o[p]); 
    }
    
    return r;
  }