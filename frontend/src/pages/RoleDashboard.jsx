import './RoleDashboard.css'

const BACKEND_ORIGIN = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080'

const dashboards = {
    customer: { eyebrow: 'CUSTOMER SPACE', title: 'Everything delicious, in one place.', copy: 'Explore local kitchens, keep your cart close, and follow your orders from the first sizzle to your doorstep.', links: [['Browse restaurants', '/restaurants'], ['My orders', '/orders'], ['View cart', '/cart']] },
    admin: { eyebrow: 'ADMIN CONTROL ROOM', title: 'Keep every kitchen moving.', copy: 'Manage restaurants, menus, delivery partners, and the orders that connect them.', links: [['Restaurants', '/admin/restaurants'], ['Delivery partners', '/admin/delivery-partners'], ['All orders', '/admin/orders']] },
    delivery: { eyebrow: 'DELIVERY PARTNER', title: 'Your next delivery starts here.', copy: 'See assigned orders, update delivery progress, and keep customers in the loop.', links: [['Assigned orders', '/delivery/dashboard'], ['Open dashboard', '/delivery/dashboard']] },
}

function RoleDashboard({ role }) {
    const dashboard = dashboards[role]
    return <main className="role-page"><div className="role-hero"><p className="eyebrow">{dashboard.eyebrow}</p><h1>{dashboard.title}</h1><p>{dashboard.copy}</p></div><section className="role-links">{dashboard.links.map(([label, path]) => <a className="role-link" href={`${BACKEND_ORIGIN}${path}`} key={path + label}><span>{label}</span><strong>↗</strong></a>)}</section><form action={`${BACKEND_ORIGIN}/logout`} method="post"><button className="logout-button" type="submit">Sign out</button></form></main>
}

export default RoleDashboard