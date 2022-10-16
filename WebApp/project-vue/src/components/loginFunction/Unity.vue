<template>
  <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="130px" class="demo-ruleForm">
    <el-form-item label="身份证号" prop="name">
      <el-input v-model="ruleForm.name"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="pass">
      <el-input type="password" v-model="ruleForm.pass" auto-complete="off"></el-input>
    </el-form-item>
    <el-form-item label="老人身份证号" prop="oldname">
      <el-input v-model="ruleForm.oldname"></el-input>
    </el-form-item>
    <el-radio-group v-model="ruleForm.radio">
      <el-radio label="dad">父亲</el-radio>
      <el-radio label="mom">母亲</el-radio>
    </el-radio-group>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">绑定</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import {Encrypt,Decrypt} from '@/utils/encryption'
export default {
  name : 'Unity',
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
      activeName: 'third',
      ruleForm: {
        name: '',
        pass: '',
        oldname: '',
        radio:''
      },
      rules: {
        name: [
          { required: true, message: '请输入您的身份证', trigger: 'blur' },
          { min: 18, max: 18, message: '18位身份证号码', trigger: 'blur' }
        ],
        pass: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        oldname: [
          { required: true, message: '请输入您亲属的身份证', trigger: 'blur' },
          { min: 18, max: 18, message: '18位身份证号码', trigger: 'blur' }
        ],
        radio:{required: true, message:'请选择您亲属的身份'}
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios
            .get("http://101.132.250.23:81/user/unity/"+this.ruleForm.name+"/"+this.ruleForm.pass+"/"+this.ruleForm.oldname+"/"+this.ruleForm.radio)//localStorage.getItem('token'))
            .then(response => {
              if (response.data.code === 200) {
                this.$alert('绑定成功！');
                console.log('注册成功');
              }
              else if(response.data.code ===503){
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
