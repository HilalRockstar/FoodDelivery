import { Link } from 'react-router-dom'
import './Auth.css'
import heroImage from '../assets/hero.png'

const BACKEND_ORIGIN = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080'

function Register() {
    return <main className="auth-page"><section className="auth-panel auth-art auth-art-register"><Link className="auth-brand" to="/">foodie</Link><div><p className="eyebrow">MAKE IT YOURS</p><h1>Your next great meal starts here.</h1><p>Create an account to order faster and follow every delivery.</p></div><img className="auth-art-image" src={heroImage} alt="" /></section><section className="auth-panel auth-form-panel"><div className="auth-form-wrap"><p className="eyebrow">GET STARTED</p><h2>Create account</h2><p className="auth-subtitle">Join foodie in less than a minute.</p><form action={`${BACKEND_ORIGIN}/register`} method="post" className="auth-form"><label htmlFor="fullName">Full name<input id="fullName" name="fullName" type="text" autoComplete="name" required placeholder="Your name" /></label><label htmlFor="email">Email address<input id="email" name="email" type="email" autoComplete="email" required placeholder="you@example.com" /></label><label htmlFor="password">Password<input id="password" name="password" type="password" autoComplete="new-password" required placeholder="Create a password" /></label><label htmlFor="phoneNumber">Phone number<input id="phoneNumber" name="phoneNumber" type="tel" autoComplete="tel" required placeholder="Your phone number" /></label><button className="button button-primary full-width" type="submit">Create account <span>→</span></button></form><p className="auth-switch">Already have an account? <Link to="/login">Sign in</Link></p></div></section></main>
}

export default Register