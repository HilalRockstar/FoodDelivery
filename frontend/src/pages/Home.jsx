import './Home.css'
import RestaurantCard from '../components/RestaurantCard'
import { Link } from 'react-router-dom'
import { useRestaurants } from '../hooks/useRestaurants'

function Home() {

    const { restaurants, loading } = useRestaurants()

    return (
        <main>

            <section className="hero-section">

                <p className="eyebrow">YOUR TABLE, WHEREVER YOU ARE</p>
                <h1>Good food has a <em>favourite</em> address.</h1>

                <p>
                    Order your favourite food from your
                    favourite restaurants.
                </p>

                <Link className="button button-primary" to="/restaurants">Explore restaurants <span>↗</span></Link>

            </section>


            <section className="restaurants-section">

                <div className="section-heading">
                    <div><p className="eyebrow">CURATED NEAR YOU</p><h2>Find your next favourite</h2></div>
                    <Link to="/restaurants" className="text-link">View all <span>→</span></Link>
                </div>

                <div className="restaurant-list">

                    {loading ? <p className="loading-state">Finding the best kitchens...</p> : restaurants.slice(0, 3).map((restaurant) => (

                        <RestaurantCard
                            key={restaurant.id}
                            restaurant={restaurant}
                        />

                    ))}

                </div>

            </section>

        </main>
    )
}

export default Home