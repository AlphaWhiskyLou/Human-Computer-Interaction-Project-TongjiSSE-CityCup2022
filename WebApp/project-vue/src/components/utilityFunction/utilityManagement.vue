<template>
    <el-container class="utility_list">
      <el-main class="main">
        <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
          <el-tab-pane label="生活缴费功能" name="all_function">
            <utility_table state="-1" :showEdit="false" :showDelete="false" :showRestore="false" :activeName="activeName"></utility_table>
          </el-tab-pane>
          <el-tab-pane label="生活缴费账户" name="all_account">
            <utilityAccount_table state="1" :showEdit="true" :showDelete="true" :showRestore="false" :activeName="activeName"></utilityAccount_table>
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>
</template>

<script>
import utilityTable from '@/components/UtilityFunction/utilityTable.vue'
import utilityAccountTable from "./utilityAccountTable";

export default {
  name: "utilityManagement",
  data() {
    return {
      activeName: 'all_function',
    };
  },methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  },
  components: {
    'utility_table': utilityTable,
    'utilityAccount_table': utilityAccountTable
  }
}
</script>

<style scoped>
.el-header {
  background-color: #20a0ff;
  color: #333;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-around;
}
.head{

}
.main {
  top: 0px;
  left: 0px;
  width: 100%;
}
</style>
