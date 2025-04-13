import { authFetch } from './authFetch'

export const login = async (email, password) => {
    return await fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({ email, password })
    })
}

export const logout = async () => {
    return await fetch('http://localhost:8080/logout', {
        method: 'POST',
        credentials: 'include',
    })
}
