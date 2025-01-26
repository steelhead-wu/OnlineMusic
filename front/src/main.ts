import './assets/main.css'
// import './assets/css/acss.css'
// main.ts
import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './routers/router.ts'
import App from './App.vue'
import { createPinia } from 'pinia'

const pinia = createPinia();
const app = createApp(App);

app.use(router).use(ElementPlus).use(pinia)


app.mount('#app');

