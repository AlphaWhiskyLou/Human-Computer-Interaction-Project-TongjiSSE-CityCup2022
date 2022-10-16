<template>
<div>
  <el-container>

  <el-main>

    <el-container>
      <el-select v-model="value" placeholder="请选择您要查看的账户">
        <el-option
          v-for="item in accountList"
          :key="item.accountID"
          :label="item.accountID"
          :value="item.accountID">
        </el-option>
      </el-select>
      <el-button type="primary" icon="el-icon-search" size="mini" style="margin-left: 3px" @click="searchClick">搜索</el-button>
      <!--    <div class="block">-->
      <!--    <span class="demonstration">带快捷选项</span>-->
      <!--    <el-date-picker-->
      <!--      v-model="value2"-->
      <!--      type="daterange"-->
      <!--      align="right"-->
      <!--      unlink-panels-->
      <!--      range-separator="至"-->
      <!--      start-placeholder="开始日期"-->
      <!--      end-placeholder="结束日期"-->
      <!--      :picker-options="pickerOptions">-->
      <!--    </el-date-picker>-->
      <!--    </div>-->
    </el-container>
    <el-row>
      <h1> </h1>
    </el-row>
     <el-table
    :data="tradeRecords"
    style="width: 100%"
    :default-sort = "{prop: 'record.tradeTime', order: 'descending'}">
    <el-table-column type="expand">
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="交易账号">
            <span>{{ props.row.accountID }}</span>
          </el-form-item>
          <el-form-item label="交易类型">
            <span>{{ props.row.functionName }}</span>
          </el-form-item>
          <el-form-item label="交易时间">
            <span>{{ props.row.tradeTime }}</span>
          </el-form-item>
          <el-form-item label="交易金额">
            <span>{{ props.row.tradeAmount }}</span>
          </el-form-item>
          <el-form-item label="对方账号">
            <span>{{ props.row.payeeAccountID }}</span>
          </el-form-item>
        </el-form>
      </template>
    </el-table-column>
    <el-table-column label="交易账号" prop="accountID">
    </el-table-column>
    <el-table-column label="交易类型" prop="functionName">
    </el-table-column>
    <el-table-column label="交易时间" sortable="true" prop="tradeTime">
    </el-table-column>
  </el-table>
  </el-main>

</el-container>

</div>

</template>

<script>
 export default {
    data() {
      return {
        tradeRecords: [],
        accountList:[],
        // pickerOptions: {
        //   shortcuts: [{
        //     text: '最近一周',
        //     onClick(picker) {
        //       const end = new Date();
        //       const start = new Date();
        //       start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
        //       picker.$emit('pick', [start, end]);
        //     }
        //   }, {
        //     text: '最近一个月',
        //     onClick(picker) {
        //       const end = new Date();
        //       const start = new Date();
        //       start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
        //       picker.$emit('pick', [start, end]);
        //     }
        //   }, {
        //     text: '最近三个月',
        //     onClick(picker) {
        //       const end = new Date();
        //       const start = new Date();
        //       start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
        //       picker.$emit('pick', [start, end]);
        //     }
        //   }]
        // },
        value1: '',
        value2: '',
        options: [],
        value: ''
      }
    },mounted(){
      this.getCardS();
   },
    methods:{
      searchClick(){
        this.getRecords();
      },
      //获取所有卡号信息
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
           // options.push({value:accountList.accountID,label:accountList.accountID});

          // for(let i in accountList){
          //   this.options.push({value:accountList[i].accountID,label:accountList[i].accountID});
          // }
          console.log(this.options);
        });
      },
      //获取所有交易记录信息
      getRecords(){
        let data = new FormData();
        //data.append("card_id", this.value);
        this.$axios.get("http://101.132.250.23:81/account/getTradeRecordByAccountID/"+this.value)
        .then((res) => {
          this.tradeRecords=res.data.data.tradeRecordList;
          console.log(this.tradeRecords);
        });
        }
    }
  }
</script>

<style scoped>

 .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
  }
</style>
