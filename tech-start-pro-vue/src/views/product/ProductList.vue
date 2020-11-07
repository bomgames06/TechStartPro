<template>
    <v-container class='container-fit'>
        <v-row dense>
            <v-col cols='12' sm='6' md='4' lg='3'>
                <v-text-field v-model='productTable.filters.name' :label='$vuetify.lang.t("$vuetify.name")' />
            </v-col>
            <v-col cols='12' sm='6' md='4' lg='3'>
                <v-text-field v-model='productTable.filters.description' :label='$vuetify.lang.t("$vuetify.description")' />
            </v-col>
            <v-col cols='12' sm='6' md='4' lg='3'>
                <v-text-field v-model='productTable.filters.minValue' :label='$vuetify.lang.t("$vuetify.minimum-value")' type='number' />
            </v-col>
            <v-col cols='12' sm='6' md='4' lg='3'>
                <v-text-field v-model='productTable.filters.maxValue' :label='$vuetify.lang.t("$vuetify.maximum-value")' type='number' />
            </v-col>
            <v-col cols='12' sm='6' md='4' lg='3'>
                <v-autocomplete v-model='productTable.filters.categoryIds' :label='$vuetify.lang.t("$vuetify.category")' :items='categories' item-value='id' item-text='name' multiple />
            </v-col>
        </v-row>
        <v-row dense>
            <v-col cols='auto'>
                <v-btn color='primary' @click='load()'>
                    <v-icon color='white'>
                        mdi-magnify
                    </v-icon>
                    <span>{{$vuetify.lang.t('$vuetify.search')}}</span>
                </v-btn>
            </v-col>
            <v-col cols='auto'>
                <v-btn color='primary' @click='openNewProductForm()'>
                    <v-icon color='white'>
                        mdi-plus
                    </v-icon>
                    <span>{{$vuetify.lang.t('$vuetify.add')}}</span>
                </v-btn>
            </v-col>
        </v-row>
        <v-row dense>
            <v-col cols='12'>
                <v-data-table :items='productTable.items' :headers='productTable.headers' :loading='productTable.loading'>
                    <template v-slot:item.value='{ value }'>
                        {{$util.numberFormat(value)}}
                    </template>
                    <template v-slot:item.actions='{ item }'>
                        <v-row dense justify='center' class='flex-nowrap'>
                            <v-col cols='auto'>
                                <v-icon @click='openEditProductForm(item)'>
                                    mdi-pencil
                                </v-icon>
                            </v-col>
                            <v-col cols='auto'>
                                <v-icon @click='deleteProduct(item)'>
                                    mdi-delete
                                </v-icon>
                            </v-col>
                        </v-row>
                    </template>
                </v-data-table>
            </v-col>
        </v-row>
        <v-dialog v-model='productFormDialog' max-width='400'>
            <v-card>
                <v-card-title>
                    {{product.id ? $vuetify.lang.t('$vuetify.edit-entity', $vuetify.lang.t('$vuetify.product')) : $vuetify.lang.t('$vuetify.new-entity', $vuetify.lang.t('$vuetify.product'))}}
                </v-card-title>
                <v-card-text>
                    <v-form ref='productForm' lazy-validation>
                        <v-row dense>
                            <v-col cols='12'>
                                <v-text-field v-model='product.name' :label='$vuetify.lang.t("$vuetify.name")' maxlength='32' counter :rules='[rules.requiredString]' />
                            </v-col>
                            <v-col cols='12'>
                                <v-textarea v-model='product.description' :label='$vuetify.lang.t("$vuetify.description")' no-resize :rules='[rules.requiredString]' />
                            </v-col>
                            <v-col cols='12'>
                                <v-text-field v-model='product.value' :label='$vuetify.lang.t("$vuetify.value")' type='number' :rules='[rules.requiredString]' />
                            </v-col>
                            <v-col cols='12'>
                                <v-autocomplete v-model='product.categoryIds' :label='$vuetify.lang.t("$vuetify.category")' :items='categories' item-value='id' item-text='name' multiple :rules='[rules.requiredArray]' />
                            </v-col>
                        </v-row>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-btn text color='primary' @click='productFormDialog = false'>
                        {{$vuetify.lang.t('$vuetify.close')}}
                    </v-btn>
                    <v-btn text color='primary' @click='saveProduct()'>
                        {{$vuetify.lang.t('$vuetify.save')}}
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-container>
</template>

