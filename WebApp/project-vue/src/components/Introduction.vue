<template>
  <div class="List">
    <div class="pic">
      <el-carousel :interval="4000" height="480px" indicator-position="outside">
        <el-carousel-item v-for="item in imagesBox" :key="item.id">
          <img :src="item.idView" class="image" />
        </el-carousel-item>
      </el-carousel>
    </div>
    <div class="word">
      <el-row>
        <el-col :span="5" style="margin: 15px">
          <el-card :body-style="{ padding: '0px' }" shadow="hover">
            <img src="../assets/bank.png" class="image" />
            <div style="padding: 14px">
              <span >线上银行开户，申请便捷<br>官方授权，安全有保障</span>
              <div class="bottom clearfix">
                <el-button type="text" class="button" @click="move1">网上开户</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="5" style="margin: 15px">
          <el-card :body-style="{ padding: '0px' }" shadow="hover">
            <img src="../assets/money.png" class="image" />
            <div style="padding: 14px">
              <span>不用转战各线下网点<br>存款取款一站式便捷服务</span>
              <div class="bottom clearfix">
                <el-button type="text" class="button" @click="move2">存取款</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="5" style="margin: 15px">
          <el-card :body-style="{ padding: '0px' }" shadow="hover">
            <img src="../assets/pay.png" class="image" />
            <div style="padding: 14px">
              <span>水费、电费、煤气费<br>不足出户一键搞定</span>
              <div class="bottom clearfix">
                <el-button type="text" class="button" @click="move3">生活缴费</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="5" style="margin: 15px">
          <el-card :body-style="{ padding: '0px' }" shadow="hover">
            <img src="../assets/help.png" class="image" />
            <div style="padding: 14px">
              <span>老人使用有困难？<br>指定子女来帮忙！</span>
              <div class="bottom clearfix">
                <el-button type="text" class="button" @click="move4">亲属协助</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
export default {
  name: "Home",
  components: {},
  data() {
    return {
      isFormVisible: false,
      imagesBox: [
        { id: 0, idView: require("@/assets/finance.png") },
        { id: 1, idView: require("@/assets/assist.png") },
        { id: 2, idView: require("@/assets/security.png") },
        // { id: 3, idView: require("@/assets/photo1.png") },
      ],
    };
  },
  created() {},
  methods: {
    move1() {
      this.$router.push("/openAccount");
    },
    move2() {
      this.$router.push("/coreFunction");
    },
    move3() {
      this.$router.push("/newUtilityFee");
    },
    move4() {
      this.$router.push("/processAssistance");
    },
  },
};
</script>

<style scoped>
.List {
  /* overflow-y: scroll; */
}

.pic {
  /* position: absolute; */
  width: 90%;
  /* height: auto; */
  margin-top: 20px;
  margin-left: 5%;
}

.word {
  position:relative;
  margin-left: 40px;
  left:50%;
  transform: translate(-50%,0);
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
 
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
</style>