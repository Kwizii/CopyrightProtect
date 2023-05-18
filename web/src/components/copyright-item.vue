<template>
  <el-card :body-style="{ padding: '0px' }" shadow="hover">
    <el-popover
        placement="bottom-end"
        popper-class="op-pop"
        :show-arrow="false"
        :width="130"
        :offset="-40"
        trigger="hover">
      <el-button-group>
        <el-button color="rgb(255, 255, 255)"
                   :icon="Tickets"
                   style="color: var(--el-color-primary)"
                   @click="emitCommand(0)">详情
        </el-button>
        <el-button color="rgb(255, 255, 255)"
                   :icon="Delete"
                   style="color: var(--el-color-danger)"
                   @click="emitCommand(1)"/>
      </el-button-group>
      <template #reference>
        <el-image
            class="image"
            fit='cover'
            alt="点击预览图片"
            loading="lazy"
            style="position:relative;"
            :src="formatUrl(copyright.originFileUrl)"
            :initial-index="previewIndex"
            :preview-src-list="previewList"
            :zoom-rate="1.33"
            :hide-on-click-modal="true">
          <template #placeholder>
            <div class="image-slot">Loading<span class="dot">...</span></div>
          </template>
        </el-image>
      </template>
    </el-popover>
    <el-divider>{{ copyright.title }}</el-divider>
    <div class="bottom">
      <p>水印内容：{{ copyright.content }}</p>
      <p>创建时间：{{ copyright.createTime }}</p>
    </div>
  </el-card>
</template>

<script>

import {defineComponent} from "vue";
import {Delete, Tickets} from "@element-plus/icons-vue";
import {formatUrl} from "@/utils/common";

export default defineComponent({
  name: 'CopyrightItem',
  computed: {
    Tickets() {
      return Tickets
    },
    Delete() {
      return Delete
    },
  },
  props: {
    copyright: {
      type: Object,
      required: true,
    },
    previewList: {
      type: Array,
      required: true
    },
    previewIndex: {
      type: Number,
      required: false
    }
  },
  methods: {
    formatUrl,
    emitCommand(command) {
      this.$emit('command', command, this.copyright);
    }
  }
})

</script>

<style scoped>
.bottom {
  font-size: 14px;
  padding: 0 10px 10px 10px;
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