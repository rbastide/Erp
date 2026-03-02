import "./assets/reset.css"
import "./assets/css/darkMode.css"

import { createApp } from 'vue'
import App from './App.vue'
import router from './index.js'

const app = createApp(App)

app.use(router)
app.mount('#app')