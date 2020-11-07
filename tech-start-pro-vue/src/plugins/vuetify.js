import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import VueI18n from 'vue-i18n';
import ptLang from '../lang/i18n/pt'

Vue.use(Vuetify);
Vue.use(VueI18n);

const locale = 'pt';

const i18n = new VueI18n({
    locale: locale,
    messages: {
        ...ptLang,
    }
})

export default new Vuetify({
    lang: {
        current: locale,
        t: (key, ...params) => i18n.t(key, params),
    },
});
