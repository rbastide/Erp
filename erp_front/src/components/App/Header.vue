<script setup>
import { useRouter } from 'vue-router';
import {onMounted, ref} from "vue";
import '../../assets/css/variable.css';

const isConnected = ref(false);

const router = useRouter();

onMounted(() => {
  isConnected.value = !!localStorage.getItem('user_token');
})
</script>

<template>
  <header class="page-header" title="">
    <div class="container-connexion">
      <img src="../../assets/uploads/Logo_unilim.png" alt="Logo Unilim">
      <p>{{ title }} <br> {{inline}}</p>
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
  background: var(--gradient-red-orange, #B51621);
  box-sizing: border-box;
}

.container-connexion img {
  position: absolute;
  width: 127px;
  height: 127px;
  left: 364px;
  top: 1.40625rem;
}
.container-connexion p{
  position: absolute;
  width: 723px;
  height: 124px;
  left: 509px;
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
</style>