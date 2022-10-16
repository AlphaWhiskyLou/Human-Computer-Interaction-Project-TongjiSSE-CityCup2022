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
      <el-form-item label="用户身份证号"  prop="userID">
        <el-input v-model="form.userID" placeholder="请输入用户的身份证号">
        </el-input>
      </el-form-item>
      <el-form-item label="请输入账户密码"  prop="password">
        <el-input v-model="form.password1" placeholder="请为账户创建密码" show-password>
        </el-input>
      </el-form-item>
          <el-form-item label="请再次输入密码"  prop="password">
            <el-input v-model="form.password2" placeholder="请再次输入密码" show-password>
            </el-input>
          </el-form-item>
      <el-form-item label="上传身份证照片">
        <el-upload
          action="https://jsonplaceholder.typicode.com/posts/"
          list-type="picture-card"
          :on-preview="handlePictureCardPreview"
          :on-remove="handleRemove"
        >
          <el-icon><plus /></el-icon>
        </el-upload>
        <el-dialog v-model="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="" />
        </el-dialog>
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
    return {
      dialogImageUrl: '',
      dialogVisible: false,
      code:'',
      imgList: [
        {id:0,idView:'./static/Bank1.svg'},
        {id:1,idView:'./static/Bank2.svg'},
        {id:2,idView:'./static/Bank3.svg'},
      ],
      form: {
        userID: '',
        password1: '',
        password2: '',
      }
      , rules: {
        userID: [
          {required: true, message: '请输入用户的身份证号', trigger: 'blur'},
          {pattern:/^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/, message: '请输入合法身份证号！', trigger: 'blur'}
        ],
        password1: [
          {required: true, message: '请输入转入账户密码', trigger: 'blur'},
          //{pattern:/^[0-9]{6}$/,message: '请输入合法密码！', trigger: 'blur'}
        ],
        password2: [
          {required: true, message: '请输入转入账户密码', trigger: 'blur'},
          //{pattern:/^[0-9]{6}$/,message: '请输入合法密码！', trigger: 'blur'}
        ],
      },
    }
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    onSubmit() {
      let Epassword;
      let data = new FormData();
      if(this.form.password1===this.form.password2){
        Epassword = Encrypt(this.form.password2);
        data.append("userID",this.form.userID);
        data.append("accountID","0");
        data.append("payeeAccountID",Epassword);
        data.append("functionID","7");
        data.append("amount","0");
        data.append("isFinished","0");
        data.append("password",Epassword);
        //TODO:确认UserUnity表中是否存在该用户
        this.$axios({
          method:"get",
          url:"http://101.132.250.23:81/assistance/getUnityIDByUser/"+this.form.userID,
          headers:{
            "token":localStorage.getItem("token"),
          }
        })
        //this.$axios.get("http://101.132.250.23:81/assistance/getUnityIDByUserID/310104200101036080/"+this.form.userID)//userID/unityID
        .then(response=>{
          if(response.data.code===200){
            this.$axios({
              method:"post",
              url:"http://101.132.250.23:81/assistance/addAssistanceRequest/",
              data:data,
              headers:{
              "token": localStorage.getItem("token")
            }
            })
              //.post("http://101.132.250.23:81/assistance/addAssistanceRequest/",data)
              .then(response => {
                if (response.data.code === 200) {
                  this.$axios.get("http://101.132.250.23:81/MessageCenter/SendMessageByQueue/"+this.form.userID+"/"+this.form.userID)
                  this.form={
                    userID: '',
                    password1: '',
                    password2: '',
                  }
                  console.log('submit!')
                  this.$confirm('开户协助请求发起成功', {
                    confirmButtonText: '确定',
                  });
                  //window.location.reload();
                }
                else{
                  this.$alert('开户协助请求发起失败，请检查您输入的信息！', {
                    confirmButtonText: '确定',
                  });
                  //window.location.reload();
                }
              })
          }
          else{
            this.$alert('开户协助请求发起失败，请确认您是否有操作权限！', {
              confirmButtonText: '确定',
            });
            //window.location.reload();
          }
        })
      }
      else{
        this.$alert('开户失败！请确认您的密码！', {
          confirmButtonText: '确定',
        });
      }

    }
  }
}
</script>
<style>
.el-carousel__item h3 {
  color: #475669;
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
