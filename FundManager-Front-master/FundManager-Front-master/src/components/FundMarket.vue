<template>
  <div>
    <el-card
      style="float: left; display: inline-block; margin: 0 10px; font-size: 2em"
      v-if="$route.meta.sorterOrFilter == 'PosInfo'"
    >
      <div>总已变现/投入: {{ sumHaveCost.toFixed(2) }} 元</div>
    </el-card>
    <el-card
      style="float: left; display: inline-block; margin: 0 10px; font-size: 2em"
      v-if="$route.meta.sorterOrFilter == 'PosInfo'"
    >
      <div>总盈亏估算: {{ sumValue.toFixed(2) }} 元</div>
    </el-card>
    <el-table
      :data="tableData"
      stripe
      style="width: 100%"
      v-loading="loading"
      element-loading-text="数据加载中"
      element-loading-spinner="el-icon-loading"
    >
      <el-table-column
        prop="id"
        label="基金代码"
        width="90"
        :formatter="idFormatter"
      >
      </el-table-column>
      <el-table-column prop="name" label="基金名称" width="180">
      </el-table-column>
      <el-table-column
        prop="type"
        label="基金类型"
        width="120"
        v-if="$route.meta.sorterOrFilter != 'PosInfo'"
      >
      </el-table-column>
      <el-table-column
        prop="managerName"
        label="基金经理"
        width="120"
        v-if="$route.meta.sorterOrFilter != 'PosInfo'"
      >
      </el-table-column>

      <el-table-column
        prop="releaseDate"
        label="发行日期"
        width="120"
        :formatter="dateFormatter"
        v-if="$route.meta.sorterOrFilter != 'PosInfo'"
      >
      </el-table-column>
      <el-table-column prop="currentPrice" label="当前净值" width="120">
      </el-table-column>
      <el-table-column
        prop="amount"
        label="仓位"
        width="120"
        v-if="$route.meta.sorterOrFilter == 'PosInfo'"
        :formatter="amountFormatter"
      >
      </el-table-column>
      <el-table-column
        label="持仓估值"
        width="120"
        :formatter="positionValueFormatter"
        v-if="$route.meta.sorterOrFilter == 'PosInfo'"
      >
      </el-table-column>
      <el-table-column
        prop="haveCost"
        label="已变现/投入"
        width="120"
        v-if="$route.meta.sorterOrFilter == 'PosInfo'"
        :formatter="haveCostFormatter"
      >
      </el-table-column>

      <el-table-column
        label="盈亏估算"
        width="120"
        :formatter="sumValueFormatter"
        v-if="$route.meta.sorterOrFilter == 'PosInfo'"
      >
      </el-table-column>
      <el-table-column label="" v-if="$route.meta.sorterOrFilter == 'PosInfo'">
      </el-table-column>
      <el-table-column
        prop="currentPrice"
        label="平均年回报率"
        width="120"
        :formatter="returnRateFormatter"
        v-if="$route.meta.sorterOrFilter != 'PosInfo'"
      >
      </el-table-column>
      <el-table-column
        prop="investmentObjectives"
        label="投资目标"
        v-if="$route.meta.sorterOrFilter != 'PosInfo'"
      >
      </el-table-column>
      <el-table-column
        fixed="right"
        label="关注"
        width="60"
        align="center"
        header-align="center"
      >
        <template #default="scope">
          <el-button @click="toggleFavorite(scope)" type="text" size="medium"
            ><i
              class="el-icon-star-on"
              style="font-size: 20px; color: orange"
              v-if="scope.row.isFavorite == true"
            ></i
            ><i
              class="el-icon-star-off"
              style="font-size: 20px; color: orange"
              v-if="scope.row.isFavorite != true"
            ></i
          ></el-button>
        </template>
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="80"
        align="center"
        header-align="center"
      >
        <template #default="scope">
          <el-button @click="openDrawer(scope.row)" type="text" size="small"
            >查看详情</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-drawer
      :title="title"
      :visible.sync="drawer"
      :direction="direction"
      :before-close="handleClose"
      size="700px"
    >
      <el-collapse v-model="activeNames">
        <el-collapse-item title="基本信息" name="1">
          <el-tree
            :data="basicData"
            node-key="id"
            :default-expanded-keys="[1, 2, 3, 4, 5, 6, 7, 8]"
            style="
              -moz-column-count: 2;
              -webkit-column-count: 2;
              column-count: 2;
            "
          ></el-tree>
        </el-collapse-item>
        <el-collapse-item title="投资目标" name="2">
          <div>
            {{ activeData.investmentObjectives }}
          </div>
        </el-collapse-item>
        <el-collapse-item title="投资范围" name="3">
          <div>
            {{ activeData.investmentScope }}
          </div>
        </el-collapse-item>
        <el-collapse-item title="投资策略" name="4">
          <div>
            {{ activeData.investmentStrategy }}
          </div>
        </el-collapse-item>
        <el-collapse-item title="操作" name="5" style="text-align: center">
          <div style="margin: 0 auto">
            <el-input
              placeholder="交易金额"
              v-model="transactionAmount"
              style="width: 200px; margin: 20px 0"
              clearable
            >
            </el-input>
            <br />
            <el-button-group>
              <el-button type="primary" @click="changePosition(0)" round
                ><i class="el-icon-download"></i>买入</el-button
              >
              <el-button type="warning" @click="changePosition(1)" round
                ><i class="el-icon-upload2"></i>卖出</el-button
              >
            </el-button-group>
            <br />
            相当于约 {{ tradeShare.toFixed(2) }} 份额
          </div>
        </el-collapse-item>
      </el-collapse>
    </el-drawer>
  </div>
