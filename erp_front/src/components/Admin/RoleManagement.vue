<script setup>
import { ref, reactive, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';
import api from "@/services/api.js";

const router = useRouter();

const editingIndex = ref(null);
const showAddForm = ref(false);
const searchQuery = ref('');

const roles = ref([]);
const perms = ref([]);

const editedRole = reactive({
  id: null,
  name: '',
  label: '',
  permissions: []
});

const getPermLabel = (id) => {
  const perm = perms.value.find(p => String(p.id) === String(id));
  return perm ? perm.label : id;
};

const getActivePermissions = (permsData) => {
  if (!permsData) return [];

  if (!Array.isArray(permsData) && typeof permsData === 'object') {
    return Object.entries(permsData)
        .filter(([_, value]) => value === true)
        .map(([key, _]) => key);
  }

  if (Array.isArray(permsData)) {
    return permsData.map(p => {
      if (typeof p === 'string' || typeof p === 'number') return String(p);
      if (p && typeof p === 'object') {
        if (p.value === false || p.granted === false) return null;
        return String(p.id || p.permID || p.key || p.name);
      }
      return null;
    }).filter(Boolean);
  }

  return [];
};

const hasPermission = (perm) => {
  const permId = String(perm.id);
  return editedRole.permissions.includes(permId);
};

const fetchPermissions = async () => {
  try {
    const response = await api.get('perm/perms');
    perms.value = Array.isArray(response.data) ? response.data : (response.data.content || []);
  } catch (error) {
    console.error(error);
  }
};

const fetchRoles = async () => {
  try {
    const response = await api.get('perm/perms/roles');
    roles.value = Array.isArray(response.data) ? response.data : (response.data.content || []);
  } catch (error) {
    console.error(error);
  }
};

onMounted(() => {
  fetchRoles();
  fetchPermissions();
});

const filteredRoles = computed(() => {
  let result = roles.value;

  const query = searchQuery.value.toLowerCase().trim();
  if (query) {
    result = result.filter(roleItem =>
        (roleItem.role?.name || roleItem.name || roleItem.label || '').toLowerCase().includes(query)
    );
  }

  return result.slice().sort((a, b) => {
    const aLen = getActivePermissions(a.permissions).length;
    const bLen = getActivePermissions(b.permissions).length;
    return bLen - aLen;
  });
});

const handleDelete = async (id) => {
  if (confirm("Êtes-vous sûr de vouloir supprimer ce rôle ?")) {
    try {
      await api.delete(`role/roles/${id}`);
      roles.value = roles.value.filter(r => r.id !== id);
      if (editedRole.id === id) handleCancel();
    } catch (error) {
      console.error(error);
    }
  }
};

const handleAddRole = () => {
  showAddForm.value = true;
  editingIndex.value = null;
  Object.assign(editedRole, { id: null, name: '', label: '', permissions: [] });
};

const handleDuplicate = (sourceRole) => {
  handleCancel();
  showAddForm.value = true;
  Object.assign(editedRole, {
    id: null,
    name: '',
    label: `${sourceRole.role?.name || sourceRole.name || sourceRole.label} (Copie)`,
    permissions: getActivePermissions(sourceRole.permissions)
  });
};

const handleModif = (roleItem, index) => {
  if (editingIndex.value === index) {
    handleCancel();
  } else {
    showAddForm.value = false;
    editingIndex.value = index;
    editedRole.id = roleItem.id;
    editedRole.name = roleItem.role?.name || roleItem.name || '';
    editedRole.label = roleItem.role?.name || roleItem.label || '';
    editedRole.permissions = getActivePermissions(roleItem.permissions);
  }
};

const saveModification = async () => {
  if (!editedRole.label) {
    return;
  }

  if (!editedRole.name) {
    editedRole.name = editedRole.label
        .toUpperCase()
        .normalize("NFD").replaceAll(/[\u0300-\u036f]/g, "")
        .replaceAll(/[^A-Z0-9]/g, '_');
  }

  const permsObject = {};
  perms.value.forEach(p => {
    permsObject[p.id] = editedRole.permissions.includes(String(p.id));
  });

  const payload = {
    id: editedRole.id,
    role: {
      id: editedRole.id,
      name: editedRole.name
    },
    permissions: permsObject
  };

  try {
    await api.post('perm/perms/role', [payload]);
    await fetchRoles();
    handleCancel();
  } catch (error) {
    console.error(error);
  }
};

const togglePermission = (perm) => {
  const permId = String(perm.id);
  const index = editedRole.permissions.indexOf(permId);

  if (index === -1) {
    editedRole.permissions.push(permId);
  } else {
    editedRole.permissions.splice(index, 1);
  }
};

const handleCancel = () => {
  editingIndex.value = null;
  showAddForm.value = false;
  Object.assign(editedRole, { id: null, name: '', label: '', permissions: [] });
};

const handleValidate = () => router.push('/home-admin');
</script>

<template>
  <Sidebar />
  <div class="page-container">
    <AppHeader title="Gestion des Rôles" />

    <main class="main-content">
      <div v-if="roles.length === 0 && searchQuery === '' && !showAddForm" class="empty-state">
        Chargement des rôles ou aucun rôle disponible...
      </div>

      <div class="grid-container">

        <div v-if="!showAddForm && searchQuery === ''" class="resource-card add-card" @click="handleAddRole">
          <div class="icon-circle plus">
            <svg width="40" height="40" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
          </div>
          <p>Ajouter un rôle</p>
        </div>

        <div v-if="showAddForm" class="resource-card is-editing">
          <div class="card-content edit-mode">
            <div class="edit-header">
              <h4>{{ editedRole.label.includes('(Copie)') ? 'Copie de Rôle' : 'Nouveau Rôle' }}</h4>
              <button class="close-icon" @click="handleCancel">✕</button>
            </div>

            <div class="input-group">
              <label class="input-label" for="role">Nom du Rôle</label>
              <input type="text" v-model="editedRole.label" class="card-input" placeholder="Ex: Administrateur">
            </div>

            <div class="scrollable-group">
              <label class="input-label" style="margin-bottom: 8px;" for="perm">Gestion des Permissions</label>
              <div class="permissions-edit-list">
                <div v-for="perm in perms" :key="perm.id" class="perm-toggle-row">
                  <span class="perm-label">{{ perm.label }}</span>
                  <div
                      class="toggle-switch"
                      :class="{ 'active': hasPermission(perm) }"
                      @click="togglePermission(perm)"
                  >
                    <div class="toggle-knob"></div>
                  </div>
                </div>
              </div>
            </div>

            <button class="save-btn" @click="saveModification">
              <svg width="20" height="20" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><polyline points="20 6 9 17 4 12"></polyline></svg>
              Ajouter
            </button>
          </div>
        </div>

        <div v-for="(roleItem, index) in filteredRoles" :key="roleItem.id" class="resource-card" :class="{ 'is-editing': editingIndex === index }">

          <div v-if="editingIndex !== index" class="card-content view-mode">

            <div class="header-view">
              <div class="icon-circle big-icon">
                <svg width="24" height="24" viewBox="0 0 24 24" stroke="currentColor" stroke-width="1.5" style="fill: none;" stroke-linecap="round" stroke-linejoin="round">
                  <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                </svg>
              </div>
              <h3 class="resource-id">{{ roleItem.role?.name || roleItem.name || roleItem.label }}</h3>
              <p class="resource-title">Permissions actives ({{ getActivePermissions(roleItem.permissions).length }})</p>
            </div>

            <div class="permissions-container-view">
              <div class="permissions-scroll-area">
                <div class="tags-container">
                  <span v-for="permId in getActivePermissions(roleItem.permissions)" :key="permId" class="perm-tag">
                    {{ getPermLabel(permId) }}
                  </span>
                  <span v-if="getActivePermissions(roleItem.permissions).length === 0" class="no-perm">Aucune permission activée</span>
                </div>
              </div>
            </div>

            <div class="card-actions">
              <button class="action-btn edit" @click="handleModif(roleItem, index)" title="Modifier">
                <svg width="20" height="20" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" style="fill: none;" stroke-linecap="round" stroke-linejoin="round"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path></svg>
              </button>

              <button class="action-btn duplicate" @click="handleDuplicate(roleItem)" title="Dupliquer">
                <svg width="20" height="20" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" style="fill: none;" stroke-linecap="round" stroke-linejoin="round">
                  <rect x="9" y="9" width="13" height="13" rx="2" ry="2"></rect>
                  <path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1"></path>
                </svg>
              </button>

              <button class="action-btn delete" @click="handleDelete(roleItem.id)" title="Supprimer">
                <svg width="20" height="20" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" style="fill: none;" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
              </button>
            </div>
          </div>

          <div v-else class="card-content edit-mode">

            <div class="edit-header">
              <h4>Édition</h4>
              <button class="close-icon" @click="handleCancel">✕</button>
            </div>

            <div class="input-group">
              <label class="input-label" for="role">Nom du Rôle</label>
              <input type="text" v-model="editedRole.label" class="card-input" placeholder="Ex: Administrateur">
            </div>

            <div class="scrollable-group">
              <label class="input-label" style="margin-bottom: 8px;" for="perm">Gestion des Permissions</label>

              <div class="permissions-edit-list">
                <div v-for="perm in perms" :key="perm.id" class="perm-toggle-row">
                  <span class="perm-label">{{ perm.label }}</span>

                  <div
                      class="toggle-switch"
                      :class="{ 'active': hasPermission(perm) }"
                      @click="togglePermission(perm)"
                  >
                    <div class="toggle-knob"></div>
                  </div>
                </div>
              </div>
            </div>

            <button class="save-btn" @click="saveModification">
              <svg width="20" height="20" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" style="fill: none;"><polyline points="20 6 9 17 4 12"></polyline></svg>
              Enregistrer
            </button>
          </div>

        </div>
      </div>
    </main>

    <footer class="sticky-bar">
      <div class="sticky-wrapper">
        <div class="search-container">
          <svg class="search-icon" width="20" height="20" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" style="fill: none;"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
          <input type="text" v-model="searchQuery" placeholder="Rechercher un rôle..." class="search-input" />
        </div>
        <button @click="handleValidate" class="btn-sys primary">Terminer</button>
      </div>
    </footer>
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
  padding: 40px 20px;
  margin-top: 180px;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 30px;
  width: 100%;
  max-width: 1200px;
  margin-bottom: 50px;
}

