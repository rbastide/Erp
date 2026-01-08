import apiClient from './api';
import {mcccStore} from "@/services/mcccStore.js";

export default {
  register(user) {
    return apiClient.post('/auth/register', user);
  },

  async login(credentials) {
    const response = await apiClient.post('/auth/login', credentials);

    if (response.data.token) {
      localStorage.setItem('user_token', response.data.token);
    }
    if(response.data.role){
        localStorage.setItem('user_role', response.data.role);
    }
    return response.data;
  },

  logout() {
    localStorage.removeItem('user_token');
    mcccStore.clearMcccStore();
  }
};