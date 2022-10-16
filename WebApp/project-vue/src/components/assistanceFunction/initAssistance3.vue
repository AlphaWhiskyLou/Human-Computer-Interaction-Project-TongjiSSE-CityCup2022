<template>
  <el-container class="utility_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <el-tab-pane label="转账" name="all_function">
          <init-transfer-assistance state="-1" :showEdit="false" :showDelete="false" :showRestore="false" :activeName="activeName"></init-transfer-assistance>
        </el-tab-pane>
        <el-tab-pane label="缴费" name="all_utility">
          <init-utility-assistance state="0" :showEdit="true" :showDelete="true" :showRestore="false" :activeName="activeName"></init-utility-assistance>
        </el-tab-pane>
        <el-tab-pane label="开户" name="all_open">
          <init-account-assistance state="2" :showEdit="true" :showDelete="true" :showRestore="false" :activeName="activeName"></init-account-assistance>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
import initTransferAssistance from "./initTransferAssistance";
import initUtilityAssistance from "./initUtilityAssistance";
import initAccountAssistance from "./initAccountAssistance";

export default {
  name: "initAssistance2",
  data() {
    return {
      activeName: 'all_open',
    };
  },methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  },
  components: {
    'initTransferAssistance': initTransferAssistance,
    'initAccountAssistance': initAccountAssistance,
    'initUtilityAssistance': initUtilityAssistance,
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
