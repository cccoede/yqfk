<template>
  <div>
    <el-dialog title="查看详情" :visible.sync="visible">
      <el-form ref="form" :model="showForm" label-width="80px">
        <el-form-item label="疫情信息标题" prop="gtitle">
          <el-col :span="16">
            <el-input v-model="showForm.gtitle" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        

        <el-form-item label="添加时间" prop="ctime">
          <el-col :span="16">
            <el-input v-model="showForm.ctime" readonly="readonly"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="疫情信息内容" prop="gcontent">
          <el-col :span="16">
            <el-input
              type="textarea"
              v-model="showForm.gcontent"
              readonly="readonly"
            ></el-input>
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
import { gonggaoByid } from "../../request/api";

export default {
  data() {
    return {
      visible: false,
      showForm: {},
    };
  },
  methods: {
    open(id) {
      gonggaoByid({ id: id }).then((res) => {
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


