<template>
  <div class="header">
    <!-- 折叠按钮 -->
    <div class="collapse-btn" @click="collapseChange">
      <el-icon v-if="sidebar.collapse">
        <Expand/>
      </el-icon>
      <el-icon v-else>
        <Fold/>
      </el-icon>
    </div>
    <div class="logo">
      <img style="height: 30px;" src="@/assets/img/logo.png"/>
      <span>区块链数字水印版权保护</span>
    </div>
    <div class="header-right">
      <div class="header-user-con">
        <!-- 用户名下拉菜单 -->
        <el-dropdown class="user-name" trigger="click" @command="handleCommand">
					<span class="el-dropdown-link">
						{{ username }}
						<el-icon class="el-icon--right">
							<arrow-down/>
						</el-icon>
					</span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </div>
  </div>
</template>
<script>
import {defineComponent} from 'vue';
import {useSidebarStore} from '@/store/sidebar';
import {useRouter} from 'vue-router';
import {logout, username} from "@/store/meta";
import request from "@/utils/request";

export default defineComponent({
  name: 'VHeader',
  data() {
    return {
      username: username(),
      sidebar: useSidebarStore(),
      // 用户名下拉菜单选择事件
      router: useRouter(),
    };
  },
  mounted() {
    if (document.body.clientWidth < 1200) {
      this.collapseChange();
    }
  },
  methods: {
    collapseChange() {
      this.sidebar.handleCollapse();
    },
    handleCommand(command) {
      if (command === 'logout') {
        request.get('/user/logout');
        logout();
        this.router.push('/login');
      }
    },
  }
})

</script>
<style scoped>
.header {
  position: relative;
  box-sizing: border-box;
  width: 100%;
  height: 70px;
  font-size: 22px;
  color: #fff;
}

.collapse-btn {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  float: left;
  padding: 0 21px;
  cursor: pointer;
}

.header .logo {
  float: left;
  display: flex;
  align-items: center;
  line-height: 70px;
}

.header .logo * {
  margin: auto 5px;
}

.header-right {
  float: right;
  padding-right: 50px;
}

.header-user-con {
  display: flex;
  height: 70px;
  align-items: center;
}

.user-name {
  margin-left: 10px;
}

.el-dropdown-link {
  color: #fff;
  cursor: pointer;
  display: flex;
  align-items: center;
}
</style>
