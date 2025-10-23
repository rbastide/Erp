<script setup lang="ts">

</script>

<template>

  <!-- Conteneur principal de RessourcePage !-->
  <div class = "ressource-page" >

    <!-- Header de la page contenant la bande rouge !-->
    <header class="page-header">
      <div class="container-nom">
        <img src="../assets/uploads/logo-unilim.png"><p>Choix ressources</p>
      </div>
      <div class="contact">Contact</div>
      <div class="date">00/00/00 <br> 12:00:00</div>
      <div class="quitter">
        <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </div>
    </header>

    <main class = "main-content">
      <div class = "choisir-ressource">
        Veuillez choisir votre ressource
      </div>

      <div class = "container-button">
        <div class ="push-button">
            <p class = "text-button">RX.XX</p>
        </div>
      </div>
    </main>

  </div>
</template>

<style scoped>
  header {
    .page-header {
      position: absolute;
      width: 1280px;
      height: 172px;
      left: 0px;
      top: 0px;
      background: #B51621;
    }

    .container-nom img {
      position: absolute;
      width: 127px;
      height: 127px;
      left: 64px;
      top: 22.5px;
    }

    .container-nom p {
      /* Bonjour,
    NOM Prénom */

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
      left: 1192px;
      top: 64px;
    }

    .contact {
      position: absolute;
      width: 126px;
      height: 52px;
      left: 774px;
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

    .date {
      position: absolute;
      width: 100px;
      height: 70px;
      left: 940px;
      top: 51px;
      font-family: 'Roboto', sans-serif;
      font-style: normal;
      font-weight: 500;
      font-size: 24px;
      line-height: 145%;
      display: flex;
      align-items: center;
      text-align: center;
      letter-spacing: -0.005em;
      text-transform: capitalize;
      color: #FFFFFF;
    }
  }
  main{
    .choisir-ressource {
      position: absolute;
      width: 672px;
      height: 66px;
      left: 38px;
      top: 196px;

      font-family: 'Roboto';
      font-style: normal;
      font-weight: 400;
      font-size: 32px;
      line-height: 38px;

      color: #E92533;
    }

    .push-button {
      /* Push Button */

      box-sizing: border-box;

      /* Auto layout */
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      padding: 0px 16px;
      isolation: isolate;

      position: absolute;
      width: 360px;
      height: 200px;
      left: 38px;
      top: 295px;

      background: #D9D9D9;
      border: 1px solid rgba(0, 0, 0, 0.25);
      box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
      border-radius: 15px;
    }

    .text-button {
      font-family: 'SF Pro', sans-serif;
      font-weight: 510;
      font-size: 64px;
      text-align: center;         /* Texte centré à l'intérieur */
      color: #B51621;
      margin: 0;
    }
  }

</style>