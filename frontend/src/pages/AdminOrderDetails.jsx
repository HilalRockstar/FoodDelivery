import { useMemo, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import { adminApi, deliveryApi } from '../services/api'
import { useAdminPartners, useOrderDetails } from '../hooks/useAdminData'
import './Workflow.css'

function AdminOrderDetails() {
    const { id } = useParams()
    const navigate = useNavigate()
    const { data: order, loading } = useOrderDetails(id)
    const { data: partners } = useAdminPartners()
    const [busy, setBusy] = useState(false)

    const availablePartners = useMemo(
        () => partners.filter((partner) => partner.available),
        [partners]
    )

    async function handleAssign(event) {
        event.preventDefault()
        const formData = new FormData(event.currentTarget)
        const deliveryPartnerId = Number(formData.get('deliveryPartnerId'))
        if (!deliveryPartnerId) return

        setBusy(true)
        try {
            await adminApi.assignDelivery(id, deliveryPartnerId)
            navigate('/admin/orders')
        } catch (error) {
            alert(error.message || 'Unable to assign delivery partner')
        } finally {
            setBusy(false)
        }
    }

    async function handleStatusChange(nextStatus) {
        setBusy(true)
        try {
            await adminApi.updateStatus(id, nextStatus)
            navigate('/admin/orders')
        } catch (error) {
            alert(error.message || 'Unable to update order status')
        } finally {
            setBusy(false)
        }
    }

    if (loading) {
        return <main className="workflow-page"><p className="loading-state">Loading order details...</p></main>
    }

    const items = order.items || []

    return <main className="workflow-page"><Link className="back-link" to="/admin/orders">← Back to orders</Link><div className="workflow-heading"><div><p className="eyebrow">ADMIN / ORDER #{order.id}</p><h1>Order workflow</h1><p>{order.restaurant?.name || 'Restaurant'} · {order.orderDate || 'Recent order'}</p></div><span className="status-badge">{String(order.status).replaceAll('_', ' ')}</span></div><div className="workflow-grid"><section className="workflow-card"><div className="workflow-card-heading"><h2>Items requested</h2><span>{items.length} items</span></div>{items.map((item) => <div className="workflow-item" key={`${item.name}-${item.quantity}`}><div className="food-thumb">{item.name.charAt(0)}</div><div><strong>{item.name}</strong><p>{item.quantity} × ₹{item.price}</p></div><b>₹{item.quantity * item.price}</b></div>)}<div className="workflow-total"><span>Total paid</span><strong>₹{Number(order.totalAmount || 0).toFixed(2)}</strong></div></section><aside className="workflow-note"><p className="eyebrow">ORDER STATUS</p><h2>{order.status === 'DELIVERED' ? 'Delivery complete.' : 'Keep the order moving.'}</h2><p>Assign a delivery partner, then update the order as it reaches the next stage.</p><div className="delivery-pulse"><i /> Live order state</div></aside></div><section className="workflow-card nested-card"><div className="workflow-card-heading"><h2>Assign delivery</h2><span>{availablePartners.length} available</span></div><form onSubmit={handleAssign} className="inline-form"><select name="deliveryPartnerId" defaultValue="" required><option value="" disabled>Select delivery partner</option>{availablePartners.map((partner) => <option key={partner.id} value={partner.id}>{partner.fullName} · {partner.vehicleNumber}</option>)}</select><button className="button button-primary" type="submit" disabled={busy}>Assign partner</button></form></section><section className="workflow-card nested-card"><div className="workflow-card-heading"><h2>Advance status</h2><span>Database sync</span></div><div className="status-actions"><button className="button button-secondary" type="button" onClick={() => handleStatusChange('CONFIRMED')} disabled={busy}>Confirmed</button><button className="button button-secondary" type="button" onClick={() => handleStatusChange('PREPARING')} disabled={busy}>Preparing</button><button className="button button-secondary" type="button" onClick={() => handleStatusChange('OUT_FOR_DELIVERY')} disabled={busy}>Out for delivery</button><button className="button button-secondary" type="button" onClick={() => handleStatusChange('DELIVERED')} disabled={busy}>Delivered</button></div></section></main>
}

export default AdminOrderDetails
