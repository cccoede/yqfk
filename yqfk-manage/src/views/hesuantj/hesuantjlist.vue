<template>
  <div>
    <div class="manage-header"></div>

    <el-table :data="tableData" stripe style="width: 100%">
      <el-table-column
        prop="renshu"
        label="易感人数"
        fixed="left"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="yi"
        label="核酸检测一天内"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="san"
        label="核酸检测一天到三天"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="wu"
        label="核酸检测三天到五天"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="qi"
        label="核酸检测五天到七天"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="ba"
        label="核酸检测大于七天"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="yilv"
        label="核酸检测一天内占比"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="sanlv"
        label="核酸检测一天到三天占比"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="wulv"
        label="核酸检测三天到五天占比"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="qilv"
        label="核酸检测五天到七天占比"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="balv"
        label="核酸检测大于七天占比"
        width="200"
      ></el-table-column>
    </el-table>

    <el-card style="height: 450px">
      <div style="height: 450px" ref="echarts"></div>
    </el-card>


  </div>
</template>

<script>
import { hesuantjlist, hesuantjtongji } from "../../request/api";

import * as echarts from "echarts";

export default {
  data() {
    return {
      tableData: [],
    };
  },
  methods: {
    loadData() {
      let q = Object.assign({}, this.pageInfo, this.queryFrom);
      hesuantjlist(q).then((res) => {
        this.tableData = res.pageResult.list;
        this.total = res.pageResult.totalCount;
      });
    },
  },
  mounted() {
    this.loadData();

    hesuantjtongji().then((res) => {
      //饼状图
      const videoOption = {
        tooltip: {
          trigger: "item",
        },
        legend: {
          top: "5%",
          left: "center",
        },
        series: [
          {
            type: "pie",
            radius: ["40%", "70%"],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: "#fff",
              borderWidth: 2,
            },
            label: {
              show: false,
              position: "center",
            },
            emphasis: {
              label: {
                show: true,
                fontSize: "40",
                fontWeight: "bold",
              },
            },
            labelLine: {
              show: false,
            },
            data: res.data,
          },
        ],
      };

      const V = echarts.init(this.$refs.echarts);
      V.setOption(videoOption);
    });

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
