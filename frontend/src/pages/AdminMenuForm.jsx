import { useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import './AdminForm.css'

const BACKEND_ORIGIN = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080'

function AdminMenuForm() {
    const { restaurantId } = useParams()
    const navigate = useNavigate()
    const [submitting, setSubmitting] = useState(false)

    async function handleSubmit(event) {
        event.preventDefault()
        setSubmitting(true)

        try {
            const formData = new FormData(event.currentTarget)
            const token = localStorage.getItem('jwtToken')

            const response = await fetch(`${BACKEND_ORIGIN}/admin/menu/create`, {
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Authorization': token ? `Bearer ${token}` : '',
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
                },
                body: new URLSearchParams(formData).toString(),
            })

            if (!response.ok) {
                throw new Error('Unable to create menu item')
            }

            navigate(`/admin/menu/${restaurantId}`)
        } catch (error) {
            alert(error.message || 'Unable to create menu item')
        } finally {
            setSubmitting(false)
        }
    }

    return <main className="form-page"><Link className="form-breadcrumb" to={`/admin/menu/${restaurantId}`}>← Back to menu</Link><div className="form-hero"><div><p className="eyebrow">CATALOG / MENU ITEM</p><h1>Add a menu item.</h1><p>Make the dish easy to understand, price, and order.</p></div><span className="status-badge">New item</span></div><form className="form-shell" onSubmit={handleSubmit}><input type="hidden" name="restaurantId" value={restaurantId} /><section className="form-section"><h2>Dish details</h2><p>What will appear on the restaurant menu.</p><div className="form-grid"><div className="form-field"><label htmlFor="name">Dish name</label><input id="name" name="name" required placeholder="e.g. Chicken Biryani" /></div><div className="form-field"><label htmlFor="price">Price</label><input id="price" name="price" type="number" min="0" step="0.01" required placeholder="0.00" /></div><div className="form-field full"><label htmlFor="description">Description</label><textarea id="description" name="description" placeholder="Ingredients, flavour, and what makes it special" /></div></div></section><section className="form-section"><h2>Availability</h2><p>Choose whether customers can order this item now.</p><div className="checkbox-field"><input id="available" name="available" type="checkbox" defaultChecked value="true" /><label htmlFor="available">Available to order</label></div></section><div className="form-footer"><Link className="form-cancel" to={`/admin/menu/${restaurantId}`}>Cancel</Link><button className="button form-submit" type="submit" disabled={submitting}>{submitting ? 'Saving...' : 'Save menu item'} <span>→</span></button></div></form></main>
}

export default AdminMenuForm