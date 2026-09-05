import { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { orderApi } from '../services/api'

function MyOrders() {
    const [orders, setOrders] = useState([])
    const [loading, setLoading] = useState(true)

    useEffect(() => { orderApi.list().then(setOrders).finally(() => setLoading(false)) }, [])

    return <main className="page-shell"><div className="page-intro compact"><p className="eyebrow">YOUR HISTORY</p><h1>Orders, all in one place.</h1><p>Keep an eye on every meal from confirmation to doorstep.</p></div>{loading ? <p className="loading-state">Loading your orders...</p> : orders.length === 0 ? <section className="empty-state"><span className="empty-icon">✓</span><h2>No orders yet</h2><p>Your next favourite meal is only a few taps away.</p><Link className="button button-primary" to="/restaurants">Start exploring</Link></section> : <div className="orders-list">{orders.map((order) => <Link className="order-row" to={`/orders/${order.id}`} key={order.id}><div><span className="order-id">ORDER #{order.id}</span><h3>{order.restaurant?.name ?? 'Foodie order'}</h3></div><div className="order-meta"><span className={`status status-${String(order.status).toLowerCase()}`}>{String(order.status).replaceAll('_', ' ')}</span><strong>₹{Number(order.totalAmount).toFixed(2)}</strong><span>→</span></div></Link>)}</div>}</main>
}

export default MyOrders