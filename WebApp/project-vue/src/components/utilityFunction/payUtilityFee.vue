<template>
  <el-main>
    <template>
      <el-carousel indicator-position="outside">
        <el-carousel-item v-for="item in 4" :key="item">
          <h3>{{ item }}</h3>
        </el-carousel-item>
      </el-carousel>
    </template>
    <el-form ref="form1" :model="form" label-width="120px">
      <el-form-item label="金额">
        <el-input v-model="form.amount" placeholder="请输入交易金额">
        </el-input>
      </el-form-item>
      <el-form-item label="用户身份证号">
        <el-input v-model="form.userID" placeholder="请输入用户的身份证号">
        </el-input>
      </el-form-item>
      <el-form-item label="用户账户">
        <el-input v-model="form.accountID" placeholder="请输入缴费账户">
        </el-input>
      </el-form-item>
      <el-form-item label="缴费项目">
        <el-select v-model="form.utility" placeholder="请选择缴费项目">
          <el-option label="水费" value="1"></el-option>
          <el-option label="电费" value="2"></el-option>
          <el-option label="煤气费" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit" >立即提交</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </el-main>
</template>

<script>
export default {
  data() {
    return {
      form: {
        amount: '',
        userID: '',
        accountID: '',
        utility: '',
      }
    }
  },
  methods: {
    current_date() {
      var date = new Date();
      var seperator1 = "-";
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      return year.toString() + month.toString() + strDate.toString();
    }, current_date_sep() {
      var date = new Date();
      var seperator1 = "-";
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      return year+seperator1 + month+seperator1+ strDate;
    }, resetForm(searchForm) {
      this.$refs[searchForm].resetFields()
    },
    onSubmit() {
      let recordID;
      recordID = this.form.userID + 1 + this.current_date() + Math.round(Math.random() * 5).toString()
      this.$axios
        .post("")
        ///pending on add record method
        .then(response => {
          if (response.status === 200) {
            this.form={
              amount: '',
              userID: '',
              accountID: '',
              payeeAccount: '',
            }
            console.log('submit!')
            this.$alert('添加成功', {
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
  color: #475669;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
