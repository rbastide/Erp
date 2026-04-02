<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from '@/services/api';
import DeleteUserModal from '../Information/DeleteUserModal.vue';

const router = useRouter();

const users = ref([]);
const editingIndex = ref(null);
const searchQuery = ref('');
const roles = ref([]);

const showDeleteModal = ref(false);
const userToDelete = ref(null);

const editedUser = reactive({
  id: null,
  identifier: '',
  role: ''
});

const fetchRoles = async () => {
  try {
    const response = await api.get('/role/all');
    roles.value = Array.isArray(response.data) ? response.data : (response.data.content || []);
  } catch (error) {
    console.error(error);
  }
};

const formatRole = (role) => {
  const rolesMap = {
    'SUPER_ADMIN': 'Super Administrateur',
    'ADMIN': 'Administrateur',
    'TEACHER': 'Professeur',
    'TEMP_TEACHER': 'Vacataire'
  };
  return rolesMap[role] || role;
};

const fetchUsers = async () => {
  try {
    const response = await api.get('/auth/users');
    let rawData = [];
    if (Array.isArray(response.data)) {
      rawData = response.data;
    } else if (response.data && Array.isArray(response.data.content)) {
      rawData = response.data.content;
    }
    users.value = rawData.filter(user => {
      if (!user.role) return true;
      const roleNormalized = user.role.toUpperCase().replace('-', '_');
      return roleNormalized !== "SUPER_ADMIN";
    });
    console.log("Utilisateurs chargés :", users.value);
  } catch (error) {
    console.error("Erreur chargement utilisateurs :", error);
  }
};

onMounted(async () => {
  await fetchUsers();
  await fetchRoles();
});

const filteredUsers = computed(() => {
  const query = searchQuery.value.toLowerCase().trim();
  if (!query) return users.value;
  return users.value.filter(user =>
      user.identifier.toLowerCase().includes(query) ||
      formatRole(user.role).toLowerCase().includes(query)
  );
});

const handleDelete = (id, identifier) => {
  userToDelete.value = { id, identifier };
  showDeleteModal.value = true;
};

const confirmDeletion = async () => {
  if (!userToDelete.value) return;
  const id = userToDelete.value.id;

  try {
    await api.delete(`/auth/users/${id}`);
    users.value = users.value.filter(u => u.id !== id);
    if (editedUser.id === id) handleCancel();
    showDeleteModal.value = false;
  } catch (error) {
    alert("Erreur lors de la suppression.");
  }
};

const saveModification = async () => {
  try {
    const payload = {
      id: editedUser.id,
      identifier: editedUser.identifier,
      role: editedUser.role
    };
    await api.post('/auth/user', payload);

    const userToUpdate = users.value.find(u => u.id === editedUser.id);
    if (userToUpdate) {
      userToUpdate.identifier = editedUser.identifier;
      userToUpdate.role = editedUser.role;
    }

    handleCancel();
  } catch (error) {
    alert("Erreur lors de la mise à jour.");
  }
};

const handleModif = (user, index) => {
  editingIndex.value = index;
  editedUser.id = user.id;
  editedUser.identifier = user.identifier;
  editedUser.role = user.role;
};

const handleCancel = () => {
  editingIndex.value = null;
  Object.assign(editedUser, {
    id: null,
    identifier: '',
    role: ''
  });
};

const handleValidate = () => router.push('/home');
const handleAddUser = () => router.push('/new-user');
</script>

<template>
  <Sidebar />
  <div class="page-container">
    <AppHeader title="Gestion des utilisateurs" />

    <main class="main-content">
      <div v-if="users.length === 0" class="empty-state">
        Chargement des utilisateurs en cours...
      </div>

      <div class="grid-container">
        <div class="user-card add-card compact-add" @click="handleAddUser" v-if="searchQuery === ''">
          <div class="icon-circle plus-circle">+</div>
          <p class="add-label">Ajouter un utilisateur</p>
        </div>

        <div v-for="(user, index) in filteredUsers" :key="user.id" class="user-card" :class="{ 'is-editing': editingIndex === index }">
          <div v-if="editingIndex !== index" class="card-content view-mode">
            <div class="icon-circle small-icon">
              <svg width="24" height="24" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" style="fill: none;">
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path>
                <circle cx="12" cy="7" r="4"></circle>
              </svg>
            </div>

            <h3 class="user-id">{{ user.identifier }}</h3>
            <span class="role-badge">{{ formatRole(user.role) }}</span>

            <div class="card-actions">
              <button class="action-btn-mini edit" @click="handleModif(user, index)">
                <svg width="16" height="16" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2.5" style="fill: none;">
                  <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
                  <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
                </svg>
              </button>
              <button class="action-btn-mini delete" @click="handleDelete(user.id, user.identifier)">
                <svg width="16" height="16" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2.5" style="fill: none;">
                  <polyline points="3 6 5 6 21 6"></polyline>
                  <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
                </svg>
              </button>
            </div>
          </div>

          <div v-else class="card-content edit-mode">
            <div class="edit-header">
              <h4>Édition</h4>
              <button class="close-icon" @click="handleCancel">✕</button>
            </div>

            <div class="input-group-compact">
              <input type="text" v-model="editedUser.identifier" class="card-input-compact" placeholder="Identifiant">
            </div>

            <div class="input-group-compact">
              <select v-model="editedUser.role" class="card-input-compact select-input">
                <option value="" disabled>-- Choisir un rôle --</option>
                <option v-for="roleItem in roles" :key="roleItem.id" :value="roleItem.name">
                  {{ roleItem.name }}
                </option>
              </select>
            </div>

            <button class="save-btn-compact" @click="saveModification()">Enregistrer</button>
          </div>
        </div>
      </div>
    </main>

    <footer class="sticky-bar">
      <div class="sticky-wrapper">
        <div class="search-container">
          <svg class="search-icon" width="20" height="20" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" style="fill: none;">
            <circle cx="11" cy="11" r="8"></circle>
            <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
          </svg>
          <input type="text" v-model="searchQuery" placeholder="Rechercher par identifiant ou rôle..." class="search-input" />
        </div>
        <button @click="handleValidate" class="btn-sys primary">Terminer</button>
      </div>
    </footer>

    <DeleteUserModal
        v-if="showDeleteModal"
        :identifier="userToDelete?.identifier"
        @confirm="confirmDeletion"
        @close="showDeleteModal = false"
    />
  </div>
