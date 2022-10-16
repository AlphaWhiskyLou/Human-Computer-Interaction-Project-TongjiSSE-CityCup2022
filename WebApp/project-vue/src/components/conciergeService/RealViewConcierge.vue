<template>
  <el-container>
    <el-main>
      <el-table :data="tableData" style="width: 100%" highlight-current-row>
        <el-table-column prop="booktime" label="预定时间" width="180" />
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
  name: "RealViewConcierge",
  data() {
    return {
      tableData:[],
      currentUserName:''
    }
  },
  methods:{
    getUserName() {
      this.$axios({
        method:"get",
        url:"http://101.132.250.23:81/user/getUser",
        headers:{
          "token": localStorage.getItem("token")
        }
      })
        .then(response=>{
          //console.log("re",response.data.data.userName)
          this.currentUserName = response.data.data.userName;
          console.log(this.currentUserName)
        })
    },
    loaddata(){
      console.log("user", this.currentUserName)
      this.$axios
        .get("http://101.132.250.23:81/concierge/ConRequest/"+this.currentUserName)
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

  },
  created(){
    this.getUserName();
  },
  watch:{
    currentUserName(){
      this.$nextTick(()=>{
        this.loaddata();//websocket建立连接
      })
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
