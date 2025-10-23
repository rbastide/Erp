<template>
  <header class="page-header">
    <div class="container-nom">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Bonjour, <br>NOM Prénom</p>
    </div>
    <div class="contact">Contact</div>
    <div class="date">00/00/00 <br> 12:00:00</div>
    <div class="quitter">
      <svg width="48" height="48" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <path d="M18 42H10C8.93913 42 7.92172 41.5786 7.17157 40.8284C6.42143 40.0783 6 39.0609 6 38V10C6 8.93913 6.42143 7.92172 7.17157 7.17157C7.92172 6.42143 8.93913 6 10 6H18M32 34L42 24M42 24L32 14M42 24H18" stroke="white" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </div>
  </header>

  <div class="main-content">
    <div class="remplir"><p>Remplir une fiche ressource</p></div>
    <div class="afficher"><p>Afficher les fiches ressource</p></div>
  </div>
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

.quitter{

  position: absolute;
  width: 48px;
  height: 48px;
  left: 1192px;
  top: 64px;
}

.contact{
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

.date{
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

/*Contenu de la page*/

.main-content{
  display: flex;
  width: 90%;
  margin: auto;
  margin-top: 254px;
  justify-content: center;
  align-items: center;
}

.remplir,
.afficher {
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0px 16px;
  width: 360px;
  height: 200px;
  background: #D9D9D9;
  box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 15px;
  margin: 50px;
}

.remplir, .afficher p {
  width: 328px;
  height: 200px;
  font-family: 'Roboto', sans-serif;
  font-style: normal;
  font-weight: 400;
  font-size: 36px;
  line-height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #E92533;
  z-index: 1;
}
</style>

<style scoped>

</style>