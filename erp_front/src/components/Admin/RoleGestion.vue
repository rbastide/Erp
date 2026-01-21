<script setup>
import { ref, reactive, onMounted, computed, nextTick } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';

const router = useRouter();

const roles = ref([]);
const editingIndex = ref(null);
const searchQuery = ref('');

// 1. Liste des permissions disponibles
const availablePermissions = [
  { code: 'MANAGE_USERS', label: 'Gestion Utilisateurs' },
  { code: 'MANAGE_COURSES', label: 'Gestion Cours' },
  { code: 'VIEW_GRADES', label: 'Consultation Notes' },
  { code: 'EDIT_SETTINGS', label: 'Paramètres Généraux' }
];

// 2. Données fictives
const mockRoles = [
  {
    id: 1,
    name: 'SUPER_ADMIN',
    label: 'Super Administrateur',
    permissions: ['MANAGE_USERS', 'MANAGE_COURSES', 'VIEW_GRADES', 'EDIT_SETTINGS']
  },
  {
    id: 2,
    name: 'TEACHER',
    label: 'Professeur',
    permissions: ['MANAGE_COURSES', 'VIEW_GRADES']
  },
  {
    id: 3,
    name: 'STUDENT',
    label: 'Étudiant',
    permissions: ['VIEW_GRADES']
  }
];

const editedRole = reactive({
  id: null,
  name: '',
  label: '',
  permissions: []
});

const loadRoles = () => {
  setTimeout(() => {
    roles.value = JSON.parse(JSON.stringify(mockRoles));
  }, 300);
};

onMounted(loadRoles);

// LOGIQUE DE TRI ET DE RECHERCHE
const filteredRoles = computed(() => {
  let result = roles.value;

  // 1. Filtrage par recherche
  const query = searchQuery.value.toLowerCase().trim();
  if (query) {
    result = result.filter(role =>
        role.label.toLowerCase().includes(query)
    );
  }

  // 2. Tri : Nouveau rôle en haut, puis par nombre de permissions décroissant
  return result.slice().sort((a, b) => {
    // Si c'est le rôle en cours de création (id -1), il reste TOUJOURS en premier
    if (a.id === -1) return -1;
    if (b.id === -1) return 1;

    // Sinon, tri par nombre de permissions (plus grand au plus petit)
    return b.permissions.length - a.permissions.length;
  });
});

const getPermissionLabel = (code) => {
  const perm = availablePermissions.find(p => p.code === code);
  return perm ? perm.label : code;
};

const handleDelete = (id) => {
  if (confirm("Êtes-vous sûr de vouloir supprimer ce rôle ?")) {
    roles.value = roles.value.filter(r => r.id !== id);
    if (editedRole.id === id) handleCancel();
  }
};

// FONCTION DE DUPLICATION
const handleDuplicate = (sourceRole) => {
  // 1. Annuler toute édition en cours
  handleCancel();

  // 2. Créer l'objet copié
  const newTempRole = {
    id: -1, // ID temporaire de création
    name: '', // On vide le nom technique pour qu'il soit régénéré proprement
    label: `${sourceRole.label} (Copie)`, // Suffixe pour le nom
    permissions: [...sourceRole.permissions] // Copie profonde des permissions
  };

  // 3. Ajouter au tableau
  roles.value.push(newTempRole);

  // 4. Passer en mode édition immédiatement
  // On utilise nextTick pour attendre que le tri computed se fasse (le id -1 va monter en haut)
  nextTick(() => {
    const index = filteredRoles.value.findIndex(r => r.id === -1);
    if (index !== -1) {
      handleModif(newTempRole, index);
    }
  });
};

const saveModification = () => {
  if (!editedRole.label) {
    alert("Le nom du rôle est obligatoire.");
    return;
  }

  if (!editedRole.name) {
    editedRole.name = editedRole.label
        .toUpperCase()
        .normalize("NFD").replace(/[\u0300-\u036f]/g, "")
        .replace(/[^A-Z0-9]/g, '_');
  }

  if (editedRole.id) {
    const roleToUpdate = roles.value.find(r => r.id === editedRole.id);
    if (roleToUpdate) {
      if (!roleToUpdate.name) roleToUpdate.name = editedRole.name;
      roleToUpdate.label = editedRole.label;
      roleToUpdate.permissions = [...editedRole.permissions];
    }
  } else {
    const newRoleIndex = roles.value.findIndex(r => r.id === -1);
    if (newRoleIndex !== -1) {
      roles.value[newRoleIndex] = {
        ...roles.value[newRoleIndex],
        id: Math.max(...roles.value.map(r => r.id)) + 1,
        name: editedRole.name,
        label: editedRole.label,
        permissions: [...editedRole.permissions]
      };
    }
  }
  handleCancel();
};

const handleModif = (role, index) => {
  editingIndex.value = index;
  editedRole.id = role.id;
  editedRole.name = role.name;
  editedRole.label = role.label;
  editedRole.permissions = [...role.permissions];
};

