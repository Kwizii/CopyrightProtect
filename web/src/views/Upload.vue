<template>
    <div class="container">
        <el-row justify="center">
            <el-col :lg="13" :md="16" :sm="19" :xs="22">
                <el-form ref="formRef" size="large" :rules="rules" :model="form" label-width="150px">
                    <el-col :span="24" class="mgb20" style="text-align: center">

                    </el-col>
                    <el-form-item>
                        <el-alert type="info" show-icon :closable="false" style="line-height: 100%;">
                            <p>上传多张图片时，对每张图片添加相同的图像描述和水印内容。</p>
                        </el-alert>
                    </el-form-item>
                    <el-form-item label="图像描述" prop="description">
                        <el-input type="textarea"
                                  placeholder="留空为上传时间"
                                  :autosize="{minRows:2,}"
                                  v-model="form.description"></el-input>
                    </el-form-item>
                    <el-form-item label="水印内容" prop="content">
                        <el-input type="textarea"
                                  placeholder="留空为用户名"
                                  :autosize="{minRows:2,}"
                                  v-model="form.content"></el-input>
                    </el-form-item>
                    <el-form-item label="上传图像(可以多张)" prop="srcList">
                        <el-upload
                                class="upload-demo"
                                ref="imageRef"
                                list-type="picture-card"
                                drag
                                multiple
                                action='http://localhost/api/upload/file'
                                accept=".jpg,.png,.jpeg"
                                :on-preview="handlePictureCardPreview"
                                :file-list="fileList"
                                :on-change="handleChange"
                                :on-remove="handleRemove"
                        >
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
                        <el-dialog v-model="dialogVisible">
                            <img w-full :src="dialogImageUrl" alt="Preview Image"/>
                        </el-dialog>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="onSubmit(formRef,imageRef)">图像版权存证</el-button>
                        <el-button @click="onReset(formRef,imageRef)">清空</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import {defineComponent, reactive, ref} from 'vue';
import {ElMessage} from 'element-plus';

export default defineComponent({
    name: 'upload',
    setup() {
        return {
            formRef: ref(),
            imageRef: ref()
        }
    },
    data() {
        return {
            rules: {
                description: [{required: true, type: 'string', message: '请输入图像描述', trigger: 'blur'}],
                content: [{required: true, type: 'string', message: '请输入水印内容', trigger: 'blur'}],
                srcList: [{required: true, min: 1, type: 'array', message: '请上传图像', trigger: 'blur'}],
            },
            form: reactive({
                description: '',
                content: '',
                srcList: [],
            }),
            dialogImageUrl: '',
            dialogVisible: false,
            fileList: []
        };
    },
    methods: {
        handleChange(file, files) {
            const srcList = [];
            for (const f in files)
                srcList.push(f.url);
            this.form.srcList = srcList;
        },
        handleRemove(file, files) {
            console.log(file, files);
        },
        handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url;
            this.dialogVisible = true;
        },
        onSubmit(formRef, imageRef) {
            // 表单校验
            if (!formRef) return;
            formRef.validate((valid) => {
                if (valid) {
                    ElMessage.success('提交成功！');
                } else {
                    return false;
                }
            });
        },
        // 重置
        onReset(formRef, imageRef) {
            if (!formRef) return;
            imageRef.clearFiles();
            formRef.resetFields();
        },
    },
});
</script>
<style>
.el-upload-dragger {
    height: 100%;
}

.el-upload--picture-card {
    --el-upload-picture-card-size: 210px;
}

.el-upload-list--picture-card {
    --el-upload-list-picture-card-size: 210px;
}

.el-dialog img {
    width: 100%;
}
</style>