</template>


<script>
export default {
  data() {
    return {
      loading: true,
      title: "",
      direction: "rtl",
      tableData: [],
      drawer: false,
      activeNames: ["1", "2", "3", "5"],
      basicData: {},
      activeData: {},
      transactionAmount: "",
      sumHaveCost: 0,
      sumValue: 0,
    };
  },
  computed: {
    tradeShare: function () {
      if (this.tableData == [] || !this.activeData) {
        return 0;
      }
      return this.transactionAmount / this.activeData.currentPrice;
    },
  },
  methods: {
    changePosition(type) {
      let realTransactionAmount = 0;
      if (type == 0) {
        realTransactionAmount = Number(this.transactionAmount);
      } else if (type == 1) {
        realTransactionAmount = -Number(this.transactionAmount);
      } else {
        console.log("交易类型不合法");
        return;
      }
      this.$http
        .post("/position/changePosition", {
          fid: this.activeData.id,
          amount: realTransactionAmount / this.activeData.currentPrice,
        })
        .then((res) => {
          if (res.data.success) {
            this.$notify({
              title: "操作成功",
              message: "已成功交易基金",
              type: "success",
            });
          } else if (res.data.error) {
            this.$notify.error({
              title: "操作失败",
              message: "未能成功交易基金，原因: " + res.data.error,
            });
          } else {
            throw "";
          }
        })
        .catch((error) => {
          this.$message.error("网络异常错误！");
          console.log(error);
        });
    },
    toggleFavorite(scope) {
      if (scope.row.isFavorite) {
        this.$http
          .post(
            "/favorite/removeFavorite",
            {
              fid: scope.row.id,
            },
            { withCredentials: true }
          )
          .then((res) => {
            if (res.data.success) {
              let tmp = this.tableData[scope.$index];
              tmp.isFavorite = !tmp.isFavorite;
              this.$set(this.tableData, scope.$index, tmp);
              this.$notify({
                title: "操作成功",
                message: "已移除关注",
                type: "success",
              });
            }
          })
          .catch((error) => {
            this.$message.error("网络异常错误！");
            console.log(error);
          });
      } else {
        this.$http
          .post(
            "/favorite/addFavorite",
            {
              fid: scope.row.id,
            },
            { withCredentials: true }
          )
          .then((res) => {
            if (res.data.success) {
              let tmp = this.tableData[scope.$index];
              tmp.isFavorite = !tmp.isFavorite;
              this.$set(this.tableData, scope.$index, tmp);
              this.$notify({
                title: "操作成功",
                message: "已添加关注",
                type: "success",
              });
            }
          })
          .catch((error) => {
            this.$message.error("网络异常错误！");
            console.log(error);
          });
      }
    },
    openDrawer(row) {
      this.title = row.name;
      this.drawer = true;
      this.activeData = row;
      // formater
      this.basicData = [
        {
          label: "基金代码",
          id: 1,
          children: [
            {
              label: this.idFormatter(null, null, row.id),
            },
          ],
        },
        {
          label: "基金名称",
          id: 2,
          children: [
            {
              label: row.name,
            },
          ],
        },
        {
          label: "基金全名",
          id: 3,
          children: [
            {
              label: row.fullName,
            },
          ],
        },
        {
          label: "基金类型",
          id: 4,
          children: [
            {
              label: row.type,
            },
          ],
        },
        {
          label: "基金经理",
          id: 5,
          children: [
            {
              label: row.managerName,
            },
          ],
        },
        {
          label: "发行日期",
          id: 6,
          children: [
            {
              label: this.dateFormatter(null, null, row.releaseDate),
            },
          ],
        },
        {
          label: "当前净值",
          id: 7,
          children: [
            {
              label: row.currentPrice,
            },
          ],
        },
        {
          label: "平均年回报率",
          id: 8,
          children: [
            {
              label: this.returnRateFormatter(row),
            },
          ],
        },
      ];
    },
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then(() => {
          done();
        })
        .catch(() => {});
    },
    positionValueFormatter(row) {
      return (row.amount * row.currentPrice).toFixed(2) + "元";
    },
    sumValueFormatter(row) {
      return (row.amount * row.currentPrice + row.haveCost).toFixed(2) + "元";
    },
    amountFormatter(row, column, cellValue) {
      return cellValue.toFixed(4);
    },
    haveCostFormatter(row, column, cellValue) {
      return cellValue.toFixed(2) + "元";
    },
    idFormatter(row, column, cellValue) {
      let res = cellValue.toString();
      let len = res.length;
      for (let index = 0; index < 6 - len; index++) {
        res = "0" + res;
      }
      return res;
    },
    dateFormatter(row, column, cellValue) {
      return cellValue.toLocaleDateString();
    },
    returnRateFormatter(row) {
      let timebetween = (new Date() - row.releaseDate) / 60 / 60 / 24 / 1000;
      return (
        (((row.currentPrice - 1) / timebetween) * 365 * 100).toFixed(2) + " %"
      );
    },
    updateFundData() {
      this.loading = true;
      this.tableData = [];
      this.$http
        .get("/fund/getFundList")
        .then((res) => {
          if (res.data.success) {
            res.data.success.forEach((row) => {
              row.releaseDate = new Date(row.releaseDate);
            });
            switch (this.$route.meta.sorterOrFilter) {
              case "PosInfo":
                this.$http
                  .get("/position/getSelfPosition")
                  .then((res1) => {
                    if (res1.data.success) {
                      let newFundData = [];
                      let tSumHaveCost = 0;
                      let tSumValue = 0;
                      res.data.success.forEach((fundItem) => {
                        res1.data.success.forEach((posItem) => {
                          if (posItem.fid == fundItem.id) {
                            tSumHaveCost += -posItem.haveCost;
                            tSumValue +=
                              fundItem.currentPrice * posItem.count -
                              posItem.haveCost;
                            newFundData.push({
                              id: fundItem.id,
                              name: fundItem.name,
                              currentPrice: fundItem.currentPrice,
                              amount: posItem.count,
                              haveCost: -posItem.haveCost,
                            });
                          }
                        });
                      });
                      this.sumValue = tSumValue;
                      this.sumHaveCost = tSumHaveCost;
                      this.tableData = newFundData.sort(function (a, b) {
                        return (
                          b.currentPrice * b.amount - a.currentPrice * a.amount
                        );
                      });
                    } else {
                      throw res.data.error;
                    }
                  })
                  .catch((error) => {
                    this.$message.error("获取数据失败，异常错误！");
                    console.log(error);
                  });
                break;
              case "LatestFund":
                res.data.success.sort(function (a, b) {
                  return b.releaseDate - a.releaseDate;
                });
                this.tableData = res.data.success;
                break;
              case "PerformanceRanking":
                res.data.success.sort(function (a, b) {
                  let timebetween = new Date() - a.releaseDate;
                  a = (a.currentPrice - 1) / timebetween;
                  timebetween = new Date() - b.releaseDate;
                  b = (b.currentPrice - 1) / timebetween;
                  return b - a;
                });
                this.tableData = res.data.success;
                break;

              case "ValuationRanking":
                res.data.success.sort(function (a, b) {
                  return b.currentPrice - a.currentPrice;
                });
                this.tableData = res.data.success;
                break;
              case "DebateFund":
                this.tableData = res.data.success.filter(
                  (fundItem) => fundItem.type.indexOf("债券") != -1
                );
                break;
              case "non-DebateFund":
                this.tableData = res.data.success.filter(
                  (fundItem) => fundItem.type.indexOf("债券") == -1
                );
                break;
              case "MyFund":
                this.$http
                  .get("/favorite/getSelfFavorite")
                  .then((res2) => {
                    if (res2.data.success) {
                      this.tableData = res.data.success.filter((fundItem) => {
                        for (
                          let index = 0;
                          index < res2.data.success.length;
                          index++
                        ) {
                          const record = res2.data.success[index];
                          if (record.fid == fundItem.id) {
                            return true;
                          }
                        }
                        return false;
                      });
                      for (
                        let index = 0;
                        index < this.tableData.length;
                        index++
                      ) {
                        this.tableData[index].isFavorite = true;
                      }
                      this.loading = false;
                    }
                  })
                  .catch((error) => {
                    this.$message.error("获取数据失败，异常错误！");
                    console.log(error);
                  });
                break;

              default:
                break;
            }
            //处理非MyFund页面（同步）
            if (this.$route.meta.sorterOrFilter != "MyFund") {
              this.$http
                .get("/favorite/getSelfFavorite")
                .then((res) => {
                  if (res.data.success) {
                    for (
                      let index = 0;
                      index < this.tableData.length;
                      index++
                    ) {
                      let flag = false;
                      res.data.success.forEach((element) => {
                        if (this.tableData[index].id == element.fid) {
                          flag = true;
                        }
                      });
                      this.tableData[index].isFavorite = flag;
                      this.$set(this.tableData, 0, this.tableData[0]); //刷新
                    }
                  }
                })
                .catch((error) => {
                  this.$message.error("获取数据失败，异常错误！");
                  console.log(error);
                });
              this.loading = false;
            }
          } else {
            this.$message.error("获取数据失败!");
            console.log(res.data.error);
          }
        })
        .catch((error) => {
          this.$message.error("获取数据失败，异常错误！");
          console.log(error);
        });
    },
  },
  watch: {
    $route() {
      this.updateFundData();
    },
  },
  mounted: function () {
    this.updateFundData();
  },
};
</script>

<style>
.el-drawer__body {
  padding: 0 20px;
  overflow-y: auto;
}
</style>