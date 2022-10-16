<template>
<div id="coreFunc">
  <el-container>
<!--    <el-aside width="500px" style="height: 100%">-->

<!--    </el-aside>-->

    <el-main>

        <div style="font-size:large;font-weight: bold;">查看账户信息</div>

<el-row>
  <el-col :span="12">
  <div class="grid-content bg-purple" style="padding-top: 50px;">
  <div>
          <el-select v-model="value" placeholder="请选择您要查看的账户" style="margin-top:20px" no-match-text="请先进行网上开户！">
        <el-option
          v-for="item in accountList"
          :key="item.accountID"
          :label="item.accountID"
          :value="item.accountID">
        </el-option>
      </el-select>
      <el-button type="primary" icon="el-icon-search" size="mini" style="margin-left: 3px" @click="searchClick">搜索</el-button>

  </div>
  <div>
          <el-image style="width: 200px; height: 200px" src="static/bankAccount.png">
          </el-image>

  </div>
  </div>
  </el-col>
  <el-col :span="12">
  <div class="grid-content bg-purple-light">
          <el-row>
      <el-carousel height="350px" direction="horizontal" :autoplay="true" arrow="never">
        <el-carousel-item v-for="item in 1" :key="item" style="display: flex;align-items: center;background-color: transparent;">

          <el-descriptions :column="1" direction="horizontal" labelClassName="my-label" contentClassName="my-label" align="center"  >
            <el-descriptions-item  v-if="bankAccountInfo.cardId!=''"
              label="储蓄类"
              labelStyle="color: green;border:1px solid; border-radius: 6px ;display: inline-block; padding: 0 0.15em">{{bankAccountInfo.cardId}}</el-descriptions-item>
            <el-descriptions-item v-if="bankAccountInfo.cardId!=''"
             label="开户时间" label-class-name="my-label" align="center">{{bankAccountInfo.openDate}}</el-descriptions-item>
            <el-descriptions-item v-if="bankAccountInfo.cardId!=''"
             label="状态" labelClassName="my-label" >
              <el-tag size="small">{{bankAccountInfo.state}}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item v-if="bankAccountInfo.cardId!=''"
             label="账户余额" label-class-name="my-label" align="center">{{bankAccountInfo.balance}}</el-descriptions-item>
            <el-descriptions-item v-if="bankAccountInfo.cardId===''"
             label="提示" align="center" >请在左侧选择要查看的账户后点击搜索！</el-descriptions-item>
          </el-descriptions>
          <!-- <h3 class="medium">{{ item }}</h3> -->
        </el-carousel-item>
      </el-carousel>
      </el-row>
  </div></el-col>
</el-row>
<!--      <el-carousel indicator-position="outside" height="250px">-->
<!--        <el-carousel-item v-for="item in imgList" :key="item.id">-->
<!--          <img :src="item.idView" class="image">-->
<!--        </el-carousel-item>-->
<!--      </el-carousel>-->

      <el-row>
                <div style="font-size:large;font-weight: bold;margin-bottom: 20px;">基础服务</div>

        <!-- <h1>基础服务</h1> -->
      </el-row>
      <el-row>
        <el-col
          :span="6"
          v-for="(o, index) in name"
          :key="o"
          :offset="index > 0 ? 3 : 0"
        >
          <el-card
            :body-style="{ padding: '0px' }"
            shadow="hover"
            @click.native="logindex(index)"
            style="background-color: white"
          >
            <div style="padding-top: 14px">
              <el-link type="success" style="font-size:large">{{ name[index] }}</el-link>
            </div>
                        <img :src="sourceImg[index]" class="image" height="160px" />
              <div class="bottom clearfix">
              </div>

          </el-card>
        </el-col>
      </el-row>

      <el-dialog title="取款" :modal="false" :visible.sync="dialogFormVisibleOne">
      <el-form :model="form1" :rules="rules2" ref="form1" class="demo-ruleForm">
        <el-form-item label="取款金额" prop="money" :label-width="formLabelWidth">
          <el-input v-model="form1.money" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="输入密码" prop="password" :label-width="formLabelWidth">
          <el-input type="password" v-model="form1.password" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('form1'),dialogFormVisibleOne = false">取 消</el-button>
        <el-button type="primary" @click="submitForm1('form1')">确 定</el-button>
      </div>
      </el-dialog>

      <el-dialog title="存款" :modal="false" :visible.sync="dialogFormVisibleTwo">
      <el-form :model="form2"  :rules="rules2" ref="form2" class="demo-ruleForm">
        <el-form-item label="存款金额" prop="money" :label-width="formLabelWidth">
          <el-input v-model="form2.money" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="输入密码" prop="password" :label-width="formLabelWidth">
          <el-input tye="password" v-model="form2.password" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('form2'),dialogFormVisibleTwo = false">取 消</el-button>
        <el-button type="primary" @click="submitForm2('form2')">确 定</el-button>
      </div>
      </el-dialog>

      <el-dialog title="转帐" :modal="false" :visible.sync="dialogFormVisibleThree">
      <el-form :model="form3"  status-icon :rules="rules3" ref="form3" label-width="100px" class="demo-ruleForm">
        <el-form-item label="转账金额" prop="money">
        <el-input v-model="form3.money" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="输入密码" prop="password">
          <el-input type="password" v-model="form3.password" autocomplete="off"></el-input>
        </el-form-item>
         <el-form-item label="对方账户" prop="transferToId">
        <el-input v-model="form3.transferToId" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm('form3'),dialogFormVisibleThree = false">取 消</el-button>
        <el-button type="primary" @click="submitForm3('form3')">确 定</el-button>
      </div>
      </el-dialog>
    </el-main>
  </el-container>
