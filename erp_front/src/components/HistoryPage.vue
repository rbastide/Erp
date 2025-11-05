<script setup>

</script>

<template>
  <header class="page-header">
    <div class="container-nom">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Bonjour, <br>NOM Prénom</p>
    </div>
    <div class="contact">Service d'aide</div>
    <div class="quitter">
      <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>
  </header>
  <body>
    <p class="description">Voici l'historique des fiches de ressource : </p>
    <div class="version-list-container">
      <ul class="version-list">
        <li class="version-item">
          <span class="version-code">R1.01</span>
          <span class="version-date">30/10/2012</span>
        </li>
        <li class="version-item">
          <span class="version-code">R1.02</span>
          <span class="version-date">15/11/2013</span>
        </li>
        <li class="version-item">
          <span class="version-code">R1.03</span>
          <span class="version-date">01/01/2014</span>
        </li>
        <li class="version-item">
          <span class="version-code">R2.01</span>
          <span class="version-date">05/08/2014</span>
        </li>
        <li class="version-item">
          <span class="version-code">R2.02</span>
          <span class="version-date">06/05/2015</span>
        </li>
        <li class="version-item">
          <span class="version-code">R2.04</span>
          <span class="version-date">08/09/2015</span>
        </li>
        <li class="version-item">
          <span class="version-code">R2.05</span>
          <span class="version-date">01/10/2015</span>
        </li>
        <li class="version-item">
          <span class="version-code">R3.02</span>
          <span class="version-date">15/05/2016</span>
        </li>
        <li class="version-item">
          <span class="version-code">R3.03</span>
          <span class="version-date">18/07/2017</span>
        </li>
        <li class="version-item last-item">
          <span class="version-code">R4.01</span>
          <span class="version-date">14/12/2017</span>
        </li>
      </ul>
    </div>
  </body>
</template>

<style scoped>

.page-header {
  position: absolute;
  width: 100%; /* Modifié de 1280px à 100% pour prendre toute la largeur */
  height: 172px;
  left: 0px;
  top: 0px;
  background: #B51621;
  /* J'ajoute box-sizing pour éviter des problèmes de padding/border qui affectent la largeur totale */
  box-sizing: border-box;
}

.container-nom img {
  position: absolute;
  width: 127px;
  height: 127px;
  left: 64px;
  top: 22.5px;
}
.container-nom p{
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

.quitter {

  position: absolute;
  width: 48px;
  height: 48px;
  right: 5%;
  top: 64px;
}

.contact{
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
}

/* Contenue de la page */

body {
  display: flex;
  justify-content: center;
  align-items: center;
}

.description{
  position: absolute;
  width: 672px;
  height: 66px;
  left: 38px;
  top: 196px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 32px;
  line-height: 38px;
  color: #E92533;
}

.version-list-container {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: auto;
  width: 800px;
  font-family: 'Roboto', sans-serif;
  padding: 10px;
}

.version-list {
  width: 100%;
  list-style: none;
  padding: 0;
  margin: 0;
}

.version-item {
  /* Auto layout */
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 10px 16px;
  isolation: isolate;
  margin-bottom: 15px;
  background: #D9D9D9;
  border: 1px solid rgba(0, 0, 0, 0.25);
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 6px;
}

.version-item.last-item {
  background-color: #dcdcdc;
  margin-bottom: 0;
}

.version-code {

  width: 943px;
  height: 137px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 510;
  font-size: 64px;
  line-height: 16px;
  display: flex;
  align-items: center;
  text-align: center;
  color: #B51621;
  flex: none;
  order: 1;
  align-self: stretch;
  flex-grow: 1;
  z-index: 1;
}

.version-date{
  width: 943px;
  height: 137px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 510;
  font-size: 64px;
  line-height: 16px;
  display: flex;
  align-items: center;
  text-align: center;
  color: #B51621;
  flex: none;
  order: 1;
  align-self: stretch;
  flex-grow: 1;
  z-index: 1;
}

</style>