<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import AppHeader from '../App/Header.vue';
import Sidebar from '../App/Sidebar.vue';

const router = useRouter();

const users = reactive([
  { id: 'Reia', role: 'Professeur', mdp: 'secret' },
  { id: 'admin', role: 'Admin', mdp: 'secret' }
]);

const editingIndex = ref(null);

const editedUser = reactive({
  id: '',
  role: '',
  oldMdp: '',
  newMdp: ''
});

const handleRetour = () => {
  router.back();
};

const handleValider = () => {
  router.push('/modif-saved');
};

const handleDelete = (index) => {
  if(confirm("Supprimer cet utilisateur ?")) {
    users.splice(index, 1);
  }
};

const handleCancel = () => {
  editingIndex.value = null;
  editedUser.id = '';
  editedUser.role = '';
  editedUser.oldMdp = '';
  editedUser.newMdp = '';
};

const handleAddUser = () => {
  router.push('/new-user');
};

const handleModif = (index) => {
  if (editingIndex.value === index) {
    handleCancel();
  } else {
    editingIndex.value = index;
    editedUser.id = users[index].id;
    editedUser.role = users[index].role;
    editedUser.oldMdp = users[index].mdp;
    editedUser.newMdp = '';
  }
};

const saveModification = (index) => {
  if (editedUser.id.trim() !== '' && editedUser.role !== '') {
    users[index].id = editedUser.id.trim();
    users[index].role = editedUser.role;

    if (editedUser.newMdp.trim() !== '') {
      users[index].mdp = editedUser.newMdp.trim();
    }

    editingIndex.value = null;
  } else {
    alert("Veuillez remplir l'Identifiant et choisir un Rôle.");
  }
};
</script>

<template>
  <Sidebar/>
  <div class="page-container">
    <AppHeader title="Gestion des utilisateurs" />

    <main class="main-content">
      <div class="grid-container">

        <div v-for="(user, index) in users" :key="index" class="user-card" :class="{ 'is-editing': editingIndex === index }">

          <div v-if="editingIndex !== index" class="card-content view-mode">
            <div class="icon-circle">
              <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"></path><circle cx="12" cy="7" r="4"></circle></svg>
            </div>
            <h3>{{ user.id }}</h3>
            <span class="role-badge">{{ user.role }}</span>

            <div class="card-actions">
              <button class="action-btn edit" @click="handleModif(index)" title="Modifier">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path></svg>
              </button>
              <button class="action-btn delete" @click="handleDelete(index)" title="Supprimer">
                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
              </button>
            </div>
          </div>

          <div v-else class="card-content edit-mode">
            <div class="edit-header">
              <h4>Modifier</h4>
              <button class="close-icon" @click="handleCancel">✕</button>
            </div>

            <div class="input-group">
              <input type="text" v-model="editedUser.id" placeholder="Identifiant" class="card-input">
            </div>

            <div class="input-group">
              <input type="text" v-model="editedUser.oldMdp" class="card-input readonly" readonly title="Ancien mot de passe">
            </div>

            <div class="input-group">
              <input type="password" v-model="editedUser.newMdp" placeholder="Nouveau Mdp" class="card-input">
            </div>

            <div class="input-group">
              <select v-model="editedUser.role" class="card-input select-input">
                <option value="" disabled>Rôle</option>
                <option value="Admin">Admin</option>
                <option value="Professeur">Professeur</option>
                <option value="Super Admin">Super Admin</option>
                <option value="Co-intervenant">Co-intervenant</option>
              </select>
            </div>

            <button class="save-btn" @click="saveModification(index)">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="20 6 9 17 4 12"></polyline></svg>
              Enregistrer
            </button>
          </div>
        </div>

        <div class="user-card add-card" @click="handleAddUser">
          <div class="icon-circle plus">
            <svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="12" y1="5" x2="12" y2="19"></line><line x1="5" y1="12" x2="19" y2="12"></line></svg>
          </div>
          <p>Ajouter un utilisateur</p>
        </div>

      </div>

      <div class="global-actions">
        <button @click="handleValider" class="btn-sys primary">Terminer</button>
        <button @click="handleRetour" class="btn-sys secondary">Retour</button>
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
  flex-direction: column;
  align-items: center;
  padding: 40px 20px;
  margin-top: 180px;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 30px;
  width: 100%;
  max-width: 1200px;
  margin-bottom: 50px;
}

/* --- Style des Cartes --- */
.user-card {
  background: #ffffff;
  border-radius: 15px;
  min-height: 320px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid rgba(0,0,0,0.05);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.user-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.1);
}

.user-card.is-editing {
  border: 2px solid #B51621;
  transform: none;
}

.card-content {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  height: 100%;
  justify-content: space-between;
}

/* --- Mode Affichage --- */
.icon-circle {
  width: 70px;
  height: 70px;
  background: rgba(181, 22, 33, 0.05);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 15px;
  color: #B51621;
}

h3 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 22px;
}

.role-badge {
  background: #f0f0f0;
  padding: 5px 12px;
  border-radius: 20px;
  font-size: 14px;
  color: #555;
  margin-bottom: 20px;
  font-weight: 500;
}

.card-actions {
  display: flex;
  gap: 15px;
  margin-top: auto;
}

.action-btn {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
}

.action-btn.edit {
  background: #e3f2fd;
  color: #1976d2;
}

.action-btn.delete {
  background: #ffebee;
  color: #c62828;
}

.action-btn:hover {
  transform: scale(1.1);
}

/* --- Mode Édition --- */
.edit-mode {
  justify-content: center;
  gap: 10px;
}

.edit-header {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.edit-header h4 {
  margin: 0;
  color: #B51621;
}

.close-icon {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  color: #999;
}

.input-group {
  width: 100%;
}

.card-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
  box-sizing: border-box;
  font-family: 'Roboto', sans-serif;
}

.card-input:focus {
  outline: none;
  border-color: #B51621;
}

.card-input.readonly {
  background-color: #f5f5f5;
  color: #888;
  cursor: not-allowed;
}

.save-btn {
  margin-top: 10px;
  width: 100%;
  padding: 10px;
  background: #B51621;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  font-weight: bold;
  transition: background 0.3s;
}

.save-btn:hover {
  background: #94121b;
}

/* --- Carte Ajouter --- */
.add-card {
  border: 2px dashed #ccc;
  cursor: pointer;
  background: transparent;
}

.add-card:hover {
  background: rgba(255, 255, 255, 0.5);
  border-color: #B51621;
}

.add-card p {
  font-weight: 500;
  color: #666;
  font-size: 18px;
}

.add-card:hover p {
  color: #B51621;
}

.icon-circle.plus {
  color: #555;
  background: #ddd;
}

.add-card:hover .icon-circle.plus {
  background: #B51621;
  color: white;
}

/* --- Boutons Globaux --- */
.global-actions {
  display: flex;
  gap: 20px;
  margin-top: 20px;
}

.btn-sys {
  padding: 12px 30px;
  border-radius: 30px;
  border: none;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.btn-sys:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0,0,0,0.15);
}

.btn-sys.primary {
  background-color: #B51621;
  color: white;
}

.btn-sys.secondary {
  background-color: #e0e0e0;
  color: #333;
}

@media (max-width: 600px) {
  .grid-container {
    grid-template-columns: 1fr;
  }
}
</style>