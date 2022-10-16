<template>
    <el-main>
      <template>
        <el-carousel indicator-position="outside">
          <el-carousel-item v-for="item in imgList" :key="item.id">
            <img :src="item.idView" class="image">
          </el-carousel-item>
        </el-carousel>
      </template>
      <el-row>
        <!-- <h1>便民生活缴费</h1> -->
        <div style="font-size:large;font-weight: bold;margin-bottom: 20px;margin-top: 20px;">便民生活缴费</div>
      </el-row>
      <el-row>
        <el-col
          :span="6"
          v-for="(o, index) in name"
          :key="o"
          :offset="index > 0 ? 3 : 0"
        >
          <el-card
            :body-style="{ padding: '30px' }"
            shadow="hover"
            @click.native="logindex(index)"
            style="background-color: white"
          >
             <div style="padding: 4px;font-size: large;font-weight: bold;margin-bottom: 20px;">
              <el-link type="success" style="color:#125D98;font-weight:bolder;font-size:larger">{{ name[index] }}</el-link>
            </div>
            <img :src="sourceImg[index]" class="image" height="200px" />

          </el-card>
        </el-col>
      </el-row>
      <el-dialog title="水费缴纳" :model="false" :visible.sync="dialogFormVisibleOne">
        <el-form ref="form1" :model="form1" :rules="rule1" label-width="120px">
          <el-form-item label="金额" prop="amount">
            <el-input v-model="form1.amount" placeholder="请输入交易金额" >
            </el-input>
          </el-form-item>
          <el-form-item label="户主身份证号"  prop="userID">
            <el-input v-model="form1.userID" placeholder="请输入户主的身份证号">
            </el-input>
          </el-form-item>
          <el-form-item label="用户账户"  prop="accountID">
            <el-input v-model="form1.accountID" placeholder="请输入缴费账户">
            </el-input>
          </el-form-item>
          <el-form-item label="账户密码"  prop="password">
            <el-input v-model="form1.password" placeholder="请输入账户密码" show-password>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit1" >立即缴费</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <el-dialog title="电费缴纳" :model="false" :visible.sync="dialogFormVisibleTwo">
        <el-form ref="form2" :model="form2" :rules="rule2" label-width="120px">
          <el-form-item label="金额" prop="amount">
            <el-input v-model="form2.amount" placeholder="请输入交易金额">
            </el-input>
          </el-form-item>
          <el-form-item label="用户身份证号" prop="userID">
            <el-input v-model="form2.userID" placeholder="请输入用户的身份证号">
            </el-input>
          </el-form-item>
          <el-form-item label="用户账户"  prop="accountID">
            <el-input v-model="form2.accountID" placeholder="请输入缴费账户">
            </el-input>
          </el-form-item>
          <el-form-item label="账户密码" prop="password">
            <el-input v-model="form2.password" placeholder="请输入账户密码" show-password>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit2" >立即缴费</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <el-dialog title="煤气费缴纳" :modal="false" :visible.sync="dialogFormVisibleThree">
        <el-form ref="form3" :model="form3" :rules="rule3" label-width="120px">
          <el-form-item label="金额" prop="amount">
            <el-input v-model="form3.amount" placeholder="请输入交易金额">
            </el-input>
          </el-form-item>
          <el-form-item label="用户身份证号" prop="userID">
            <el-input v-model="form3.userID" placeholder="请输入用户的身份证号">
            </el-input>
          </el-form-item>
          <el-form-item label="用户账户" prop="accountID">
            <el-input v-model="form3.accountID" placeholder="请输入缴费账户">
            </el-input>
          </el-form-item>
          <el-form-item label="账户密码" prop="password">
            <el-input v-model="form3.password" placeholder="请输入账户密码" show-password>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit3" >立即缴费</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </el-main>
</template>

