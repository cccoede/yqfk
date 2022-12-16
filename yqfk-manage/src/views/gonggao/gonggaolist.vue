<template>
  <div>
    

    <div class="manage-header">
      
      <el-form ref="searchForm" :inline="true" :model="queryFrom" size="small">
        <el-form-item label="疫情信息标题">
          <el-input
            v-model="queryFrom.gtitle"
            placeholder="请输入疫情信息标题"
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
      <el-table-column prop="gtitle" label="疫情信息标题"></el-table-column>
      <el-table-column prop="ctime" label="添加时间"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="showData(scope.row)"
            ><i class="el-icon-share"></i>查看详情</el-button
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
  gonggaolist,
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
      let q = Object.assign({}, this.pageInfo, this.queryFrom)
      gonggaolist(q).then((res) => {
        console.log(res, "res");
        this.tableData = res.pageResult.list;
        this.total = res.pageResult.totalCount
      });
    },

    search() {
      this.pageInfo.page = 1
      this.loadData()
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
