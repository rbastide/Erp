import apiClient from './api';
import { mcccStore } from "@/services/mcccStore.js";

export default {
  register(user) {
    return apiClient.post('/auth/register', user);
  },

  async login(credentials) {
    const response = await apiClient.post('/auth/login', credentials);

    if (response.data.token) {
      localStorage.setItem('user_token', response.data.token);
    }
    if (response.data.role) {
      localStorage.setItem('user_role', response.data.role);
    }
    return response.data;
  },

  logout() {
    localStorage.removeItem('user_token');
    localStorage.removeItem('user_role');
    this.clearAuthService();
    mcccStore.clearMcccStore();
  },

  lastName: '',
  firstName: '',

  registerAuthService() {
    localStorage.setItem("AuthService", JSON.stringify({
      lastName: this.lastName,
      firstName: this.firstName
    }));
  },

  loadAuthService() {
    const storedData = localStorage.getItem("AuthService");
    if (storedData) {
      try {
        const parsedData = JSON.parse(storedData);
        this.lastName = parsedData.lastName || '';
        this.firstName = parsedData.firstName || '';
      } catch (e) {
        console.error("Erreur lors du chargement de AuthService :", e);
        this.clearAuthService();
      }
    }
  },

  clearAuthService() {
    this.lastName = '';
    this.firstName = '';
    localStorage.removeItem("AuthService");
  }
};