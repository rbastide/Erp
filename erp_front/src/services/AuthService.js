import apiClient from './api';
import api from './api';
import {mcccStore} from "@/services/mcccStore.js";
import {reactive} from "vue";

export default {
  register(user) {
    return apiClient.post('/auth/register', user);
  },

  async login(credentials) {
    const response = await apiClient.post('/auth/login', credentials);

    if (response.data.role) {
      localStorage.setItem('user_role', response.data.role);
    }

    authStore.firstName = response.data.firstname || response.data.firstName;
    authStore.lastName = response.data.lastname || response.data.lastName;

    authStore.save();

    return response.data;
  },

    async handleCasLogin(token) {
        document.cookie = `auth_token=${token}; path=/; max-age=86400; SameSite=Lax`;

        try {
            const tokenPayload = JSON.parse(atob(token.split('.')[1]));

            const identifier = tokenPayload.sub;

            const response = await apiClient.get(`/auth/user-info-and-role/${identifier}`);

            if (response.data.role) {
                localStorage.setItem('user_role', response.data.role);
            }

            authStore.firstName = response.data.firstname;
            authStore.lastName = response.data.lastname;
            authStore.save();

        } catch (error) {
            console.error("Impossible de récupérer les infos de l'utilisateur après le CAS", error);
        }
    },



  async logout() {
    await api.post('/auth/logout');
    localStorage.removeItem('user_role');
    authStore.clear();
    mcccStore.clearMcccStore();
  },

  lastName: '',
  firstName: ''
};

export const authStore = reactive({
    firstName: '',
    lastName: '',

    save() {
        localStorage.setItem("AuthService", JSON.stringify({
            firstName: this.firstName,
            lastName: this.lastName
        }));
    },

    load() {
        const stored = localStorage.getItem("AuthService");
        if (stored) {
            try {
                const data = JSON.parse(stored);
                this.firstName = data.firstName || '';
                this.lastName = data.lastName || '';
            } catch (e) {
                console.error("Erreur chargement AuthStore", e);
            }
        }
    },

    clear() {
        this.firstName = '';
        this.lastName = '';
        cookieStore.delete('user_token').then(_ => {

        });
    }
});