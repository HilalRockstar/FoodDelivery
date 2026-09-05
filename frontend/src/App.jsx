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
import AdminRestaurants from './pages/AdminRestaurants'
import AdminDeliveryPartners from './pages/AdminDeliveryPartners'
import AdminOrders from './pages/AdminOrders'
import AdminMenu from './pages/AdminMenu'
import OrderDetails from './pages/OrderDetails'
import DeliveryDashboard from './pages/DeliveryDashboard'
import DeliveryOrderDetails from './pages/DeliveryOrderDetails'
import AdminRestaurantForm from './pages/AdminRestaurantForm'
import AdminMenuForm from './pages/AdminMenuForm'
import AdminPartnerForm from './pages/AdminPartnerForm'

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
                    <Route path="/orders/:id" element={<OrderDetails />} />

                    <Route path="/user/dashboard" element={<RoleDashboard role="customer" />} />
                    <Route path="/admin/dashboard" element={<RoleDashboard role="admin" />} />
                    <Route path="/admin/restaurants" element={<AdminRestaurants />} />
                    <Route path="/admin/restaurants/create" element={<AdminRestaurantForm />} />
                    <Route path="/admin/delivery-partners" element={<AdminDeliveryPartners />} />
                    <Route path="/admin/orders" element={<AdminOrders />} />
                    <Route path="/admin/menu/:restaurantId" element={<AdminMenu />} />
                    <Route path="/admin/menu/create/:restaurantId" element={<AdminMenuForm />} />
                    <Route path="/admin/delivery-partners/create" element={<AdminPartnerForm />} />
                    <Route path="/delivery/dashboard" element={<RoleDashboard role="delivery" />} />
                    <Route path="/delivery/orders" element={<DeliveryDashboard />} />
                    <Route path="/delivery/order/:id" element={<DeliveryOrderDetails />} />

                </Routes>

            </BrowserRouter>

        </CartProvider>
    )
}

export default App