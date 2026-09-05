import { useEffect, useState } from 'react'
import { restaurantApi } from '../services/api'

export function useRestaurants() {
    const [restaurants, setRestaurants] = useState([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        restaurantApi.list().then(setRestaurants).catch(() => setRestaurants([])).finally(() => setLoading(false))
    }, [])

    return { restaurants, loading }
}

export function useRestaurantMenu(id) {
    const [menuItems, setMenuItems] = useState([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        setLoading(true)
        restaurantApi.menu(id).then(setMenuItems).catch(() => setMenuItems([])).finally(() => setLoading(false))
    }, [id])

    return { menuItems, loading }
}