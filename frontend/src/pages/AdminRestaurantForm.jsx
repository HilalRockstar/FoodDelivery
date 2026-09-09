import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import './AdminForm.css'

const BACKEND_ORIGIN = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080'

function AdminRestaurantForm() {
    const navigate = useNavigate()
    const [submitting, setSubmitting] = useState(false)

    async function handleSubmit(event) {
        event.preventDefault()
        setSubmitting(true)

        try {
            const formData = new FormData(event.currentTarget)
            const token = localStorage.getItem('jwtToken')

            const response = await fetch(`${BACKEND_ORIGIN}/admin/restaurants/create`, {
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Authorization': token ? `Bearer ${token}` : '',
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
                },
                body: new URLSearchParams(formData).toString(),
            })

            if (!response.ok) {
                throw new Error('Unable to create restaurant')
            }

            navigate('/admin/restaurants')
        } catch (error) {
            alert(error.message || 'Unable to create restaurant')
        } finally {
            setSubmitting(false)
        }
    }

    return <main className="form-page"><Link className="form-breadcrumb" to="/admin/restaurants">← Back to restaurants</Link><div className="form-hero"><div><p className="eyebrow">CATALOG / NEW RESTAURANT</p><h1>Add a restaurant.</h1><p>Give customers a clear reason to choose this kitchen. You can update the menu once it is live.</p></div><span className="status-badge">Draft</span></div><form className="form-shell" onSubmit={handleSubmit}><section className="form-section"><h2>Restaurant identity</h2><p>The essential details customers see first.</p><div className="form-grid"><div className="form-field"><label htmlFor="name">Restaurant name</label><input id="name" name="name" required placeholder="e.g. Paradise Restaurant" /></div><div className="form-field"><label htmlFor="phoneNumber">Phone number</label><input id="phoneNumber" name="phoneNumber" required placeholder="e.g. +91 98765 43210" /></div><div className="form-field full"><label htmlFor="description">Description</label><textarea id="description" name="description" placeholder="What makes this kitchen special?" /></div></div></section><section className="form-section"><h2>Location & hours</h2><p>Help customers know where you are and when to order.</p><div className="form-grid"><div className="form-field full"><label htmlFor="address">Address</label><input id="address" name="address" required placeholder="Street, area, city" /></div><div className="form-field"><label htmlFor="openingTime">Opening time</label><input id="openingTime" name="openingTime" type="time" /></div><div className="form-field"><label htmlFor="closingTime">Closing time</label><input id="closingTime" name="closingTime" type="time" /></div></div></section><div className="form-footer"><Link className="form-cancel" to="/admin/restaurants">Cancel</Link><button className="button form-submit" type="submit" disabled={submitting}>{submitting ? 'Saving...' : 'Save restaurant'} <span>→</span></button></div></form></main>
}

export default AdminRestaurantForm