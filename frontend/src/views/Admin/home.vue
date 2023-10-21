<template>
  <el-container class="home-container">
    <!--顶部-->
    <el-header style="margin-right: 15px; width: 100%">
      <span class="nav-logo">😀</span>
      <span class="head-title">欢迎管理员登陆</span>
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
        <el-submenu>
          <template slot="title">
            <i class="el-icon-menu"></i>
            用户管理
          </template>
          <el-menu-item index="/customer0"><span slot="title">待审核</span></el-menu-item>
          <el-menu-item index="/customer1"><span slot="title">审核通过</span></el-menu-item>
          <el-menu-item index="/customer2"><span slot="title">审核不通过</span></el-menu-item>
        </el-submenu>

        <el-submenu>
          <template slot="title">
            <i class="el-icon-menu"></i>
            商品管理
          </template>
          <el-menu-item index="/product0"><span slot="title">待审核</span></el-menu-item>
          <el-menu-item index="/product1"><span slot="title">审核通过</span></el-menu-item>
          <el-menu-item index="/product2"><span slot="title">审核不通过</span></el-menu-item>
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
