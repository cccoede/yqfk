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
        prop="wei"
        label="未接种人数"
        width="100"
      ></el-table-column>
      <el-table-column
        prop="yi"
        label="已接种一针人数"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="er"
        label="已接种二针人数"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="san"
        label="已接种三针或以上人数"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="weilv"
        label="未接种率"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="yilv"
        label="一针接种率"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="erlv"
        label="二针接种率"
        width="200"
      ></el-table-column>
      <el-table-column
        prop="sanlv"
        label="三针接或以上种率"
        width="200"
      ></el-table-column>
    </el-table>

    <el-card style="height: 450px">
      <div style="height: 450px" ref="echarts"></div>
    </el-card>
  </div>
</template>

<script>
import { yimiaotjlist, yimiaotjtongji } from "../../request/api";

import * as echarts from "echarts";

export default {
  data() {
    return {
      tableData: [],
    };
  },
  methods: {
    loadData() {
      let q = Object.assign({});
      yimiaotjlist(q).then((res) => {
        this.tableData = res.pageResult.list;
      });
    },
  },
  mounted() {
    this.loadData();

    yimiaotjtongji().then((res) => {
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
