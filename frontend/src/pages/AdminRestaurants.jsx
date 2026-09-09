import { useState } from 'react'
import { Link } from 'react-router-dom'
import AdminShell from '../components/AdminShell'
import { useAdminRestaurants } from '../hooks/useAdminData'
import './AdminPage.css'

function AdminRestaurants() {
    const [query, setQuery] = useState('')
    const { data: restaurants, loading } = useAdminRestaurants()
    const filtered = restaurants.filter((item) => item.name.toLowerCase().includes(query.toLowerCase()))

    return <AdminShell
        eyebrow="CATALOG"
        title="Restaurants"
        description="Manage the kitchens, menus, and availability across your delivery network."
        action={<Link className="admin-action" to="/admin/restaurants/create">+ Add restaurant</Link>}
    >
        <div className="admin-stats">
            <div className="admin-stat"><p>Total restaurants</p><strong>{restaurants.length}</strong><span>From your database</span></div>
            <div className="admin-stat"><p>Active now</p><strong>{restaurants.filter((item) => item.active).length}</strong><span>Currently available</span></div>
            <div className="admin-stat"><p>Directory</p><strong>{loading ? '...' : restaurants.length}</strong><span>Loaded from API</span></div>
            <div className="admin-stat"><p>Coverage</p><strong>Live</strong><span>Database connected</span></div>
        </div>
        <section className="admin-table-wrap">
            <div className="admin-table-toolbar">
                <h2>Restaurant directory</h2>
                <input className="admin-search" value={query} onChange={(event) => setQuery(event.target.value)} placeholder="Search restaurants" />
            </div>
            {loading ? <p className="loading-state">Loading restaurants...</p> : <table className="admin-table"><thead><tr><th>Restaurant</th><th>Location</th><th>Phone</th><th>Status</th><th /></tr></thead><tbody>{filtered.map((item) => <tr key={item.id}><td><strong>{item.name}</strong><p className="subtle">ID #{item.id}</p></td><td>{item.address}</td><td>{item.phoneNumber}</td><td><span className={`status-badge ${item.active ? '' : 'busy'}`}>{item.active ? 'Active' : 'Closed'}</span></td><td><Link className="table-action" to={`/admin/menu/${item.id}`}>View menu ↗</Link></td></tr>)}</tbody></table>}
        </section>
    </AdminShell>
}

export default AdminRestaurants