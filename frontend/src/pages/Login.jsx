import { useState } from 'react'
import { Link, useNavigate, useSearchParams } from 'react-router-dom'
import './Auth.css'
import heroImage from '../assets/hero.png'

const BACKEND_ORIGIN = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080'

function Login() {
    const [searchParams] = useSearchParams()
    const navigate = useNavigate()
    const [formData, setFormData] = useState({ email: '', password: '' })
    const [error, setError] = useState('')
    const hasError = searchParams.has('error')
    const loggedOut = searchParams.has('logout')

    async function handleSubmit(event) {
        event.preventDefault()
        setError('')

        try {
            const response = await fetch(`${BACKEND_ORIGIN}/api/auth/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                credentials: 'include',
                body: JSON.stringify(formData),
            })

            if (!response.ok) {
                throw new Error('Invalid email or password')
            }

            const data = await response.json()
            localStorage.setItem('jwtToken', data.token)
            localStorage.setItem('userRole', data.role)

            const destination = data.role === 'ADMIN'
                ? '/admin/dashboard'
                : data.role === 'DELIVERY_PARTNER'
                    ? '/delivery/dashboard'
                    : '/user/dashboard'

            navigate(destination)
        } catch (err) {
            setError(err.message || 'Invalid email or password')
        }
    }

    return <main className="auth-page"><section className="auth-panel auth-art"><Link className="auth-brand" to="/">foodie</Link><div><p className="eyebrow">WELCOME BACK</p><h1>Come back to what tastes good.</h1><p>Sign in and pick up your next favourite meal.</p></div><img className="auth-art-image" src={heroImage} alt="" /></section><section className="auth-panel auth-form-panel"><div className="auth-form-wrap"><p className="eyebrow">YOUR ACCOUNT</p><h2>Sign in</h2><p className="auth-subtitle">Use your account email to continue.</p>{hasError && <p className="form-message form-error">Email or password was not recognised.</p>}{loggedOut && <p className="form-message">You have been signed out.</p>}{error && <p className="form-message form-error">{error}</p>}<form onSubmit={handleSubmit} className="auth-form"><label htmlFor="email">Email address<input id="email" name="email" type="email" autoComplete="email" required placeholder="you@example.com" value={formData.email} onChange={(event) => setFormData({ ...formData, email: event.target.value })} /></label><label htmlFor="password">Password<input id="password" name="password" type="password" autoComplete="current-password" required placeholder="Enter your password" value={formData.password} onChange={(event) => setFormData({ ...formData, password: event.target.value })} /></label><button className="button button-primary full-width" type="submit">Sign in <span>→</span></button></form><p className="auth-switch">New to foodie? <Link to="/register">Create an account</Link></p></div></section></main>
}

export default Login