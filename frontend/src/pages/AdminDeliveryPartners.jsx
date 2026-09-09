import { Link } from 'react-router-dom'
import AdminShell from '../components/AdminShell'
import { useAdminPartners } from '../hooks/useAdminData'
import './AdminPage.css'

function AdminDeliveryPartners() {
    const { data: partners, loading } = useAdminPartners()

    return <AdminShell
        eyebrow="OPERATIONS"
        title="Delivery partners"
        description="Keep your delivery team visible, available, and ready for the next order."
        action={<Link className="admin-action" to="/admin/delivery-partners/create">+ Add partner</Link>}
    >
        <div className="admin-stats">
            <div className="admin-stat"><p>Total partners</p><strong>{partners.length}</strong><span>From your database</span></div>
            <div className="admin-stat"><p>Available</p><strong>{partners.filter((item) => item.available).length}</strong><span>Ready for assignment</span></div>
            <div className="admin-stat"><p>On delivery</p><strong>{partners.filter((item) => !item.available).length}</strong><span>Currently active</span></div>
            <div className="admin-stat"><p>Accounts</p><strong>{partners.filter((item) => item.enabled).length}</strong><span>Enabled partners</span></div>
        </div>
        <section className="admin-table-wrap">
            <div className="admin-table-toolbar">
                <h2>Partner directory</h2>
                <span className="subtle">Live database records</span>
            </div>
            {loading ? <p className="loading-state">Loading delivery partners...</p> : <table className="admin-table"><thead><tr><th>Partner</th><th>Contact</th><th>Vehicle</th><th>Availability</th><th /></tr></thead><tbody>{partners.map((item) => <tr key={item.id}><td><strong>{item.fullName}</strong><p className="subtle">{item.email}</p></td><td>{item.phoneNumber}</td><td>{item.vehicleNumber}</td><td><span className={`status-badge ${item.available ? '' : 'busy'}`}>{item.available ? 'Available' : 'On delivery'}</span></td><td><a className="table-action" href={`http://localhost:8080/admin/delivery-partners/${item.id}`}>View details ↗</a></td></tr>)}</tbody></table>}
        </section>
    </AdminShell>
}

export default AdminDeliveryPartners