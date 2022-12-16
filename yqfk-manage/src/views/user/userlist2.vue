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
       
        <el-form-item label="用户名(身份证)" prop="username">
          <el-col :span="16">
            <el-input v-model="operateForm.username" :readonly="userreadonly" placeholder="请输入用户名(身份证)"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="姓名" prop="name">
          <el-col :span="16">
            <el-input v-model="operateForm.name" placeholder="请输入姓名"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="手机号码" prop="tel">
          <el-col :span="16">
            <el-input v-model="operateForm.tel" placeholder="请输入手机号码"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="工作学习单位" prop="workunit">
          <el-col :span="16">
            <el-input v-model="operateForm.workunit" placeholder="请输入工作单位"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="户籍地" prop="hjd">
          <el-col :span="16">
            <el-input v-model="operateForm.hjd" placeholder="请输入户籍地"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="居住地" prop="jzd">
          <el-col :span="16">
            <el-input v-model="operateForm.jzd" placeholder="请输入居住地"></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="性别" prop="sex">
          <el-radio-group v-model="operateForm.sex">
            <el-radio label="男"></el-radio>
            <el-radio label="女"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="文化程度" prop="whcd">
          <el-col :span="16">
            <el-input v-model="operateForm.whcd" placeholder="请输入文化程度"></el-input>
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
        <el-form-item label="用户名(身份证)">
          <el-input
            v-model="queryFrom.username"
            placeholder="请输入用户名(身份证)"
          ></el-input>
        </el-form-item>

        <el-form-item label="姓名">
          <el-input
            v-model="queryFrom.name"
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
      <el-table-column prop="username" label="用户名(身份证)"  fixed="left" width="300"></el-table-column>
      <el-table-column prop="name" label="姓名" width="200"></el-table-column>
      <el-table-column prop="tel" label="手机号码" width="200"></el-table-column>
      <el-table-column prop="workunit" label="工作学习单位" width="300"></el-table-column>
      <el-table-column prop="hjd" label="户籍地" width="300"></el-table-column>
      <el-table-column prop="jzd" label="居住地" width="300"></el-table-column>
      <el-table-column prop="sex" label="性别" width="200"></el-table-column>
      <el-table-column prop="whcd" label="文化程度" width="200"></el-table-column>
      <el-table-column prop="beizhu" label="备注" width="200"></el-table-column>
      <el-table-column prop="ctime" label="添加时间" width="200" ></el-table-column>
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
  userlist,
  userdel,
  useradd,
  userByid,
  userupdate,
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
        username: [
          { required: true, message: "用户名(身份证)不能为空", trigger: "blur" },
          { min: 18,  message: "身份证长度必须18位", trigger: "blur" },
          { max: 18,  message: "身份证长度必须18位", trigger: "blur" },
        ],
        name: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
        ],
        workunit: [
          { required: true, message: "工作单位不能为空", trigger: "blur" },
        ],
        tel: [ 
          { required: true, message: "手机号码不能为空", trigger: "blur" },
          {
            pattern: /^0?1[3,4,5,6,7,8,9][0,1,2,3,4,5,6,7,8,9]\d{8}$/,
            message: "手机号码格式不正确",
            trigger: "blur",
          },
        ],
        hjd: [
          { required: true, message: "户籍地不能为空", trigger: "blur" },
        ],
        jzd: [
          { required: true, message: "居住地不能为空", trigger: "blur" },
        ],
        sex: [
          { required: true, message: "性别不能为空", trigger: "blur" },
        ],
      },
      userreadonly: false,
      
    };
  },
  methods: {
    loadData() {
      let q = Object.assign({}, this.pageInfo, this.queryFrom,{role:3})
      userlist(q).then((res) => {
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
        userdel({ id: row.id }).then((res) => {
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
          userupdate(this.operateForm).then((res) => {
            if (res.code === 0) {
              this.$message.success(res.msg);
              this.loadData();
            } else {
              this.$message.error(res.msg);
            }
            this.isShow = false;
          });
        } else {
          let q = Object.assign({}, this.operateForm,{role:3})
          useradd(q).then((res) => {
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
      userByid({ id: row.id }).then((res) => {
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
