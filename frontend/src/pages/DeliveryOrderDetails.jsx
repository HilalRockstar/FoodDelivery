import { Link, useParams } from 'react-router-dom'
import AdminShell from '../components/AdminShell'
import { useOrderDetails } from '../hooks/useAdminData'
import './Workflow.css'

function DeliveryOrderDetails() {
    const { id } = useParams()
    const { data: order, loading } = useOrderDetails(id, true)
    if (loading) return <AdminShell eyebrow="DELIVERY" title="Loading order" description="Fetching the assigned order from the database."><p className="loading-state">Loading delivery details...</p></AdminShell>
    const items = order.items || []
    return <AdminShell eyebrow={`DELIVERY / ORDER #${id}`} title="Delivery details" description="Keep the customer informed as this order moves toward its destination."><Link className="back-link" to="/delivery/orders">← Back to assigned orders</Link><div className="delivery-detail-grid"><section className="workflow-card"><div className="workflow-card-heading"><div><p className="order-id">ORDER #{id}</p><h2>{order.restaurant?.name || 'Restaurant'}</h2></div><span className="status-badge busy">{String(order.status).replaceAll('_', ' ')}</span></div><div className="customer-callout"><span className="admin-avatar">{order.user?.fullName?.charAt(0) || 'C'}</span><div><strong>{order.user?.fullName || 'Customer'}</strong><p>Customer · Database order</p></div><span className="call-icon">⌕</span></div><div className="workflow-card-heading"><h2>Items to deliver</h2><span>₹{Number(order.totalAmount || 0).toFixed(2)} total</span></div>{items.map((item) => <div className="workflow-item" key={item.name}><div className="food-thumb">{item.name.slice(0, 2).toUpperCase()}</div><div><strong>{item.name}</strong><p>{item.quantity} × ₹{item.price}</p></div><b>₹{item.quantity * item.price}</b></div>)}</section><aside className="delivery-action-panel"><p className="eyebrow">NEXT ACTION</p><h2>Update the order status when the delivery changes.</h2><form action="http://localhost:8080/delivery/update-status" method="post"><input type="hidden" name="orderId" value={id} /><input type="hidden" name="status" value="DELIVERED" /><button className="button button-primary full-width" type="submit">Mark delivered <span>✓</span></button></form><p className="subtle">Current status: {String(order.status).replaceAll('_', ' ')}</p></aside></div></AdminShell>
}

export default DeliveryOrderDetails
