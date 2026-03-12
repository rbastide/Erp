import axios from 'axios';

const BACK_END_IP = import.meta.env.VITE_BACK_END_IP;
const BACK_END_PORT = import.meta.env.VITE_PORT_BACK_END;

const apiClient = axios.create({
  baseURL: `https://${BACK_END_IP}:${BACK_END_PORT}/api`,
  headers: {
    'Content-Type': 'application/json'
  },
  withCredentials: true
});

export default apiClient;