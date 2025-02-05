import './assets/main.css'
// import './assets/css/acss.css'
// main.ts
import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './routers/router.ts'
import App from './App.vue'
import {createPinia} from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'


/* import the fontawesome core */
import {library} from '@fortawesome/fontawesome-svg-core'

/* import font awesome icon component */
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'

/* import specific icons */
import {fas} from '@fortawesome/free-solid-svg-icons'


/* add icons to the library */
library.add(fas);

// 大菠萝
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate)
const app = createApp(App).component('font-awesome-icon', FontAwesomeIcon);
app.use(router).use(ElementPlus).use(pinia)


app.mount('#app');




