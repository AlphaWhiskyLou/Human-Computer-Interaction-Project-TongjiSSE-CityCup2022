<template>
  <div>
  <div class="background">
    <img :src="imgSrc" class="imgSrc"/>
  </div>
    <el-row>
      <h1>欢迎来到您的普惠网上银行</h1>
  <div class="login">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="登录" name="first">
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="身份证号" prop="name">
            <el-input v-model="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="注册" name="second">
        <register></register>
      </el-tab-pane>
    </el-tabs>
  </div>
    </el-row>
  </div>
</template>
<script>
import Register from '@/components/loginFunction/Register'
import Unity from "@/components/loginFunction//Unity"
import Unityverify from "@/components/loginFunction/Unityverify";
import {Encrypt,Decrypt} from '@/utils/encryption'
export default {
  name : 'Login',
  mounted() {
    localStorage.removeItem('token');
  },
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    return {
      imgSrc: './static/Frame.png',
      activeName: 'first',
      ruleForm: {
        name: '',
        pass: '',
        checkPass: '',
      },
      rules: {
        name: [
          { required: true, message: '请输入您的身份证', trigger: 'blur' },
          {pattern:/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入合法身份证号！', trigger: 'blur'}
        ],
        pass: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ]
      },

    };
  },
  methods: {
    //选项卡切换
    handleClick(tab, event) {
    },
    //重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    //提交表单
    submitForm(formName) {
      this.loading= true;
      let Epassword;
      let data = new FormData();
      Epassword = Encrypt(this.ruleForm.pass);
      data.append("username",this.ruleForm.name);
      data.append("password",Epassword);
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios({
            method:"post",
            url:"http://101.132.250.23:81/user/login/",
            data:data
          })
            //.get("http://101.132.250.23:81/user/login/"+this.ruleForm.name+"/"+this.ruleForm.pass)//localStorage.getItem('token'))
            .then(response => {
              if (response.data.code === 200) {
                localStorage.setItem('token',response.data.data.token);
                this.$alert('登录成功！');
                this.$router.replace({path: '/introduction'});
                console.log('登录成功');
              }
              else {
                this.$alert('登录失败!', '密码或用户名错误!');
                console.log('登录失败');
                return false;
              }
            })
        } else {
          console.log('登录失败');
          return false;
        }
      });
    },
  },
  components: { Register},
}
</script>
<style rel="stylesheet/scss" lang="scss">
body {
  margin: 0;
  padding: 0;
}
.login {
  width: 400px;
  margin: 0 auto;
}
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0;

}
.el-tabs__item {
  text-align: center;
  width: 60px;
}

.demo-ruleForm .el-form-item__label{
  color:#000000;
  font-weight:bold;
  font-size:15px
}

.background {
  width: 100%;
  height: 100%;
  padding: 0;
  margin: 0;
  /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: fixed;
  background-size: 100% 100%;
  //固定定位
}

.imgSrc{
  height: 100%;
  width: 100%;
}


//.login {
//  width: 100%;
//  height: 100%;
//  z-index: 1;
//  position: absolute;
//  text-align: center;
//}

</style>
