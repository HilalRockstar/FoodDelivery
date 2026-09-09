import { Link, NavLink, useNavigate } from 'react-router-dom'
import { useCart } from '../hooks/useCart'
import './Navbar.css'

const BACKEND_ORIGIN = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080'

function Navbar() {
    const token = localStorage.getItem('jwtToken')
    const userRole = localStorage.getItem('userRole')
    const navigate = useNavigate()

    async function handleSignOut() {
        try {
            await fetch(`${BACKEND_ORIGIN}/logout`, {
                method: 'POST',
                credentials: 'include',
            })
        } catch (error) {
            // ignore backend logout errors and continue with local cleanup
        }

        localStorage.removeItem('jwtToken')
        localStorage.removeItem('userRole')
        navigate('/login?logout')
    }

    return (
        <nav className="navbar">

            <Link to="/" className="logo"><span>F</span>foodie</Link>

            <div className="nav-links">

                <NavLink to="/" end>Home</NavLink><NavLink to="/restaurants">Restaurants</NavLink><NavLink to="/orders">My orders</NavLink>
                <NavLink className="cart-link" to="/cart">Cart <span>{useCart().cart.length}</span></NavLink>
                {token ? (
                    <>
                        <span className="login-link">{userRole || 'USER'}</span>
                        <button className="login-link logout-button" type="button" onClick={handleSignOut}>Logout</button>
                    </>
                ) : (
                    <NavLink className="login-link" to="/login">Sign in</NavLink>
                )}

            </div>

        </nav>
    )
}

export default Navbar