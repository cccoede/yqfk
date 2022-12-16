<template>
  <div>
    <el-dialog title="个人中心" :visible.sync="uservisible">
      <el-form ref="form" :model="userForm" label-width="80px" :rules="rules">
        <el-form-item label="用户名" prop="dname">
          <el-col :span="16">
            <el-input
              v-model="userForm.username"
              readonly="readonly"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="姓名" prop="name">
          <el-col :span="16">
            <el-input
              v-model="userForm.name"
              placeholder="请输入姓名"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="手机号码" prop="tel">
          <el-col :span="16">
            <el-input
              v-model="userForm.tel"
              placeholder="请输入手机号码"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="用户头像">
          <el-upload
            class="avatar-uploader"
            action=""
            :http-request="uploadFile"
            :show-file-list="false"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
          <el-button @click="uservisible = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getUserinfo, userinfoUpdate } from "../request/api";
import axios from "axios"; // 引入axios
import store from "../store";

export default {
  data() {
    return {
      userForm: {},
      uservisible: false,
      rules: {
        name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        tel: [
          { required: true, message: "手机号码不能为空", trigger: "blur" },
          {
            pattern: /^0?1(3|4|5|7|8|9)\d{9}$/,
            message: "手机号码格式不正确",
            trigger: "blur",
          },
        ],
      },
      imageUrl: "",
    };
  },
  methods: {
    open() {
      this.uservisible = true;
      getUserinfo().then((res) => {
        if (res.code != 0) {
          this.$message.error("对应记录不存在");
          return;
        }
        this.userForm = res.data;
        this.imageUrl = res.data.pic;
      });
    },

    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return false;
        }
        let q = Object.assign({},this.userForm,{"pic": this.imageUrl});
        userinfoUpdate(q).then((res) => {
          if (res.code === 0) {
            this.$message.success(res.msg);
            this.uservisible = false;
          } else {
            this.$message.error(res.msg);
          }
        });
      });
    },

    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        this.$message.error("上传头像图片只能是JPG或者PNG格式!");

        return false;
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
        return false;
      }
      return true;
    },
    uploadFile(param) {
      store.commit("getToken");
      const token = store.state.user.token;
      axios.defaults.headers.post["token"] = token;
      const formData = new FormData();
      formData.append("file", param.file);
      axios
        .post("/api/file/upload/img", formData, {
          "Content-type": "multipart/form-data",
        })
        .then((Response) => {
          if (Response.data.code === 0) {
            this.imageUrl = Response.data.url;
          } else {
            this.$message.error("上传异常");
          }
        });
    },
  },
};
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
