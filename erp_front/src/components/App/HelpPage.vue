<script setup>
import { useRouter } from 'vue-router';
import AppHeader from './Header.vue';
import Sidebar from './Sidebar.vue';

const router = useRouter();

const handleRetour = () => {
  if (localStorage.getItem('user_token') === null) {
    router.push('/login');
  } else {
    router.back();
  }
};
</script>

<template>
  <div class="screen-layout">
    <AppHeader title="Service d'aide" :aide="false"/>

    <div class="content-wrapper">

      <Sidebar :helpActive="true"/>

      <main class="main-content">

        <div class="cards-container">

          <div class="help-card">
            <div class="icon-circle">
              <svg width="40" height="40" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"></path>
              </svg>
            </div>
            <h3>Par Téléphone</h3>
            <div class="card-text">
              <p class="highlight">+33 1 23 45 67 89 </p>
              <p class="sub-text">LUNDI - SAMEDI : 9H - 19H CET</p>
            </div>
          </div>

          <div class="help-card">
            <div class="icon-circle">
              <svg width="40" height="40" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                <path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"></path>
                <polyline points="22,6 12,13 2,6"></polyline>
              </svg>
            </div>
            <h3>Par Email</h3>
            <div class="card-text">
              <p class="highlight email">svp-dsi@unilim.fr</p>
              <p class="sub-text">Nous nous efforcerons de vous répondre sous 48h</p>
            </div>
          </div>

        </div>

        <div class="footer-actions">
          <button @click="handleRetour" class="btn-sys secondary">Retour</button>
        </div>

      </main>
    </div>
  </div>
</template>

<style scoped>
.screen-layout {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
  background-color: #f8f9fa;
  font-family: 'Roboto', sans-serif;
}

.content-wrapper {
  flex: 1;
  display: flex;
  position: relative;
  overflow: hidden;
}

.main-content {
  flex: 1;
  margin-left: 80px;
  height: 100%;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px;
}

.cards-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 50px;
  margin-bottom: 60px;
  width: 100%;
  max-width: 1000px;
}

.help-card {
  background: #ffffff;
  width: 380px;
  min-height: 300px;
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 40px 30px;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  box-shadow: 0 10px 25px rgba(0,0,0,0.05);
  border: 1px solid transparent;
}

.help-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 15px 35px rgba(181, 22, 33, 0.1);
  border-color: rgba(181, 22, 33, 0.1);
}

/* Icônes */
.icon-circle {
  width: 80px;
  height: 80px;
  background: rgba(181, 22, 33, 0.05);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 25px;
  color: #B51621;
  transition: all 0.3s ease;
}

.help-card:hover .icon-circle {
  background: #B51621;
  color: #ffffff;
  transform: scale(1.1);
}

h3 {
  font-size: 28px;
  color: #333;
  margin: 0 0 20px 0;
  font-weight: 500;
}

.card-text {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.highlight {
  font-size: 22px;
  font-weight: 700;
  color: #B51621;
  margin: 0;
}

.highlight.email {
  font-size: 20px;
  text-decoration: underline;
  text-decoration-color: rgba(181, 22, 33, 0.3);
}

.sub-text {
  font-size: 16px;
  color: #666;
  line-height: 1.5;
  margin: 0;
}

/* Bouton Retour */
.footer-actions {
  margin-top: 20px;
}

.btn-sys {
  padding: 15px 50px;
  border-radius: 50px;
  border: none;
  font-size: 18px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s;
  letter-spacing: 0.03125em;
  font-family: 'Roboto', sans-serif;
}

.btn-sys.secondary {
  background-color: white;
  color: #555;
  border: 2px solid #e0e0e0;
}

.btn-sys.secondary:hover {
  border-color: #B51621;
  color: #B51621;
  transform: translateY(-3px);
  box-shadow: 0 5px 15px rgba(0,0,0,0.05);
}

/* Responsive */
@media (max-width: 900px) {
  .cards-container {
    flex-direction: column;
    align-items: center;
    gap: 30px;
  }

  .help-card {
    width: 100%;
    max-width: 400px;
  }

  .main-content {
    margin-left: 0;
    padding-left: 100px;
  }
}

@media (max-width: 600px) {
  .main-content {
    padding: 20px 20px 20px 90px;
  }
}
</style>