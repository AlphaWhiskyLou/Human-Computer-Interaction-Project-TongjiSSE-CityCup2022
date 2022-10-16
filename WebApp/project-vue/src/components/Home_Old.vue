<template>
  <el-container class="home_container">
    <el-header>
      <div class="home_title">网上银行0.1</div>
      <el-dropdown>
    <span class="el-dropdown-link">
      登录用户菜单
      <el-icon class="el-icon--right">
        <arrow-down />
      </el-icon>
    </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>Action 1</el-dropdown-item>
            <el-dropdown-item>Action 2</el-dropdown-item>
            <el-dropdown-item>Action 3</el-dropdown-item>
            <el-dropdown-item disabled>Action 4</el-dropdown-item>
            <el-dropdown-item divided>Action 5</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-header>
    <el-main>
      <el-row class="tac">
        <el-col :span="12">
          <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            @open="handleOpen"
            @close="handleClose"
          >
            <el-submenu index="1">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>网上银行</span>
              </template>
              <el-menu-item-group>
                <template slot="title">银行账户</template>
                <el-menu-item index="1-1">转账</el-menu-item>
                <el-menu-item index="1-2">查账</el-menu-item>
                <el-menu-item index="1-3">还款</el-menu-item>
              </el-menu-item-group>
              <el-submenu index="1-4">
                <template slot="title">我的银行</template>
                <el-menu-item index="1-4-1">交易记录查询</el-menu-item>
                <el-menu-item index="1-4-2">消费记录总结</el-menu-item>
                <el-menu-item index="1-4-3">个人信息查询</el-menu-item>
                <el-menu-item index="1-4-4">个人信息修改</el-menu-item>
              </el-submenu>

            </el-submenu>
            <el-submenu index="2">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>生活缴费</span>
              </template>
              <el-menu-item-group>
                <template slot="title">水电煤便民缴费</template>
                <el-menu-item index="/payUtilityFee" @click="goTo('/payUtilityFee')">
                  <i class="el-icon-menu"></i>
                  <span slot="title">缴纳费用</span>
                </el-menu-item>
                <el-menu-item index="/utilityRecord" @click="goTo('/utilityRecord')">
                  <i class="el-icon-menu"></i>
                  <span slot="title">费用缴纳记录</span>
                </el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            <el-submenu index="3">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>辅助功能</span>
              </template>
              <el-menu-item-group>
                <template slot="title">亲属协助操作</template>
                <el-menu-item index="/processAssistance" @click="goTo('/processAssistance')">
                  <i class="el-icon-menu"></i>
                  <span slot="title">批准已发起的亲属协助操作</span>
                </el-menu-item>
                <el-menu-item index="/initAssistance" @click="goTo('/initAssistance')">
                  <i class="el-icon-menu"></i>
                  <span slot="title">发起亲属协助操作</span>
                </el-menu-item>
                <el-menu-item index="/viewAssistance" @click="goTo('/viewAssistance')">
                  <i class="el-icon-menu"></i>
                  <span slot="title">查看亲属协助操作记录</span>
                </el-menu-item>
              </el-menu-item-group>
              <el-submenu index="3-4">
                <template slot="title">专人上门服务</template>
                <el-menu-item index="1-4-1">礼宾查询</el-menu-item>
                <el-menu-item index="1-4-2">服务预约</el-menu-item>
                <el-menu-item index="1-4-3">查看服务记录</el-menu-item>
              </el-submenu>
            </el-submenu>

            <el-menu-item index="/utilityManagement" @click="goTo('/utilityManagement')">
              <i class="el-icon-menu"></i>
              <span slot="title">生活缴费管理</span>
            </el-menu-item>
            <el-menu-item index="/assistanceManagement" @click="goTo('/assistanceManagement')">
              <i class="el-icon-setting"></i>
              <span slot="title">协助操作管理</span>
            </el-menu-item>
          </el-menu>
        </el-col>
        <router-view></router-view>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
export default {
  data() {
    return {};
  },

  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    goTo(path) {
      this.$router.replace(path);
    }
  }
};

</script>

<style scoped>
.el-col-12{
  width: 15%;
}
.home_title{
  font-size: 23px;
  font-weight: bolder;
  color: #F56C6C;
  text-align: center;
  margin-top: 10px;
}
.home_container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
}
.el-header {
  background-color: #20a0ff;
  color: #333;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>