</template>

<style scoped>
.page-container {
  min-height: 100vh;
  background-color: #f8f9fa;
  font-family: 'Roboto', sans-serif;
  padding-bottom: 100px;
}
.main-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  margin-top: 170px;
}
.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 20px;
  width: 100%;
  max-width: 1200px;
  margin-bottom: 50px;
}
.user-card {
  background: #ffffff;
  border-radius: 12px;
  min-height: 220px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  border: 1px solid transparent;
  position: relative;
  box-sizing: border-box;
}
.user-card.is-editing {
  border: 2px solid #B51621;
  min-height: 280px;
}
.card-content {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  height: 100%;
  justify-content: space-between;
  box-sizing: border-box;
}
.icon-circle {
  width: 50px;
  height: 50px;
  background: rgba(181, 22, 33, 0.05);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 10px;
  color: #B51621;
}
.user-id {
  margin: 0;
  color: #333;
  font-size: 18px;
  font-weight: 700;
}
.role-badge {
  background: #f0f0f0;
  padding: 4px 12px;
  border-radius: 15px;
  font-size: 11px;
  color: #666;
  font-weight: bold;
  margin-top: 5px;
}
.card-actions {
  display: flex;
  gap: 10px;
  margin-top: 15px;
}
.action-btn-mini {
  width: 34px;
  height: 34px;
  border-radius: 50%;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #f5f5f5;
  color: #555;
  transition: 0.2s;
}
.action-btn-mini.edit:hover { background: #e3f2fd; color: #1976d2; }
.action-btn-mini.delete:hover { background: #ffebee; color: #c62828; }
.add-card {
  border: 2px dashed #ddd;
  cursor: pointer;
  background: transparent;
}
.add-card:hover {
  background: #ffffff;
  border-color: #B51621;
}
.plus-circle {
  font-size: 20px;
  background: #eee;
  color: #666;
}
.add-card:hover .plus-circle {
  background: #B51621;
  color: #ffffff;
}
.add-label {
  font-size: 14px;
  font-weight: bold;
  color: #999;
  margin-top: 10px;
}
.edit-header {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}
.edit-header h4 { margin: 0; font-size: 14px; color: #B51621; }

.input-group-compact {
  width: 100%;
  margin-bottom: 8px;
  box-sizing: border-box;
}

.card-input-compact {
  width: 100%;
  padding: 8px 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 13px;
  background: #fafafa;
  box-sizing: border-box; /* CORRECTION ICI */
}

.save-btn-compact {
  width: 100%;
  padding: 10px;
  background: #B51621;
  color: white;
  border: none;
  border-radius: 6px;
  font-weight: bold;
  cursor: pointer;
  margin-top: 5px;
  box-sizing: border-box; /* CORRECTION ICI */
}

.sticky-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  background: white;
  box-shadow: 0 -5px 15px rgba(0,0,0,0.1);
  padding: 12px 0;
  z-index: 100;
}
.sticky-wrapper {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 40px;
}
.search-container { position: relative; width: 320px; }
.search-icon { position: absolute; left: 15px; top: 50%; transform: translateY(-50%); color: #999; }
.search-input { width: 100%; padding: 10px 15px 10px 40px; border-radius: 50px; border: 1px solid #ddd; outline: none; font-size: 14px; box-sizing: border-box; }
.btn-sys.primary {
  background: #B51621;
  color: white;
  padding: 12px 35px;
  border-radius: 50px;
  font-weight: bold;
  border: none;
  cursor: pointer;
  min-width: 160px;
}
.close-icon { background: none; border: none; font-size: 16px; cursor: pointer; color: #999; }
</style>