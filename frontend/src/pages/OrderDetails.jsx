import { Link, useParams } from 'react-router-dom'
import { useOrderDetails } from '../hooks/useAdminData'
import './Workflow.css'

function OrderDetails() {
    const { id } = useParams()
    const { data: order, loading } = useOrderDetails(id)
    if (loading) return <main className="workflow-page"><p className="loading-state">Loading order details...</p></main>
    const items = order.items || []
    return <main className="workflow-page"><Link className="back-link" to="/orders">← Back to orders</Link><div className="workflow-heading"><div><p className="eyebrow">ORDER #{order.id}</p><h1>Order details</h1><p>{order.restaurant?.name || 'Restaurant'} · {order.orderDate || 'Recent order'}</p></div><span className="status-badge">{String(order.status).replaceAll('_', ' ')}</span></div><section className="progress-track"><span className="progress-step done">✓<small>Confirmed</small></span><span className="progress-line active" /><span className="progress-step current">2<small>{String(order.status).replaceAll('_', ' ')}</small></span><span className="progress-line" /><span className="progress-step">3<small>Delivered</small></span></section><div className="workflow-grid"><section className="workflow-card"><div className="workflow-card-heading"><h2>Order items</h2><span>{items.length} items</span></div>{items.map((item) => <div className="workflow-item" key={item.name}><div className="food-thumb">{item.name.charAt(0)}</div><div><strong>{item.name}</strong><p>{item.quantity} × ₹{item.price}</p></div><b>₹{item.quantity * item.price}</b></div>)}<div className="workflow-total"><span>Total paid</span><strong>₹{Number(order.totalAmount || 0).toFixed(2)}</strong></div></section><aside className="workflow-note"><p className="eyebrow">ORDER STATUS</p><h2>{order.status === 'DELIVERED' ? 'Delivered successfully.' : 'Your order is moving.'}</h2><p>We are keeping this order status synced with the database.</p><div className="delivery-pulse"><i /> Live order status</div></aside></div></main>
}

export default OrderDetails
