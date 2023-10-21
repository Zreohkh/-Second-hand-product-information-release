<template>
    <div class="login clearfix">
        <el-row type="flex" justify="center">
          <el-form class="login-container" label-position="left" label-width="0px">
            <h3 class="login_title">注册账号</h3>
            <hr>
            <el-form-item>
              <el-input type="text" v-model="user.username" placeholder="请输入用户名" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item>
              <el-input type="text" v-model="user.email" placeholder="请输入邮箱"></el-input>
            </el-form-item>
            <el-form-item>
              <el-input v-model="user.password" show-password placeholder="请输入密码"></el-input>
            </el-form-item>
            <el-form-item style="width: 112%">
              <el-button type="primary" style="width: 100%; border: none" @click="doRegister()">注册账号</el-button>
            </el-form-item>
          </el-form>
        </el-row>
      
    </div>
  </template>
   
  <script>
  import { customerRegister } from '@/api/customer';
  export default {
    name: "login",
    data() {
      return {
        user: {
          username: "",
          email: "",
          password: ""
        },
      };
    },
    created() {
      // console.log($);
      // console.log("1111");
    },
    methods: {
      doRegister() {
        if (!this.user.username) {
          this.$message.error("请输入用户名！");
          return;
        } else if (!this.user.email) {
          this.$message.error("请输入邮箱！");
          return;
        } else if (this.user.email != null) {
          var reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
          if (!reg.test(this.user.email)) {
            this.$message.error("请输入有效的邮箱！");
          } else if (!this.user.password) {
            this.$message.error("请输入密码！");
            return;
          } else {
            // this.$router.push({ path: "/" }); //无需向后台提交数据，方便前台调试
            var _this = this;
            customerRegister( {
                name: this.user.username,
                email: this.user.email,
                password: this.user.password
              })
              .then(res => {
                console.log("输出response.data", res.data);
                if (res.data.code === 200) {
                //存储token
                let data=res.data.data;
                let token=data.token;
                let user=data.user;
                _this.$store.commit('SET_TOKENN', token);
                _this.$store.commit('SET_USER', user);
                console.log("Redirecting to '/'");
                _this.$router.replace({ path: '/' });
                } 
                else {
                  alert("您输入的用户名已存在！");
                }
              });
          }
        }
      }
    }
  };
  </script>
   
  <!-- Add "scoped" attribute to limit CSS to this component only -->
  <style scoped>
  .login {
    width: 100%;
    height: 740px;
    background: url("../../assets/img/bingbing1.jpg") no-repeat;
    background-size: cover;
    overflow: hidden;
  }
   
  h3 {
    color: #0babeab8;
    font-size: 24px;
  }
  hr {
    background-color: #444;
    margin: 20px auto;
  }
  .login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 90px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}
  .el-button {
    width: 80%;
    margin-left: -50px;
  }
  </style>
