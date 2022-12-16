<template>
  <div>
    <el-dialog title="查看详情" :visible.sync="visible">
      <el-form ref="form" :model="showForm" label-width="80px">
        <el-form-item label="身份证" prop="sfz">
          <el-col :span="16">
            <el-input v-model="showForm.sfz" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="姓名" prop="xm">
          <el-col :span="16">
            <el-input v-model="showForm.xm" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="采样地点" prop="address">
          <el-col :span="16">
            <el-input v-model="showForm.address" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="采样时间" prop="ctime">
          <el-col :span="16">
            <el-input v-model="showForm.ctime" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="采样机构" prop="jigou">
          <el-col :span="16">
            <el-input v-model="showForm.jigou" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="采样结果" prop="jieguo">
          <el-col :span="16">
            <el-input v-model="showForm.jieguo" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="备注" prop="beizhu">
          <el-col :span="16">
            <el-input v-model="showForm.beizhu" readonly="readonly"></el-input>
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
import { hesuanByid } from "../../request/api";

export default {
  data() {
    return {
      visible: false,
      showForm: {},
    };
  },
  methods: {
    open(id) {
      hesuanByid({ id: id }).then((res) => {
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


