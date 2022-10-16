<template>
  <el-container>

    <el-main>
      <el-table :data="tableData.slice((currentPage-1)*PageSize,currentPage*PageSize)" stripe style="width: 100%" :default-sort = "{prop: 'assistanceDate', order: 'descending'}" :header-cell-style="{background:'#FCE996',color:'#606266'}">
        <el-table-column prop="assistanceDate" label="发起日期" width="380" />
        <el-table-column prop="userName" label="用户编号" width="380" />
        <el-table-column prop="accountID" label="账户编号" width="380" />
        <el-table-column prop="functionName" label="功能" width="320"  :filters="[
        { text: '转账', value: '转账' },
        { text: '开户', value: '开户' },
        { text: '水费支付', value: '水费支付' },
        { text: '电费支付', value: '电费支付' },
        { text: '煤气费支付', value: '煤气费支付' },
      ]" :filter-method="filterHandler"/>
        <el-table-column prop="amount" label="交易金额" width="180" />
      </el-table>
      <div class="tabListPage">
        <el-pagination @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="currentPage"
                       :page-sizes="pageSizes"
                       :page-size="PageSize" layout="total, sizes, prev, pager, next, jumper"
                       :total="totalCount">
        </el-pagination>
      </div>
    </el-main>
  </el-container>
</template>

<script>
export default {
  name: "viewAssistance",
  data() {
    return {
      userId: "",
      tableData: [],
      messageData:[],
      keywords: "",
      currentPage:1,
      // 总条数，根据接口获取数据长度(注意：这里不能为空)
      totalCount:1,
      // 个数选择器（可修改）
      pageSizes:[10,20,30,40],
      // 默认每页显示的条数（可修改）
      PageSize:20,
    };
  }, mounted() {
    var _this = this;
    this.loadBlogs();
  }, methods: {
    handleSizeChange(val) {
      // 改变每页显示的条数
      this.PageSize=val
      // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.currentPage=1
    },
    // 显示第几页
    handleCurrentChange(val) {
      // 改变默认的页数
      this.currentPage=val
    },
    searchClick(){
      this.loadBlogs();
    }, loadBlogs(){
      this.$axios({
        method: "get",
        url: "http://101.132.250.23:81/assistance/getAssistanceRequestByUserID/",
        headers:{
          "token": localStorage.getItem("token"),
        }
      })
        //.get("http://101.132.250.23:81/assistance/getAssistanceRequestByUserID/"+"310104200101036099")
        .then(response => {
          this.totalCount=response.data.data.assistanceRequestDisp.length;
          let tableData1 = [];
          for(let i in response.data.data.assistanceRequestDisp){
            tableData1.push(response.data.data.assistanceRequestDisp[i]);
          }
          this.tableData = tableData1;
          console.log(response);
        })
        .catch(error => {
          console.log(error);
        });
    }  ,filterHandler(value, row, column) {
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
