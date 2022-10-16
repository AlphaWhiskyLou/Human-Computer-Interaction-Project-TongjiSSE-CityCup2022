<template>
  <el-main>
    <template>
      <el-carousel indicator-position="outside">
        <el-carousel-item v-for="item in imgList" :key="item.id">
          <img :src="item.idView" class="image">
        </el-carousel-item>
      </el-carousel>
    </template>
    <el-form ref="form1" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="金额" prop="amount">
        <el-input v-model="form.amount" placeholder="请选择交易金额" >
        </el-input>
      </el-form-item>
      <el-form-item label="用户身份证号"  prop="userID">
        <el-input v-model="form.userID" placeholder="请输入用户的身份证号">
        </el-input>
      </el-form-item>
      <el-form-item label="用户账户"  prop="accountID">
        <el-input v-model="form.accountID" placeholder="请输入用户的账户">
        </el-input>
      </el-form-item>
      <el-form-item label="账户密码"  prop="password">
        <el-input v-model="form.password" placeholder="请输入用户的账户密码" show-password>
        </el-input>
      </el-form-item>
      <el-form-item label="转入用户账户" prop="payeeAccount">
        <el-input v-model="form.payeeAccount" placeholder="请输入转入账户" ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit" >立即提交</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </el-main>
</template>

<script>
import {Encrypt,Decrypt} from '@/utils/encryption'
export default {
  data() {
    var validateAccount = (rule, value, callback) => {
      //验证用户的账户是否存在
      this.$axios.get('http://localhost:81/api/account/' + value).then(res => {
        if (res.data.data.length === 0) {
          callback(new Error('账户不存在'));
        } else {
          callback();
        }
      });
    };
    return {
      code:'',
      imgList: [
        {id:0,idView:'./static/Bank1.svg'},
        {id:1,idView:'./static/Bank2.svg'},
        {id:2,idView:'./static/Bank3.svg'},
      ],
      form: {
        amount: '',
        userID: '',
        accountID: '',
        pwd: '',
        payeeAccount: '',
      }
      , rules: {
        amount: [
          {required: true, message: '请选择交易金额', trigger: 'blur'},
          {pattern:/^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$/, message: '请输入合法金额！', trigger: 'blur'}
        ],
        userID: [
          {required: true, message: '请输入用户的身份证号', trigger: 'blur'},
          {pattern:/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入合法身份证号！', trigger: 'blur'}
        ],
        accountID: [
          {required: true, message: '请输入用户的账户', trigger: 'blur'},
          //{pattern:/^[0-9]{6}$/,message: '请输入合法账户！', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入转入账户密码', trigger: 'blur'},
          //{pattern:/^[0-9]{6}$/,message: '请输入合法密码！', trigger: 'blur'}
        ],
        payeeAccount: [
          {required: true, message: '请输入转入账户', trigger: 'blur'},
          //{pattern:/^[0-9]{6}$/,message: '请输入合法账户！', trigger: 'blur'}
        ],
      },
    }
  },
  methods: {
    onSubmit() {
      let Epassword;
      let data = new FormData();
      Epassword = Encrypt(this.form.password)
      data.append("userID",this.form.userID);
      data.append("accountID",this.form.accountID);
      data.append("payeeAccountID",this.form.payeeAccount);
      data.append("functionID","1");
      data.append("amount",this.form.amount);
      data.append("isFinished","0");
      data.append("password",Epassword);
      //TODO:确认UserUnity表中是否存在该用户
      this.$axios({
        method:"get",
        url:"http://101.132.250.23:81/assistance/getUnityIDByUser/"+this.form.userID,
        headers:{
          "token": localStorage.getItem("token"),
        }
      })
        //.get("http:/101.132.250.23:81/assistance/getUnityIDByUserID/310104200101036080/"+this.form.userID)//userID/unityID
      .then(response=>{
        if(response.data.code===200){
          this.$axios({
            method:"post",
            url:"http://101.132.250.23:81/assistance/addAssistanceRequest/",
            data:data,
            headers: {
              "token": localStorage.getItem("token")
            }
          })
            //.post("http://101.132.250.23:81/assistance/addAssistanceRequest/",data)
            .then(response => {
              if (response.data.code === 200) {
                this.$axios.get("http://101.132.250.23:81/MessageCenter/SendMessageByQueue/"+this.form.userID+"/"+this.form.userID)
                this.form={
                  amount: '',
                  userID: '',
                  accountID: '',
                  payeeAccount: '',
                }
                console.log('submit!')
                this.$confirm('转账协助请求发起成功', {
                  confirmButtonText: '确定',
                });
              }
              else{
                this.$alert('转账协助请求发起失败，请检查您输入的信息！', {
                  confirmButtonText: '确定',
                });
              }
            })
        }
        else{
          this.$alert('转账协助请求发起失败，请确认您是否有操作权限！', {
            confirmButtonText: '确定',
          });
        }
      })

    }
  }
}
</script>
<style>
.el-carousel__item h3 {
  color: #fff7e8;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
  text-align: center;
}

.image {
  width: 50%;
  display: run-in;
}

.el-carousel__item:nth-child(2n) {
  background-color: #fff7e8;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #fff7e8;
}
</style>
