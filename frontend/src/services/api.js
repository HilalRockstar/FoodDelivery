const API_BASE = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

function getToken() {
    return localStorage.getItem('jwtToken') || ''
}

function clearToken() {
    localStorage.removeItem('jwtToken')
}

function buildHeaders(options = {}) {
    const headers = new Headers(options.headers || {})
    const token = getToken()

    if (token) {
        headers.set('Authorization', `Bearer ${token}`)
    }

    return headers
}

function handleAuthFailure(response) {
    if (response.status === 401 || response.status === 403) {
        clearToken()
        if (window.location.pathname !== '/login') {
            window.location.assign('/login?error=expired')
        }
    }
}

async function request(path, options = {}) {
    const response = await fetch(`${API_BASE}${path}`, {
        credentials: 'include',
        ...options,
        headers: buildHeaders(options),
    })

    if (!response.ok) {
        handleAuthFailure(response)
        throw new Error(`Request failed: ${response.status}`)
    }

    const type = response.headers.get('content-type') || ''
    if (!type.includes('application/json')) throw new Error('Backend returned an HTML page')
    return response.json()
}

async function command(path, options = {}) {
    const response = await fetch(`${API_BASE}${path}`, {
        credentials: 'include',
        ...options,
        headers: buildHeaders(options),
    })

    if (!response.ok) {
        handleAuthFailure(response)
        throw new Error(`Request failed: ${response.status}`)
    }

    return true
}

export const restaurantApi = {
    list: () => request('/api/restaurants'),
    menu: (id) => request(`/api/restaurants/${id}/menu`),
}

export const cartApi = {
    get: () => request('/api/cart'),
    add: (id) => command(`/user/cart/add/${id}`),
    increase: (id) => command(`/user/cart/increase/${id}`),
    decrease: (id) => command(`/user/cart/decrease/${id}`),
    remove: (id) => command(`/user/cart/remove/${id}`),
    clear: () => command('/user/cart/clear'),
}

export const orderApi = {
    list: () => request('/api/orders'),
    place: () => command('/orders/place', { method: 'POST' }),
}

export const adminApi = {
    restaurants: () => request('/api/admin/restaurants'),
    menu: (id) => request(`/api/admin/restaurants/${id}/menu`),
    deliveryPartners: () => request('/api/admin/delivery-partners'),
    orders: () => request('/api/admin/orders'),
}

export const deliveryApi = {
    orders: () => request('/api/delivery/orders'),
    order: (id) => request(`/api/delivery/orders/${id}`),
}

export const orderDetailsApi = { get: (id) => request(`/api/orders/${id}`) }