const togglePermission = (permCode) => {
  const index = editedRole.permissions.indexOf(permCode);
  if (index === -1) {
    editedRole.permissions.push(permCode);
  } else {
    editedRole.permissions.splice(index, 1);
  }
};

const handleAddRole = () => {
  const newTempRole = { id: -1, name: '', label: '', permissions: [] };
  handleCancel();
  roles.value.push(newTempRole); // On push, le tri s'occupera de le mettre en haut (car id -1)

  nextTick(() => {
    const index = filteredRoles.value.findIndex(r => r.id === -1);
    if(index !== -1) handleModif(newTempRole, index);
  });
};

const handleCancel = () => {
  // Si on annule, on supprime l'élément temporaire (id -1)
  const tempIndex = roles.value.findIndex(r => r.id === -1);
  if (tempIndex !== -1) {
    roles.value.splice(tempIndex, 1);
  }

  editingIndex.value = null;
  Object.assign(editedRole, { id: null, name: '', label: '', permissions: [] });
};

const handleValider = () => router.push('/home-admin');
</script>

<template>
  <Sidebar />
  <div class="page-container">
    <AppHeader title="Gestion des Rôles" />

    <main class="main-content">
      <div v-if="roles.length === 0" class="empty-state">
        Chargement des rôles...
      </div>

      <div class="grid-container">
        <div class="user-card add-card compact-add" @click="handleAddRole" v-if="searchQuery === '' && editingIndex === null">
          <div class="icon-circle plus-circle">+</div>
          <p class="add-label">Ajouter un rôle</p>
        </div>

        <div v-for="(role, index) in filteredRoles" :key="role.id" class="user-card" :class="{ 'is-editing': editingIndex === index }">

          <div v-if="editingIndex !== index" class="card-content view-mode">
            <div class="header-view">
              <div class="icon-circle small-icon">
                <svg width="24" height="24" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" fill="none">
                  <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
                </svg>
              </div>
              <h3 class="user-id">{{ role.label }}</h3>
            </div>

            <div class="permissions-list-view">
              <span class="perm-title-view">Permissions actives ({{ role.permissions.length }}) :</span>
              <div class="tags-container">
                <span v-for="permCode in role.permissions" :key="permCode" class="perm-tag">
                  {{ getPermissionLabel(permCode) }}
                </span>
                <span v-if="role.permissions.length === 0" class="no-perm">Aucune permission</span>
              </div>
            </div>

            <div class="card-actions">
              <button class="action-btn-mini edit" @click="handleModif(role, index)" title="Modifier">
                <svg width="16" height="16" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2.5" fill="none"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path></svg>
              </button>

              <button class="action-btn-mini duplicate" @click="handleDuplicate(role)" title="Dupliquer">
                <svg width="16" height="16" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2.5" fill="none">
                  <rect x="9" y="9" width="13" height="13" rx="2" ry="2"></rect>
                  <path d="M5 15H4a2 2 0 0 1-2-2V4a2 2 0 0 1 2-2h9a2 2 0 0 1 2 2v1"></path>
                </svg>
              </button>

              <button class="action-btn-mini delete" @click="handleDelete(role.id)" title="Supprimer">
                <svg width="16" height="16" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2.5" fill="none"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
              </button>
            </div>
          </div>

          <div v-else class="card-content edit-mode">
            <div class="edit-header">
              <h4>{{ editedRole.id === -1 ? 'Création / Copie' : 'Édition' }}</h4>
              <button class="close-icon" @click="handleCancel">✕</button>
            </div>

            <div class="input-group-compact">
              <label class="input-label">Nom du Rôle</label>
              <input type="text" v-model="editedRole.label" class="card-input-compact" placeholder="ex: Administrateur">
            </div>

            <hr class="separator"/>

            <div class="permissions-edit-list">
              <label class="input-label" style="margin-bottom: 8px;">Gestion des Permissions</label>

              <div v-for="perm in availablePermissions" :key="perm.code" class="perm-toggle-row">
                <span class="perm-label">{{ perm.label }}</span>

                <div
                    class="toggle-switch"
                    :class="{ 'active': editedRole.permissions.includes(perm.code) }"
                    @click="togglePermission(perm.code)"
                >
                  <div class="toggle-knob"></div>
                </div>
              </div>
            </div>

            <button class="save-btn-compact" @click="saveModification(index)">Enregistrer</button>
          </div>
        </div>
      </div>
    </main>

    <footer class="sticky-bar">
      <div class="sticky-wrapper">
        <div class="search-container">
          <svg class="search-icon" width="20" height="20" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" fill="none"><circle cx="11" cy="11" r="8"></circle><line x1="21" y1="21" x2="16.65" y2="16.65"></line></svg>
          <input type="text" v-model="searchQuery" placeholder="Rechercher un rôle..." class="search-input" />
        </div>
        <button @click="handleValider" class="btn-sys primary">Terminer</button>
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
  padding: 20px;
  margin-top: 170px;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 20px;
  width: 100%;
  max-width: 1200px;
  margin-bottom: 50px;
}

