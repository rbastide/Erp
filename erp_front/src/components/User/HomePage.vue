<script setup>
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import {onMounted, ref} from "vue";
import {authStore} from "@/services/AuthService.js";
import api from "@/services/api.js";

const router = useRouter();
const menus = ref([]);
const isSuperAdmin = ref(false);

const icons = {
  "1": `<path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"></path>
        <circle cx="9" cy="7" r="4"></circle>
        <path d="M23 21v-2a4 4 0 0 0-3-3.87"></path>
        <path d="M16 3.13a4 4 0 0 1 0 7.75"></path>`,

  "2": `<path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
        <polyline points="14 2 14 8 20 8"></polyline>
        <line x1="12" y1="18" x2="12" y2="12"></line>
        <line x1="9" y1="15" x2="15" y2="15"></line>`,

  "3": `<rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
        <line x1="9" y1="3" x2="9" y2="21"></line>`,

  "4": `<rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
        <line x1="9" y1="3" x2="9" y2="21"></line>`,

  "5": `<path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"></path>
        <polyline points="14 2 14 8 20 8"></polyline>
        <line x1="16" y1="13" x2="8" y2="13"></line>
        <line x1="16" y1="17" x2="8" y2="17"></line>
        <polyline points="10 9 9 9 8 9"></polyline>`,

  "6": `<rect x="3" y="3" width="18" height="18" rx="2" ry="2"></rect>
        <line x1="9" y1="3" x2="9" y2="21"></line>`,

  "7": `<path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
        <polyline points="22 4 12 14.01 9 11.01"></polyline>`,

  "8": `<path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"></path>
        <polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline>
        <line x1="12" y1="22.08" x2="12" y2="12"></line>`,

  "9": `<polygon points="12 2 2 7 12 12 22 7 12 2"></polygon>
        <polyline points="2 17 12 22 22 17"></polyline>
        <polyline points="2 12 12 17 22 12"></polyline>`,

  "10": `<path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>`,

  "11": `<path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
         <polyline points="22,6 12,13 2,6"></polyline>`,
};

const fetchAvaliableMenu = async () => {
  const response = await api.get('/menus');
  menus.value = response.data;
  isSuperAdmin.value = menus.value.some(menu => menu.id === 1);
  console.log(menus.value);
};

onMounted(() => {
  fetchAvaliableMenu();
  authStore.load();
});
</script>

<template>
  <Sidebar :dashboardActive="true" :showDepartments="isSuperAdmin" />
  <div class="page-container">
    <AppHeader title="Bonjour," :inline="authStore.lastName + ' ' + authStore.firstName" />

    <main class="main-content">
      <div class="card-action" v-for="menu in menus" @click="router.push(menu.route);" >
        <div class="icon-circle">
<<<<<<< HEAD
          <svg
              width="40" height="40" viewBox="0 0 24 24"
              stroke="currentColor" stroke-width="2" fill="none"
              v-html="icons[menu.iconId]"
          ></svg>
        </div>
        <p>{{menu.label}}</p>
      </div>
    </main>
  </div>
</template>

<style scoped>
.page-container {
  min-height: 100vh;
  background-color: #f8f9fa;
  font-family: 'Roboto', sans-serif;
}

.main-content {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  gap: 40px;
  padding: 40px 20px;
  margin-top: 175px;
}

.card-action {
  cursor: pointer;
  background: #ffffff;
  width: 280px;
  height: 180px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 30px;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  box-shadow: 0 10px 20px rgba(0,0,0,0.05), 0 6px 6px rgba(0,0,0,0.1);
  border: 1px solid rgba(233, 37, 51, 0.1);
}

.card-action:hover {
  transform: translateY(-10px);
  box-shadow: 0 15px 30px rgba(233, 37, 51, 0.15);
  background-color: #ffffff;
  border-color: #E92533;
}

.icon-circle {
  width: 90px;
  height: 90px;
  background: rgba(233, 37, 51, 0.05);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 25px;
  color: #E92533;
  transition: all 0.3s ease;
}

.card-action:hover .icon-circle {
  background: #E92533;
  color: #ffffff;
  transform: scale(1.1);
}

.card-action p {
  margin: 0;
  font-size: 28px;
  font-weight: 500;
  line-height: 1.3;
  color: #333333;
  transition: color 0.3s ease;
}

.card-action:hover p {
  color: #E92533;
}

@media (max-width: 768px) {
  .main-content {
    margin-top: 140px;
    gap: 20px;
  }
  .card-action {
    width: 300px;
    height: 220px;
  }
  .card-action p {
    font-size: 20px;
  }
}
</style>