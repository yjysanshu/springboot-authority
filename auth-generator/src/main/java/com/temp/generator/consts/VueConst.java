package com.temp.generator.consts;

public class VueConst extends CommonConst {

    //待替换的文本
    public static final String REPLACE_COLUMN_COMMENT = "\\[columnComment\\]";
    public static final String REPLACE_TABLE_GRID_COLUMN = "\\[tableGridColumn\\]";
    public static final String REPLACE_TABLE_FORM_COLUMN = "\\[tableFormColumn\\]";
    public static final String REPLACE_CREATE_FORM_COLUMN = "\\[createFormColumn\\]";
    public static final String REPLACE_CREATE_FORM_DEFAULT = "\\[createFormDefault\\]";

    public static final String TXT_TABLE_GRID_COLUMN = "            <el-table-column label=\"[columnComment]\" prop=\"[columnNameRe]\" align=\"center\"></el-table-column>\n";
    public static final String TXT_CREATE_FORM_COLUMN = "                    [columnNameRe]: null,\n";
    public static final String TXT_CREATE_FORM_DEFAULT = "                this.createdForm.[columnNameRe] = null;\n";
    public static final String TXT_TABLE_FORM_COLUMN = "                <el-form-item label=\"[columnComment]\">\n" +
                                                        "                    <el-input v-model=\"createdForm.[columnNameRe]\" placeholder=\"请填写[columnComment]\"></el-input>\n" +
                                                        "                </el-form-item>\n";
    public static final String TXT_VUE = "<template>\n" +
            "    <div class=\"components-container\">\n" +
            "        <div class=\"filter-container\">\n" +
            "            <el-input v-model=\"tableQuery.name\" @keyup.enter.native=\"handleFilter\" style=\"width: 200px;\" placeholder=\"查询信息\"></el-input>\n" +
            "            <el-button style=\"margin-left: 10px;\" @click=\"handleFilter\" type=\"primary\"><i class=\"el-icon-search\"></i></el-button>\n" +
            "            <el-button @click=\"handleReset\" type=\"primary\"><i class=\"el-icon-refresh\"></i></el-button>\n" +
            "            <el-button @click=\"showDialog('create')\" type=\"primary\"><i class=\"el-icon-plus\"></i> 新增[[]]</el-button>\n" +
            "        </div>\n" +
            "\n" +
            "        <el-table :data=\"tableData\" v-loading.body=\"tableLoading\" element-loading-text=\"拼命加载中\" stripe border fit highlight-current-row style=\"width: 100%\">\n" +
            "[tableGridColumn]" +
            "            <el-table-column label=\"操作\" align=\"center\">\n" +
            "                <template slot-scope=\"scope\">\n" +
            "                    <el-button size=\"small\" @click=\"showDialog('update', scope.row)\" type=\"primary\"><i class=\"el-icon-edit\"></i> </el-button>\n" +
            "                    <el-button size=\"small\" @click=\"deleteRecord(scope.row.id)\" type=\"danger\"><i class=\"el-icon-delete\"></i> </el-button>\n" +
            "                </template>\n" +
            "            </el-table-column>\n" +
            "        </el-table>\n" +
            "\n" +
            "        <div class=\"pagination-container\">\n" +
            "            <el-pagination @size-change=\"handleSizeChange\" @current-change=\"handleCurrentChange\"\n" +
            "                           :current-page.sync=\"tableQuery.currentPage\" :page-sizes=\"[10, 20, 50]\"\n" +
            "                           :page-size=\"tableQuery.limit\" layout=\"total, sizes, prev, pager, next, jumper\" :total=\"total\">\n" +
            "            </el-pagination>\n" +
            "        </div>\n" +
            "\n" +
            "        <el-dialog :title=\"formTitle\" :visible.sync=\"formVisible\" width=\"35%\">\n" +
            "            <el-form :model=\"createdForm\" label-position=\"left\" label-width=\"70px\" style=\"width: 80%; margin-left:0px;\">\n" +
            "[tableFormColumn]" +
            "            </el-form>\n" +
            "            <div slot=\"footer\" class=\"dialog-footer\">\n" +
            "                <el-button @click=\"formVisible = false\">取 消</el-button>\n" +
            "                <el-button type=\"primary\" :loading=\"formSubmiting\" @click=\"commitForm\">确 认</el-button>\n" +
            "            </div>\n" +
            "        </el-dialog>\n" +
            "    </div>\n" +
            "</template>\n" +
            "\n" +
            "<script>\n" +
            "    import { confirmBox } from 'utils/message';\n" +
            "    import { isJson } from 'utils';\n" +
            "\n" +
            "    export default {\n" +
            "        data() {\n" +
            "            return {\n" +
            "                tableQuery: {\n" +
            "                    limit: 10,\n" +
            "                    currentPage: 1,\n" +
            "                },\n" +
            "                total: null,\n" +
            "                tableData: [],\n" +
            "                tableLoading: true,\n" +
            "                formTitle: null,\n" +
            "                formVisible: false,\n" +
            "                formSubmiting: false,\n" +
            "                createdForm: {\n" +
            "[createFormColumn]" +
            "                },\n" +
            "                deletedForm: {\n" +
            "                    id: null\n" +
            "                },\n" +
            "            };\n" +
            "        },\n" +
            "        mounted() {\n" +
            "            this.getList();\n" +
            "        },\n" +
            "        methods: {\n" +
            "            getList() {\n" +
            "                this.tableLoading = true;\n" +
            "                this.$api.module.[modelClass].list({\n" +
            "                    data: this.tableQuery\n" +
            "                }).then(response => {\n" +
            "                    this.total = response.data.data.total;\n" +
            "                    this.tableData = response.data.data.list;\n" +
            "                    this.tableLoading = false;\n" +
            "                }).catch(error => {\n" +
            "                    this.tableLoading = false;\n" +
            "                    console.log(error);\n" +
            "                });\n" +
            "            },\n" +
            "            handleFilter() {\n" +
            "                this.getList();\n" +
            "            },\n" +
            "            handleReset() {\n" +
            "                this.tableQuery.limit = 10;\n" +
            "                this.tableQuery.currentPage = 1;\n" +
            "\n" +
            "                this.getList();\n" +
            "            },\n" +
            "            handleSizeChange(val) {\n" +
            "                this.tableQuery.limit = val;\n" +
            "                this.getList();\n" +
            "            },\n" +
            "            handleCurrentChange(val) {\n" +
            "                this.tableQuery.currentPage = val;\n" +
            "                this.getList();\n" +
            "            },\n" +
            "            clearDialog() {\n" +
            "[createFormDefault]" +
            "            },\n" +
            "            showDialog(type, row = null) {\n" +
            "                this.formVisible = true;\n" +
            "                this.clearDialog();\n" +
            "                if (type == 'create') {\n" +
            "                    this.formTitle = \"新增[[]]信息\";\n" +
            "                } else {\n" +
            "                    this.formTitle = \"修改[[]]信息\";\n" +
            "                    Object.assign(this.createdForm, row);\n" +
            "                    if (isJson(this.createdForm.value)) {\n" +
            "                        this.createdForm.value = JSON.parse(this.createdForm.value);\n" +
            "                    }\n" +
            "                }\n" +
            "            },\n" +
            "            commitForm() {\n" +
            "                this.formSubmiting = true;\n" +
            "                this.$api.module.[modelClass].save({\n" +
            "                    data: this.createdForm\n" +
            "                }).then(response => {\n" +
            "                    this.formSubmiting = false;\n" +
            "                    this.formVisible = false;\n" +
            "                    this.$notify({\n" +
            "                        title: '成功',\n" +
            "                        message: '保存成功',\n" +
            "                        type: 'success',\n" +
            "                        duration: 1500,\n" +
            "                    });\n" +
            "                    this.handleFilter();\n" +
            "                    console.log(response);\n" +
            "                }).catch(error => {\n" +
            "                    this.formSubmiting = false;\n" +
            "                    this.$notify({\n" +
            "                        title: '错误',\n" +
            "                        message: '保存失败',\n" +
            "                        type: 'error',\n" +
            "                        duration: 3000,\n" +
            "                    });\n" +
            "                    console.log(error);\n" +
            "                });\n" +
            "            },\n" +
            "            deleteRecord(id) {\n" +
            "                confirmBox('[[]]').then(() => {\n" +
            "                    this.deletedForm.id = id;\n" +
            "                    this.$api.module.[modelClass].delete({\n" +
            "                        data: this.deletedForm\n" +
            "                    }).then(response => {\n" +
            "                        this.$notify({\n" +
            "                            title: '成功',\n" +
            "                            message: '删除成功',\n" +
            "                            type: 'success',\n" +
            "                            duration: 1500,\n" +
            "                        });\n" +
            "                        this.handleFilter();\n" +
            "                        console.log(response);\n" +
            "                    }).catch(error => {\n" +
            "                        this.formSubmiting = false;\n" +
            "                        this.$notify({\n" +
            "                            title: '错误',\n" +
            "                            message: '删除失败',\n" +
            "                            type: 'error',\n" +
            "                            duration: 3000,\n" +
            "                        });\n" +
            "                        console.log(error);\n" +
            "                    });\n" +
            "                });\n" +
            "            },\n" +
            "        }\n" +
            "    };\n" +
            "</script>\n";
}
