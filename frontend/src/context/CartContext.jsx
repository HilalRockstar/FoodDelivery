import { createContext, useEffect, useState } from 'react'
import { cartApi } from '../services/api'

const CartContext = createContext()

export function CartProvider({ children }) {

    const [cart, setCart] = useState([])
    const [loading, setLoading] = useState(false)

    useEffect(() => {
        cartApi.get().then(setCart).catch(() => setCart([]))
    }, [])

    async function addToCart(item) {
        setLoading(true)
        try {
            await cartApi.add(item.id)
            setCart(await cartApi.get())
        } finally {
            setLoading(false)
        }
    }

    async function changeQuantity(item, direction) {
        setLoading(true)
        try {
            if (direction === 'increase') {
                await cartApi.increase(item.id ?? item.cartItemId)
            } else {
                await cartApi.decrease(item.id ?? item.cartItemId)
            }
            setCart(await cartApi.get())
        } finally {
            setLoading(false)
        }
    }

    async function removeFromCart(item) {
        setLoading(true)
        try {
            await cartApi.remove(item.id ?? item.cartItemId)
            setCart(await cartApi.get())
        } finally {
            setLoading(false)
        }
    }

    async function clearCart() {
        setLoading(true)
        try {
            await cartApi.clear()
            setCart([])
        } finally {
            setLoading(false)
        }
    }

    return (
        <CartContext.Provider
            value={{
                cart,
                addToCart,
                changeQuantity,
                removeFromCart,
                clearCart,
                loading
            }}
        >
            {children}
        </CartContext.Provider>
    )
}

export default CartContext