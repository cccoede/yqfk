<template>
  <div>
    <el-dialog
      :title="operateType === 'add' ? '新增' : '编辑'"
      :visible.sync="isShow"
    >
      <el-form
        ref="form"
        :model="operateForm"
        label-width="80px"
        :rules="rules"
      >
        <el-form-item label="身份证" prop="sfz">
          <el-col :span="16">
            <el-input v-model="operateForm.sfz" :readonly="userreadonly" placeholder="请输入身份证"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="姓名" prop="xm">
          <el-col :span="16">
            <el-input v-model="operateForm.xm" placeholder="请输入姓名"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="疫苗名称" prop="ymc">
          <el-col :span="16">
            <el-input v-model="operateForm.ymc" placeholder="请输入疫苗名称"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="疫苗厂家" prop="ymgs">
          <el-col :span="16">
            <el-input v-model="operateForm.ymgs" placeholder="请输入疫苗厂家"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="接种地点" prop="jdzd">
          <el-col :span="16">
            <el-input v-model="operateForm.jdzd" placeholder="请输入接种地点"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="接种时间" prop="ctime">
          <el-col :span="16">
            <el-date-picker
              type="datetime"
              placeholder="请输入接种时间"
              v-model="operateForm.ctime"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
            ></el-date-picker>
          </el-col>
        </el-form-item>

        <el-form-item label="备注" prop="beizhu">
          <el-col :span="16">
            <el-input v-model="operateForm.beizhu" placeholder="请输入备注"></el-input>
          </el-col>
        </el-form-item>



        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
          <el-button @click="isShow = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <div class="manage-header">
      
      <el-form ref="searchForm" :inline="true" :model="queryFrom" size="small">
        <el-form-item label="身份证">
          <el-input
            v-model="queryFrom.sfz"
            placeholder="请输入身份证"
          ></el-input>
        </el-form-item>

        <el-form-item label="姓名">
          <el-input
            v-model="queryFrom.xm"
            placeholder="请输入姓名"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button size="mini" type="primary" @click="search" icon="el-icon-search"
            >查询</el-button
          >
          <el-button size="mini" type="success" @click="addData"><i class="el-icon-plus"></i>新增</el-button>

        </el-form-item>
      </el-form>
    </div>

    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="sfz" label="身份证"  fixed="left" width="300"></el-table-column>
      <el-table-column prop="xm" label="姓名" width="200"></el-table-column>
      <el-table-column prop="ymc" label="疫苗名称" width="200"></el-table-column>
      <el-table-column prop="ymgs" label="疫苗厂家" width="300"></el-table-column>
      <el-table-column prop="jdzd" label="接种地点" width="300"></el-table-column>
      <el-table-column prop="ctime" label="接种时间" width="300"></el-table-column>
      <el-table-column prop="beizhu" label="备注" width="200"></el-table-column>
      <el-table-column label="操作" fixed="right" width="300" >
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="showData(scope.row)"
            ><i class="el-icon-share"></i>详情</el-button
          >
          <el-button size="mini" type="warning" @click="editData(scope.row)"
            ><i class="el-icon-edit"></i>编辑</el-button
          >
          <el-button size="mini" type="danger" @click="delData(scope.row)"
            ><i class="el-icon-delete"></i>删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <div class="page_wrap">
        <el-pagination
          ref="refPagination"
          layout="total,sizes,prev,pager,next,jumper"
          :total="total"
          :page-sizes="[10, 20, 30, 40]"
          :current-page.sync="pageInfo.page"
          :page-size.sync="pageInfo.limit"
          @current-change="loadData"
          @size-change="search"
        ></el-pagination>
    </div>

 <show ref="refshow" ></show>
  </div>
</template>

<script>
import {
  yimiaolist,
  yimiaodel,
  yimiaoadd,
  yimiaoByid,
  yimiaoupdate,
} from "../../request/api";
import show from "./show.vue";

export default {
  components: { show },
  data() {
    return {
      tableData: [],
      queryFrom: {
      },
      pageInfo: {
        page: 1,
        limit: 10
      },
      total: 0,
      operateType: "add",
      isShow: false,
      operateForm: {
      },
      rules: {
        sfz: [
          { required: true, message: "身份证不能为空", trigger: "blur" },
          { min: 18,  message: "身份证长度必须18位", trigger: "blur" },
          { max: 18,  message: "身份证长度必须18位", trigger: "blur" },
        ],
        xm: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
        ],
        ymc: [
          { required: true, message: "疫苗名称不能为空", trigger: "blur" },
        ],
        ymgs: [
          { required: true, message: "疫苗厂家不能为空", trigger: "blur" },
        ],
        jdzd: [
          { required: true, message: "接种地点不能为空", trigger: "blur" },
        ],
        ctime: [
          { required: true, message: "接种时间不能为空", trigger: "blur" },
        ],
      },
      userreadonly: false,
      
    };
  },
  methods: {
    loadData() {
      let q = Object.assign({}, this.pageInfo, this.queryFrom)
      yimiaolist(q).then((res) => {
        console.log(res, "res");
        this.tableData = res.pageResult.list;
        this.total = res.pageResult.totalCount
      });
    },

    search() {
      this.pageInfo.page = 1
      this.loadData()
    },

    delData(row) {
      this.$confirm("此操作将永久删除该项, 是否继续?", "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        yimiaodel({ id: row.id }).then((res) => {
          if (res.code === 0) {
            this.$message.success(res.msg);
            this.loadData();
          } else {
            this.$message.error(res.msg);
          }
        });
      });
    },

    addData() {
      this.isShow = true;
      this.operateType = "add";
      this.operateForm = {
      };
      this.userreadonly = false;
    },

    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return false;
        }
        if (this.operateType === "edit") {
          yimiaoupdate(this.operateForm).then((res) => {
            if (res.code === 0) {
              this.$message.success(res.msg);
              this.loadData();
            } else {
              this.$message.error(res.msg);
            }
            this.isShow = false;
          });
        } else {
          yimiaoadd(this.operateForm).then((res) => {
            if (res.code === 0) {
              this.$message.success(res.msg);
              this.loadData();
            } else {
              this.$message.error(res.msg);
            }

            this.isShow = false;
          });
        }
      });
    },

    editData(row) {
      this.operateType = "edit";
      this.isShow = true;
      this.loadDataByid(row);
      this.userreadonly = true;
    },

    showData(row) {
      this.$refs.refshow.open(row.id);
    },

    loadDataByid(row) {
      yimiaoByid({ id: row.id }).then((res) => {
        if (res.code != 0) {
          this.$message.error("对应记录不存在");
          return;
        }
        this.operateForm = res.data;
      });
    },
  },
  mounted() {
    this.loadData();
  },
};
</script>

<style lang="less" scoped>
.manage-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.page_wrap {
  text-align: left;
  padding-top: 10px;
}
</style>
