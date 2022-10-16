<template>
  <el-container>
      <el-main>
        <el-row>
        <el-form :inline="true" ref="form" :model="ruleForm" label-width="80px" size="mini" align="left">
        <el-form-item label="时间:">
          <el-col :span="9">
            <el-form-item prop="date">
              <el-date-picker type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择时间日期" v-model="ruleForm.date"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="性别:" prop="gender">
          <el-select v-model="ruleForm.gender" placeholder="请选择志愿者性别">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="语言:" prop="language">
          <el-select v-model="ruleForm.language" placeholder="请选择志愿者常用语言">
            <el-option label="普通话" value="普通话"></el-option>
            <el-option label="方言" value="方言"></el-option>
            <el-option label="英语" value="英语"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm('ruleForm')">查询</el-button>
        </el-form-item>
        </el-form>
        </el-row>
          <el-table :data="tableData" style="width: 100%" highlight-current-row>
            <el-table-column label="选择" width="100">
              <template slot-scope="scope">
                <el-button @click="updateStatus(scope.row)" size="mini">选择</el-button>
              </template>
            </el-table-column>
            <el-table-column prop="conciergeID" label="礼宾编号" width="180" />
            <el-table-column prop="conciergeName" label="礼宾姓名" width="180" />
            <el-table-column prop="gender" label="性别" width="180" />
            <el-table-column prop="conciergeLanguage" label="语言" width="180" />
            <el-table-column prop="personalStatement" label="个人陈述" width="180" />
          </el-table>

      </el-main>
  </el-container>
</template>

<script>

export default {
  name: "Concierge",
  data() {
    return {
      ruleForm: {
        date: '',
        gender: '',
        language: []
      },
      tableData:[],
      currentUserName : ''
    }
  },
  mounted() {
    this.getUserName();
    this.loaddata();
  },
  methods:{
    handleSelectionChange (row) {
      this.tableData.forEach(item => {
        // 排他,每次选择时把其他选项都清除
        if (item.id !== row.id) {
          item.checked = false
        }
      })
      console.log(row)
    },
    loaddata(){
      this.$axios
        .get("http://101.132.250.23:81/concierge/FreeRequest")
        .then(response => {
          let tableData1 = [];
          /**
           * 这里需要将拿到的对象转为数组，进行赋值，这样才不会类型错误
           */
          for(let i in response.data.data.ConciergeList){
            tableData1.push(response.data.data.ConciergeList[i]);
          }
          this.tableData = tableData1;
          //console.log(response);
        })
        .catch(error => {
          console.log(error);
        });
    },
    submitForm() {
      let formdata = new FormData();
      formdata.append("gender",this.ruleForm.gender);
      formdata.append("language",this.ruleForm.language);
      console.log("this.ruleForm",this.ruleForm.language);
      // this.$axios
      //   .get("http://localhost:81/concierge/ConciergeRequest",{
      //     params:{
      //       gender:encodeURI(this.ruleForm.gender),
      //       language:encodeURI(this.ruleForm.language[0])
      //     }
      //   })
      this.$axios({
        method: 'post',
        url:"http://101.132.250.23:81/concierge/ConciergeRequest",
        data: formdata,
      })
          //+this.ruleForm.gender+"/"+this.ruleForm.language[0])
        .then(response => {
          let tableData1 = [];
          /**
           * 这里需要将拿到的对象转为数组，进行赋值，这样才不会宝类型错误
           */
          for(let i in response.data.data.ConciergeList){
            tableData1.push(response.data.data.ConciergeList[i]);
          }
          this.tableData = tableData1;
          console.log(response);
        })
        .catch(error => {
          console.log(error);
        });
    },
    getUserName() {
      this.$axios({
        method:"get",
        url:"http://101.132.250.23:81/user/getUser",
        headers:{
          "token": localStorage.getItem("token")
        }
      })
        .then(response=>{
          this.currentUserName = response.data.data.userName;
        })
    },
    updateStatus(row) {
    console.log()
    let dataform = new FormData();
    dataform.append("userID",this.currentUserName);
    dataform.append("conciergeID",row.conciergeID);
    console.log("date", this.ruleForm.date);
    dataform.append("date",this.ruleForm.date);
    console.log(dataform.get("userID"),dataform.get("conciergeID"),dataform.get("date"));
    this.$axios({
      method:"post",
      url:"http://101.132.250.23:81/concierge/addConciergeRequest",
      data: dataform,
    })
      //.get("http://localhost:81/addConciergeRequest/"+this.currentUserName+"/"+row.conciergeID+"/"+this.ruleForm.date)
      .then(response=> {
        if(response.data.code === 200){
          this.$alert('您已经选择该礼宾为您服务！', {
            confirmButtonText: '确定',
            callback: action => {
              this.loadRequests();
            }
          });
        }})
    }
  }
}
</script>

<style scoped>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}

</style>
