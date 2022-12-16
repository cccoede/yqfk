<template>
  <div>
    <el-dialog title="查看详情" :visible.sync="visible">
      <el-form ref="form" :model="showForm" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-col :span="16">
            <el-input v-model="showForm.username" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="姓名" prop="name">
          <el-col :span="16">
            <el-input v-model="showForm.name" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="手机号码" prop="tel">
          <el-col :span="16">
            <el-input v-model="showForm.tel" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="添加时间" prop="ctime">
          <el-col :span="16">
            <el-input v-model="showForm.ctime" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-button @click="visible = false">关闭</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { userByid } from "../../request/api";

export default {
  data() {
    return {
      visible: false,
      showForm: {},
    };
  },
  methods: {
    open(id) {
      userByid({ id: id }).then((res) => {
        if (res.code != 0) {
          this.$message.error("对应记录不存在");
          return;
        }
        this.visible = true;
        this.showForm = res.data;
      });
    },
  },
};
</script>


