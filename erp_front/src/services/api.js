import axios from 'axios';

const BACK_END_IP = import.meta.env.VITE_BACK_END_IP;

const apiClient = axios.create({
    baseURL: `https://${BACK_END_IP}:443/api`,
    headers: {
        'Content-Type': 'application/json'
    }
});

apiClient.interceptors.request.use(config => {
    const token = localStorage.getItem('user_token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default apiClient;