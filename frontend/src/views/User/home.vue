<template>
  <el-container class="home-container">
    <!--顶部-->
    <el-header style="margin-right: 15px; width: 100%">
      <span class="nav-logo">😀</span>
      <span class="head-title">欢迎用户登陆</span>
    </el-header>
    <!-- 主体 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="13%">
        <el-menu
          :default-active="$route.path"
          router
          text-color="black"
          active-text-color="red"
        >
        <el-menu-item index="/index">
          <i class="el-icon-s-home"></i>
          <span slot="title">搜索</span>
        </el-menu-item>

        <el-menu-item index="/homepage">
          <i class="el-icon-s-custom"></i>
          <span slot="title">个人信息管理</span>
        </el-menu-item>

        <el-menu-item index="/product">
          <i class="el-icon-menu"></i>
          <span slot="title">商品管理</span>
        </el-menu-item>

        <el-submenu index="/manage">
          <template slot="title">
            <i class="el-icon-menu"></i>
            申请管理
          </template>
          <el-menu-item index="/apply"><span slot="title">申请列表</span></el-menu-item>
          <el-menu-item index="/applied"><span slot="title">被申请列表</span></el-menu-item>
        </el-submenu>

        </el-menu>
      </el-aside>
      <el-main>
        <!--路由占位符-->
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>

</template>

<script>
export default {
  name: "Home",
  data() {
    return {

    };
  },
};
</script>

<style >
.nav-logo {
  position: absolute;
  padding-top: -1%;
  left: 5%;
  font-size: 40px;
}

.head-title {
  position: absolute;
  padding-top: 20px;
  left: 15%;
  font-size: 20px;
  font-weight: bold;
}

</style>
