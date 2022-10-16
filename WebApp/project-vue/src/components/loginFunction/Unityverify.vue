<template>
  <el-form :model="ruleForm1" :rules="rules" ref="ruleForm" label-width="130px" class="demo-ruleForm">
    <el-form-item label="身份证号" prop="name">
      <el-input v-model="ruleForm1.name"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="pass">
      <el-input type="password" v-model="ruleForm1.pass" auto-complete="off"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm1('ruleForm1')">确认绑定</el-button>
      <el-button @click="resetForm('ruleForm1')">重置</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import {Encrypt,Decrypt} from '@/utils/encryption'
export default {
  name : 'Unityverify',
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm1.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    return {
      activeName: 'fourth',
      ruleForm1: {
        name: '',
        pass: '',
        oldname: '',
        relationship:''
      },
      rules1: {
        name: [
          { required: true, message: '请输入您的身份证', trigger: 'blur' },
          { min: 18, max: 18, message: '18位身份证号码', trigger: 'blur' }
        ],
        pass: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    submitForm1(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
            .get("http://101.132.250.23:81/user/unityverify/"+this.ruleForm1.name+"/"+this.ruleForm1.pass)//localStorage.getItem('token'))
            .then(response => {
              if (response.data.code === 200) {
                this.$alert('验证成功！');
                console.log('验证成功');
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
