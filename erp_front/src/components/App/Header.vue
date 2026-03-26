<script setup>
import { useRouter } from 'vue-router';
import { onMounted, onUnmounted, ref, computed } from "vue";
import '../../assets/css/variable.css';
import api from "@/services/api.js";

const props = defineProps({
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
  },
  showDepartments: {
    type: Boolean,
    default: false
  },
});

const isConnected = ref(false);
const router = useRouter();

const dep = ref(localStorage.getItem('user_department_name') || '');
const selectedDept = ref(Number(localStorage.getItem('user_department_id')) || null);
const userRole = ref('');

const universityDepartments = ref([]);
const isDropdownOpen = ref(false);

const formatDeptName = (name) => {
  if (name.trim().indexOf(' ') !== -1) {
    return name.replace(/[^A-ZÀ-ÖØ-Þ]/g, '');
  }
  return name;
};

const fetchDepartmentsAndUser = async () => {
  try {
    const [allDeps, rightDep] = await Promise.all([
      api.get('/universityDepartment/getUniversityDepartments'),
      api.get('/auth/user/department')
    ]);

    if (allDeps.data) {
      universityDepartments.value = allDeps.data;
    }

    if (rightDep.data) {
      selectedDept.value = rightDep.data.departmentId;
      // @ts-ignore
      localStorage.setItem('user_department_id', selectedDept.value);

      const depFound = universityDepartments.value.find(d => d.universityDepartmentID === selectedDept.value);
      if (depFound){
        dep.value = formatDeptName(depFound.universityDepartmentName);
        localStorage.setItem('user_department_name', dep.value);
      }
    }
  } catch (e) {
    console.error("Erreur lors de la récupération des départements", e);
  }
};

const canChangeDepartment = computed(() => {
  return props.showDepartments && userRole.value === 'SUPER-ADMIN' && universityDepartments.value.length > 1;
});

const selectDept = async (id) => {
  try {
    selectedDept.value = id;
    isDropdownOpen.value = false;
    await api.patch(`/auth/users/department/${id}`);

    localStorage.setItem('user_department_id', id);
    const depFound = universityDepartments.value.find(d => d.universityDepartmentID === id);
    if (depFound) {
      localStorage.setItem('user_department_name', formatDeptName(depFound.universityDepartmentName));
    }

    globalThis.location.reload();
  } catch (e) {
    console.error("Erreur lors du changement de département", e);
  }
};

const toggleDropdown = () => {
  if (canChangeDepartment.value) {
    isDropdownOpen.value = !isDropdownOpen.value;
  }
};

const closeDropdown = (e) => {
  if (!e.target.closest('.header-dep-wrapper')) {
    isDropdownOpen.value = false;
  }
};

onMounted(() => {
  isConnected.value = !!localStorage.getItem('user_token');

  const role = localStorage.getItem('user_role');
  userRole.value = role ? role.toUpperCase() : 'USER';

  if(!props.loginPage) {
    fetchDepartmentsAndUser();
  }
  document.addEventListener('click', closeDropdown);
});

onUnmounted(() => {
  document.removeEventListener('click', closeDropdown);
});
</script>

<template>
  <header class="page-header">
    <div class="container-connexion">
      <img src="../../assets/uploads/Logo_unilim.png" alt="Logo Unilim">

      <p class="header-title">
        {{ title }} <br> {{ inline }}
      </p>

      <div v-if="!loginPage" class="header-dep-wrapper">
        <p
            class="header-dep"
            :class="{ 'clickable': canChangeDepartment }"
            @click="toggleDropdown"
        >
          Département {{ dep }}
          <svg v-if="canChangeDepartment" class="chevron" :class="{ 'open': isDropdownOpen }" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round">
            <path d="M6 9l6 6 6-6"/>
          </svg>
        </p>

        <transition name="dropdown">
          <div v-if="isDropdownOpen && canChangeDepartment" class="dep-dropdown">
            <div
                v-for="dept in universityDepartments"
                :key="dept.universityDepartmentID"
                class="dep-item"
                :class="{ 'active': selectedDept === dept.universityDepartmentID }"
                @click="selectDept(dept.universityDepartmentID)"
            >
              <div class="icon-wrapper sub-icon">
                <div class="dot" v-if="selectedDept === dept.universityDepartmentID"></div>
              </div>
              <span>{{ formatDeptName(dept.universityDepartmentName) }}</span>
            </div>
          </div>
        </transition>
      </div>
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
  overflow: visible;
  z-index: 100;
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

.header-dep-wrapper {
  position: absolute;
  right: 50px;
  top: 50%;
  transform: translateY(-50%);
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.header-dep {
  margin: 0;
  font-family: 'Roboto', sans-serif;
  font-weight: 700;
  font-size: 24px;
  color: rgba(255, 255, 255, 0.9);
  text-align: right;
  line-height: 1.2;
  background: rgba(0, 0, 0, 0.1);
  padding: 10px 20px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  gap: 10px;
  user-select: none;
}

.header-dep.clickable {
  cursor: pointer;
  transition: background 0.2s ease, transform 0.1s ease;
}

.header-dep.clickable:hover {
  background: rgba(0, 0, 0, 0.2);
}

.header-dep.clickable:active {
  transform: scale(0.98);
}

.chevron {
  transition: transform 0.3s ease;
  width: 20px;
  height: 20px;
}

.chevron.open {
  transform: rotate(180deg);
}

.dep-dropdown {
  position: absolute;
  top: 115%;
  right: 0;
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.2);
  min-width: 220px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  border: 1px solid #eee;
  z-index: 1000;
}

.dep-item {
  padding: 15px 20px;
  cursor: pointer;
  font-family: 'Roboto', sans-serif;
  font-size: 16px;
  color: #333;
  font-weight: 500;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
}

.dep-item:hover {
  background: #f4f7f9;
  color: #B51621;
}

.dep-item.active {
  background: #fff5f5;
  color: #B51621;
  font-weight: 700;
}

.icon-wrapper.sub-icon {
  width: 20px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
}

.dot {
  width: 8px;
  height: 8px;
  background-color: currentColor;
  border-radius: 50%;
}

.dropdown-enter-active,
.dropdown-leave-active {
  transition: opacity 0.2s ease, transform 0.2s ease;
  transform-origin: top right;
}
.dropdown-enter-from,
.dropdown-leave-to {
  opacity: 0;
  transform: scale(0.95) translateY(-10px);
}
</style>