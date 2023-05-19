<template>
  <div class="container">
    <el-row justify="center">
      <el-col :lg="13" :md="16" :sm="19" :xs="22">
        <el-form ref="formRef" size="large" :rules="rules" :model="form" label-width="150px">
          <el-form-item>
            <el-alert type="info" show-icon :closable="false" style="line-height: 100%;">
              <p>上传多张图片时，对每张图片添加相同的图像标题和水印内容。</p>
            </el-alert>
          </el-form-item>
          <el-form-item label="图像标题" prop="title">
            <el-input type="textarea"
                      placeholder="留空为上传时间"
                      :autosize="{minRows:2,}"
                      v-model="form.title"
                      clearable/>
          </el-form-item>
          <el-form-item label="水印内容" prop="content">
            <el-input type="textarea"
                      placeholder="仅支持英文，留空为用户名"
                      :autosize="{minRows:2,}"
                      v-model="form.content"
                      clearable
            />
          </el-form-item>
          <el-form-item label="上传图像(可以多张)" prop="fileList">
            <el-upload
                class="upload-demo"
                ref="imageRef"
                list-type="picture-card"
                drag
                multiple
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
            <el-button type="primary" @click="onSubmit(formRef)">图像版权存证</el-button>
            <el-button @click="onReset(formRef,imageRef)">清空</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {defineComponent, ref} from 'vue';
import {ElMessage} from 'element-plus';
import request from "@/utils/request";
import {now} from "@/utils/common";

export default defineComponent({
  name: 'upload',
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
        content: [{
          pattern: /^[a-zA-Z0-9\s\-_.!?,']+$/,
          type: 'string',
          message: '仅支持英文字母、英文字符、数字的水印内容',
          trigger: 'blur'
        }]
      },
      form: {
        title: '',
        content: '',
        fileList: [],
      },
      viewerVisible: false,
      previewIdx: 0
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
          const username = localStorage.getItem("username");
          const events = [];
          for (const fileIdx in this.form.fileList) {
            const file = this.form.fileList[fileIdx];
            const copyright = {
              title: this.form.title === "" ? now() : this.form.title,
              content: this.form.content === "" ? username : this.form.content,
              url: file.uploadUrl,
              name: file.name
            }
            events.push(this.uploadCopyright(copyright));
          }
          await Promise.all(events);
          ElMessage.success(`版权存证成功`);
          this.onReset(this.formRef, this.imageRef);
        } else {
          return false;
        }
      });
    },
    async uploadCopyright(copyright) {
      try {
        return request.post(`/copyright/create`, copyright, {
          headers: {
            'Content-Type': 'application/json'
          }
        });
      } catch (err) {
        ElMessage.error(`${copyright.name}存证失败，${err.response.data}`);
      }
    },
    // 重置
    onReset(formRef, imageRef) {
      if (!formRef) return;
      imageRef.clearFiles();
      formRef.resetFields();
    },
  },
})
;
</script>