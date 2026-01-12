<template>
    <div>
        <!-- 顶部搜索栏 -->
        <p>
            <el-input v-model="name" placeholder="请输入歌曲名" clearable style="width: 300px"></el-input>

            <el-button type="success" @click="query">查询</el-button>
            <el-button type="primary" @click="handleAdd">新增</el-button>
        </p>

        <!-- 表格 -->
        <el-table :data="tableData" style="width: 100%">
            <el-table-column prop="id" label="ID" width="80"></el-table-column>

            <el-table-column prop="name" label="歌曲名" width="200"></el-table-column>

            <el-table-column label="封面" width="140">
                <template slot-scope="scope">
                    <img v-if="scope.row.pic" :src="getFullUrl(scope.row.pic)" style="width: 80px; height: 80px" />
                </template>
            </el-table-column>

            <el-table-column label="听歌" width="220">
                <template slot-scope="scope">
                    <audio v-if="scope.row.url" :src="getFullUrl(scope.row.url)" controls style="width: 180px" />
                </template>
            </el-table-column>

            <el-table-column prop="introduction" label="简介"></el-table-column>

            <el-table-column label="操作" width="220">
                <template slot-scope="scope">
                    <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>

                    <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.id)">
                        <el-button slot="reference" size="mini" type="danger">删除</el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!-- 分页 -->
        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
            :page-size="size" :total="total" :page-sizes="[4, 10, 20, 50]"
            layout="total, sizes, prev, pager, next, jumper">
        </el-pagination>

        <!-- 新增/编辑弹窗 -->
        <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="40%">
            <el-form ref="form" :model="form" label-width="80px">
                <el-form-item label="歌曲名">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>

                <el-form-item label="简介">
                    <el-input type="textarea" v-model="form.introduction"></el-input>
                </el-form-item>

                <!-- 上传封面 -->
                <el-form-item label="封面">
                    <el-upload class="avatar-uploader" action="http://localhost:8085/file/songPic/upload"
                        :show-file-list="false" :on-success="handlePicSuccess">
                        <img v-if="picUrl" :src="picUrl" class="avatar" style="width: 100px; height: 100px" />
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>

                <!-- 上传歌曲文件 -->
                <el-form-item label="歌曲文件">
                    <el-upload action="http://localhost:8085/file/songFile/upload" :on-success="handleMp3Success"
                        :limit="1">
                        <el-button type="primary">上传 MP3</el-button>
                    </el-upload>

                    <div v-if="form.url" style="margin-top: 10px; color: #666">
                        当前文件：{{ form.url }}
                    </div>
                </el-form-item>
            </el-form>

            <span slot="footer">
                <el-button @click="dialogVisible = false">取消</el-button>
                <el-button type="primary" @click="submit">确定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
import request from "../utils/request";

export default {
    data() {
        return {
            tableData: [],
            name: "",
            pageNum: 1,
            size: 4,
            total: 0,

            dialogVisible: false,
            dialogTitle: "",
            picUrl: "",

            form: {
                id: "",
                name: "",
                introduction: "",
                pic: "",
                url: "",
            },
        };
    },

    created() {
        this.query();
    },

    methods: {
        getFullUrl(path) {
            if (!path) return "";
            return "http://localhost:8085" + path;
        },

        handlePicSuccess(res) {
            this.form.pic = res.path;
            this.picUrl = this.getFullUrl(res.path);
        },

        handleMp3Success(res) {
            this.form.url = res.path;
        },

        /* ------- 新增按钮 -------- */
        handleAdd() {
            this.dialogTitle = "新增歌曲";
            this.dialogVisible = true;
            this.form = { id: "", name: "", introduction: "", pic: "", url: "" };
            this.picUrl = "";
        },

        /* ------- 编辑按钮 -------- */
        handleEdit(row) {
            this.dialogTitle = "编辑歌曲";
            this.dialogVisible = true;
            this.form = Object.assign({}, row);
            this.picUrl = this.getFullUrl(row.pic);
        },

        /* ------- 查询 -------- */
        query() {
            request
                .get("/song/list", {
                    params: {
                        name: this.name,
                        pageNum: this.pageNum,
                        size: this.size,
                    },
                })
                .then((res) => {
                    this.tableData = res.list;
                    this.total = res.total;
                });
        },

        handleSizeChange(val) {
            this.size = val;
            this.query();
        },

        handleCurrentChange(val) {
            this.pageNum = val;
            this.query();
        },

        /* ------- 提交 -------- */
        submit() {
            request.post("/song/update", this.form).then((res) => {
                if (res.success) {
                    this.$message.success("操作成功");
                    this.dialogVisible = false;
                    this.query();
                } else {
                    this.$message.error("操作失败");
                }
            });
        },

        /* ------- 删除 -------- */
        handleDelete(id) {
            request.get("/song/delete?id=" + id).then((res) => {
                if (res.success) {
                    this.$message.success("删除成功");
                    this.query();
                } else {
                    this.$message.error("删除失败");
                }
            });
        },
    },
};
</script>

<style>
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    overflow: hidden;
    width: 100px;
    height: 100px;
}
</style>