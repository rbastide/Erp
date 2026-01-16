<script setup>
import { useRouter } from 'vue-router';
import { onMounted, ref } from "vue";
import '../../assets/css/variable.css';
import api from "@/services/api.js";

defineProps({
  title: {
    type: String,
    default: ''
  },
  inline: {
    type: String,
    default: ''
  },
  loginPage:{
    type: Boolean,
    default: false
  }
});

const isConnected = ref(false);
const router = useRouter();
const dep = ref('');

const formatDeptName = (name) => {
  if (name.trim().indexOf(' ') !== -1) {
    return name.replace(/[^A-ZÀ-ÖØ-Þ]/g, '');
  }
  return name;
};

const fetchUserDepartment = async () => {
  try {
    const allDeps = await api.get('/universityDepartment/getUniversityDepartments');
    const rightDepId = await api.get('/auth/user/department');

    if (allDeps.data && rightDepId.data) {
      const depFound = allDeps.data.find((d) =>
          d.universityDepartmentID === rightDepId.data.departmentId
      );

      if (depFound){
        dep.value = formatDeptName(depFound.universityDepartmentName);
      }
    }
  } catch (e) {
    console.error("Erreur récupération département", e);
  }
}

onMounted(() => {
  isConnected.value = !!localStorage.getItem('user_token');
  fetchUserDepartment();
})
</script>

<template>
  <header class="page-header">
    <div class="container-connexion">
      <img src="../../assets/uploads/Logo_unilim.png" alt="Logo Unilim">

      <p class="header-title">
        {{ title }} <br> {{ inline }}
      </p>

      <p v-if="!loginPage" class="header-dep">
        {{ dep }}
      </p>
    </div>
  </header>
</template>

<style scoped>
.page-header {
  position: absolute;
  width: 100%;
  height: 172px;
  left: 0;
  top: 0;
  background: var(--gradient-red-orange, #B51621);
  box-sizing: border-box;
  overflow: hidden;
}

.container-connexion img {
  position: absolute;
  width: 127px;
  height: 127px;
  left: 364px;
  top: 1.40625rem;
}

.header-title {
  position: absolute;
  width: 723px;
  height: 124px;
  left: 509px;
  top: 24px;
  margin: 0;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 900;
  font-size: 56px;
  line-height: 110%;
  display: flex;
  align-items: center;
  letter-spacing: -0.03em;
  color: #FFFFFF;
}

.header-dep {
  position: absolute;
  right: 50px;
  top: 50%;
  transform: translateY(-50%);
  margin: 0;
  font-family: 'Roboto', sans-serif;
  font-weight: 700;
  font-size: 24px;
  color: rgba(255, 255, 255, 0.9);
  text-align: right;
  max-width: 300px;
  line-height: 1.2;
  background: rgba(0, 0, 0, 0.1);
  padding: 10px 20px;
  border-radius: 12px;
}
</style>