<template>
  <el-card class="box-card">
    <el-form action="submit">
      <h2>登录</h2>
      <!-- user -->
      <el-form-item>
        <el-input
          v-model="loginform.email"
          prefix-icon="el-icon-info"
          placeholder="请输入邮箱"
          prop="email"
        ></el-input>
      </el-form-item>
      <!-- password -->
      <el-form-item>
        <el-input
          type="password"
          v-model="loginform.password"
          prefix-icon="el-icon-warning"
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login">登录</el-button>
      </el-form-item>
      <el-form-item>
        <router-link to="/Register" class="toRegister"
          >没有账号?去注册</router-link
        >
      </el-form-item>
    </el-form>
  </el-card>
  <!-- LoginBox end -->
</template>


<script>
export default {
  data() {
    return {
      loginform: {},
    };
  },
  methods: {
    login() {
      this.$http
        .post("/user/login", {
          email: this.loginform.email,
          password: this.loginform.password,
        })
        .then((res) => {
          if (res.data.success) {
            console.log(res.data.success);
            this.$message({
              message: res.data.success.name + " 登录成功",
              type: "success",
            });
            this.$router.push("/");
          } else {
            this.$message.error("登录失败");
            console.log(res.data.error);
          }
        })
        .catch((error) => {
          this.$message.error("登录失败,异常错误!");
          console.log(error);
        });
    },
  },
};
</script>

<style>
.box-card {
  width: 400px;
  padding: 35px;
  color: #eee;
  position: absolute;
  left: 50%;
  top: 50%;
  margin-left: -200px;
  margin-top: -250px;
  background: rgba(22, 32, 41, 0.75);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
}
.box-card h2 {
  text-align: center;
  height: 20px;
  font: "microsoft yahei", Helvetica, Tahoma, Arial, "Microsoft jhengHei",
    sans-serif;
  color: #fff;
  line-height: 20px;
  padding: 0 0 35px 0;
}

.toRegister {
  text-decoration: none;
  color: grey;
}
.toRegister:visited {
  color: grey;
}
</style>