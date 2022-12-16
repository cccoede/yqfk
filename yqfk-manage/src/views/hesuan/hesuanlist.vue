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
            <el-input
              v-model="operateForm.sfz"
              :readonly="userreadonly"
              placeholder="请输入身份证"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="姓名" prop="xm">
          <el-col :span="16">
            <el-input
              v-model="operateForm.xm"
              placeholder="请输入姓名"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="采样地点" prop="address">
          <el-col :span="16">
            <el-input
              v-model="operateForm.address"
              placeholder="请输入采样地点"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="采样时间" prop="ctime">
          <el-col :span="16">
            <el-date-picker
              type="datetime"
              placeholder="请输入采样时间"
              v-model="operateForm.ctime"
              value-format="yyyy-MM-dd HH:mm:ss"
              style="width: 100%"
            ></el-date-picker>
          </el-col>
        </el-form-item>

        <el-form-item label="采样机构" prop="jigou">
          <el-col :span="16">
            <el-input
              v-model="operateForm.jigou"
              placeholder="请输入采样机构"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="采样结果" prop="jieguo">
          <el-radio-group v-model="operateForm.jieguo">
            <el-radio label="阴性"></el-radio>
            <el-radio label="阳性"></el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="备注" prop="beizhu">
          <el-col :span="16">
            <el-input
              v-model="operateForm.beizhu"
              placeholder="请输入备注"
            ></el-input>
          </el-col>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onSubmit">提交</el-button>
          <el-button @click="isShow = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="批量导入" :visible.sync="isShowpl">
      <el-form ref="importform" :model="exceloperateForm" label-width="80px">
        <el-form-item label="导入说明">
          <el-col :span="16">
            身份证、姓名、采样地点、采样时间(2022-12-31
            09:30:30)、采样机构、采样结果(阴性或者阳性)、备注(非必填)
          </el-col>
        </el-form-item>

        <el-form-item label="">
          <el-col :span="16">
            <a href="http://localhost:8888/yqfk/uploads/核酸导入模板格式.xlsx"
              ><span style="font-size: 16px">点击下载模板</span></a
            >
          </el-col>
        </el-form-item>

        <el-form-item label="excel文件" prop="fileList">
          <el-upload
            class="upload-demo"
            action=""
            accept=".xlsx"
            :before-upload="beforeUpload"
            :http-request="uploadFile"
            :on-remove="handleRemove"
            :before-remove="beforeRemove"
            multiple
            :limit="limit"
            :on-exceed="handleExceed"
            :file-list="fileList"
          >
            <el-button size="medium" type="primary">选择文件</el-button>
            <div slot="tip" class="el-upload__tip">
              <h3>只能上传xlsx后缀文件且不超过10MB</h3>
            </div>
          </el-upload>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="onimportSubmit">开始导入</el-button>
          <el-button @click="isShowpl = false">取消</el-button>
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
          <el-input v-model="queryFrom.xm" placeholder="请输入姓名"></el-input>
        </el-form-item>

        <el-form-item label="采样结果">
          <el-input
            v-model="queryFrom.jieguo"
            placeholder="请输入采样结果"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button
            size="mini"
            type="primary"
            @click="search"
            icon="el-icon-search"
            >查询</el-button
          >
          <el-button size="mini" type="success" @click="addData"
            ><i class="el-icon-plus"></i>新增</el-button
          >

          <el-button
            size="mini"
            type="danger"
            v-if="this.rolevalue === '1'"
            @click="importData"
            ><i class="el-icon-plus"></i>批量导入</el-button
          >

          <el-button
            size="mini"
            type="danger"
            v-if="this.rolevalue === '1'"
            @click="exportData"
          >
            <i class="el-icon-d-arrow-right"></i>按查询条件批量导出</el-button
          >
        </el-form-item>
      </el-form>
    </div>

    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column
        prop="sfz"
        label="身份证"
        fixed="left"
        width="300"
      ></el-table-column>
      <el-table-column
        prop="jieguo"
        label="采样结果"
        width="300"
      ></el-table-column>
      <el-table-column prop="xm" label="姓名" width="200"></el-table-column>
      <el-table-column
        prop="address"
        label="采样地点"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="ctime"
        label="采样时间"
        width="300"
      ></el-table-column>
      <el-table-column
        prop="jigou"
        label="采样机构"
        width="300"
      ></el-table-column>
      <el-table-column prop="beizhu" label="备注" width="200"></el-table-column>
      <el-table-column label="操作" fixed="right" width="300">
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

    <show ref="refshow"></show>
  </div>
</template>

