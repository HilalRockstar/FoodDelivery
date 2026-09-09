import { BrowserRouter, Navigate, Route, Routes, useLocation } from 'react-router-dom'

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

function ProtectedRoute({ children, allowedRoles }) {
    const location = useLocation()
    const token = localStorage.getItem('jwtToken')
    const userRole = localStorage.getItem('userRole')

    if (!token) {
        return <Navigate to="/login?error=auth" replace state={{ from: location.pathname }} />
    }

    if (allowedRoles && !allowedRoles.includes(userRole)) {
        return <Navigate to="/login?error=forbidden" replace state={{ from: location.pathname }} />
    }

    return children
}

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

                    <Route
                        path="/restaurants"
                        element={
                            <ProtectedRoute allowedRoles={['USER']}>
                                <Restaurants />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/restaurants/:id"
                        element={
                            <ProtectedRoute allowedRoles={['USER']}>
                                <RestaurantMenu />
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/cart"
                        element={
                            <ProtectedRoute allowedRoles={['USER']}>
                                <Cart />
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/orders"
                        element={
                            <ProtectedRoute allowedRoles={['USER']}>
                                <MyOrders />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/orders/:id"
                        element={
                            <ProtectedRoute allowedRoles={['USER']}>
                                <OrderDetails />
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/user/dashboard"
                        element={
                            <ProtectedRoute allowedRoles={['USER']}>
                                <RoleDashboard role="customer" />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/admin/dashboard"
                        element={
                            <ProtectedRoute allowedRoles={['ADMIN']}>
                                <RoleDashboard role="admin" />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/admin/restaurants"
                        element={
                            <ProtectedRoute allowedRoles={['ADMIN']}>
                                <AdminRestaurants />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/admin/restaurants/create"
                        element={
                            <ProtectedRoute allowedRoles={['ADMIN']}>
                                <AdminRestaurantForm />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/admin/delivery-partners"
                        element={
                            <ProtectedRoute allowedRoles={['ADMIN']}>
                                <AdminDeliveryPartners />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/admin/orders"
                        element={
                            <ProtectedRoute allowedRoles={['ADMIN']}>
                                <AdminOrders />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/admin/menu/:restaurantId"
                        element={
                            <ProtectedRoute allowedRoles={['ADMIN']}>
                                <AdminMenu />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/admin/menu/create/:restaurantId"
                        element={
                            <ProtectedRoute allowedRoles={['ADMIN']}>
                                <AdminMenuForm />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/admin/delivery-partners/create"
                        element={
                            <ProtectedRoute allowedRoles={['ADMIN']}>
                                <AdminPartnerForm />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/delivery/dashboard"
                        element={
                            <ProtectedRoute allowedRoles={['DELIVERY_PARTNER']}>
                                <RoleDashboard role="delivery" />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/delivery/orders"
                        element={
                            <ProtectedRoute allowedRoles={['DELIVERY_PARTNER']}>
                                <DeliveryDashboard />
                            </ProtectedRoute>
                        }
                    />
                    <Route
                        path="/delivery/order/:id"
                        element={
                            <ProtectedRoute allowedRoles={['DELIVERY_PARTNER']}>
                                <DeliveryOrderDetails />
                            </ProtectedRoute>
                        }
                    />

                </Routes>

            </BrowserRouter>

        </CartProvider>
    )
}

export default App