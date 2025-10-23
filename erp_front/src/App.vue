<template>
  <!--
    On importe et on affiche notre nouveau composant LoginPage.
    Tout le reste (les composants d'exemple) a été supprimé.
  -->
  <!--<LoginPage />
  <RessourcePage/>-->
  <HomePage/>
</template>

<script setup>
// 1. Importer le composant que nous venons de créer
import LoginPage from './components/LoginPage.vue';
import RessourcePage from './components/RessourcePage.vue';
import HomePage from './components/HomePage.vue';

// 2. Le composant est automatiquement enregistré et utilisable
//    dans le <template> grâce à 'script setup'.
</script>

<style>
/* Styles globaux pour réinitialiser le CSS du navigateur.
  (Ces styles ne sont PAS 'scoped')
  Placez-les ici ou dans votre 'src/assets/main.css'.
*/


</style>
