<template>
  <div>
    <el-dialog title="修改密码" :visible.sync="passwordvisible">
      <el-form
        ref="form"
        :model="passwordForm"
        label-width="80px"
        :rules="rules"
      >
        <el-form-item label="原密码" prop="oldpassword">
          <el-col :span="16">
            <el-input
              type="password"
              v-model="passwordForm.oldpassword"
              placeholder="请输入原密码"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="新密码" prop="newpassword">
          <el-col :span="16">
            <el-input
              type="password"
              v-model="passwordForm.newpassword"
              placeholder="请输入新密码"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="确认密码" prop="confirmpassword">
          <el-col :span="16">
            <el-input
              type="password"
              v-model="passwordForm.confirmpassword"
              placeholder="请输入确认密码"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
          <el-button @click="passwordvisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>

import { userpassword } from "../request/api";


export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value !== this.passwordForm.newpassword) {
        callback(new Error("新密码和确认密码不一致!"));
      } else {
        callback();
      }
    };

    return {
      passwordForm: {
        oldpassword:"",
        newpassword:"",
        confirmpassword:"",
      },
      passwordvisible: false,
      rules: {
        oldpassword: [
          { required: true, message: "原密码不能为空", trigger: "blur" },
        ],
        newpassword: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          { min: 6, message: "新密码长度必须大于6位", trigger: "blur" },
        ],
        confirmpassword: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          { validator: validatePass, trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    open() {
      this.passwordvisible = true;
      this.passwordForm = {
      };
    },

    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return false;
        }
        
        userpassword(this.passwordForm).then((res) => {
            if (res.code === 0) {
              this.$message.success(res.msg);
              this.passwordvisible = false;
            } else {
              this.$message.error(res.msg);
            }
           
          });

      });
    },

  },
};
</script>

