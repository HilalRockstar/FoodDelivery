import { Link } from 'react-router-dom'
import AdminShell from '../components/AdminShell'
import { useDeliveryOrders } from '../hooks/useAdminData'
import './Workflow.css'

function DeliveryDashboard() {
    const { data: orders, loading } = useDeliveryOrders()
    return <AdminShell eyebrow="DELIVERY PARTNER / TODAY" title="Your delivery run" description="Everything assigned to you today, with the next action always close at hand." action={<span className="status-badge">● Live</span>}><div className="admin-stats"><div className="admin-stat"><p>Assigned orders</p><strong>{orders.length}</strong><span>From your account</span></div><div className="admin-stat"><p>Delivered</p><strong>{orders.filter((item) => item.status === 'DELIVERED').length}</strong><span>Completed</span></div><div className="admin-stat"><p>On the way</p><strong>{orders.filter((item) => item.status === 'OUT_FOR_DELIVERY').length}</strong><span>Currently active</span></div><div className="admin-stat"><p>Total value</p><strong>₹{orders.reduce((sum, item) => sum + Number(item.totalAmount || 0), 0)}</strong><span>Assigned orders</span></div></div><section className="delivery-order-list">{loading ? <p className="loading-state">Loading assigned orders...</p> : orders.map((order) => <Link className="delivery-order-card" to={`/delivery/order/${order.id}`} key={order.id}><div className="delivery-order-icon">#{String(order.id).slice(-2)}</div><div className="delivery-order-copy"><span className="order-id">ORDER #{order.id}</span><h2>{order.restaurant?.name || 'Restaurant'}</h2><p>{order.user?.fullName || 'Customer'} · ₹{order.totalAmount}</p></div><div><span className={`status-badge ${order.status === 'DELIVERED' ? '' : 'busy'}`}>{String(order.status).replaceAll('_', ' ')}</span><strong className="delivery-arrow">→</strong></div></Link>)}</section></AdminShell>
}

export default DeliveryDashboard
