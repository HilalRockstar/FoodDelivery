import { Link, useSearchParams } from 'react-router-dom'
import './Auth.css'

const BACKEND_ORIGIN = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080'

function Login() {
    const [searchParams] = useSearchParams()
    const hasError = searchParams.has('error')
    const loggedOut = searchParams.has('logout')

    return <main className="auth-page"><section className="auth-panel auth-art"><Link className="auth-brand" to="/">foodie</Link><div><p className="eyebrow">WELCOME BACK</p><h1>Come back to what tastes good.</h1><p>Sign in and pick up your next favourite meal.</p></div><span className="auth-mark">F</span></section><section className="auth-panel auth-form-panel"><div className="auth-form-wrap"><p className="eyebrow">YOUR ACCOUNT</p><h2>Sign in</h2><p className="auth-subtitle">Use your account email to continue.</p>{hasError && <p className="form-message form-error">Email or password was not recognised.</p>}{loggedOut && <p className="form-message">You have been signed out.</p>}<form action={`${BACKEND_ORIGIN}/login`} method="post" className="auth-form"><label htmlFor="username">Email address<input id="username" name="username" type="email" autoComplete="email" required placeholder="you@example.com" /></label><label htmlFor="password">Password<input id="password" name="password" type="password" autoComplete="current-password" required placeholder="Enter your password" /></label><button className="button button-primary full-width" type="submit">Sign in <span>→</span></button></form><p className="auth-switch">New to foodie? <Link to="/register">Create an account</Link></p></div></section></main>
}

export default Login