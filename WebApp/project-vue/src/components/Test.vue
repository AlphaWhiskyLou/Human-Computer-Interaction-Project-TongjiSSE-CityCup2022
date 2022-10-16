<template>
  <div>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="utilityID" label="ID" width="180" />
    <el-table-column prop="utilityName" label="Name" width="180" />
  </el-table>
    <el-table :data="tableData99" style="width: 100%">
      <el-table-column prop="accountID" label="1" width="180" />
      <el-table-column prop="utilityID" label="2" width="180" />
      <el-table-column prop="userID" label="3" width="180" />
      <el-table-column prop="utilityBalance" label="4" width="180" />
    </el-table>
    <div style="display: flex;justify-content: flex-start">
      <el-input
        placeholder="通过标题搜索..."
        prefix-icon="el-icon-search"
        v-model="keywords" style="width: 400px" size="mini">
      </el-input>
      <el-button type="primary" icon="el-icon-search" size="mini" style="margin-left: 3px" @click="searchClick">搜索
      </el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      tableData99: [],
      keywords: ''
    }
  },mounted() {
    var _this = this;
    this.loading = true;
    this.loaddata();
    this.loadBlogs();
  },methods: {
    loaddata(){
      this.$axios
        .get("http://localhost:81/utility/getUtilityFunction")
        .then(response => {
          let tableData1 = [];
          /**
           * 这里需要将拿到的对象转为数组，进行赋值，这样才不会宝类型错误
           */
          for(let i in response.data.data.utilityFunction){
            tableData1.push(response.data.data.utilityFunction[i]);
          }
          this.tableData = tableData1;
          console.log(response);
        })
        .catch(error => {
          console.log(errror);
        });

    }, searchClick(){
      this.loadBlogs();
    }, loadBlogs(){
      this.$axios
        .get("http://localhost:81/utility/getUtilityAccountById/"+this.keywords)
        .then(response => {
          let tableData2 = [];
          tableData2.push(response.data.data.utilityAccount);
          this.tableData99 = tableData2;
          console.log(response);
        })
        .catch(error => {
          console.log(errror);
        });
      }
    }

}
</script>
