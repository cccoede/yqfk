<template>
  <div>
    <el-dialog title="查看详情" :visible.sync="visible">
      <el-form ref="form" :model="showForm" label-width="80px">
        <el-form-item label="报备内容" prop="bcontent">
          <el-col :span="16">
            <el-input v-model="showForm.bcontent" type="textarea"  readonly="readonly"></el-input>
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
import { baobeiByid } from "../../request/api";

export default {
  data() {
    return {
      visible: false,
      showForm: {},
    };
  },
  methods: {
    open(id) {
      baobeiByid({ id: id }).then((res) => {
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


