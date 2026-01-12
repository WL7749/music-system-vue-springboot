<template>
    <div>
        <p>
            <el-input v-model="title" placeholder="请输入歌单名称" clearable style="width: 300px" />
            <el-button type="success" @click="query">查询</el-button>
            <el-button type="primary" @click="handleAdd">新增</el-button>
        </p>

        <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80" />
            <el-table-column prop="title" label="歌单名" width="220" />
            <el-table-column prop="pic" label="封面" width="140">
                <template slot-scope="scope">
                    <img v-if="scope.row.pic" :src="getImageUrl(scope.row.pic)" style="width: 80px; height: 80px" />
                </template>
            </el-table-column>
            <el-table-column prop="style" label="风格" width="150" />
            <el-table-column prop="introduction" label="简介" />

            <el-table-column label="操作" width="200">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
                    <el-popconfirm title="确定删除该歌单吗？" @confirm="handleDelete(scope.row.id)">
                        <el-button slot="reference" size="mini" type="danger">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
            :page-size="size" :total="total" :page-sizes="[4, 10, 20, 50]"
            layout="total, sizes, prev, pager, next, jumper" />

        <!-- 弹窗 -->
        <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="40%">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="歌单名">
                    <el-input v-model="form.title" />
                </el-form-item>
                <el-form-item label="风格">
                    <el-input v-model="form.style" />
                </el-form-item>
                <el-form-item label="简介">
                    <el-input type="textarea" :rows="3" v-model="form.introduction" />
                </el-form-item>

                <el-form-item label="封面">
                    <el-upload class="avatar-uploader" action="http://localhost:8085/file/songListPic/upload"
                        :show-file-list="false" :on-success="handlePicSuccess" :before-upload="beforePicUpload">
                        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
            </el-form>

            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import request from '../utils/request'

export default {
    data() {
        return {
            tableData: [],
            dialogVisible: false,
            dialogTitle: '',
            title: '',
            pageNum: 1,
            size: 4,
            total: 0,
            imageUrl: '',
            form: {
                id: '',
                title: '',
                pic: '',
                introduction: '',
                style: ''
            }
        }
    },
    created() {
        this.query()
    },
    methods: {
        // 拼图片完整 URL
        getImageUrl(path) {
            if (!path) return ''
            return 'http://localhost:8085' + path
        },

        // 上传封面成功
        handlePicSuccess(res, file) {
            // 后端返回 { success: true, path: '/music/img/songListPic/xxx.png' }
            this.form.pic = res.path
            this.imageUrl = this.getImageUrl(res.path)
        },

        beforePicUpload(file) {
            const isImg = file.type === 'image/jpeg' || file.type === 'image/png'
            const isLt5M = file.size / 1024 / 1024 < 5
            if (!isImg) {
                this.$message.error('只能上传 JPG/PNG 图片')
            }
            if (!isLt5M) {
                this.$message.error('图片大小不能超过 5MB')
            }
            return isImg && isLt5M
        },

        // 查询
        query() {
            request
                .get('/songList/list', {
                    params: {
                        title: this.title,
                        pageNum: this.pageNum,
                        size: this.size
                    }
                })
                .then(res => {
                    this.tableData = res.list
                    this.total = res.total
                })
        },

        handleSizeChange(val) {
            this.size = val
            this.query()
        },
        handleCurrentChange(val) {
            this.pageNum = val
            this.query()
        },

        handleAdd() {
            this.dialogTitle = '新增歌单'
            this.dialogVisible = true
            this.form = {
                id: '',
                title: '',
                pic: '',
                introduction: '',
                style: ''
            }
            this.imageUrl = ''
        },

        handleEdit(row) {
            this.dialogTitle = '编辑歌单'
            this.dialogVisible = true
            this.form = Object.assign({}, row)
            this.imageUrl = this.getImageUrl(row.pic)
        },

        submit() {
            request.post('/songList/update', this.form).then(res => {
                if (res.success) {
                    this.$message.success('操作成功')
                    this.dialogVisible = false
                    this.query()
                } else {
                    this.$message.error('操作失败')
                }
            })
        },

        handleDelete(id) {
            request
                .get('/songList/delete', { params: { id } })
                .then(res => {
                    if (res.success) {
                        this.$message.success('删除成功')
                        this.query()
                    } else {
                        this.$message.error('删除失败')
                    }
                })
        }
    }
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
    border-color: #409eff;
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