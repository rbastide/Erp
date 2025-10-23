<template>
  <header class="page-header">
    <div class="container-connexion">
      <img src="../assets/uploads/Logo_unilim.png" alt="Logo Unilim"><p>Connexion</p>
    </div>
    <div class="contact">Contact</div>
    <div class="date">00/00/00 <br> 12:00:00</div>
     </header>
     <main class="main-content">
           <form class="login-card">

             <!-- Groupe pour l'identifiant -->
             <div class="form-group">
               <label for="username">Identifiant</label>
               <!--
                 v-model="username" lie cet input à la variable 'username'
                 dans notre script.
               -->
               <input
                 type="text"
                 id="username"
                 placeholder="Identifiant"
                 v-model="username"
                 required
               />
             </div>

             <!-- Groupe pour le mot de passe -->
             <div class="form-group">
               <label for="password">Mot de passe</label>
               <!--
                 v-model="password" lie cet input à la variable 'password'
                 dans notre script.
               -->
               <input
                 type="password"
                 id="password"
                 placeholder="Mot de passe"
                 v-model="password"
                 required
               />
             </div>

             <!-- Bouton de soumission -->
             <button type="submit" class="login-button">Se connecter</button>

           </form>
         </main>


</template>

<style scoped>

  .body {
  background-color: white;
  }

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



  .contact{
    position: absolute;
    width: 126px;
    height: 52px;
    left: 1500px;
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
    left: 1750px;
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


/* --- Style du Contenu Principal --- */
.main-content {
  background-color: #FFFFFF;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  font-family: 'Roboto', sans-serif;
}

/* --- Style de la Carte de Connexion --- */
.login-card {
  background-color: #FFFFFF;
  padding: 2.5rem; /* 40px */
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  border: 1px solid #dcdcdc;

}

.form-group {
  margin-bottom: 1.5rem; /* 24px */
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem; /* 8px */
  font-weight: bold;
  font-size: 0.9rem; /* 14px */
  color: black;

}

.form-group input {
  width: 100%;
  padding: 0.75rem; /* 12px */
  border: 1px solid #dcdcdc;
  border-radius: 4px;
  box-sizing: border-box;
  font-size: 1rem; /* 16px */
}

.form-group input:focus {
  outline: none;
  border-color: #B51621;
  box-shadow: 0 0 0 2px rgba(181, 22, 33, 0.2);
}

.login-button {
  width: 100%;
  padding: 0.8rem; /* 13px */
  border: none;
  border-radius: 4px;
  background-color: #B51621;
  color: #FFFFFF;
  font-size: 1rem; /* 16px */
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.login-button:hover {
  background-color: #9c121b;
}

.main-content {

  min-height: 100vh;
  padding-top: 172px;
  box-sizing: border-box;
  }



</style>
