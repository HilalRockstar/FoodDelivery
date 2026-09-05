import { NavLink, Link } from 'react-router-dom'
import './AdminShell.css'

function AdminShell({ title, eyebrow, description, action, children }) {
    return <main className="admin-app"><aside className="admin-sidebar"><Link className="admin-logo" to="/">foodie<span>admin</span></Link><p className="admin-nav-label">WORKSPACE</p><nav><NavLink to="/admin/dashboard">Overview</NavLink><NavLink to="/admin/restaurants">Restaurants</NavLink><NavLink to="/admin/delivery-partners">Delivery partners</NavLink><NavLink to="/admin/orders">Orders</NavLink></nav><div className="admin-sidebar-footer"><span className="admin-avatar">A</span><div><strong>Admin account</strong><small>Operations</small></div></div></aside><section className="admin-content"><header className="admin-topbar"><span className="admin-mobile-brand">foodie / admin</span><span className="admin-live"><i /> System operational</span></header><div className="admin-inner"><div className="admin-heading"><div><p className="eyebrow">{eyebrow}</p><h1>{title}</h1><p>{description}</p></div>{action}</div>{children}</div></section></main>
}

export default AdminShell