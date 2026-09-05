const API_BASE = import.meta.env.VITE_API_BASE_URL || ''

const demoRestaurants = [
    { id: 1, name: 'Paradise Restaurant', description: 'Biryani, tandoor and Indian comfort food.', address: 'Ramanathapuram', phoneNumber: '+91 98765 43210', active: true },
    { id: 2, name: 'Food Corner', description: 'Crispy pizzas, loaded fries and quick bites.', address: 'Keelakarai', phoneNumber: '+91 98765 43211', active: true },
    { id: 3, name: 'Spice Hub', description: 'South Indian classics with a modern touch.', address: 'Chennai', phoneNumber: '+91 98765 43212', active: true },
]

const demoMenus = {
    1: [
        { id: 1, name: 'Chicken Biryani', description: 'Fragrant basmati rice, tender chicken and saffron.', price: 180, available: true, restaurantName: 'Paradise Restaurant' },
        { id: 2, name: 'Mutton Biryani', description: 'Slow-cooked mutton with aromatic spices.', price: 250, available: true, restaurantName: 'Paradise Restaurant' },
    ],
    2: [
        { id: 3, name: 'Chicken Pizza', description: 'Roasted chicken, peppers and stretchy mozzarella.', price: 220, available: true, restaurantName: 'Food Corner' },
        { id: 4, name: 'Veg Pizza', description: 'Garden vegetables, herbs and mozzarella.', price: 180, available: true, restaurantName: 'Food Corner' },
    ],
    3: [{ id: 5, name: 'Masala Dosa', description: 'Golden dosa with spiced potato filling.', price: 120, available: true, restaurantName: 'Spice Hub' }],
}

let localCart = []

async function request(path, options = {}) {
    const response = await fetch(`${API_BASE}${path}`, { credentials: 'include', ...options })
    const type = response.headers.get('content-type') || ''
    if (!response.ok) throw new Error(`Request failed: ${response.status}`)
    if (!type.includes('application/json')) throw new Error('Backend returned an HTML page')
    return response.json()
}

function cartSnapshot() {
    return localCart.map((item) => ({ ...item, totalPrice: item.price * item.quantity }))
}

export const restaurantApi = {
    async list() {
        try { return await request('/user/restaurants') } catch { return demoRestaurants }
    },
    async menu(id) {
        try { return await request(`/user/menu/${id}`) } catch { return demoMenus[id] || [] }
    },
}

export const cartApi = {
    async get() {
        try { return await request('/user/cart') } catch { return cartSnapshot() }
    },
    async add(menuItemId) {
        const item = Object.values(demoMenus).flat().find((menuItem) => menuItem.id === menuItemId)
        try { return await request(`/user/cart/add/${menuItemId}`) } catch {
            if (item) {
                const existing = localCart.find((cartItem) => cartItem.id === item.id)
                if (existing) existing.quantity += 1
                else localCart.push({ ...item, quantity: 1, cartItemId: item.id })
            }
            return cartSnapshot()
        }
    },
    async increase(id) { try { return await request(`/user/cart/increase/${id}`) } catch { return updateLocal(id, 1) } },
    async decrease(id) { try { return await request(`/user/cart/decrease/${id}`) } catch { return updateLocal(id, -1) } },
    async remove(id) { try { return await request(`/user/cart/remove/${id}`) } catch { localCart = localCart.filter((item) => item.id !== id && item.cartItemId !== id); return cartSnapshot() } },
    async clear() { try { await request('/user/cart/clear') } catch { localCart = [] } },
}

function updateLocal(id, delta) {
    const item = localCart.find((cartItem) => cartItem.id === id || cartItem.cartItemId === id)
    if (item) item.quantity += delta
    localCart = localCart.filter((cartItem) => cartItem.quantity > 0)
    return cartSnapshot()
}

export const orderApi = {
    async list() {
        try { return await request('/orders') } catch { return [] }
    },
    async place() {
        try { return await request('/orders/place', { method: 'POST' }) } catch { localCart = []; return null }
    },
}