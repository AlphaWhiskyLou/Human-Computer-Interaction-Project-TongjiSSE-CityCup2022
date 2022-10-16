<template>
  <el-container>
    <el-main>
      <el-table :data="tableData" stripe style="width: 100%" class="assistanceTable" :default-sort = "{prop: 'assistanceDate', order: 'descending'}" :header-cell-style="{background:'#FFB65D',color:'#000D4D'}">
        <el-table-column prop="assistanceDate" label="日期" width="280" />
        <el-table-column prop="userName" label="用户姓名" width="280" />
        <el-table-column prop="accountID" label="账户编号" width="280" />
        <el-table-column prop="functionName" label="功能" width="280" :filters="[
         { text: '转账', value: '转账' },
        { text: '开户', value: '开户' },
        { text: '水费支付', value: '水费支付' },
        { text: '电费支付', value: '电费支付' },
        { text: '煤气费支付', value: '煤气费支付' },
      ]" :filter-method="filterHandler"/>
        <el-table-column prop="amount" label="交易金额" width="160" />
        <el-table-column
          label="操作"
          width="180">
          <template slot-scope="scope">
            <el-button @click="updateStatus(scope.row)" size="mini">同意</el-button>
            <el-button @click="declineUpdate(scope.row)" type="danger" size="mini">拒绝</el-button>
          </template>
        </el-table-column>
        <el-table-column
          align="right" width="180">
          <template slot="header" slot-scope="scope">
            <el-button @click="updateMessage" size="mini">刷新消息列表</el-button>
          </template>
        </el-table-column>
      </el-table>
      </el-main>
  </el-container>
</template>

<script>
export default {
  name: "processAssistance",
  data() {
    return {
      tableData: [],
      messageData:[],
      keywords: ""
    };
  }, mounted() {
    this.updateMessage();
  },
  methods:{
    //TODO:利用后端传上来的Token获取真实的UserID信息
    updateMessage(){
      this.loadRequests();
      this.$axios({
        method:"get",
        url:"http://101.132.250.23:81/MessageCenter/ReceiveMessageByQueue/",
        headers:{
          "token": localStorage.getItem("token"),
        }
      })
        //.get("http://101.132.250.23:81/MessageCenter/ReceiveMessageByQueue/" + "310104200101036099")
      .then(res => {
        if(res.data.code === 200){
          this.$message({
            message: "您有新的待处理协助信息！",
            type: 'success',
            size: 'large'
          });
          this.loadRequests();
        }
        this.messageData = res.data;
        console.log(this.messageData);
      })
    },
    // loadMessage() {
    //   this.$axios.get("http://localhost:81/MessageCenter/ReceiveMessageByQueue/"+"310104200101036099")
    //     .then(response=> {
    //       if(response.data.code === 200){
    //         this.$message({
    //           message: "您有新的待处理协助信息！",
    //           type: 'success',
    //           size: 'large'
    //         });
    //         this.loadRequests();
    //       }
    //       console.log(response);
    //     }).catch(error => {
    //     console.log(error);
    //   });
    // },
    updateStatus(row) {
    console.log(row);
    this.$axios
      .post("http://101.132.250.23:81/assistance/updateAssistanceRequestStatus/"+row.assistanceID+"/"+1)
      .then(response=> {
        console.log('submit!')
        if(response.status===200){
          //还需要添加真实的金钱操作！！！
          //进行转账或余额削减
        this.$alert('您的亲属协助操作已成功完成', {
          confirmButtonText: '确定',
          callback: action => {
            this.loadRequests();
          }
        });
        }
      })
  },declineUpdate(row){
      console.log(row);
      this.$axios
        .post("http://101.132.250.23:81/assistance/updateAssistanceRequestStatus/"+row.assistanceID+"/"+2)
        .then(response=> {
          console.log('submit!')
          if(response.status===200){
            this.$alert('您已拒绝亲属发起的协助操作', {
              confirmButtonText: '确定',
              callback: action => {
                this.loadRequests();
              }
            });
          }
        })
    }
    ,searchClick(){
      this.loadRequests();
    }, loadRequests(){
      this.$axios({
        method: "get",
        url: "http://101.132.250.23:81/assistance/getUnfinishedAssistanceRequestByUserID/",
        headers:{
          "token": localStorage.getItem("token"),
        }
      })
        //.get("http://101.132.250.23:81/assistance/getUnfinishedAssistanceRequestByUserID/"+"310104200101036099")
        .then(response => {
          let tableData1 = [];
          for(let i in response.data.data.assistanceRequestDisp){
            tableData1.push(response.data.data.assistanceRequestDisp[i]);
          }
          this.tableData = tableData1;
          console.log(response);
        })
        .catch(error => {
          console.log(errror);
        });
    },filterHandler(value, row, column) {
      const property = column['property']
      return row[property] === value
    }
    }

}
</script>

<style scoped>
.row1{
  height:60px;line-height:60px;margin:10px 0;
}
</style>
