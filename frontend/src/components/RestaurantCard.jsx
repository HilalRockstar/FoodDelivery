import { Link } from 'react-router-dom'
import './RestaurantCard.css'

function RestaurantCard({ restaurant }) {

    return (
        <article className="restaurant-card"><div className="restaurant-art"><span>{restaurant.name?.charAt(0)}</span><small>OPEN</small></div><div className="restaurant-card-body"><div className="card-kicker"><span className="status-dot">Open</span><span>25–35 min</span></div><h3>{restaurant.name}</h3><p>{restaurant.description}</p><div className="card-footer"><span>⌖ {restaurant.address}</span><Link to={`/restaurants/${restaurant.id}`}>View menu <span>↗</span></Link></div></div></article>
    )
}

export default RestaurantCard