.empty-state {
  margin-bottom: 30px;
  font-size: 18px;
  color: #999;
  font-style: italic;
}

.resource-card {
  background: #ffffff;
  border-radius: 20px;
  height: 420px;
  display: flex;
  flex-direction: column;
  padding: 25px;
  box-sizing: border-box;
  box-shadow: 0 10px 25px rgba(0,0,0,0.05);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
  border: 2px solid transparent;
}

.resource-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 15px 35px rgba(0,0,0,0.1);
  border-color: rgba(181, 22, 33, 0.1);
}

.resource-card.is-editing {
  border: 2px solid #B51621;
  transform: none;
  box-shadow: none;
}

.card-content {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.header-view, .edit-header, .input-group, .card-actions, .save-btn {
  flex-shrink: 0;
}

.header-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin-bottom: 10px;
}

.icon-circle {
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  transition: all 0.3s ease;
}

.icon-circle.big-icon {
  width: 90px;
  height: 90px;
  background: rgba(243, 221, 221, 0.64);
  color: #B51621;
  margin-bottom: 15px;
}

.icon-circle.big-icon svg {
  width: 45px;
  height: 45px;
}

.resource-id {
  margin: 0 0 5px 0;
  color: #2c3e50;
  font-size: 24px;
  font-weight: 900;
  letter-spacing: -0.5px;
  text-align: center;
}

