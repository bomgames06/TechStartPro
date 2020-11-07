<template>
    <v-container class='container-fit'>
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
                <v-btn color='primary' @click='openNewCategoryForm()'>
                    <v-icon color='white'>
                        mdi-plus
                    </v-icon>
                    <span>{{$vuetify.lang.t('$vuetify.add')}}</span>
                </v-btn>
            </v-col>
        </v-row>
        <v-row dense>
            <v-col cols='12'>
                <v-data-table :items='categoryTable.items' :headers='categoryTable.headers' :loading='categoryTable.loading'>
                    <template v-slot:item.actions='{ item }'>
                        <v-row dense justify='center' class='flex-nowrap'>
                            <v-col cols='auto'>
                                <v-icon @click='openEditCategoryForm(item)'>
                                    mdi-pencil
                                </v-icon>
                            </v-col>
                            <v-col cols='auto'>
                                <v-icon @click='deleteCategory(item)'>
                                    mdi-delete
                                </v-icon>
                            </v-col>
                        </v-row>
                    </template>
                </v-data-table>
            </v-col>
        </v-row>
        <v-dialog v-model='categoryFormDialog' max-width='400'>
            <v-card>
                <v-card-title>
                    {{category.id ? $vuetify.lang.t('$vuetify.edit-entity', $vuetify.lang.t('$vuetify.category')) : $vuetify.lang.t('$vuetify.new-entity', $vuetify.lang.t('$vuetify.category'))}}
                </v-card-title>
                <v-card-text>
                    <v-form ref='categoryForm' lazy-validation>
                        <v-row dense>
                            <v-col cols='12'>
                                <v-text-field v-model='category.name' :label='$vuetify.lang.t("$vuetify.name")' :rules='[rules.requiredString]' maxlength='32' counter />
                            </v-col>
                        </v-row>
                    </v-form>
                </v-card-text>
                <v-card-actions>
                    <v-btn text color='primary' @click='categoryFormDialog = false'>
                        {{$vuetify.lang.t('$vuetify.close')}}
                    </v-btn>
                    <v-btn text color='primary' @click='saveCategory()'>
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
        name: 'CategoryList',
        data: function () {
            return {
                categoryTable: {
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
                            text: this.$vuetify.lang.t('$vuetify.actions'),
                            value: 'actions',
                            align: 'center',
                            sortable: false,
                            filterable: false,
                            width: 1,
                        }
                    ],
                    items: [],
                    loading: false,
                },
                category: this.cleanCategory(),
                categoryFormDialog: false,
                rules: {
                    requiredString: value => (!!value && !!value.trim()) || this.$vuetify.lang.t('$vuetify.mandatory'),
                }
            };
        },
        created: function () {
            this.load();
        },
        methods: {
            load: function () {
                this.loadCategories();
            },
            loadCategories: async function () {
                try {
                    this.categoryTable.loading = true;
                    const resolve = await axios.get(`${this.$api.base}/category`);
                    this.categoryTable.items = resolve.data;
                } catch (reject) {
                    this.categoryTable.items = [];
                    this.$toastr.error(this.$vuetify.lang.t('$vuetify.system-error'));
                } finally {
                    this.categoryTable.loading = false;
                }
            },
            cleanCategory: function () {
                return {
                    id: null,
                    name: null,
                };
            },
            openNewCategoryForm: function () {
                this.category = this.cleanCategory();
                this.categoryFormDialog = true;
                if (this.$refs.categoryForm) {
                    this.$refs.categoryForm.resetValidation()
                }
            },
            openEditCategoryForm: function (item) {
                this.category = this.cleanCategory();
                this.category.id = item.id;
                this.category.name = item.name;
                this.categoryFormDialog = true;
                if (this.$refs.categoryForm) {
                    this.$refs.categoryForm.resetValidation()
                }
            },
            saveCategory: function () {
                if(this.category.id) {
                    this.editCategory();
                } else {
                    this.addCategory();
                }
            },
            addCategory: async function () {
                if (!this.$refs.categoryForm.validate()) {
                    return;
                }
                try {
                    await axios.post(`${this.$api.base}/category`, this.category);
                    this.$toastr.success(this.$vuetify.lang.t('$vuetify.entity-added', this.$vuetify.lang.t('$vuetify.category')))
                    this.categoryFormDialog = false;
                    this.load();
                } catch (reject) {
                    this.$toastr.error(this.$vuetify.lang.t('$vuetify.system-error'));
                }
            },
            editCategory: async function () {
                if (!this.$refs.categoryForm.validate()) {
                    return;
                }
                try {
                    await axios.put(`${this.$api.base}/category/${this.category.id}`, this.category);
                    this.$toastr.success(this.$vuetify.lang.t('$vuetify.entity-edited', this.$vuetify.lang.t('$vuetify.category')))
                    this.categoryFormDialog = false;
                    this.load();
                } catch (reject) {
                    this.$toastr.error(this.$vuetify.lang.t('$vuetify.system-error'));
                }
            },
            deleteCategory: async function (item) {
                const result = await this.$swal({
                    title: `${this.$vuetify.lang.t('$vuetify.attention')}!!!`,
                    text: this.$vuetify.lang.t('$vuetify.do-you-really-want-to-delete-this-record-question'),
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonText: this.$vuetify.lang.t('$vuetify.confirm'),
                    cancelButtonText: this.$vuetify.lang.t('$vuetify.cancel'),
                })
                if (!result.value) {
                    return;
                }
                try {
                    await axios.delete(`${this.$api.base}/category/${item.id}`);
                    this.$toastr.success(this.$vuetify.lang.t('$vuetify.entity-deleted', this.$vuetify.lang.t('$vuetify.category')))
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