<script>
import {Encrypt,Decrypt} from '@/utils/encryption'
export default {
  name: "newUtilityFee",
  data() {
    return {
      imgList: [
        {id:0,idView:'./static/Bank1.svg'},
        {id:1,idView:'./static/Bank2.svg'},
        {id:2,idView:'./static/Bank3.svg'},
      ],
      name: ["水费缴纳", "电费缴纳", "煤气费缴纳"],
      sourceImg: [
        "/static/water.png",
        "/static/electricity.png",
        "/static/gas.png",
      ],
      dialogFormVisible: [false,false,false],
      dialogFormVisibleOne: false,
      dialogFormVisibleTwo: false,
      dialogFormVisibleThree: false,
      form1: {
        amount: '',
        userID: '',
        accountID: '',
        password:'',
        utility: '',
      },
      form2: {
        amount: '',
        userID: '',
        accountID: '',
        password:'',
        utility: '',
      },
      form3: {
        amount: '',
        userID: '',
        accountID: '',
        password:'',
        utility: '',
      },
      formLabelWidth: '120px',
      rule1:{
        amount:[
          {required: true, trigger:"blur", message: "请输入交易金额"},
          {pattern:/^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$/, message: '请输入合法金额！', trigger: 'blur'}
        ],
        userID:[
          {required:true, trigger:"blur", message: "请输入身份证号"},
          {pattern:/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入合法身份证号！', trigger: 'blur'}
        ],
        accountID:[
          {required:true, trigger:"blur", message: "请输入账户名"},
        ],
        password:[
          {required:true, trigger:"blur", message: "请输入密码"},
        ],
      },rule2:{
        amount:[
          {required: true, trigger:"blur", message: "请输入交易金额"},
          {pattern:/^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$/, message: '请输入合法金额！', trigger: 'blur'}
        ],
        userID:[
          {required:true, trigger:"blur", message: "请输入身份证号"},
          {pattern:/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入合法身份证号！', trigger: 'blur'}
        ],
        accountID:[
          {required:true, trigger:"blur", message: "请输入账户名"},
        ],
        password:[
          {required:true, trigger:"blur", message: "请输入密码"},
        ],
      },rule3:{
        amount:[
          {required: true, trigger:"blur", message: "请输入交易金额"},
          {pattern:/^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$/, message: '请输入合法金额！', trigger: 'blur'}
        ],
        userID:[
          {required:true, trigger:"blur", message: "请输入身份证号"},
          {pattern:/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入合法身份证号！', trigger: 'blur'}
        ],
        accountID:[
          {required:true, trigger:"blur", message: "请输入账户名"},
        ],
        password:[
          {required:true, trigger:"blur", message: "请输入密码"},
        ],
      },
    }
  },
  methods: {
    logindex(index){
      console.log(this.dialogFormVisible[index]);
      if(index==0){
        this.dialogFormVisibleOne=true;
      }else if(index==1){
        this.dialogFormVisibleTwo=true;
      }else{
        this.dialogFormVisibleThree=true;
      }
    },
    getBackground(index) {
      return {
        backgroundImage: "url('" + this.sourceImg[index] + "')",
        backgroundSize: "contain",
        backgroundRepeat: "no-repeat",
      };
    },
    onSubmit1() { //Water Fee
      let formdata = new FormData();
      let Eaccount = Encrypt(this.form1.accountID);
      let Epassword = Encrypt(this.form1.password);
      formdata.append("userID",this.form1.userID);
      formdata.append("utilityBalance",this.form1.amount);
      formdata.append("functionID","4");
      formdata.append("accountID",Eaccount);
      formdata.append("password",Epassword);
      //TODO:利用后端传上来的Token获取真实的UserID信息
      this.$axios({
        method: "post",
        url: "http://101.132.250.23:81/utility/utilityPayment/",
        data: formdata,
      })
        //.post("http://101.132.250.23:81/utility/utilityPayment/",formdata)
        .then((response) => {
          if (response.data.code === 200) {
            this.form1={
              amount: '',
              userID: '',
              accountID: '',
              password:'',
              utility: '',
            }
            console.log('submit!')
            this.$alert('缴费成功！', {
              confirmButtonText: '确定',
            });
          }
          else{
            this.$alert('缴费失败！', {
              confirmButtonText: '确定',
            });
          }
        })
    },onSubmit2() { //Electricity Fee
      let formdata1 = new FormData();
      let Eaccount = Encrypt(this.form2.accountID);
      let Epassword = Encrypt(this.form2.password);
      formdata1.append("userID",this.form2.userID);
      formdata1.append("utilityBalance",this.form2.amount);
      formdata1.append("functionID","5");
      formdata1.append("accountID",Eaccount);
      formdata1.append("password",Epassword);
      this.$axios
        .post("http://101.132.250.23:81/utility/utilityPayment/",formdata1)
        .then(response => {
          if (response.data.code === 200) {
            this.form2={
              amount: '',
              userID: '',
              accountID: '',
              password:'',
              utility: '',
            }
            console.log('submit!')
            this.$alert('缴费成功！', {
              confirmButtonText: '确定',
            });
          }
          else{
            this.$alert('缴费失败！', {
              confirmButtonText: '确定',
            });
          }
        })
    },onSubmit3() {  //Gas Fee
      let formdata2 = new FormData();
      let Eaccount = Encrypt(this.form3.accountID);
      let Epassword = Encrypt(this.form3.password);
      formdata2.append("userID",this.form3.userID);
      formdata2.append("utilityBalance",this.form3.amount);
      formdata2.append("functionID","6");
      formdata2.append("accountID",Eaccount);
      formdata2.append("password",Epassword);
      this.$axios
        .post("http://101.132.250.23:81/utility/utilityPayment/",formdata2)
        .then(response => {
          if (response.data.code === 200) {
            this.form3={
              amount: '',
              userID: '',
              accountID: '',
              password:'',
              utility: '',
            }
            console.log('submit!')
            this.$alert('缴费成功！', {
              confirmButtonText: '确定',
            });
          }
          else{
            this.$alert('缴费失败！', {
              confirmButtonText: '确定',
            });
          }
        })
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
        } else {
          console.log('error submit!!');
          return false;
        }
      })
    }
  },
};
</script>

<style scoped>
.el-header,
.el-footer {
  background-color: #fff7e8;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: white;
  color: #333;
  text-align: center;
  line-height: 25px;
}

.el-main {
  background-color: #fff7e8;
  color: black;
  text-align: center;
  line-height: 20px;
  font-family: "PingFang SC";
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 50%;
  display: run-in;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
.el-carousel__item h3 {
  color: #fff7e8;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #fff7e8;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #fff7e8;
}
</style>
