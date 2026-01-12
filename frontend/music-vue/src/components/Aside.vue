<template>
    <div>
        <!-- 导航菜单 -->
        <el-menu style="width: 250px; min-height: calc(100vh - 50px)" default-active="2" class="el-menu-vertical-demo"
            @open="handleOpen" @close="handleClose" :default-openeds="[path]" router>
            <el-submenu index="1">
                <template slot="title">
                    <i class="el-icon-location"></i>
                    <span>用户模块</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item index="/consumer_list">用户列表</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
            <el-submenu index="2">
                <template slot="title">
                    <i class="el-icon-menu"></i>
                    <span>歌手模块</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item index="/singer_list">歌手列表</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
            <el-submenu index="3">
                <template slot="title">
                    <i class="el-icon-document"></i>
                    <span>歌单模块</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item index="/songList_list">歌单列表</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
            <el-submenu index="4">
                <template slot="title">
                    <i class="el-icon-setting"></i>
                    <span>歌曲模块</span>
                </template>
                <el-menu-item-group>
                    <el-menu-item index="/song_list">歌曲列表</el-menu-item>
                </el-menu-item-group>
            </el-submenu>
        </el-menu>
    </div>
</template>

<script>
export default {
    data() {
        return {
            //path变量
            path: this.$route.path,
        };
    },
    methods: {
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
    },
};
</script>