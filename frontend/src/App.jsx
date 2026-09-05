import { BrowserRouter, Routes, Route } from 'react-router-dom'

import { CartProvider } from './context/CartContext'

import Navbar from './components/Navbar'
import Home from './pages/Home'
import Restaurants from './pages/Restaurants'
import RestaurantMenu from './pages/RestaurantMenu'
import Cart from './pages/Cart'
import MyOrders from './pages/MyOrders'
import Login from './pages/Login'
import Register from './pages/Register'
import RoleDashboard from './pages/RoleDashboard'

function App() {

    return (

        <CartProvider>

            <BrowserRouter>

                <Navbar />

                <Routes>

                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />

                    <Route
                        path="/"
                        element={<Home />}
                    />

                    <Route path="/restaurants" element={<Restaurants />} />
                    <Route path="/restaurants/:id" element={<RestaurantMenu />} />

                    <Route
                        path="/cart"
                        element={<Cart />}
                    />

                    <Route path="/orders" element={<MyOrders />} />

                    <Route path="/user/dashboard" element={<RoleDashboard role="customer" />} />
                    <Route path="/admin/dashboard" element={<RoleDashboard role="admin" />} />
                    <Route path="/delivery/dashboard" element={<RoleDashboard role="delivery" />} />

                </Routes>

            </BrowserRouter>

        </CartProvider>
    )
}

export default App