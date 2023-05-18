import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import {createPinia} from "pinia";
import MasonryWall from "@yeger/vue-masonry-wall";
import ElementPlus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import 'element-plus/dist/index.css';

const app = createApp(App)

// 加载ELEMENT ICON
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}
app.use(ElementPlus);
app.use(MasonryWall);
app.use(createPinia());
app.use(router);
app.mount('#app')