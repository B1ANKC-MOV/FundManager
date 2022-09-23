<template>
  <el-card class="box-card">
    <el-form action="submit">
      <h2>注册</h2>
      <!-- user -->
      <el-form-item>
        <el-input
          v-model="loginform.name"
          prefix-icon="el-icon-info"
          placeholder="请输入用户名"
        ></el-input>
      </el-form-item>
      <!-- user -->
      <el-form-item>
        <el-input
          v-model="loginform.email"
          prefix-icon="el-icon-info"
          placeholder="请输入邮箱"
          type="email"
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
        <el-button type="primary" @click="login">注册</el-button>
      </el-form-item>
      <el-form-item>
        <router-link to="/Login" class="toRegister"
          >已有账号?去登录</router-link
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
        .post("/user/register", {
          name: this.loginform.name,
          email: this.loginform.email,
          password: this.loginform.password,
        })
        .then((res) => {
          if (res.data.success) {
            console.log(res.data.success);
            this.$message({
              message: "注册成功，请登录！",
              type: "success",
            });
            this.$router.push("/Login");
          } else {
            console.log(res.data.error);
            this.$message.error("注册失败");
          }
        })
        .catch((error) => {
          this.$message.error("注册失败,异常错误!");
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