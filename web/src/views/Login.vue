<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">图像数字版权保护系统</div>
      <el-form :model="params" :rules="rules" ref="form" label-width="0px" class="ms-content">
        <el-form-item prop="username">
          <el-input v-model="params.username" placeholder="用户名">
            <template #prepend>
              <el-button :icon="User"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              type="password"
              placeholder="密码"
              show-password
              v-model="params.password"
              @keyup.enter="submitForm(form)">
            <template #prepend>
              <el-button :icon="Lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm(form)">登录</el-button>
        </div>
        <!--                <p class="login-tips">Tips : 用户名和密码随便填。</p>-->
      </el-form>
    </div>
  </div>
</template>

<script>
import {defineComponent, ref} from 'vue';
import {useRouter} from 'vue-router';
import {ElMessage} from 'element-plus';
import {Lock, User} from "@element-plus/icons-vue";
import request from "@/utils/request";
import {login} from "@/store/meta";

export default defineComponent({
  setup() {
    return {
      form: ref()
    };
  },
  data() {
    return {
      router: useRouter(),
      params: {
        username: 'admin',
        password: '123456'
      },
      rules: {
        username: [{required: true, message: '请输入用户名', trigger: 'blur'}],
        password: [{required: true, message: '请输入密码', trigger: 'blur'}]
      },
    };
  },
  methods: {
    submitForm(formEl) {
      if (!formEl) return;
      formEl.validate((valid) => {
        console.log(this);
        if (valid) {
          this.login();
        } else {
          return false;
        }
      });
    },
    login() {
      request.get("/user/login", {params: this.params})
          .then(data => {
            console.log(data);
            login(data);
            ElMessage.success('登录成功');
            this.router.push('/');
          }, err => {
            ElMessage.error(err.response.data);
          })

    },
  },
  computed: {
    User() {
      return User
    },
    Lock() {
      return Lock
    }
  },
});

</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-size: 100%;
}

.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}

.ms-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #fff;
}
</style>
