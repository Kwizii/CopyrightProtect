import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
import {createPinia} from "pinia";
import 'element-plus/dist/index.css';
import './assets/css/icon.css';

const app = createApp(App)

app.use(createPinia());
app.use(router)
// 加载ELEMENT ICON
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}
app.mount('#app')
