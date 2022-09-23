<template>
  <div style="text-align: center">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span> 个人主页 </span>
      </div>
      <div>当前余额：{{ selfInfo.balance.toFixed(2) }}</div>
      <el-input
        placeholder="金额"
        v-model="transactionAmount"
        style="width: 200px; margin: 20px 0"
        clearable
      >
      </el-input>
      <br />
      <el-button-group>
        <el-button type="primary" @click="charge(0)" round
          ><i class="el-icon-download"></i>充值</el-button
        >
        <el-button type="warning" @click="charge(1)" round
          ><i class="el-icon-upload2"></i>提现</el-button
        >
      </el-button-group>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      transactionAmount: 0,
    };
  },
  props: ["selfInfo"],
  computed: {},
  methods: {
    charge(type) {
      let realTransactionAmount = 0;
      if (this.transactionAmount == 0) {
        this.$notify.error({
          title: "操作失败",
          message: "操作金额为0",
        });
        return;
      }
      if (type == 0) {
        realTransactionAmount = Number(this.transactionAmount);
      } else if (type == 1) {
        realTransactionAmount = -Number(this.transactionAmount);
      } else {
        console.log("交易类型不合法");
        return;
      }
      this.$http
        .post("/user/charge", {
          toCharge: realTransactionAmount,
        })
        .then((res) => {
          if (res.data.success != undefined) {
            this.$emit("update-status");
            this.$notify({
              title: "操作成功",
              message: "当前余额为" + Number(res.data.success).toFixed(2),
              type: "success",
            });
          } else if (res.data.error) {
            this.$notify.error({
              title: "操作失败",
              message: "错误原因: " + res.data.error,
            });
          }
        })
        .catch((error) => {
          this.$message.error("网络异常错误！");
          console.log(error);
        });
    },
  },
};
</script>

<style>
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}

.box-card {
  width: 480px;
}
</style>