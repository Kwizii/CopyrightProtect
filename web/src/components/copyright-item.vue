<template>
    <el-card :body-style="{ padding: '3px' }" shadow="hover">
        <el-image
                class="image"
                fit='cover'
                alt="点击预览图片"
                loading="lazy"
                :src="copyright.watermarkedFileAddress"
                :preview-src-list="[copyright.watermarkedFileAddress]"
                :zoom-rate="1.33"
                :hide-on-click-modal="true"
        >
            <template #placeholder>
                <div class="image-slot">Loading<span class="dot">...</span></div>
            </template>
        </el-image>
        <div style="padding: 10px">
            <span>{{ copyright.id }}</span>
            <div class="bottom">
                <el-divider>图像信息</el-divider>
                <p>水印内容：</p>
                <p>{{ copyright.content }}</p>
                <p>区块链地址：</p>
                <p>{{ copyright.chainAddress }}</p>
                <p>创建时间：{{ copyright.createTime }}</p>
                <el-divider>哈希信息</el-divider>
                <p>MD5：</p>
                <p>{{ copyright.md5Hash }}</p>
                <p>SHA-1：</p>
                <p>{{ copyright.sha1Hash }}</p>
                <p>SHA-256：</p>
                <p>{{ copyright.sha256Hash }}</p>
            </div>
        </div>
    </el-card>
</template>

<script>

import {defineComponent} from "vue";

export default defineComponent({
    name: 'CopyrightItem',
    props: {
        copyright: {
            type: Object,
            required: true,
        },
    }, setup(props) {
        // console.log(props.copyright);
    },
})

</script>

<style scoped>
.bottom {
    font-size: 14px;
}

.bottom p {
    white-space: normal;

}

.image {
    width: 100%;
    display: block;
}

.image-slot {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background: var(--el-fill-color-light);
    color: var(--el-text-color-secondary);
    font-size: 14px;
}

.dot {
    animation: dot 2s infinite steps(3, start);
    overflow: hidden;
}
</style>
