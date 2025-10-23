<template>
  <!--
    On importe et on affiche notre nouveau composant LoginPage.
    Tout le reste (les composants d'exemple) a été supprimé.
  -->
  <LoginPage />
</template>

<script setup>
// 1. Importer le composant que nous venons de créer
import LoginPage from './components/LoginPage.vue';

// 2. Le composant est automatiquement enregistré et utilisable
//    dans le <template> grâce à 'script setup'.
</script>

<style>
/* Styles globaux pour réinitialiser le CSS du navigateur.
  (Ces styles ne sont PAS 'scoped')
  Placez-les ici ou dans votre 'src/assets/main.css'.
*/
body {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

#app {
  height: 100vh;
}
</style>
