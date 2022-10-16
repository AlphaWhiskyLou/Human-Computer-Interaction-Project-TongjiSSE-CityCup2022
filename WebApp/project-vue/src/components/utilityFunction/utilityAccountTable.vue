<template>
  <div>
  <el-table :data="tableData99" style="width: 100%">
    <el-table-column prop="accountID" label="生活缴费账户编号" width="180" />
    <el-table-column prop="utilityName" label="生活缴费项目名称" width="180" />
    <el-table-column prop="userID" label="用户ID" width="180" />
    <el-table-column prop="utilityBalance" label="生活缴费账户余额" width="180" />
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
</template>

<script>
export default {
  name: "utilityAccountTable",
  data() {
    return {
      tableData99: [],
    };
  }, mounted() {
    var _this = this;
    this.loading = true;
    this.loaddata();
  }, methods: {
    loaddata() {
      this.$axios
        .get("http://localhost:81/utility/getUtilityAccount")
        .then(response => {
          let tableData190 = [];
          /**
           * 这里需要将拿到的对象转为数组，进行赋值，这样才不会宝类型错误
           */
          for (let i in response.data.data.utilityAccount) {
            tableData190.push(response.data.data.utilityAccount[i]);
          }
          this.tableData99 = tableData190;
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
