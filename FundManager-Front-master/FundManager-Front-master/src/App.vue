<template>
  <div id="app">
    <el-container>
      <el-header v-show="!$route.meta.doNotShowFrame">
        <!-- header begin -->
        <el-row>
          <el-col :xs="8" :sm="11" :md="14" :lg="17" :xl="19">
            <el-menu
              :default-active="$route.name"
              class="el-menu-demo"
              mode="horizontal"
              @select="changeMainComponent"
              background-color="#545c64"
              text-color="#fff"
              active-text-color="#ffd04b"
            >
              <el-submenu index="1">
                <template slot="title">我的基金</template>
                <el-menu-item index="PosInfo">持仓信息</el-menu-item>
                <el-menu-item index="TransactionRecord">交易记录</el-menu-item>
              </el-submenu>
              <el-submenu index="2">
                <template slot="title">基金市场</template>
                <el-menu-item index="LatestFund">新发基金</el-menu-item>
                <el-submenu index="2-2">
                  <template slot="title">基金排行</template>
                  <el-menu-item index="PerformanceRanking"
                    >业绩排行</el-menu-item
                  >
                  <el-menu-item index="ValuationRanking">净值排行</el-menu-item>
                </el-submenu>
                <el-submenu index="2-3">
                  <template slot="title">基金推荐</template>
                  <el-menu-item index="DebateFund">稳健债基</el-menu-item>
                  <el-menu-item index="StockFund">非债券基金</el-menu-item>
                </el-submenu>
              </el-submenu>
              <el-menu-item index="MyFund">自选基金</el-menu-item>
            </el-menu>
          </el-col>

          <el-col :xs="8" :sm="7" :md="6" :lg="4" :xl="3">
            <el-tooltip
              class="item"
              effect="dark"
              content="可搜索代号、名称、全名、类型、经理"
              placement="bottom"
            >
              <el-input
                placeholder="请输入想要搜索的基金"
                prefix-icon="el-icon-search"
                clearable
                v-model="inputSearchFund"
                style="width: 200px"
                @input="search"
              ></el-input>
            </el-tooltip>
          </el-col>
          <el-col
            id="personalInfoPanel"
            :xs="8"
            :sm="6"
            :md="4"
            :lg="3"
            :xl="2"
          >
            <el-avatar
              size="large"
              id="mainAvatar"
              src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
            ></el-avatar>

            <el-dropdown
              trigger="click"
              id="headerUserName"
              @command="userOptions"
            >
              <span class="el-dropdown-link">
                <span class="need-to-be-ellipsis" style="color: #409eff">{{
                  selfInfo.name
                }}</span>
                <span class="need-to-be-ellipsis">
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </span>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item icon="el-icon-user" command="goToSelfInfo"
                  >个人主页</el-dropdown-item
                >
                <el-dropdown-item
                  icon="el-icon-s-tools"
                  command="securitySetting"
                  >安全设置</el-dropdown-item
                >
                <el-dropdown-item icon="el-icon-setting" command="infoSetting"
                  >信息设置</el-dropdown-item
                >
                <el-dropdown-item icon="el-icon-back" command="logout"
                  >登出</el-dropdown-item
                >
              </el-dropdown-menu>
            </el-dropdown>
          </el-col>
        </el-row>
        <!-- header end -->
      </el-header>
      <el-main>
        <router-view
          @update-status="checkAndUpdateLogonStatus"
          :self-info="selfInfo"
          ref="interacteCom"
        />
      </el-main>
    </el-container>
    <el-dialog
      title="安全设置"
      :visible.sync="securitySetting"
      width="500px"
      :before-close="handleClose"
    >
      <el-input
        placeholder="请输入新密码"
        v-model="newPasword"
        show-password
      ></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="securitySetting = false">取 消</el-button>
        <el-button type="primary" @click="securitySettingCheck"
          >确 定</el-button
        >
      </span>
    </el-dialog>
    <el-dialog
      title="信息设置"
      :visible.sync="infoSetting"
      width="500px"
      :before-close="handleClose"
    >
      <el-input placeholder="请输入新用户名" v-model="newUsername"></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="infoSetting = false">取 消</el-button>
        <el-button type="primary" @click="infoSettingCheck">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "App",
  data() {
    return {
      newUsername: "",
      newPasword: "",
      securitySetting: false,
      infoSetting: false,
      inputSearchFund: "",
      backUpFlag: "",
      dataBackUp: undefined,
      selfInfo: {
        name: "",
        balance: 0,
        id: 0,
        email: "",
        password: "",
        registeredAt: "",
      },
    };
  },
  computed: {},
  methods: {
    infoSettingCheck() {
      this.$http
        .post("/user/update/name", {
          name: this.newUsername,
        })
        .then((res) => {
          if (res.data.success) {
            this.$notify({
              title: "操作成功",
              message: "用户名更新成功",
              type: "success",
            });
            this.checkAndUpdateLogonStatus();
          } else {
            throw res.data.error;
          }
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("用户名更新失败，异常错误！");
        });
      this.infoSetting = false;
    },
    securitySettingCheck() {
      this.securitySetting = false;
      this.infoSetting = false;
      this.$http
        .post("/user/update/password", {
          password: this.newPasword,
        })
        .then((res) => {
          if (res.data.success) {
            this.$notify({
              title: "操作成功",
              message: "密码更新成功，请重新登录",
              type: "success",
            });
            Object.assign(this.$data, this.$options.data());
            this.$router.push("/Login");
          } else {
            throw res.data.error;
          }
        })
        .catch((err) => {
          console.log(err);
          this.$message.error("密码更新失败，异常错误！");
        });
    },
    search(searchStr) {
      if (this.backUpFlag != this.$route.name) {
        this.backUpFlag = this.$route.name;
        this.dataBackUp = this.$refs.interacteCom.tableData;
      }
      if (searchStr) {
        this.$refs.interacteCom.loading = true;
        let tmp = [];
        this.dataBackUp.forEach((item) => {
          if (
            item.id == searchStr ||
            item.name.indexOf(searchStr) != -1 ||
            (item.fullName && item.fullName.indexOf(searchStr) != -1) ||
            (item.type && item.type.indexOf(searchStr) != -1) ||
            (item.managerName && item.managerName.indexOf(searchStr) != -1)
          ) {
            tmp.push(item);
          }
        });
        this.$refs.interacteCom.tableData = tmp;
        this.$refs.interacteCom.loading = false;
      } else {
        this.$refs.interacteCom.tableData = this.dataBackUp;
      }
    },
    changeMainComponent: function (index) {
      this.$router.push("/" + index);
    },
    checkAndUpdateLogonStatus: function () {
      this.$http
        .get("/user/getSelfInfo")
        .then((res) => {
          if (res.data.success) {
            this.selfInfo = res.data.success;
          } else {
            console.log("未登录，重定向到登录页面");
            Object.assign(this.$data, this.$options.data());
            this.$router.push("/Login");
          }
        })
        .catch((error) => {
          console.log(error);
          Object.assign(this.$data, this.$options.data());
          this.$router.push("/Login");
        });
    },
    userOptions: function (command) {
      switch (command) {
        case "logout":
          this.$http.get("/user/logout");
          Object.assign(this.$data, this.$options.data());
          this.$router.push("/Login");
          break;
        case "goToSelfInfo":
          this.$router.push("/SelfInfo");
          break;
        case "securitySetting":
          this.securitySetting = true;
          break;
        case "infoSetting":
          this.infoSetting = true;
          break;
        default:
          break;
      }
    },
    handleClose(done) {
      this.$confirm("确认关闭？").then(function () {
        done();
      });
    },
  },
  watch: {
    $route() {
      if (this.$route.path == "/") {
        this.$router.push("/MyFund");
      }
      if (!["Login", "Register"].includes(this.$route.name)) {
        this.checkAndUpdateLogonStatus();
      }
    },
  },
  mounted() {
    if (this.$route.path == "/") {
      this.$router.push("/MyFund");
    }
    if (!["Login", "Register"].includes(this.$route.name)) {
      this.checkAndUpdateLogonStatus();
    }
  },
};
</script>
<style>
@import url("//unpkg.com/element-ui@2.15.0/lib/theme-chalk/index.css");
/* 布局容器样式开始 */
#app {
  height: 100vh;
}

.el-header,
.el-footer {
  background-color: #545c64;
  color: #333;
  text-align: center;
  line-height: 60px;
}

/* .el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
} */

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

.el-container {
  height: 100%;
}
/* 布局容器样式结束 */

#mainAvatar {
  position: absolute;
  top: 50%;
  transform: translate(-50%, -50%);
}

#headerUserName {
  color: whitesmoke;
  left: 20px;
}

#personalInfoPanel,
#headerUserName {
  height: 60px;
}

.el-dropdown-link {
  margin-left: 20px;
  font-size: 16px;
}

.need-to-be-ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  max-width: 5em;
  display: inline-block;
  box-sizing: border-box;
}
</style>
