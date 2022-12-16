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

        <el-form-item label="疫苗厂家" prop="ymgs">
          <el-col :span="16">
            <el-input v-model="showForm.ymgs" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="疫苗名称" prop="ymc">
          <el-col :span="16">
            <el-input v-model="showForm.ymc" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="接种地点" prop="jdzd">
          <el-col :span="16">
            <el-input v-model="showForm.jdzd" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="接种时间" prop="ctime">
          <el-col :span="16">
            <el-input v-model="showForm.ctime" readonly="readonly"></el-input>
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
import { yimiaoByid } from "../../request/api";

export default {
  data() {
    return {
      visible: false,
      showForm: {},
    };
  },
  methods: {
    open(id) {
      yimiaoByid({ id: id }).then((res) => {
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


