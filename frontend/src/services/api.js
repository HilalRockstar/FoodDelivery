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
    add: (id) => command(`/api/cart/add/${id}`, { method: 'POST' }),
    increase: (id) => command(`/api/cart/increase/${id}`, { method: 'POST' }),
    decrease: (id) => command(`/api/cart/decrease/${id}`, { method: 'POST' }),
    remove: (id) => command(`/api/cart/remove/${id}`, { method: 'POST' }),
    clear: () => command('/api/cart/clear', { method: 'POST' }),
}

export const orderApi = {
    list: () => request('/api/orders'),
    place: () => command('/api/orders/place', { method: 'POST' }),
}

export const adminApi = {
    restaurants: () => request('/api/admin/restaurants'),
    menu: (id) => request(`/api/admin/restaurants/${id}/menu`),
    deliveryPartners: () => request('/api/admin/delivery-partners'),
    orders: () => request('/api/admin/orders'),
    assignDelivery: (orderId, deliveryPartnerId) => command(`/api/admin/orders/${orderId}/assign-delivery`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ deliveryPartnerId }),
    }),
    updateStatus: (orderId, status) => command(`/api/admin/orders/${orderId}/status`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ status }),
    }),
}

export const deliveryApi = {
    orders: () => request('/api/delivery/orders'),
    order: (id) => request(`/api/delivery/orders/${id}`),
    updateStatus: (orderId, status) => command(`/api/delivery/orders/${orderId}/status`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ status }),
    }),
}

export const orderDetailsApi = { get: (id) => request(`/api/orders/${id}`) }