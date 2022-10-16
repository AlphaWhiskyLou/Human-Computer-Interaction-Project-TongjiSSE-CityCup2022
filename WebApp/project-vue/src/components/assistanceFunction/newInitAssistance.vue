<template>
  <el-main>
    <template>
      <el-carousel indicator-position="outside">
        <el-carousel-item v-for="item in imgList" :key="item.id">
          <img :src="item.idView" class="image">
        </el-carousel-item>
      </el-carousel>
    </template>
    <el-row>
        <div style="font-size:large;font-weight: bold;margin-bottom: 20px;margin-top: 20px;">便民生活缴费</div>
    </el-row>
    <el-row>
      <el-col
        :span="6"
        v-for="(o, index) in name"
        :key="o"
        :offset="index > 0 ? 3 : 0"
      >
        <el-card
          :body-style="{ padding: '30px' }"
          shadow="hover"
          @click.native="logindex(index)"
          style="background-color: white"
        >
                     <div style="padding: 4px;font-size: large;font-weight: bold;margin-bottom: 20px;">
            <el-link type="success" style="color:#125D98;font-weight:bolder;font-size:larger">{{ name[index] }}</el-link>
          </div>

          <img :src="sourceImg[index]" class="image" height="200px"/>
        </el-card>
      </el-col>
    </el-row>
  </el-main>
</template>

<script>
export default {
  name: "newInitAssistance",

  data() {
    return {
      imgList: [
        {id:0,idView:'./static/Bank1.svg'},
        {id:1,idView:'./static/Bank2.svg'},
        {id:2,idView:'./static/Bank3.svg'},
      ],
      name: ["协助转账", "协助缴费", "协助开户"],
      sourceImg: [
        "/static/transfer.png",
        "/static/wallet.png",
        "/static/card.png"
      ],
      dialogFormVisible: [false, false, false],
      dialogFormVisibleOne: false,
      dialogFormVisibleTwo: false,
      dialogFormVisibleThree: false,
    }
  },methods: {
    logindex(index) {
      console.log(index)
      if(index==0){
        this.$router.push({
          path: '/initAssistance'
        })
      }else if(index==1){
        this.$router.push({
          path: '/initAssistance2'
        })
      }else{
        this.$router.push({
          path: '/initAssistance3'
        })
      }
    }
  }
};
</script>

<style scoped>
.el-footer {
  background-color: #fff7e8;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: white;
  color: #333;
  text-align: center;
  line-height: 25px;
}

.el-main {
  background-color: #fff7e8;
  color: black;
  text-align: center;
  line-height: 20px;
  font-family: "PingFang SC";
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
.time {
  font-size: 13px;
  color: #999;
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
  width: 50%;
  display: run-in;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both;
}
.el-carousel__item h3 {
  color: #fff7e8;
  font-size: 18px;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
  text-align: center;
}

.el-carousel__item:nth-child(2n) {
  background-color: #fff7e8;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #fff7e8;
}
</style>
