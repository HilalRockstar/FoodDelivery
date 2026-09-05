import { Link, NavLink } from 'react-router-dom'
import { useCart } from '../hooks/useCart'
import './Navbar.css'

function Navbar() {

    return (
        <nav className="navbar">

            <Link to="/" className="logo"><span>F</span>foodie</Link>

            <div className="nav-links">

                <NavLink to="/" end>Home</NavLink><NavLink to="/restaurants">Restaurants</NavLink><NavLink to="/orders">My orders</NavLink>
                <NavLink className="cart-link" to="/cart">Cart <span>{useCart().cart.length}</span></NavLink>

            </div>

        </nav>
    )
}

export default Navbar