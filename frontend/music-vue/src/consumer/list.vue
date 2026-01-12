<template>
    <div>
        <p>
            <!-- v-model="name"绑定了变量name -->
            <el-input v-model="name" placeholder="请输入关键字" clearable style="width: 300px;"></el-input>
            <el-button type="success" @click="query">查询</el-button>
            <el-button type="primary" @click="handleAdd">新增</el-button>
        </p>
        <!-- dom 数据 绑定了数据源tableData -->
        <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="id" label="编号" width="180">
            </el-table-column>
            <el-table-column prop="username" label="姓名" width="180">
            </el-table-column>
            <el-table-column prop="password" label="密码">
            </el-table-column>
            <el-table-column prop="sex" label="性别">
            </el-table-column>
            <el-table-column prop="avator" label="头像">
                <template slot-scope="scope">
                    <img :src="getImageUrl(scope.row.avator)" style="width: 80px;height: 80px;" />
                </template>
            </el-table-column>
            <el-table-column prop="phoneNum" label="手机号码">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
                    <el-button size="mini" type="danger"
                        @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
            :page-sizes="[4, 10, 20, 50]" :page-size="size" layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
        <!-- dialog -->
        <el-dialog title="提示" :visible.sync="dialogVisible" width="30%">

            <el-form ref="form" :model="form" label-width="80px">

                <el-form-item label="头像">
                    <el-upload class="avatar-uploader" action="http://localhost:8085/file/avatar/upload"
                        :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>

                <el-form-item label="名称">
                    <el-input v-model="form.username"></el-input>
                </el-form-item>

                <el-form-item label="密码">
                    <el-input v-model="form.password"></el-input>
                </el-form-item>

                <el-form-item label="性别">
                    <el-select v-model="form.sex" style="width: 100%;" placeholder="请选择性别">
                        <el-option label="男" value="1"></el-option>
                        <el-option label="女" value="0"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="手机">
                    <el-input v-model="form.phoneNum"></el-input>
                </el-form-item>

            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submit">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<!-- js 中有对象数据 -->
<script>
import request from '../utils/request';
export default {
    //vue对象中的数据==data
    data() {
        return {
            tableData: [],
            dialogVisible: false,
            form: {
                id: '',
                username: '',
                password: '',
                sex: '',
                phoneNum: '',
            },
            fileList: [],
            name: '', // 新增响应式变量
            pageNum: 1, // 新增响应式变量
            size: 4, // 新增响应式变量
            total: 0, // 新增响应式变量
            imageUrl: '',
        }
    },

    //生命周期：vue创建成功后触发的函数
    created: function () {
        this.query();
    },

    //定义函数集
    methods: {
        // 上传成功
        handleAvatarSuccess(res, file) {
            this.imageUrl = "http://localhost:8085" + res.path;
            this.form.avator = res.path;
            console.log("头像路径 =", this.form.avator);
        },

        getImageUrl(path) {
            if (!path) return "";
            return "http://localhost:8085" + path;
        },

        // 上传前校验
        beforeAvatarUpload(file) {
            const isJPG = file.type === "image/jpeg" || file.type === "image/png";
            const isLt10M = file.size / 1024 / 1024 < 10;
            if (!isJPG) this.$message.error("只能上传 JPG/PNG！");
            if (!isLt10M) this.$message.error("不能超过 10MB！");
            return isJPG && isLt10M;
        },

        // 分页大小变化
        handleSizeChange(val) {
            this.size = val;
            this.query();
        },

        // 页码变化
        handleCurrentChange(val) {
            this.pageNum = val;
            this.query();
        },

        // 新增
        handleAdd() {
            this.dialogVisible = true;
            this.form = {
                id: "",
                username: "",
                password: "",
                sex: "",
                phoneNum: "",
                avator: "",
            };
            this.imageUrl = "";
        },

        // 查询
        query() {
            request.get("/consumer/list", {
                params: {
                    name: this.name,
                    pageNum: this.pageNum,
                    size: this.size,
                }
            }).then(res => {
                this.tableData = res.list;
                this.total = res.total;
            })
        },

        // 提交（新增或修改）
        submit() {
            request.post("/consumer/update", this.form).then(res => {
                if (res.success) {
                    this.$message.success("操作成功");
                    this.dialogVisible = false;
                    this.query();
                } else {
                    this.$message.error("操作失败");
                }
            });
        },

        // 编辑
        handleEdit(index, row) {
            this.dialogVisible = true;
            this.form = Object.assign({}, row);
            this.imageUrl = this.getImageUrl(row.avator);
        },

        // 删除
        handleDelete(index, row) {
            request.get("/consumer/delete?id=" + row.id).then(res => {
                if (res.success) {
                    this.$message.success("删除成功");
                    this.query();
                } else {
                    this.$message.error("删除失败");
                }
            });
        },

        handleClose(done) {
            this.$confirm("确认关闭?").then(_ => done());
        }
    },
}
</script>

<style>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
}

.avatar-uploader .el-upload:hover {
    border-color: #409EFF;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}

.avatar {
    width: 178px;
    height: 178px;
    display: block;
}
</style>