.user-card {
  background: #ffffff;
  border-radius: 12px;
  min-height: 250px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  border: 1px solid transparent;
  position: relative;
}

.user-card.is-editing {
  border: 2px solid #B51621;
}

.card-content {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
  justify-content: space-between;
}

.header-view {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
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

.user-id { margin: 0; color: #333; font-size: 18px; font-weight: 700; margin-bottom: 10px; }

.permissions-list-view {
  width: 100%;
  margin: 5px 0 15px 0;
  text-align: left;
  flex-grow: 1;
}

.perm-title-view {
  font-size: 11px;
  color: #999;
  text-transform: uppercase;
  font-weight: bold;
  display: block;
  margin-bottom: 8px;
  text-align: center;
}

.tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  justify-content: center;
}

.perm-tag {
  background: #f1f3f5;
  color: #555;
  font-size: 11px;
  padding: 4px 8px;
  border-radius: 4px;
  font-weight: 500;
}

.no-perm { font-size: 12px; color: #bbb; font-style: italic; }

.permissions-edit-list {
  width: 100%;
  max-height: 200px;
  overflow-y: auto;
  padding-right: 5px;
  margin-bottom: 10px;
}

.perm-toggle-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
  border-bottom: 1px dashed #eee;
}

.perm-toggle-row:last-child { border-bottom: none; }
.perm-label { font-size: 13px; color: #444; }

.toggle-switch {
  width: 36px;
  height: 20px;
  background-color: #ddd;
  border-radius: 20px;
  position: relative;
  cursor: pointer;
  transition: background-color 0.3s;
}

.toggle-switch.active {
  background-color: #B51621;
}

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

.toggle-switch.active .toggle-knob {
  transform: translateX(16px);
}

.input-group-compact { width: 100%; margin-bottom: 10px; text-align: left;}
.input-label { font-size: 11px; font-weight: bold; color: #777; margin-bottom: 4px; display: block; }
.card-input-compact { width: 100%; padding: 8px 10px; border: 1px solid #ddd; border-radius: 6px; font-size: 13px; background: #fafafa; box-sizing: border-box; }
.save-btn-compact { width: 100%; padding: 10px; background: #B51621; color: white; border: none; border-radius: 6px; font-weight: bold; font-size: 14px; cursor: pointer; margin-top: 10px; }
.separator { border: 0; border-top: 1px solid #eee; margin: 10px 0; width: 100%; }

.card-actions { display: flex; gap: 10px; margin-top: auto; }
.action-btn-mini { width: 34px; height: 34px; border-radius: 50%; border: none; cursor: pointer; display: flex; align-items: center; justify-content: center; transition: 0.2s; background: #f5f5f5; color: #555; }
.action-btn-mini.edit:hover { background: #e3f2fd; color: #1976d2; }
.action-btn-mini.delete:hover { background: #ffebee; color: #c62828; }

/* STYLE POUR LE BOUTON DUPLIQUER */
.action-btn-mini.duplicate:hover { background: #e8f5e9; color: #2e7d32; }

.add-card { border: 2px dashed #ddd; cursor: pointer; background: transparent; min-height: 250px; }
.add-card:hover { transform: translateY(-5px); background: #ffffff; border-color: #B51621; box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1); }
.plus-circle { font-size: 20px; background: #eee; color: #666; }
.add-card:hover .plus-circle { background: #B51621; color: #ffffff; }
.add-card:hover .add-label { color: #B51621; }
.add-label { font-size: 14px; font-weight: bold; color: #999; margin-top: 10px; text-align: center; }

.edit-header { width: 100%; display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; }
.edit-header h4 { margin: 0; font-size: 14px; color: #B51621; }
.close-icon { background: none; border: none; font-size: 16px; cursor: pointer; color: #999; }
.empty-state { margin-bottom: 20px; color: #666; font-style: italic; }

.sticky-bar { position: fixed; bottom: 0; left: 0; width: 100%; background: white; box-shadow: 0 -5px 15px rgba(0,0,0,0.1); padding: 12px 0; z-index: 100; }
.sticky-wrapper { max-width: 1200px; margin: 0 auto; display: flex; justify-content: space-between; align-items: center; padding: 0 40px; }
.search-container { position: relative; width: 320px; }
.search-icon { position: absolute; left: 15px; top: 50%; transform: translateY(-50%); color: #999; }
.search-input { width: 100%; padding: 10px 15px 10px 40px; border-radius: 50px; border: 1px solid #ddd; outline: none; font-size: 14px; }
.btn-sys.primary { background: #B51621; color: white; padding: 12px 35px; border-radius: 50px; font-weight: bold; border: none; cursor: pointer; min-width: 160px; font-size: 15px; }
</style>