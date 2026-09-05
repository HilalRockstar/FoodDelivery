import { useMemo, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import AdminShell from '../components/AdminShell'
import { useRestaurantMenu } from '../hooks/useRestaurants'
import { useAdminRestaurants } from '../hooks/useAdminData'
import './AdminPage.css'
import './AdminMenu.css'

function AdminMenu() {
    const { restaurantId } = useParams()
    const { menuItems, loading } = useRestaurantMenu(restaurantId)
    const { data: restaurants } = useAdminRestaurants()
    const restaurant = restaurants.find((item) => String(item.id) === restaurantId)
    const [query, setQuery] = useState('')
    const items = useMemo(() => menuItems.filter((item) => item.name.toLowerCase().includes(query.toLowerCase())), [menuItems, query])

    return <AdminShell eyebrow="CATALOG / MENU" title={restaurant?.name || 'Restaurant menu'} description="Keep dishes, prices, and availability up to date for this restaurant." action={<a className="admin-action" href={`http://localhost:8080/admin/menu/create/${restaurantId}`}>+ Add menu item</a>}><div className="menu-context"><Link to="/admin/restaurants">← Back to restaurants</Link><span className="status-badge">{restaurant?.active ? 'Restaurant active' : 'Live database record'}</span></div><div className="admin-stats"><div className="admin-stat"><p>Total dishes</p><strong>{menuItems.length}</strong><span>On this menu</span></div><div className="admin-stat"><p>Available</p><strong>{menuItems.filter((item) => item.available).length}</strong><span>Ready to order</span></div><div className="admin-stat"><p>Restaurant</p><strong>{loading ? '...' : 'Live'}</strong><span>Loaded from API</span></div><div className="admin-stat"><p>Avg. price</p><strong>₹{menuItems.length ? Math.round(menuItems.reduce((sum, item) => sum + Number(item.price), 0) / menuItems.length) : 0}</strong><span>Per dish</span></div></div><section className="admin-table-wrap"><div className="admin-table-toolbar"><div><h2>Menu items</h2><p className="subtle">Edit the menu that customers see.</p></div><input className="admin-search" value={query} onChange={(event) => setQuery(event.target.value)} placeholder="Search dishes" /></div>{loading ? <p className="loading-state">Loading menu items...</p> : <div className="menu-admin-grid">{items.map((item) => <article className="menu-admin-card" key={item.id}><div className="menu-admin-art">{item.name.split(' ').map((word) => word[0]).slice(0, 2).join('')}</div><div className="menu-admin-copy"><div className="menu-admin-title"><div><p className="menu-category">Menu item</p><h3>{item.name}</h3></div><strong>₹{item.price}</strong></div><p>{item.description}</p><div className="menu-admin-footer"><span className={`status-badge ${item.available ? '' : 'busy'}`}>{item.available ? 'Available' : 'Hidden'}</span><a className="table-action" href={`http://localhost:8080/admin/menu/delete/${item.id}/${restaurantId}`}>Remove ↗</a></div></div></article>)}</div>}{!loading && items.length === 0 && <p className="loading-state">No menu items match your search.</p>}</section></AdminShell>
}

export default AdminMenu
