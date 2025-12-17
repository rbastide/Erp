<script setup lang="ts">
  import { useRouter } from 'vue-router';
  import AppHeader from './Header.vue';

  const router = useRouter();

  const handleRetour = () => {
    router.push('/home');
  };
</script>

<template>
  <AppHeader title="Information"/>
  <main class="main-content">
    <p class="text">Une erreur est survenue !</p>
  </main>
  <footer>
    <div @click="handleRetour" class="btn-quitter">Retour au menu</div>
  </footer>
</template>

<style scoped>
.main-content{
  position: relative;
}
.text{
  text-align: center;
  margin-top: 192px;
  color: #B51621;
  font-size: 40px;
  font-family: 'Roboto', sans-serif;
}

/*Footer*/
.btn-quitter{
  width: 150px;
  padding: 0.8rem; /* 13px */
  border: none;
  text-align: center;
  border-radius: 4px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1rem; /* 16px */
  font-weight: bold;
  font-family: 'Roboto', sans-serif;
  cursor: pointer;
  transition: background-color 0.2s ease;
  position: relative;
  margin: 5% auto;

}

.btn-quitter:hover{
  background: #999999;
  transform: translateY(-4px);
  cursor: pointer;
}
</style>