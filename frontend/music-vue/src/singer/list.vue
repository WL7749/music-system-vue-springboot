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
            <el-table-column prop="name" label="歌手" width="180">
            </el-table-column>
            <el-table-column prop="birth" label="生日">
            </el-table-column>
            <el-table-column prop="sex" label="性别">
            </el-table-column>
            <el-table-column prop="pic" label="图片">
                <template slot-scope="scope">
                    <img :src="getImageUrl(scope.row.pic)" style="width: 80px;height: 80px;" />
                </template>
            </el-table-column>
            <el-table-column prop="location" label="地点">
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
        <!-- 对话框 -->
        <el-dialog title="提示" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
            <!-- :model="form" 绑定了某个对象 -->
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="头像">
                    <!-- 上传组件 -->
                    <el-upload class="avatar-uploader" action="http://localhost:8085/file/avatar/upload"
                        :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload">
                        <img v-if="imageUrl" :src="imageUrl" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item label="名称">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="生日">
                    <el-input v-model="form.birth"></el-input>
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-select v-model="form.sex" style="width: 100%;" placeholder="请选择性别">
                        <el-option label="男" :value="1"></el-option>
                        <el-option label="女" :value="0"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="地点">
                    <el-input v-model="form.location"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submit">确 定</el-button>
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

            name: '',
            pageNum: 1,
            size: 4,
            total: 0,
            imageUrl: '',
            form: {
                id: '',
                name: '',
                sex: '',
                birth: '',
                location: '',
                pic: ''
            },

            fileList: []
        }
    },


    //生命周期：vue创建成功后触发的函数
    created: function () {
        this.query();
    },

    //定义函数集
    methods: {
        //上传成功后的处理
        handleAvatarSuccess(res, file) {
            this.fileList = res;
            //this.form.url = res.url
            console.log("res[0].name=" + res.url);
        },
        //用于拼接完整的图片访问地址
        getImageUrl(path) {
            return "http://localhost:8085" + path;
        },
        //上传成功后的处理函数
        handleAvatarSuccess(res, file) {
            this.imageUrl = URL.createObjectURL(file.raw);
            //给avator赋值
            this.form.pic = res.path;
            console.log("this.form.pic=" + this.form.pic);
        },
        //上传之前的处理函数
        beforeAvatarUpload(file) {
            const isJPG = (file.type === 'image/jpeg') || (file.type === 'image/png');
            const isLt10M = file.size / 1024 / 1024 < 10;
            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG\png 格式!');
            }
            if (!isLt10M) {
                this.$message.error('上传头像图片大小不能超过 10MB!');
            }
            return isJPG && isLt10M;
        },
        //记录条数的变化
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            //变更查询条数
            this.size = val;
            //查询数据
            this.query();
        },
        //页码的变化
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            //变更当前页码
            this.pageNum = val;
            //查询数据
            this.query();
        },
        //提供新增点击
        handleAdd() {
            //显示对话框
            this.dialogVisible = true;
            //清空form中的数据
            this.form = {
                id: '',
                name: '',
                pic: '',
                sex: '',
                birth: '',
                location: '',
            }
        },
        //定义一个查询请求函数
        query() {
            //发起查询
            request.get("/singer/list", {
                params: {
                    name: this.name,
                    pageNum: this.pageNum,
                    size: this.size,
                }
            }).then((res) => {
                console.log("res=" + res.list);
                this.tableData = res.list;
                this.total = res.total;
            })
        },
        //提交处理-发起请求
        submit() {
            //发起查询
            request.post("/singer/update", this.form).then((res) => {
                if (res.success == true) {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    //关闭对话框
                    this.dialogVisible = false;
                    //重新查询数据
                    this.query();
                } else {
                    this.$message.error('操作失败');
                }
            });
        },
        //点击编辑
        handleEdit(index, row) {
            console.log(index, row);
            //显示对话框
            this.dialogVisible = true;
            //将row赋值到form
            this.form.id = row.id;
            this.form.name = row.name;
            this.form.birth = row.birth;
            this.form.sex = row.sex;
            this.form.location = row.location;
            this.form.pic = row.pic;
        },
        //删除函数
        handleDelete(index, row) {
            //发起删除
            request.get("/singer/delete?id=" + row.id).then((res) => {
                if (res.success == true) {
                    this.$message({
                        message: '删除成功',
                        type: 'success'
                    });
                    //重新查询数据
                    this.query();
                } else {
                    this.$message.error('删除失败');
                }
            });
        },
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => { });
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