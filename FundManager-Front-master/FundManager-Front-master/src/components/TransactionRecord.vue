<template>
  <div>
    <el-table
      :data="tableData"
      stripe
      style="width: 100%"
      v-loading="loading"
      element-loading-text="数据加载中"
      element-loading-spinner="el-icon-loading"
    >
      <el-table-column
        prop="tdate"
        label="交易时间"
        width="200"
        :formatter="dateFormatter"
      >
      </el-table-column>
      <el-table-column
        prop="type"
        label="类型"
        width="120"
        :formatter="typeFormatter"
      >
      </el-table-column>
      <el-table-column label="交易内容" :formatter="contentFormatter">
      </el-table-column>
    </el-table>
  </div>
</template>


<script>
export default {
  data() {
    return {
      loading: true,
      tableData: [],
    };
  },
  methods: {
    dateFormatter(row, col, cellValue) {
      return new Date(cellValue).toLocaleString("zh-CN");
    },
    typeFormatter(row, col, cellValue) {
      return {
        0: "持仓变动",
        1: "充值/提现",
      }[cellValue];
    },
    contentFormatter(row) {
      let newId = row.id.toString();
      switch (row.type) {
        case 0:
          while (newId.length < 6) {
            newId = "0" + newId;
          }
          if (row.amount > 0) {
            return (
              "以" +
              row.price.toFixed(2) +
              "元的净值购买了基金代号为" +
              newId +
              "的共" +
              row.amount.toFixed(4) +
              "的份额,支出约" +
              (row.amount * row.price).toFixed(2) +
              "元"
            );
          } else {
            return (
              "以" +
              row.price.toFixed(2) +
              "的净值出售了代号为" +
              newId +
              "的基金共" +
              -row.amount.toFixed(4) +
              "的份额,收入约" +
              (-row.amount * row.price).toFixed(2) +
              "元"
            );
          }
        case 1:
          if (row.amount > 0) {
            return "充值" + row.amount.toFixed(2) + "元";
          } else {
            return "提现" + -row.amount.toFixed(2) + "元";
          }

        default:
          return "";
      }
    },
  },
  mounted: function () {
    this.$http
      .get("/transactionRecord/getMyTransactionRecords")
      .then((res) => {
        if (res.data.success) {
          this.tableData = res.data.success;
          this.loading = false;
        } else {
          throw res.data.error;
        }
      })
      .catch((error) => {
        this.$message.error("获取数据失败，异常错误！");
        console.log(error);
      });
  },
};
</script>

<style scoped>
.sp {
  color: orange;
}
</style>