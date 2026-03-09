<script setup lang="js">
import { onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import AuthService from '@/services/AuthService';

const route = useRoute();
const router = useRouter();

onMounted(async () => {
  const token = route.query.token;

  if (token) {
    await AuthService.handleCasLogin(token);

    router.push('/home');
  } else {
    router.push('/login');
  }
});
</script>

<template>
  <div class="auth-success-container">
    <div class="loader-box">
      <h2>Authentification réussie...</h2>
      <p>Redirection en cours, veuillez patienter.</p>
    </div>
  </div>
</template>

<style scoped>
/* Tes styles de tout à l'heure */
.auth-success-container { display: flex; justify-content: center; align-items: center; height: 100vh; background-color: #f8f9fa; }
.loader-box { text-align: center; background: white; padding: 40px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
h2 { color: #B51621; margin-bottom: 10px; }
p { color: #666; }
</style>