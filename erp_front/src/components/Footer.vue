<template>
  <footer class="app-footer">
    <p>&copy; {{ currentYear }} Erp App - Tous droits réservés.</p>
  </footer>
</template>

<script>
export default {
  name: 'AppFooter',
  data() {
    return {
      currentYear: new Date().getFullYear()
    };
  }
};
</script>

<style scoped>
.app-footer {
  font-family: 'Roboto', sans-serif;
  width: 100%;
  padding: 15px 0;
  text-align: center;
  background-color: #f0f0f0;
  border-top: 1px solid #ccc;
  margin-top: auto;
}
.app-footer nav a {
  margin: 0 5px;
  text-decoration: none;
  color: #333;
}
</style>