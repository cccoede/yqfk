<template>
  <div class="logindiv">
    <el-form
      :model="form"
      status-icon
      :rules="rules"
      ref="form"
      label-width="100px"
      class="login-container"
    >
      <h3 class="login_title">疫情防控管理系统</h3>
      <el-form-item
        label="用户名"
        label-width="80px"
        prop="username"
        class="username"
      >
        <el-input
          type="input"
          v-model="form.username"
          autocomplete="off"
          placeholder="请输入账号"
        ></el-input>
      </el-form-item>
      <el-form-item label="密码" label-width="80px" prop="password">
        <el-input
          type="password"
          v-model="form.password"
          autocomplete="off"
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item class="login_submit">
        <el-button type="primary" @click="login" class="login_submit" size="mini"
          >登录</el-button
        >

        <el-button type="success" @click="register" size="mini">用户注册</el-button>
      </el-form-item>
    </el-form>

    <el-dialog
      title="用户注册"
      :visible.sync="isShow"
      :modal-append-to-body="false"
    >
      <el-form
        ref="opForm"
        :model="operateForm"
        label-width="80px"
        :rules="regrules"
      >
        <el-form-item label="用户名(身份证)" prop="username">
          <el-col :span="16">
            <el-input
              v-model="operateForm.username"
              placeholder="请输入用户名(身份证)"
              auto-complete="false"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="新密码" prop="password">
          <el-col :span="16">
            <el-input
              type="password"
              v-model="operateForm.password"
              placeholder="请输入新密码"
              auto-complete="false"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmpassword">
          <el-col :span="16">
            <el-input
              type="password"
              v-model="operateForm.confirmpassword"
              placeholder="请输入确认密码"
              auto-complete="false"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="姓名" prop="name">
          <el-col :span="16">
            <el-input
              v-model="operateForm.name"
              placeholder="请输入姓名"
              auto-complete="false"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="手机号码" prop="tel">
          <el-col :span="16">
            <el-input
              v-model="operateForm.tel"
              placeholder="请输入手机号码"
              auto-complete="false"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onSubmit">注册</el-button>
          <el-button @click="isShow = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>
<script>
import { userlogin,userregister } from "../request/api";
export default {
  name: "login",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value !== this.operateForm.password) {
        callback(new Error("密码和确认密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      form: {},
      rules: {
        username: [
          { required: true, message: "请输入用户名(身份证)", trigger: "blur" },
          
        ],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      },
      isShow: false,
      operateForm: {},
      regrules: {
        username: [
          { required: true, message: "用户名不能为空", trigger: "blur" },
          { min: 18,  message: "用户名(身份证)长度必须18位", trigger: "blur" },
          { max: 18,  message: "用户名(身份证)长度必须18位", trigger: "blur" },
        ],
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
          { min: 6, message: "密码长度必须大于6位", trigger: "blur" },
        ],
        confirmpassword: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          { validator: validatePass, trigger: "blur" },
        ],
        name: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
        ],
        tel: [
          { required: true, message: "手机号码不能为空", trigger: "blur" },
          {
            pattern: /^0?1[3,4,5,6,7,8,9][0,1,2,3,4,5,6,7,8,9]\d{8}$/,
            message: "手机号码格式不正确",
            trigger: "blur",
          },
        ],

      },
    };
  },

  methods: {
    login() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return false;
        }

        userlogin(this.form).then((res) => {
          console.log(res, "res");
          if (res.code === 200) {
            this.$store.commit("clearMenu");
            this.$store.commit("setMenu", res.data);
            this.$store.commit("setToken", res.token);
            this.$store.commit("addMenu", this.$router);
            this.$router.push({ name: "home" });
            this.$message.warning(res.msg);
          } else {
            this.$message.warning(res.msg);
          }
        });
      });
    },

    register() {
      this.isShow = true;
      this.operateForm = {};
    },

    onSubmit() {
      this.$refs.opForm.validate((valid) => {
        if (!valid) {
          return false;
        }
        userregister(this.operateForm).then((res) => {
            if (res.code === 0) {
              this.$message.success(res.msg);
              this.isShow = false;
            } else {
              this.$message.error(res.msg);
            }
            
          });

      });
    },
  },

  created() {
    if (sessionStorage.getItem("refresh") == 1) {
      sessionStorage.removeItem("refresh");
      window.location.reload();
    }
  },
};
</script>

<style lang="less" scoped>
.logindiv {
  background-image: url("../assets/login_bg.jpg");
  width: 100%;
  height: 100%;
  min-width: 1000px;
  background-size: 100% 100%;
  background-position: center center;
  overflow: auto;
  background-repeat: no-repeat;
  position: fixed;
  line-height: 100%;
}

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 180px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background-color: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
  font-weight: bold;
  font-size: 25px;
}
.login_submit {
  margin: 10px auto 0px auto;
}
</style>