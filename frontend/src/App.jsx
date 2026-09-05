import { BrowserRouter, Routes, Route } from 'react-router-dom'

import { CartProvider } from './context/CartContext'

import Navbar from './components/Navbar'
import Home from './pages/Home'
import Restaurants from './pages/Restaurants'
import RestaurantMenu from './pages/RestaurantMenu'
import Cart from './pages/Cart'
import MyOrders from './pages/MyOrders'

function App() {

    return (

        <CartProvider>

            <BrowserRouter>

                <Navbar />

                <Routes>

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

                </Routes>

            </BrowserRouter>

        </CartProvider>
    )
}

export default App