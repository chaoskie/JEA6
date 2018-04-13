// See: https://auth0.com/blog/secure-your-react-and-redux-app-with-jwt-authentication/

const BASE_URL = 'http://localhost:8080/Kwetter-Gamma/api/v1/';

function callApi(endpoint, authenticated, config) {

    let token = localStorage.getItem('id_token') || null

    if (authenticated) {
        if (token) {
            config = {
                ...config,
                headers: {
                    ...config.headers,
                    'Authorization': `Bearer ${token}`
                }
            }
        }
        else {
            throw new Error("No token saved!");
        }
    }
    
    return fetch(BASE_URL + endpoint, config);
}

export function callApiGet(endpoint, authenticated) {
    let config = { headers: { 'Content-Type':'application/json' }}
    return callApi(endpoint, authenticated, config);
}

export function callApiPost(endpoint, authenticated, body) {
    let config = {
        headers: { 'Content-Type':'application/json' },
        method: "POST",
        body: (typeof body === 'string') ? body : JSON.stringify(body)
    };
    console.log('post call ;'+endpoint);
    return callApi(endpoint, authenticated, config);
}

export function callApiDelete(endpoint, authenticated, body) {
    let config = {
        headers: { 'Content-Type':'application/json' },
        method: "DELETE",
        body: (typeof body === 'string') ? body : JSON.stringify(body)
    };

    return callApi(endpoint, authenticated, config);
}

export function callApiPut(endpoint, authenticated, body) {
    let config = {
        headers: { 'Content-Type':'application/json' },
        method: "PUT",
        body: (typeof body === 'string') ? body : JSON.stringify(body)
    };

    return callApi(endpoint, authenticated, config);
}