.resource-title {
  font-size: 14px;
  color: #B51621;
  margin: 0;
  font-weight: 600;
  text-align: center;
}

.permissions-container-view {
  display: flex;
  flex-direction: column;
  flex: 1;
  min-height: 0;
  margin-bottom: 15px;
  width: 100%;
}

.permissions-container-view .permissions-scroll-area {
  flex: 1;
  overflow-y: auto;
  padding-right: 5px;
}

.permissions-container-view .permissions-scroll-area::-webkit-scrollbar { width: 4px; }
.permissions-container-view .permissions-scroll-area::-webkit-scrollbar-thumb { background: #ddd; border-radius: 4px; }

.tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  justify-content: center;
}

.perm-tag {
  background: #e4e4e4;
  color: #444;
  font-size: 12px;
  padding: 6px 12px;
  border-radius: 50px;
  font-weight: 500;
  border: 1px solid #e2e8f0;
  box-shadow: 0 2px 4px rgba(0,0,0,0.02);
}

.no-perm { font-size: 13px; color: #bbb; font-style: italic; text-align: center; width: 100%;}

.card-actions {
  display: flex;
  gap: 15px;
  margin-top: auto;
  justify-content: center;
}

.action-btn {
  width: 45px;
  height: 45px;
  border-radius: 50%;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
  background: #f0f2f5;
  color: #555;
}

.action-btn.edit:hover { background: #e3f2fd; color: #1551ae; transform: scale(1.1); }
.action-btn.delete:hover { background: #ffebee; color: #c62828; transform: scale(1.1); }
.action-btn.duplicate:hover { background: #e8f5e9; color: #2e7d32; transform: scale(1.1); }

.edit-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  border-bottom: 1px solid #eee;
  padding-bottom: 10px;
}

.edit-header h4 { margin: 0; color: #B51621; font-size: 18px; }

.close-icon {
  background: none;
  border: none;
  font-size: 22px;
  cursor: pointer;
  color: #999;
  transition: color 0.2s;
}
.close-icon:hover { color: #333; }

.input-group { width: 100%; text-align: left; margin-bottom: 15px; }
.input-label { display: block; font-size: 12px; color: #888; font-weight: bold; margin-bottom: 5px; margin-left: 5px; }

.card-input {
  width: 100%;
  padding: 10px 15px;
  border: 2px solid #eee;
  border-radius: 8px;
  font-size: 14px;
  box-sizing: border-box;
  font-family: 'Roboto', sans-serif;
  transition: border-color 0.3s;
}
.card-input:focus { outline: none; border-color: #B51621; }

.save-btn {
  margin-top: auto;
  width: 100%;
  padding: 12px;
  background: #B51621;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  font-weight: bold;
  font-size: 15px;
  transition: background 0.3s, transform 0.2s;
}
.save-btn:hover { background: #94121b; transform: translateY(-2px); }

.scrollable-group {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  min-height: 0;
  margin-bottom: 15px;
  width: 100%;
}

.permissions-edit-list {
  flex-grow: 1;
  overflow-y: auto;
  background: #fafafa;
  border-radius: 8px;
  border: 1px solid #eee;
  padding: 5px 12px;
  box-sizing: border-box;
}

.permissions-edit-list::-webkit-scrollbar { width: 4px; }
.permissions-edit-list::-webkit-scrollbar-thumb { background: #ddd; border-radius: 4px; }

.perm-toggle-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px dashed #e0e0e0;
}

.perm-toggle-row:last-child { border-bottom: none; }
.perm-label { font-size: 13px; color: #444; font-weight: 500;}

.toggle-switch {
  width: 38px;
  height: 20px;
  background-color: #ddd;
  border-radius: 20px;
  position: relative;
  cursor: pointer;
  transition: background-color 0.3s;
  flex-shrink: 0;
}

.toggle-switch.active { background-color: #B51621; }

.toggle-knob {
  width: 16px;
  height: 16px;
  background-color: white;
  border-radius: 50%;
  position: absolute;
  top: 2px;
  left: 2px;
  transition: transform 0.3s;
  box-shadow: 0 1px 3px rgba(0,0,0,0.2);
}

.toggle-switch.active .toggle-knob { transform: translateX(18px); }

.add-card {
  border: 3px dashed #e0e0e0;
  cursor: pointer;
  background: transparent;
  box-shadow: none;
  justify-content: center;
  align-items: center;
}

.add-card:hover {
  background: rgba(181, 22, 33, 0.02);
  border-color: #B51621;
  transform: translateY(-5px);
}

.add-card p {
  font-weight: 700;
  color: #888;
  font-size: 20px;
  transition: color 0.3s;
  margin: 0;
}
.add-card:hover p { color: #B51621; }

.icon-circle.plus {
  width: 80px;
  height: 80px;
  color: #515151;
  background: #e0e0e0;
  margin-bottom: 15px;
}
.add-card:hover .icon-circle.plus {
  background: #B51621;
  color: white;
  transform: scale(1.1);
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
.search-input { width: 100%; padding: 10px 15px 10px 40px; border-radius: 50px; border: 1px solid #ddd; outline: none; font-size: 14px; }

.btn-sys.primary {
  background: linear-gradient(135deg, #B51621 0%, #d92533 100%);
  color: white;
  padding: 12px 35px;
  border-radius: 50px;
  font-weight: bold;
  border: none;
  cursor: pointer;
  min-width: 160px;
  font-size: 15px;
}
.btn-sys.primary:hover {
  background: linear-gradient(135deg, #94121b 0%, #B51621 100%);
  transform: translateY(-2px);
  box-shadow: 0 4px 10px rgba(181, 22, 33, 0.3);
}

@media (max-width: 600px) {
  .grid-container { grid-template-columns: 1fr; }
  .sticky-wrapper { flex-direction: column; gap: 10px; }
  .search-container { width: 100%; }
  .btn-sys.primary { width: 100%; }
}
</style>