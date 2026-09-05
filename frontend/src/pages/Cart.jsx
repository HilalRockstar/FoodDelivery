import { Link, useNavigate } from 'react-router-dom'
import { useCart } from '../hooks/useCart'
import { orderApi } from '../services/api'

function Cart() {
    const { cart, changeQuantity, removeFromCart, clearCart, loading } = useCart()
    const navigate = useNavigate()
    const total = cart.reduce((sum, item) => sum + Number(item.totalPrice ?? item.price * item.quantity), 0)

    async function placeOrder() {
        await orderApi.place()
        await clearCart()
        navigate('/orders')
    }

    return <main className="page-shell cart-page">
        <div className="page-intro compact"><p className="eyebrow">YOUR ORDER</p><h1>Ready when you are.</h1><p>Review your picks before they leave the kitchen.</p></div>
        {cart.length === 0 ? <section className="empty-state"><span className="empty-icon">+</span><h2>Your cart is waiting</h2><p>Add something delicious from one of our kitchens.</p><Link className="button button-primary" to="/restaurants">Browse restaurants</Link></section> : <div className="cart-layout"><section className="cart-items">{cart.map((item) => <article className="cart-row" key={item.cartItemId ?? item.id}><div className="food-thumb">{item.name?.charAt(0)}</div><div className="cart-item-copy"><h3>{item.name ?? item.menuItemName}</h3><p>₹{Number(item.price).toFixed(2)} each</p></div><div className="quantity-control"><button onClick={() => changeQuantity(item, 'decrease')} disabled={loading}>−</button><span>{item.quantity}</span><button onClick={() => changeQuantity(item, 'increase')} disabled={loading}>+</button></div><strong>₹{Number(item.totalPrice ?? item.price * item.quantity).toFixed(2)}</strong><button className="remove-button" onClick={() => removeFromCart(item)} aria-label={`Remove ${item.name}`}>×</button></article>)}<button className="clear-button" onClick={clearCart}>Clear cart</button></section><aside className="summary-panel"><p className="eyebrow">SUMMARY</p><div className="summary-line"><span>Subtotal</span><strong>₹{total.toFixed(2)}</strong></div><div className="summary-line muted"><span>Delivery</span><span>Calculated at checkout</span></div><div className="summary-total"><span>Total</span><strong>₹{total.toFixed(2)}</strong></div><button className="button button-primary full-width" onClick={placeOrder} disabled={loading}>Place order <span>→</span></button></aside></div>}
    </main>
}

export default Cart