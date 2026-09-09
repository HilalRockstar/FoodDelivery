import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import './AdminForm.css'

const BACKEND_ORIGIN = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080'

function AdminPartnerForm() {
    const navigate = useNavigate()
    const [submitting, setSubmitting] = useState(false)

    async function handleSubmit(event) {
        event.preventDefault()
        setSubmitting(true)

        try {
            const formData = new FormData(event.currentTarget)
            const token = localStorage.getItem('jwtToken')

            const response = await fetch(`${BACKEND_ORIGIN}/admin/delivery-partners/create`, {
                method: 'POST',
                credentials: 'include',
                headers: {
                    'Authorization': token ? `Bearer ${token}` : '',
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8',
                },
                body: new URLSearchParams(formData).toString(),
            })

            if (!response.ok) {
                throw new Error('Unable to create delivery partner')
            }

            navigate('/admin/delivery-partners')
        } catch (error) {
            alert(error.message || 'Unable to create delivery partner')
        } finally {
            setSubmitting(false)
        }
    }

    return <main className="form-page"><Link className="form-breadcrumb" to="/admin/delivery-partners">← Back to delivery partners</Link><div className="form-hero"><div><p className="eyebrow">OPERATIONS / NEW PARTNER</p><h1>Bring someone onboard.</h1><p>Create a secure delivery partner account and keep their vehicle details close.</p></div><span className="status-badge">New partner</span></div><form className="form-shell" onSubmit={handleSubmit}><section className="form-section"><h2>Personal details</h2><p>Used for delivery assignment and communication.</p><div className="form-grid"><div className="form-field"><label htmlFor="fullName">Full name</label><input id="fullName" name="fullName" required placeholder="e.g. Arun Kumar" /></div><div className="form-field"><label htmlFor="phoneNumber">Phone number</label><input id="phoneNumber" name="phoneNumber" required placeholder="e.g. +91 90000 12345" /></div><div className="form-field full"><label htmlFor="email">Email address</label><input id="email" name="email" type="email" required placeholder="partner@example.com" /></div></div></section><section className="form-section"><h2>Vehicle & access</h2><p>Set up the information they need to start delivering.</p><div className="form-grid"><div className="form-field"><label htmlFor="vehicleNumber">Vehicle number</label><input id="vehicleNumber" name="vehicleNumber" required placeholder="e.g. TN 65 AB 1234" /></div><div className="form-field"><label htmlFor="password">Temporary password</label><input id="password" name="password" type="password" required minLength="8" placeholder="At least 8 characters" /><span className="form-help">They can use this with their email to sign in.</span></div></div></section><div className="form-footer"><Link className="form-cancel" to="/admin/delivery-partners">Cancel</Link><button className="button form-submit" type="submit" disabled={submitting}>{submitting ? 'Creating...' : 'Create partner'} <span>→</span></button></div></form></main>
}

export default AdminPartnerForm