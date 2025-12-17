<script setup>
import { useRouter } from 'vue-router';

// Déclaration des props
const props = defineProps({
  title: {
    type: String,
    required: true
  },
  inline: {
    type: String,
  },
  aide: {
    type: Boolean,
    default: true
  },
  deconnexion: {
    type: Boolean,
    default: true
  }
});

const router = useRouter();

const handleAide = () => {
  router.push('/aide');
};

const handleDeconnexion = () => {
  router.push('/deconnexion');
};
</script>

<template>
  <header class="page-header">
    <div class="container-connexion">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim">
      <p>{{ title }} <br> {{inline}}</p>
    </div>
    <div v-if="aide" @click="handleAide" class="aide">Service d'aide</div>
    <div v-if="deconnexion" @click="handleDeconnexion" class="quitter">
      <svg width="48" height="48" viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg">
        <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>
  </header>
</template>

<style scoped>
.page-header {
  position: absolute;
  width: 100%;
  height: 172px;
  left: 0;
  top: 0;
  background: linear-gradient(90deg, #96111a 0%, #b51621 40%, #E05000 100%);
  box-sizing: border-box;
}

.container-connexion img {
  position: absolute;
  width: 127px;
  height: 127px;
  left: 64px;
  top: 22.5px;
}
.container-connexion p{
  position: absolute;
  width: 723px;
  height: 124px;
  left: 209px;
  top: 24px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 900;
  font-size: 56px;
  line-height: 110%;
  display: flex;
  align-items: center;
  letter-spacing: -0.03em;
  color: #FFFFFF;
}

.aide{
  position: absolute;
  width: 126px;
  height: 52px;
  right: 15%;
  top: 60px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 500;
  font-size: 36px;
  line-height: 145%;
  display: flex;
  align-items: center;
  text-align: center;
  letter-spacing: -0.005em;
  text-transform: capitalize;
  color: #FFFFFF;
  cursor: pointer;
}

.quitter {
  position: absolute;
  width: 48px;
  height: 48px;
  right: 5%;
  top: 64px;
  cursor: pointer;
}
</style>