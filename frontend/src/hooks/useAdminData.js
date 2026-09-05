import { useEffect, useMemo, useState } from 'react'
import { adminApi, deliveryApi, orderDetailsApi } from '../services/api'

const EMPTY_LIST = []
const EMPTY_OBJECT = {}

function useRequest(loader, initialValue = EMPTY_LIST) {
    const [data, setData] = useState(initialValue)
    const [loading, setLoading] = useState(true)
    useEffect(() => { loader().then(setData).catch(() => setData(initialValue)).finally(() => setLoading(false)) }, [loader, initialValue])
    return { data, loading }
}

export const useAdminRestaurants = () => useRequest(adminApi.restaurants)
export const useAdminPartners = () => useRequest(adminApi.deliveryPartners)
export const useAdminOrders = () => useRequest(adminApi.orders)
export const useDeliveryOrders = () => useRequest(deliveryApi.orders)
export function useOrderDetails(id, delivery = false) {
    const loader = useMemo(() => () => delivery ? deliveryApi.order(id) : orderDetailsApi.get(id), [id, delivery])
    return useRequest(loader, EMPTY_OBJECT)
}