</div>
</template>

<script scoped>
import {Encrypt,Decrypt} from '@/utils/encryption'
export default {
  name: "Corefunc",
  mounted() {
    this.getCardS();
  },
  data() {
    //校验银行卡号格式
    var checkCardId = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('银行卡号不能为空！'));
        }
        setTimeout(() => {
          //正则校验，没试过，不知道准不准确value.match("/^([1-9]{1})(\d{14}|\d{18})$/")
         if(!value){
           return callback();
         }else{
          callback();
         }
        }
        , 600);
      };
      //校验密码
        var validatePassword = (rule, value, callback) => {
        if (!value) {
          callback(new Error('密码不能为空！'));
        } else {
          callback();
        }
      };
      //校验转账金额
       var validateBalance = (rule, value, callback) => {
        if (!value) {
          callback(new Error('转账金额不能为空'));
        } else {
         if(!value.match('^[1-9][0-9]*$')){
            callback(new Error('请输入正整数金额！'))
          }else{
          callback();
          }
        }
      };
    return {
      value: '',
      accountList: [],
      imgList: [
        {id:0,idView:'./static/Bank1.png'},
        {id:1,idView:'./static/Bank2.png'},
        {id:2,idView:'./static/Bank3.png'},
      ],
      icon: "el-input__icon el-icon-view",
      passw: "password",
      currentDate: new Date(),
      name: ["取款", "存款", "转账"],
      sourceImg: [
        "/static/transfer.svg",
        "/static/save.svg",
        "/static/transfer.svg",
      ],
      dialogFormVisible: [false, false, false],
      dialogFormVisibleOne: false,
      dialogFormVisibleTwo: false,
      dialogFormVisibleThree: false,
      bankAccountInfo: {
        cardId: "",
        openDate: "",
        balance: "",
        state: ""
      },
      formLabelWidth: "120px",
      rules3: {
          password: [
            {validator: validatePassword,trigger:'blur'},
          ],
          transferToId: [
            { validator:checkCardId, trigger: 'blur' }
          ],
          money: [
            {validator: validateBalance, trigger: 'blur' }
          ],
      },
      rules2:{
        password: [
            {validator: validatePassword,trigger:'blur'},
          ],
          money: [
            {validator: validateBalance, trigger: 'blur' }
          ],
      },
      form1:{
        money:'',
        password:'',
      },
       form2:{
        money:'',
        password:'',
      },
      form3:{
        money:'',
        password:'',
        transferToId:'',
      }
    };
  },
  methods: {
    getCardS(){
      this.$axios({
        method:"post",
        url:"http://101.132.250.23:81/coreBusiness/getAllAccountId",
        headers:{
          "token":localStorage.getItem("token"),
        }
      })
        //.post("http://101.132.250.23:81/coreBusiness/getAllAccountId")
        .then((res) => {
          console.log(res);
          this.accountList = res.data.data.bankAccountList;
          console.log(typeof(accountList));
          console.log(this.options);
        });
    },
    //密码的隐藏和显示
    showPass() {
      //点击图标是密码隐藏或显示
      if (this.passw == "text") {
        this.passw = "password";
        //更换图标
        this.icon = "el-input__icon el-icon-view";
      } else {
        this.passw = "text";
        this.icon = "el-input__icon el-icon-loading";
        setTimeout(()=>{
              this.icon = "";
        },500)
      }
    },
    //密码失焦事件
    onBlur(){
         this.passw = "password";
         this.icon = "el-input__icon el-icon-view";
    },
    logindex(index) {
      console.log(this.dialogFormVisible[index]);
      if(this.bankAccountInfo.cardId===''){
         this.$alert('请先选择交易的银行卡', '未选择银行卡', {
          confirmButtonText: '确定',
          })
          return;
      }
      if(this.bankAccountInfo.state==="已挂失"){
        this.$alert('该银行卡已冻结，请至银行解冻', '银行卡冻结', {
          confirmButtonText: '确定',
          })
          return;
      }
      if (index == 0) {
        this.dialogFormVisibleOne = true;
      } else if (index == 1) {
        this.dialogFormVisibleTwo = true;
      } else {
        this.dialogFormVisibleThree = true;
      }
    },
    getBackground(index) {
      return {
        backgroundImage: "url('" + this.sourceImg[index] + "')",
        backgroundSize: "contain",
        backgroundRepeat: "no-repeat",
      };
    },
    searchClick(){
      this.getCardInfo();
    },
    getCardInfo() {
      let data = new FormData();
      data.append("accountId", this.value);
      this.$axios
        .post(
          "http://101.132.250.23:81/coreBusiness/getAccountInfoById",
          data
        )
        .then((res) => {
          var accountInfo = res.data.data.account;
          console.log(accountInfo);
          this.bankAccountInfo.cardId = accountInfo.accountID;
          this.bankAccountInfo.openDate = accountInfo.openDate.toString();
          this.bankAccountInfo.balance = accountInfo.balance;
          this.bankAccountInfo.state = accountInfo.isReportLoss
            ? "已挂失"
            : "正常";
        });
    },
    submitForm3(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.transferMoney();
          } else {
          this.$alert('请按照正确格式输入', '输入有误', {
          confirmButtonText: '确定',
          })
            return false;
          }
        });
      },
      submitForm2(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.saveMoney();
          } else {
          this.$alert('请按照正确格式输入', '输入有误', {
          confirmButtonText: '确定',
          })
            return false;
          }
        });
      },
      submitForm1(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.takeMoney();
          } else {
          this.$alert('请按照正确格式输入', '输入有误', {
          confirmButtonText: '确定',
          })
            return false;
          }
        });
      },
    resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    takeMoney(){
      var money=this.form1.money;
      var password=this.form1.password;
      var curCardId=this.bankAccountInfo.cardId;
      let data = new FormData();
      data.append("accountId", curCardId);
      data.append("money",money);
      data.append("password",Encrypt(password));
      this.$axios
        .post(
          "http://101.132.250.23:81/coreBusiness/getMoney",
          data
        ).then(res=>{
          if(res.data.code==200){
          this.getCardInfo();
          this.$alert('成功取款￥'+money, '交易成功', {
          confirmButtonText: '确定',
          callback: action => {
            this.dialogFormVisibleOne=false;
            }
          })
         }
         else{
          this.$alert(res.data.message, '交易失败', {
          confirmButtonText: '确定',
          callback: action => {
            this.dialogFormVisibleOne=false;
            }
          })
         }
        })
    },
    saveMoney(){
      var money=this.form2.money;
      var password=this.form2.password;
      var curCardId=this.bankAccountInfo.cardId;
      let data = new FormData();
      data.append("accountId", curCardId);
      data.append("money",money);
      data.append("password",Encrypt(password));
      this.$axios
        .post(
          "http://101.132.250.23:81/coreBusiness/saveMoney",
          data
        ).then(res=>{
          if(res.data.code==200){
          this.getCardInfo();
          this.$alert('成功存款￥'+money, '交易成功', {
          confirmButtonText: '确定',
          callback: action => {
            this.dialogFormVisibleTwo=false;
            }
          })
         }else{
          this.$alert(res.data.message, '交易失败', {
          confirmButtonText: '确定',
          callback: action => {
            this.dialogFormVisibleTwo=false;
            }
          })
         }
        })
    },
    transferMoney(){
      var money=this.form3.money;
      var password=this.form3.password;
      var transferToId=this.form3.transferToId;
      var curCardId=this.bankAccountInfo.cardId;
      let data = new FormData();
      data.append("accountId", curCardId);
      data.append("money",money);
      data.append("password",Encrypt(password));
      data.append("transferToId",transferToId);
      this.$axios
        .post(
          // "http://localhost:81/coreBusiness/transferMoney",
          "http://101.132.250.23:81/coreBusiness/transferMoney",
          data
        ).then(res=>{
          if(res.data.code==200){
          this.getCardInfo();
          this.$alert('成功转账￥'+money, '交易成功', {
          confirmButtonText: '确定',
          callback: action => {
            this.dialogFormVisibleTwo=false;
            }
          })
         }else{
          this.$alert(res.data.message, '交易失败', {
          confirmButtonText: '确定',
          callback: action => {
            this.dialogFormVisibleTwo=false;
            }
          })
         }
        })
      }
  },
  created() {
    this.getCardInfo();
  },
  watch: {
    //用来监听
    $route: {
      //监听路由
      handler: function (val, oldVal) {
        console.log(val); //变化之后的路径
        this.getCardInfo();
      },
    },
  },
};
</script>

<style scoped>
.my-label{
  background-color: #fff7e8;
}

.main .describe{
  background-color: #fff7e8;
}

.el-header,
.el-footer {
  background-color: #fff7e8;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #fff7e8;
  color: #333;
  text-align: center;
  line-height: 25px;
}

.image {
  width: 50%;
  display: run-in;
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
  width: 100%;
  display: block;
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
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #fff7e8;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #fff7e8;
  }

.el-descriptions{
  width: 400px;
  padding: 20px;
      background-color: #FFF;
    border-radius: 20px;
        border: 8px solid #EBEEF5;
}

  .my-label {
    background-color: #99a9bf;
  }
</style>