<script>
    import axios from 'axios';

    export default {
        name: 'ProductList.vue',
        data: function () {
            return {
                productTable: {
                    headers: [
                        {
                            text: this.$vuetify.lang.t('$vuetify.code'),
                            value: 'id',
                            width: 200,
                        },
                        {
                            text: this.$vuetify.lang.t('$vuetify.name'),
                            value: 'name',
                        },
                        {
                            text: this.$vuetify.lang.t('$vuetify.description'),
                            value: 'description',
                        },
                        {
                            text: this.$vuetify.lang.t('$vuetify.value'),
                            value: 'value',
                            align: 'end',
                        },
                        {
                            text: this.$vuetify.lang.t('$vuetify.actions'),
                            value: 'actions',
                            align: 'center',
                            sortable: false,
                            filterable: false,
                            width: 1,
                        }
                    ],
                    items: [],
                    filters: {
                        name: null,
                        description: null,
                        minValue: null,
                        maxValue: null,
                        categoryIds: [],
                    },
                    loading: false,
                },
                product: this.cleanProduct(),
                productFormDialog: false,
                categories: [],
                rules: {
                    requiredString: value => (!!value && !!value.trim()) || this.$vuetify.lang.t('$vuetify.mandatory'),
                    requiredArray: value => (!!value && !!value.length) || this.$vuetify.lang.t('$vuetify.mandatory'),
                },
            };
        },
        created: function () {
            this.load();
        },
        methods: {
            load: function () {
                this.loadProducts();
                this.loadCategories();
            },
            loadProducts: async function () {
                const params = {};
                if (this.productTable.filters.name && this.productTable.filters.name.trim()) {
                    params.name = this.productTable.filters.name.trim();
                }
                if (this.productTable.filters.description && this.productTable.filters.description.trim()) {
                    params.description = this.productTable.filters.description.trim();
                }
                if (this.productTable.filters.minValue) {
                    params.minValue = this.productTable.filters.minValue;
                }
                if (this.productTable.filters.maxValue) {
                    params.maxValue = this.productTable.filters.maxValue;
                }
                if (this.productTable.filters.categoryIds.length) {
                    params.categoryIds = this.productTable.filters.categoryIds.join(',');
                }
                try {
                    this.productTable.loading = true;
                    const resolve = await axios.get(`${this.$api.base}/product`, { params: params });
                    this.productTable.items = resolve.data;
                } catch (reject) {
                    this.productTable.items = [];
                    this.$toastr.error(this.$vuetify.lang.t('$vuetify.system-error'));
                } finally {
                    this.productTable.loading = false;
                }
            },
            openNewProductForm: function () {
                this.product = this.cleanProduct();
                this.loadCategories();
                this.productFormDialog = true;
                if (this.$refs.productForm) {
                    this.$refs.productForm.resetValidation()
                }
            },
            openEditProductForm: function (item) {
                this.product = this.cleanProduct();
                this.product.id = item.id;
                this.product.name = item.name;
                this.product.description = item.description;
                this.product.value = String(item.value);
                this.product.categoryIds = item.categories.map(category => category.id);
                this.loadCategories();
                this.productFormDialog = true;
                if (this.$refs.productForm) {
                    this.$refs.productForm.resetValidation()
                }
            },
            loadCategories: async function () {
                try {
                    const resolve = await axios.get(`${this.$api.base}/category`);
                    this.categories = resolve.data;
                } catch (reject) {
                    this.categories = [];
                    this.$toastr.error(this.$vuetify.lang.t('$vuetify.system-error'));
                }
            },
            cleanProduct: function () {
                return {
                    id: null,
                    name: null,
                    description: null,
                    value: null,
                    categoryIds: [],
                };
            },
            saveProduct: function () {
                if(this.product.id) {
                    this.editProduct();
                } else {
                    this.addProduct();
                }
            },
            addProduct: async function () {
                if (!this.$refs.productForm.validate()) {
                    return;
                }
                try {
                    await axios.post(`${this.$api.base}/product`, this.product);
                    this.$toastr.success(this.$vuetify.lang.t('$vuetify.entity-added', this.$vuetify.lang.t('$vuetify.product')))
                    this.productFormDialog = false;
                    this.load();
                } catch (reject) {
                    this.$toastr.error(this.$vuetify.lang.t('$vuetify.system-error'));
                }
            },
            editProduct: async function () {
                if (!this.$refs.productForm.validate()) {
                    return;
                }
                try {
                    await axios.put(`${this.$api.base}/product/${this.product.id}`, this.product);
                    this.$toastr.success(this.$vuetify.lang.t('$vuetify.entity-edited', this.$vuetify.lang.t('$vuetify.product')))
                    this.productFormDialog = false;
                    this.load();
                } catch (reject) {
                    this.$toastr.error(this.$vuetify.lang.t('$vuetify.system-error'));
                }
            },
        },
    };
</script>

<style lang='scss' scoped>
</style>
