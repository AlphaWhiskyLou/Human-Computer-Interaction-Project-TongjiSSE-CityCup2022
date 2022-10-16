<template>
  <el-container class="assistance_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <el-tab-pane label="协助信息查看" name="all_info">
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
import utilityAccountTable from '@/components/UtilityFunction/utilityAccountTable';
import assistanceTable from '@/components/AssistanceFunction/assistanceTable.vue';

export default {
  name: "utilityManagement",
  data() {
    return {
      activeName: 'all_info',
    };
  },methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  },
  components: {
    'utility_table': assistanceTable,
    'utilityAccount_table': utilityAccountTable
  }
}
</script>

<style scoped>

</style>
