import axios from 'axios';

const BACK_END_IP = import.meta.env.VITE_BACK_END_IP;
const BACK_END_PORT = import.meta.env.VITE_PORT_BACK_END;
const CAS_URL = import.meta.env.VITE_CAS_URL;

const apiClient = axios.create({
  baseURL: `https://${BACK_END_IP}:${BACK_END_PORT}/api`,
  headers: {
    'Content-Type': 'application/json'
  },
  withCredentials: true
});

export const connectWithCas = () => {
  window.location.href = `${CAS_URL}`;
}

export default apiClient;