<template>
  <div>
   

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

       <el-form-item label="采样结果">
          <el-input
            v-model="queryFrom.jieguo"
            placeholder="请输入采样结果"
          ></el-input>
        </el-form-item>

        <el-form-item label="复核结果">
          <el-input
            v-model="queryFrom.status"
            placeholder="请输入复核结果"
          ></el-input>
        </el-form-item>


        <el-form-item>
          <el-button size="mini" type="primary" @click="search" icon="el-icon-search"
            >查询</el-button
          >
        </el-form-item>
      </el-form>
    </div>

    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="sfz" label="身份证"  fixed="left" width="300"></el-table-column>
      <el-table-column prop="jieguo" label="采样结果" width="300"></el-table-column>
      <el-table-column prop="status" label="复核结果" width="300"></el-table-column>
      <el-table-column prop="ftime" label="复核时间" width="300"></el-table-column>
      <el-table-column prop="xm" label="姓名" width="200"></el-table-column>
      <el-table-column prop="address" label="采样地点" width="200"></el-table-column>
      <el-table-column prop="ctime" label="采样时间" width="300"></el-table-column>
      <el-table-column prop="jigou" label="采样机构" width="300"></el-table-column>
      <el-table-column prop="beizhu" label="备注" width="200"></el-table-column>
      <el-table-column label="操作" fixed="right" width="300" >
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="showData(scope.row)"
            ><i class="el-icon-share"></i>详情</el-button
          >
          
          <el-button size="mini" type="danger" @click="delData(scope.row)" v-if="scope.row.status==='未复核'"
            ><i class="el-icon-edit"></i>复核阳性</el-button
          >
          <el-button size="mini" type="danger" @click="delData2(scope.row)" v-if="scope.row.status==='未复核'"
            ><i class="el-icon-edit"></i>复核阴性</el-button
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
  hesuanlist,hesuanupdate2,hesuanupdate3,
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
      
    };
  },
  methods: {
    loadData() {
      let q = Object.assign({}, this.pageInfo, this.queryFrom,{"jieguo":"阳性"})
      hesuanlist(q).then((res) => {
        this.tableData = res.pageResult.list;
        this.total = res.pageResult.totalCount
      });
    },

    search() {
      this.pageInfo.page = 1
      this.loadData()
    },

    delData(row) {
      this.$confirm("确定复核阳性吗?", "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        hesuanupdate2({ id: row.id }).then((res) => {
          if (res.code === 0) {
            this.$message.success(res.msg);
            this.loadData();
          } else {
            this.$message.error(res.msg);
          }
        });
      });
    },

    delData2(row) {
      this.$confirm("确定复核阴性吗?", "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        hesuanupdate3({ id: row.id }).then((res) => {
          if (res.code === 0) {
            this.$message.success(res.msg);
            this.loadData();
          } else {
            this.$message.error(res.msg);
          }
        });
      });
    },


    showData(row) {
      this.$refs.refshow.open(row.id);
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
