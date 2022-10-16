<template>
  <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="身份证号" prop="name">
      <el-input v-model="ruleForm.name"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="pass">
      <el-input type="password" v-model="ruleForm.pass" auto-complete="off"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="checkPass">
      <el-input type="password" v-model="ruleForm.checkPass" auto-complete="off"></el-input>
    </el-form-item>
    <el-form-item label="姓名" prop="username">
      <el-input type="name" v-model="ruleForm.username" auto-complete="off"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="gender">
      <el-radio-group v-model="ruleForm.gender">
        <el-radio label="男">男</el-radio>
        <el-radio label="女">女</el-radio>
      </el-radio-group>
    </el-form-item>

    <el-form-item label="年龄" prop="age">
      <el-input type="age" v-model="ruleForm.age" auto-complete="off"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import {Encrypt,Decrypt} from '@/utils/encryption'
export default {
  name : 'Register',
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
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      activeName: 'second',
      ruleForm: {
        name: '',
        pass: '',
        checkPass: '',
        username:'',
        gender:'',
        age:'',
      },
      rules: {
        name: [
          { required: true, message: '请输入您的身份证', trigger: 'blur' },
          {pattern:/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入合法身份证号！', trigger: 'blur'}
        ],
        pass: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ],
        username: [
          { required: true, message: '请输入您的名字', trigger: 'blur' },
        ],
        gender: [
          { required: true, message: '请输入您的性别'},
        ],
        age: [
          { required: true, message: '请输入您的年龄', trigger: 'blur' },
          { min: 0, max: 200, message: '请输入您的正确年龄', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let formdata = new FormData();
          let Epassword = Encrypt(this.ruleForm.pass);
          formdata.append("userID",this.ruleForm.name);
          formdata.append("userPassword",Epassword);
          formdata.append("name",this.ruleForm.username);
          formdata.append("gender",this.ruleForm.gender);
          formdata.append("age",this.ruleForm.age);
          //formdata.append("userPassword",this.ruleForm.pass);
          this.$axios({
            method: 'post',
            url:"http://101.132.250.23:81/user/register/",
            data: formdata
          })
            //.get("http://101.132.250.23:81/user/register/"+this.ruleForm.name+"/"+this.ruleForm.pass)//localStorage.getItem('token'))
            .then(response => {
              if (response.data.code === 200) {
                localStorage.setItem('token',response.data.data.token);
                this.$alert('注册成功！');
                this.$router.replace({path: '/coreFunction'});
                console.log('注册成功');
              }
              else if(response.data.code ===503){
                this.$alert('此身份证已被注册！');
                console.log('注册失败');
                return false;
              }
              else {
                this.$alert('注册失败！');
                console.log('注册失败');
                return false;
              }
            })
        } else {
          console.log('注册失败');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>
