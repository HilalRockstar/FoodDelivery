import RestaurantCard from '../components/RestaurantCard'
import { useRestaurants } from '../hooks/useRestaurants'

function Restaurants() {
    const { restaurants, loading } = useRestaurants()

    return <main className="page-shell">
        <div className="page-intro">
            <p className="eyebrow">THE FOODIE GUIDE</p>
            <h1>Restaurants worth <em>leaving home for.</em></h1>
            <p>Browse local favourites, then build a meal around what sounds good right now.</p>
        </div>
        <div className="restaurant-toolbar"><span>{loading ? 'Loading kitchens...' : `${restaurants.length} kitchens open now`}</span><span className="filter-pill">All cuisines <span>⌄</span></span></div>
        <div className="restaurant-list restaurant-list-large">
            {loading ? <p className="loading-state">Finding the best kitchens...</p> : restaurants.map((restaurant) => <RestaurantCard key={restaurant.id} restaurant={restaurant} />)}
        </div>
    </main>
}

export default Restaurants