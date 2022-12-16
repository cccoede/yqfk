<template>
  <div>
    
    <div class="manage-header">

    </div>

    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column prop="sfz" label="身份证"  fixed="left" width="300"></el-table-column>
      <el-table-column prop="xm" label="姓名" width="200"></el-table-column>
      <el-table-column prop="address" label="采样地点" width="200"></el-table-column>
      <el-table-column prop="ctime" label="采样时间" width="300"></el-table-column>
      <el-table-column prop="jigou" label="采样机构" width="300"></el-table-column>
      <el-table-column prop="jieguo" label="采样结果" width="300"></el-table-column>
      <el-table-column prop="beizhu" label="备注" width="200"></el-table-column>
      <el-table-column label="操作" fixed="right" width="300" >
        <template slot-scope="scope">
          <el-button size="mini" type="success" @click="showData(scope.row)"
            ><i class="el-icon-share"></i>详情</el-button
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
  hesuanlist,
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
      hesuanlist(q).then((res) => {
        this.tableData = res.pageResult.list;
        this.total = res.pageResult.totalCount
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
