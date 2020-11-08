import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import util from './util/util';
import VueSweetalert2 from 'vue-sweetalert2';
import Toastr from 'toastr';
import _ from 'lodash';

Toastr.options = {
    closeButton: true,
    debug: false,
    newestOnTop: true,
    progressBar: true,
    positionClass: 'toast-bottom-right',
    preventDuplicates: true,
    onclick: null,
    showDuration: '300',
    hideDuration: '1000',
    timeOut: '5000',
    extendedTimeOut: '1000',
    showEasing: 'swing',
    hideEasing: 'linear',
    showMethod: 'fadeIn',
    hideMethod: 'fadeOut',
};

Vue.use(VueSweetalert2);

Vue.config.productionTip = false

Vue.prototype.$api = {
    base: 'http://localhost:8080/tech-start-pro/rest/api'
}
Vue.prototype.$util = util;
Vue.prototype.$toastr = Toastr;
Vue.prototype.$lodash = _;

new Vue({
    router,
    store,
    vuetify,
    render: h => h(App)
}).$mount('#app')
