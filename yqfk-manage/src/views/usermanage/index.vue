<template>
  <div>
    

    <div class="manage-header">
      

      <el-form ref="searchForm" :inline="true" :model="queryFrom" size="small" >
        <el-form-item label="">
          <el-input
            v-model="queryFrom.username"
            placeholder="请输入用户名"
          ></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input
            v-model="queryFrom.name"
            placeholder="请输入姓名"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-button size="mini" type="primary" @click="search" icon="el-icon-search">查询</el-button>
        </el-form-item>
      </el-form>
    </div>

    <el-table :data="tableData" stripe  border style="width: 100%">
      <el-table-column prop="username" label="用户名" fixed="left" width="300" ></el-table-column>
      <el-table-column prop="name" label="姓名" width="200" ></el-table-column>
      <el-table-column prop="role" label="用户角色"  width="200"></el-table-column>
      <el-table-column prop="status" label="账户状态"  width="200"></el-table-column>
      <el-table-column prop="lasttime" label="上次登录时间" width="200" ></el-table-column>
      <el-table-column prop="ctime" label="添加时间" width="200"></el-table-column>
     
      <el-table-column label="操作" fixed="right" width="300">
        <template slot-scope="scope">

          <el-button size="mini" type="danger" @click="resetpassword(scope.row)"
            ><i class="el-icon-edit"></i>重置密码</el-button
          >

          <el-button size="mini" type="warning" @click="resetstatus2(scope.row)"  v-if="scope.row.status=== '正常' "
            ><i class="el-icon-edit"></i>禁用账户</el-button
          >

          <el-button size="mini" type="warning" @click="resetstatus3(scope.row)"  v-if="scope.row.status=== '禁用' "
            ><i class="el-icon-edit"></i>启用账户</el-button
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

  </div>
</template>

<script>
import {
  usermanagelist,
  passwordreset,
  resetstatus,

} from "../../request/api";

export default{
  data() {
    return {
      tableData: [],
      queryFrom: {
      },
      pageInfo: {
        page: 1,
        limit: 10,
      },
      total: 0,
    };
  },
  methods: {
    loadData() {
      let q = Object.assign({}, this.pageInfo, this.queryFrom);
      usermanagelist(q).then((res) => {
        this.tableData = res.pageResult.list.map(item => {
                    item.role = item.role === 2 ? "工作人员" : "易感人群"
                    return item
        })

        this.total = res.pageResult.totalCount;
      });

    },

    search() {
      this.pageInfo.page = 1;
      this.loadData();
    },

    resetpassword(row) {
      this.$confirm("此操作将重置该用户的密码为111111, 是否继续?", "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        passwordreset({ id: row.id }).then((res) => {
          if (res.code === 0) {
            this.$message.success(res.msg);
            this.loadData();
          } else {
            this.$message.error(res.msg);
          }
        });
      });
    },

    resetstatus2(row) {
      this.$confirm("此操作将禁用账户, 是否继续?", "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        resetstatus({ id: row.id, status: '禁用' }).then((res) => {
          if (res.code === 0) {
            this.$message.success(res.msg);
            this.loadData();
          } else {
            this.$message.error(res.msg);
          }
        });
      });
    },

     resetstatus3(row) {
      this.$confirm("此操作将启用账户, 是否继续?", "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        resetstatus({ id: row.id, status: '正常' }).then((res) => {
          if (res.code === 0) {
            this.$message.success(res.msg);
            this.loadData();
          } else {
            this.$message.error(res.msg);
          }
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
