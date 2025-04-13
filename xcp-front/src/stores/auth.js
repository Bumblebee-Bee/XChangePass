import { defineStore } from 'pinia'

export const useAuthStore = defineStore('auth', {
    state: () => ({
        isLoggedIn: false,
        isReady: false
    }),
    actions: {
        login() {
            this.isLoggedIn = true
            localStorage.setItem('isLoggedIn', 'true')
        },
        logout() {
            this.isLoggedIn = false
            localStorage.setItem('isLoggedIn', 'false')
        },
        checkAuth() {
            this.isLoggedIn = localStorage.getItem('isLoggedIn') === 'true'
            this.isReady = true
        }
    }
})
