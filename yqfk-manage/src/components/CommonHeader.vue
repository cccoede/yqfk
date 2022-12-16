<template>
  <header>
    <div class="l-content">
      <el-button
        @click="handleMenu"
        plain
        icon="el-icon-menu"
        size="mini"
      ></el-button>
      <!-- <h3 style="color: #fff; display: inline-block">首页</h3> -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item
          v-for="item in tags"
          :key="item.path"
          :to="{ path: item.path }"
          >{{ item.label }}</el-breadcrumb-item
        >
      </el-breadcrumb>
    </div>

    <div class="r-content">
      <el-dropdown trigger="click" size="medium">
        <span>
          <img class="user" :src="userImg" />
        </span>
        <el-dropdown-menu slot="dropdown" >
          <el-dropdown-item @click.native="editUser"><h3>个人中心</h3></el-dropdown-item>
          <el-dropdown-item @click.native="editPassword">修改密码</el-dropdown-item>
          <el-dropdown-item @click.native="logOut">退出系统</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <password ref="refpassword"></password>
      <usercenter ref="refusercenter"></usercenter>
    </div>
  </header>
</template>

<script>
import { mapState } from "vuex";
import password from "./password.vue";
import usercenter from "./usercenter.vue";
import { homeuserinfo,userloginout } from "../request/api";

export default {
  name: "CommonHeader",
  components: { password,usercenter },
  data() {
    return {
      userImg: "",
    };
  },
  methods: {
    handleMenu() {
      this.$store.commit("collapseMenu");
    },
    logOut() {
      userloginout().then((res) => {
        if (res.code === 0) {
          this.$message.success(res.msg);
          sessionStorage.setItem('refresh',1)

          this.$store.commit("clearToken");
          this.$store.commit("clearMenu");
          this.$router.push("/login");
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    editPassword() {
      this.$refs.refpassword.open();
    },
    editUser() {
      this.$refs.refusercenter.open();
    },
  },
  computed: {
    ...mapState({
      tags: (state) => state.tab.tabsList,
    }),
  },
  mounted() {
    homeuserinfo().then((res) => {
      if (res.code === 0) {
        this.userImg = res.data.pic;
       
      } else {
        this.$message.error(res.msg);
      }
    });
  },
  
};
</script>

<style lang="less" scoped>
header {
  display: flex;
  height: 100%;
  justify-content: space-between;
  align-items: center;
}
.l-content {
  display: flex;
  align-items: center;
  .el-button {
    margin-right: 20px;
  }
}
.r-content {
  .user {
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }
}
/deep/ .el-breadcrumb__item {
  .el-breadcrumb__inner {
    font-weight: normal;
    &.is-link {
      color: #666;
    }
    a {
      color: #666;
    }
  }
  &:last-child {
    .el-breadcrumb__inner {
      color: #fff;
    }
  }
}
</style>