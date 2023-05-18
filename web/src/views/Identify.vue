<template>
  <div class="container">
    <el-row justify="center" :gutter="10">
      <el-col :lg="6" :md="8" :sm="8" class="col-left">
        <el-form ref="formRef"
                 class="identify-form"
                 size="large"
                 label-position="top"
                 :rules="rules"
                 :model="form">
          <el-form-item>
            <el-alert type="info" show-icon :closable="false" style="line-height: 100%;">
              <p>上传多张图片时，提取每张图像的水印内容。</p>
            </el-alert>
          </el-form-item>
          <el-form-item label="上传待鉴定图像（可多张）" prop="fileList">
            <el-upload
                class="upload-demo"
                ref="imageRef"
                list-type="picture-card"
                drag
                accept=".jpg,.png,.jpeg"
                :auto-upload="false"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-change="handleChange">
              <el-icon class="el-icon--upload">
                <upload-filled/>
              </el-icon>
              <div class="el-upload__text">
                将图像拖拽至此处或 <em>点击上传</em>
              </div>
              <template #tip>
                <div class="el-upload__tip">
                  仅支持JPG/PNG格式图像
                </div>
              </template>
            </el-upload>
            <el-image-viewer
                v-if="viewerVisible"
                @close="toggleViewerVisible"
                :initial-index="previewIdx"
                :url-list="Object.values(form.fileList).map(file=>file.url)"
                :z-index="3000"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit(formRef)">图像版权水印鉴定</el-button>
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :lg="18" :md="16" :sm="14">
        <el-carousel
            v-loading="loading"
            trigger="click"
            :autoplay="false">
          <el-carousel-item v-if="!watermarks.length">
            <h2>提取后的水印显示在此处</h2>
          </el-carousel-item>
          <el-carousel-item v-for="(watermark,idx) in watermarks">
            <el-image
                alt="点击查看大图"
                hide-on-click-modal
                preview-teleported
                fit="cover"
                :initial-index="idx"
                :src="watermark"
                :preview-src-list="watermarks">
              <template #placeholder>
                <div class="image-slot">Loading<span class="dot">...</span></div>
              </template>
            </el-image>
          </el-carousel-item>
        </el-carousel>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {defineComponent, ref} from 'vue';
import {ElMessage} from 'element-plus';
import request from "@/utils/request";
import {UploadFilled} from "@element-plus/icons-vue";

export default defineComponent({
  name: 'identify',
  components: {UploadFilled},
  setup() {
    return {
      formRef: ref(),
      imageRef: ref(),
    }
  },
  data() {
    return {
      rules: {
        fileList: [{required: true, min: 1, type: 'array', message: '请上传图像', trigger: 'blur'}],
      },
      form: {
        fileList: [],
      },
      viewerVisible: false,
      previewIdx: 0,
      watermarks: [],
      loading: false,
    };
  },
  methods: {
    async uploadImage(file) {
      return await request.post('/upload/file', {
        file: file
      }, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
    },
    async handleChange(file, files) {
      try {
        file.uploadUrl = await this.uploadImage(file.raw);
        this.form.fileList = files;
        ElMessage.success("图像上传成功");
      } catch (e) {
        this.imageRef.handleRemove(file);
        ElMessage.error("图像上传失败");
      }
      console.log(this.form.fileList);
    },
    handlePreview(file) {
      this.previewIdx = this.form.fileList.indexOf(file);
      this.toggleViewerVisible();
    },
    toggleViewerVisible() {
      this.viewerVisible = !this.viewerVisible;
    },
    handleRemove(file, files) {
      this.form.fileList = files;
    },
    onSubmit(formRef) {
      // 表单校验
      if (!formRef) return;
      formRef.validate(async (valid) => {
        if (valid) {
          this.loading = true;
          const events = [];
          for (const fileIdx in this.form.fileList) {
            const file = this.form.fileList[fileIdx];
            events.push(this.identifyCopyright(file.uploadUrl));
          }
          this.watermarks = await Promise.all(events);
          this.loading = false;
        } else {
          return false;
        }
      });
    },
    async identifyCopyright(url) {
      try {
        return request.get(`/copyright/watermark`, {
          params: {
            url: url
          }
        });
      } catch (err) {
        ElMessage.error(`水印提取失败，${err.response.data}`);
      }
    }
  },
})
;
</script>
<style>
.identify-form .el-form-item__error {
  left: 50%;
  transform: translateX(-50%);
}

.identify-form .el-form-item__label {
  text-align: center !important;
}

.identify-form .el-form-item__content * {
  margin-left: auto !important;
  margin-right: auto !important;
}

.el-carousel__container {
  text-align: center;
  height: 100%;
}

.el-image {
  width: 100%;
}

.el-carousel__item {
  display: flex;
  align-items: center;
  justify-content: center;
}

.el-carousel__item > .el-image {
  height: 100%;
}

.el-carousel {
  height: 100%;
}

.col-left {
  border-right: solid 1px rgb(220, 223, 230);
}
</style>