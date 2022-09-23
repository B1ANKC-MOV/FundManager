import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import axios from 'axios'
import router from './router'
axios.defaults.baseURL = window.location.protocol + '//' + window.location.hostname + ':8888'
axios.defaults.withCredentials = true

Vue.prototype.$http = axios
Vue.use(ElementUI);
Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
}).$mount('#app')