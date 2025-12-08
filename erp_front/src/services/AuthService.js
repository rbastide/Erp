import apiClient from './api';

export default {
  register(user) {
    return apiClient.post('/auth/register', user);
  },

  async login(credentials) {
    const response = await apiClient.post('/auth/login', credentials);

    if (response.data.token) {
      localStorage.setItem('user_token', response.data.token);
    }
    return response.data;
  },

  logout() {
    localStorage.removeItem('user_token');
  }
};