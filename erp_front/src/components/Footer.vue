<template>
  <footer class="app-footer">
    <p>&copy; {{ currentYear }} Erp App - Tous droits réservés.</p>
  </footer>
</template>

<script>
export default {
  // Le nom du composant
  name: 'AppFooter',
  data() {
    return {
      currentYear: new Date().getFullYear()
    };
  }
};
</script>

<style scoped>
/* Styles de base pour le footer */
.app-footer {
  font-family: 'Roboto', sans-serif;
  width: 100%;
  padding: 15px 0;
  text-align: center;
  background-color: #f0f0f0; /* Exemple de couleur de fond */
  border-top: 1px solid #ccc;
  /* Assurez-vous qu'il reste en bas de la page si nécessaire */
  margin-top: auto;
}
.app-footer nav a {
  margin: 0 5px;
  text-decoration: none;
  color: #333;
}
</style>