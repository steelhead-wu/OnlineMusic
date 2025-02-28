import './assets/main.css'
import './assets/css/global.css'
// import './assets/css/acss.css'
// main.ts
import {createApp} from 'vue'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import 'element-plus/dist/index.css'
import router from './routers/router.ts'
import App from './App.vue'
import {createPinia} from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

//Import Froala Editor
import 'froala-editor/js/plugins.pkgd.min.js';
//Import third party plugins
import 'froala-editor/js/third_party/embedly.min';
import 'froala-editor/js/third_party/font_awesome.min';
import 'froala-editor/js/third_party/spell_checker.min';
import 'froala-editor/js/third_party/image_tui.min';
// Import Froala Editor css files.
import 'froala-editor/css/froala_editor.pkgd.min.css';
import 'froala-editor/css/froala_style.min.css';
import VueFroala from 'vue-froala-wysiwyg';

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
app.use(router).use(ElementPlus, {
    locale: zhCn,
}).use(pinia).use(VueFroala)


app.mount('#app');