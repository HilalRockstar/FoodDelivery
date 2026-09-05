import { Link, useParams } from 'react-router-dom'
import { useRestaurantMenu } from '../hooks/useRestaurants'
import { useCart } from '../hooks/useCart'
import MenuItemCard from '../components/MenuItemCard'

function RestaurantMenu() {

    const { id } = useParams()
    const { menuItems, loading } = useRestaurantMenu(id)
    const { addToCart, loading: cartLoading } = useCart()

    return (
        <main className="page-shell menu-page">
            <Link className="back-link" to="/restaurants">← All restaurants</Link>
            <div className="menu-header"><div><p className="eyebrow">TODAY'S MENU</p><h1>{menuItems[0]?.restaurantName || 'Restaurant menu'}</h1><p>Made fresh, packed carefully, and on its way to you.</p></div><span className="status-dot">Open now</span></div>
            <div className="menu-grid">
            {loading ? <p className="loading-state">Loading the menu...</p> : menuItems.map((item) => (

                <MenuItemCard
                    key={item.id}
                    item={item}
                    onAddToCart={() => addToCart(item)}
                    disabled={cartLoading || !item.available}
                />

            ))}
            </div>
        </main>
    )
}

export default RestaurantMenu