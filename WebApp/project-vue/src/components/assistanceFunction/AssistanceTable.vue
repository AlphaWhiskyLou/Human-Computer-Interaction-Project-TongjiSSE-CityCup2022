<template>
  <div>
    <div style="display: flex;justify-content: flex-start">
      <el-input
        placeholder="通过用户名搜索协助记录..."
        prefix-icon="el-icon-search"
        v-model="keywords" style="width: 400px" size="mini">
      </el-input>
      <el-button type="primary" icon="el-icon-search" size="mini" style="margin-left: 3px" @click="searchClick">搜索
      </el-button>
    </div>
    <div>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="userID" label="用户编号" width="180" />
        <el-table-column prop="accountID" label="账户编号" width="180" />
        <el-table-column prop="functionName" label="功能" width="180" />
        <el-table-column prop="utilityName" label="生活缴费" width="180" />
        <el-table-column prop="amount" label="交易金额" width="180" />
        <el-table-column
          label="操作"
          width="300">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small">编辑</el-button>
            <el-button @click="userDelete(scope.row)" type="text" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  name: "AssistanceTable",
  data() {
    return {
      tableData: [],
      keywords: ""
    };
  }, mounted() {
    var _this = this;
  }, methods: {
    searchClick(){
      this.loadBlogs();
    }, loadBlogs(){
      this.$axios
        .get("http://localhost:81/assistance/getAssistanceRequestByUserID/"+this.keywords)
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
    }
  }

}
</script>

<style scoped>

</style>
