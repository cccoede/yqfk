<template>
  <el-row class="home" :gutter="20">
    <el-col :span="24" style="margin-top: 0px">
      <el-card shadow="hover">
        <div align="center" style="font-size: 30px">
          欢迎使用疫情防控管理系统
          <br /><br />
        </div>

        <div class="user">
          <img :src="userImg" height="100px" />
          <div class="userinfo">
            <p class="name">您好，{{ username }}</p>
            <p class="access">系统权限，{{ role }}</p>
          </div>
        </div>
        <div class="login-info">
          <p>
            上次登录时间：<span>{{ lasttime }}</span>
          </p>
        </div>
      </el-card>

      <el-card style="margin-top: 10px; height: 280px">
        <el-table :data="tableData">
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
      </el-card>
    </el-col>
<show ref="refshow" ></show>

  <el-dialog
  width="80%"
      title="检测结果阳性预警，请及时复核"
      :visible.sync="isShowyj"
    >

    <el-table :data="yjtableData" stripe style="width: 100%">
        
      <el-table-column prop="sfz" label="身份证"  fixed="left" width="200"></el-table-column>
      <el-table-column prop="jieguo" label="采样结果" width="100"></el-table-column>
      <el-table-column prop="xm" label="姓名" width="200"></el-table-column>
      <el-table-column prop="address" label="采样地点" width="200"></el-table-column>
      <el-table-column prop="ctime" label="采样时间" width="200"></el-table-column>
      <el-table-column prop="jigou" label="采样机构" width="200"></el-table-column>
      <el-table-column prop="beizhu" label="备注" width="200"></el-table-column>
      
    </el-table>

     
    </el-dialog>


  </el-row>



</template>

<script>
import { homeuserinfo, gonggaolist,hesuanlist } from "../../request/api";

import show from "../gonggao/show.vue";
import store from "@/store";

export default {
  name: "home",
  components: { show },
  data() {
    return {
      userImg: "",
      username: "",
      role: "",
      lasttime: "",
      tableData: [],
      pageInfo: {
        page: 1,
        limit: 3,
      },
      yjtableData: [],
      isShowyj: false,
    };
  },
  methods: {
    showData(row) {
      this.$refs.refshow.open(row.id);
    },

  },
  mounted() {
    homeuserinfo().then((res) => {
      if (res.code === 0) {
        this.userImg = res.data.pic;
        this.username = res.data.name;
        let rolevalue = res.data.role;
        if (rolevalue === 1) {
          this.role = "管理员";
        }
        if (rolevalue === 2) {
          this.role = "工作人员";
        }
        if (rolevalue === 3) {
          this.role = "易感人群";
        }
        this.lasttime = res.data.lasttime;
      } else {
        this.$message.error(res.msg);
      }
    });

    let q = Object.assign({}, this.pageInfo);
    gonggaolist(q).then((res) => {
      this.tableData = res.pageResult.list;
    });

    let str = store.state.user.token;
    let rolevalue = str.charAt(0);

    if(rolevalue==='1'){
      let f = Object.assign({}, {"jieguo":"阳性","status":"未复核"});
      hesuanlist(f).then((res) => {
      if(JSON.stringify(res.pageResult.list) !== "[]"){
        this.yjtableData = res.pageResult.list;
        this.isShowyj = true;
      }
    });
    }
    

 
  },
};
</script>