<script>
import {
  hesuanlist,
  hesuandel,
  hesuanadd,
  hesuanByid,
  hesuanupdate,
  hesuanexceladd,
  hesuanexcelout,
} from "../../request/api";
import show from "./show.vue";

import axios from "axios"; // 引入axios
import store from "@/store";

export default {
  components: { show },
  data() {
    return {
      tableData: [],
      queryFrom: {},
      pageInfo: {
        page: 1,
        limit: 10,
      },
      total: 0,
      operateType: "add",
      isShow: false,
      operateForm: {},
      rules: {
        sfz: [
          { required: true, message: "身份证不能为空", trigger: "blur" },
          { min: 18, message: "身份证长度必须18位", trigger: "blur" },
          { max: 18, message: "身份证长度必须18位", trigger: "blur" },
        ],
        xm: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        address: [
          { required: true, message: "采样地点不能为空", trigger: "blur" },
        ],
        ctime: [
          { required: true, message: "采样时间不能为空", trigger: "blur" },
        ],
        jigou: [
          { required: true, message: "采样机构不能为空", trigger: "blur" },
        ],
        jieguo: [
          { required: true, message: "采样结果不能为空", trigger: "blur" },
        ],
      },
      userreadonly: false,
      isShowpl: false,
      fileList: [],
      limit: 3,
      exceloperateForm: {},
      rolevalue: "",
    };
  },
  methods: {
    loadData() {
      let str = store.state.user.token;
      this.rolevalue = str.charAt(0);
      let q = Object.assign({}, this.pageInfo, this.queryFrom);
      hesuanlist(q).then((res) => {
        this.tableData = res.pageResult.list;
        this.total = res.pageResult.totalCount;
      });
    },

    search() {
      this.pageInfo.page = 1;
      this.loadData();
    },

    delData(row) {
      this.$confirm("此操作将永久删除该项, 是否继续?", "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        hesuandel({ id: row.id }).then((res) => {
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
      this.operateForm = {};
      this.userreadonly = false;
    },

    onSubmit() {
      this.$refs.form.validate((valid) => {
        if (!valid) {
          return false;
        }
        if (this.operateType === "edit") {
          hesuanupdate(this.operateForm).then((res) => {
            if (res.code === 0) {
              this.$message.success(res.msg);
              this.loadData();
            } else {
              this.$message.error(res.msg);
            }
            this.isShow = false;
          });
        } else {
          hesuanadd(this.operateForm).then((res) => {
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
      hesuanByid({ id: row.id }).then((res) => {
        if (res.code != 0) {
          this.$message.error("对应记录不存在");
          return;
        }
        this.operateForm = res.data;
      });
    },

    handleExceed() {
      this.$message.warning("只能上传" + this.limit + "个文件");
    },

    handleRemove(file, fileList) {
      if (file && file.status === "success") {
        this.fileList = fileList;
      }
    },

    beforeRemove(file) {
      if (file && file.status === "success") {
        return this.$confirm(`确定移除 ${file.name}？`);
      }
    },

    beforeUpload(file) {
      let extension = file.name.substring(file.name.lastIndexOf(".") + 1);
      const acceptFiles = ["xls", "xlsx"];

      if (acceptFiles.indexOf(extension) == -1) {
        this.$message.error("上传xlsx后缀格式文件!");

        return false;
      }
      const isLt10M = file.size / 1024 / 1024 < 10;

      if (!isLt10M) {
        this.$message.error("上传文件大小不能超过10MB!");
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
        .post("/api/file/upload/file", formData, {
          "Content-type": "multipart/form-data",
        })
        .then((Response) => {
          if (Response.data.code === 0) {
            this.fileList.push({
              name: Response.data.name,
              url: Response.data.url,
            });
          } else {
            this.$message.error("上传异常");
          }
        });
    },

    importData() {
      this.isShowpl = true;
    },

    onimportSubmit() {
      if (Object.keys(this.fileList).length != 1) {
        this.$message.error("请选择上传的Excel文件");
      } else {
        let q = Object.assign({}, this.operateForm, {
          excelpath: this.fileList[0].url,
        });
        hesuanexceladd(q).then((res) => {
          if (res.code === 0) {
            this.$message.success(res.msg);
            this.loadData();
          } else {
            this.$message.error(res.msg);
          }
          this.isShowpl = false;
        });
      }
    },

    exportData() {
      let q = Object.assign({}, this.queryFrom);
      hesuanexcelout(q).then((res) => {
        this.$confirm("导出成功,是否下载到电脑", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "success",
        })
          .then(() => {
            window.location.href = res.url;
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消",
            });
          });
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
