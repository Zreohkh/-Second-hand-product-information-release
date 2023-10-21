<template>
<el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="id" prop="cid" v-if="false">
      <el-input readonly v-model="ruleForm.cid" />
 </el-form-item>
  <el-form-item label="审核状态" prop="rcon">
     <el-input :value="formattedRcon()" :disabled="true"></el-input>
  </el-form-item>
  <el-form-item label="用户名" prop="cname">
    <el-input v-model="ruleForm.cname"></el-input>
  </el-form-item>
  <el-form-item label="密码" prop="cpass">
    <el-input v-model="ruleForm.cpass"></el-input>
  </el-form-item>
  <el-form-item label="邮箱" prop="email">
    <el-input v-model="ruleForm.email"></el-input>
  </el-form-item>
  <el-form-item label="QQ" prop="qq">
    <el-input v-model="ruleForm.qq"></el-input>
  </el-form-item>
  <el-form-item label="微信号" prop="wechat">
    <el-input v-model="ruleForm.wechat"></el-input>
  </el-form-item>
  <el-form-item label="电话" prop="phone">
    <el-input v-model="ruleForm.phone"></el-input>
  </el-form-item>
  <el-form-item label="个人描述" prop="des">
    <el-input type="textarea" v-model="ruleForm.des"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="submitForm('ruleForm')">修改</el-button>
    <el-button @click="resetForm('ruleForm')">重置</el-button>
  </el-form-item>
</el-form>
</template>
<script>
  import { customerModify,customerMes } from '@/api/customer';
  export default {
    data() {
      return {
        ruleForm: {
          cid: this.$store.state.user.cid,
          cname: '',
          cpass: '',
          email: '',
          des: '',
          qq: '',
          wechat: '',
          phone: '',
          rcon:'',
        },
        rules: {
          cname: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 1, max: 32, message: '长度在 1 到 32 个字符', trigger: 'blur' }
          ],
          cpass: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 1, max: 32, message: '长度在 1 到 32 个字符', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' },
            { min: 1, max: 32, message: '长度在 1 到 32 个字符', trigger: 'blur' }
          ],
          des: [
            { min: 1, max: 256, message: '长度在 1 到 256 个字符', trigger: 'blur' }
          ],
          qq: [
            { min: 1, max: 32, message: '长度在 1 到 32 个字符', trigger: 'blur' }
          ],
          wechat: [
            { min: 1, max: 32, message: '长度在 1 到 32 个字符', trigger: 'blur' }
          ],
          phone: [
            { min: 1, max: 32, message: '长度在 1 到 32 个字符', trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      formattedRcon() {
      switch (this.ruleForm.rcon) {
        case 0:
          return "审核中";
        case 2:
          return "审核不通过,请重新修改";
        // 其他情况...
        default:
          return "";
        }
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            const copy = Object.assign(this.ruleForm);
            copy.rcon=0;
            customerModify(copy).then(res=>{
              let code = res.data.code;
              let mes = res.data.message;
              if(code===200){
                let user = res.data.data;
                this.ruleForm.rcon = 0;
                this.$store.commit('SET_USER', user);
              }
              this.$message(mes);
            });
            
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm() {
          customerMes({
          'id': this.$store.state.user.cid
          })
          .then(response => {
            this.ruleForm = response.data.data;
          })
          .catch(error => {
            console.error('Error fetching data:', error);
          });
    },
   },
    mounted() {
      customerMes({
      'id': this.$store.state.user.cid
      })
      .then(response => {
        let data = response.data.data
        this.ruleForm = data;
        this.$store.commit('SET_USER', data);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }
}
</script>