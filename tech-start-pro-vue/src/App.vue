<template>
    <v-app>
        <v-app-bar app color="primary" dark>
            <div class="d-flex align-center">
                <v-img
                    alt="Vuetify Logo"
                    class="shrink mr-2"
                    contain
                    src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
                    transition="scale-transition"
                    width="40"
                />
                <span class='text-h4'>
                  {{ $vuetify.lang.t('$vuetify.application-name') }}
                </span>
            </div>
        </v-app-bar>
        <v-main style='position: relative' class='overflow-y-auto'>
            <v-container fluid class='container-body'>
                <router-view />
            </v-container>
            <v-navigation-drawer permanent width='256'>
                <v-list dense>
                    <v-list-item class='px-2'>
                        <v-list-item-avatar>
                            <v-img src="https://i2.wp.com/www.cycat.io/wp-content/uploads/2018/10/Default-user-picture.jpg"></v-img>
                        </v-list-item-avatar>
                        <v-list-item-title>{{ $vuetify.lang.t('$vuetify.guest') }}</v-list-item-title>
                    </v-list-item>
                </v-list>
                <v-divider />
                <v-list nav dense>
                    <v-list-item link @click='$router.push("/home")'>
                        <v-list-item-icon>
                            <v-icon>mdi-home</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>Home</v-list-item-title>
                    </v-list-item>
                    <v-list-item link @click='$router.push("/categories")'>
                        <v-list-item-icon>
                            <v-icon>mdi-shape</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>{{ $vuetify.lang.t('$vuetify.categories') }}</v-list-item-title>
                    </v-list-item>
                    <v-list-item link @click='$router.push("/products")'>
                        <v-list-item-icon>
                            <v-icon>mdi-format-list-checkbox</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>{{ $vuetify.lang.t('$vuetify.products') }}</v-list-item-title>
                    </v-list-item>
                </v-list>
            </v-navigation-drawer>
        </v-main>
    </v-app>
</template>

<script>
    export default {
        name: 'App',
        data: () => ({
        }),
    };
</script>

<style lang='scss'>
    @import 'style/app.scss';

    .container-body {
        position: absolute;
        padding-left: 256px !important;
        left: 0;
        right: 0;
        top: 0;
        bottom: 0;
        margin-right: 0 !important;
    }
</style>
