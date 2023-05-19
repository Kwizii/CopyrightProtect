<template>
  <div class="container" style="margin-bottom: 10px;">
    <el-row :gutter="20">
      <el-col :span="8" :sm="7" :xs="6">
        <el-input placeholder="图像标题" v-model="searchParams.title" clearable></el-input>
      </el-col>
      <el-col :span="8" :sm="7" :xs="6">
        <el-input placeholder="水印内容" v-model="searchParams.content" clearable></el-input>
      </el-col>
      <el-col :span="4" :sm="6" :xs="8">
        <el-button type="primary" @click="search" :icon="Search">搜索</el-button>
        <el-button type="primary" @click="router.push('/upload')" :icon="Plus">新增</el-button>
      </el-col>
      <el-col :span="4" :sm="4" :xs="4">
        <el-select @change="refreshPage" v-model="searchParams.createTimeAsc">
          <el-option :key="1" :value="1" label="创建日期升序"/>
          <el-option :key="0" :value="0" label="创建日期降序"/>
        </el-select>
      </el-col>
    </el-row>
    <el-divider/>
    <el-pagination
        :current-page="page.current"
        :page-size="page.size"
        :page-sizes="[30, 60]"
        background
        layout="total, sizes, ->, prev, pager, next, jumper, "
        :total="page.total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>
  <masonry-wall :items="copyrightList" :column-width="300" :gap="14">
    <template #default="{item,index}">
      <CopyrightItem :copyright="item"
                     :preview-index="index"
                     :preview-list="Object.values(copyrightList).map(obj=>formatUrl(obj.originFileUrl))"
                     @command="handleCommand"/>
    </template>
  </masonry-wall>

  <el-dialog
      v-model="infoDialogVisible"
      center
      title="版权图像详情"
      width="50%">
    <div>
      <el-row :gutter="10">
        <el-col :span="12">
          <el-divider>原图</el-divider>
          <el-popover
              popper-class="op-pop"
              :show-arrow="false"
              :width="100"
              :offset="-40"
              trigger="hover">
            <el-button color="rgb(255, 255, 255)"
                       :icon="Download"
                       style="color: var(--el-color-primary)"
                       @click="download(formatUrl(bcInfo.originFileUrl),bcInfo.originFileUrl)">下载原图
            </el-button>
            <template #reference>
              <div class="center">
                <el-image :src="formatUrl(bcInfo.originFileUrl)"
                          :initial-index="0"
                          :preview-src-list="[formatUrl(bcInfo.originFileUrl),formatUrl(bcInfo.watermarkFileUrl)]">
                  <template #placeholder>
                    <div class="image-slot">Loading<span class="dot">...</span></div>
                  </template>
                </el-image>
              </div>
            </template>
          </el-popover>
        </el-col>
        <el-col :span="12">
          <el-divider>带水印图像</el-divider>
          <el-popover
              popper-class="op-pop"
              :show-arrow="false"
              :width="100"
              :offset="-40"
              trigger="hover">
            <el-button color="rgb(255, 255, 255)"
                       :icon="Download"
                       style="color: var(--el-color-primary)"
                       @click="download(formatUrl(bcInfo.watermarkFileUrl),bcInfo.watermarkFileUrl)">下载水印图
            </el-button>
            <template #reference>
              <div class="center">
                <el-image :src="formatUrl(bcInfo.watermarkFileUrl)"
                          :initial-index="1"
                          :preview-src-list="[formatUrl(bcInfo.originFileUrl),formatUrl(bcInfo.watermarkFileUrl)]">
                  <template #placeholder>
                    <div class="image-slot">Loading<span class="dot">...</span></div>
                  </template>
                </el-image>
              </div>
            </template>
          </el-popover>
        </el-col>
      </el-row>
      <el-divider>链上信息</el-divider>
      <p>水印内容：{{ bcInfo.content }}</p>
      <p>存证交易哈希：{{ bcInfo.txHash }}</p>
      <p>创建时间：{{ bcInfo.createTime }}</p>
      <p>上链时间：{{ bcInfo.createTime }}</p>
      <el-divider>原文件哈希</el-divider>
      <p>MD5：{{ bcInfo.md5Hash }}</p>
      <p>SHA-1：{{ bcInfo.sha1Hash }}</p>
      <p>SHA-256：{{ bcInfo.sha256Hash }}</p>
    </div>
    <template #footer>
    </template>
  </el-dialog>
  <el-dialog
      v-model="delDialogVisible"
      center
      title="删除图像版权"
      width="30%">
    <span>注意：只能删除数据库中的数据，已上链的数据无法被删除。</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="danger" @click="delCopyright(selected.id)">
          确认
        </el-button>
        <el-button @click="delDialogVisible = false">取消</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script name="copyright">
import {defineComponent} from 'vue';
import CopyrightItem from "@/components/copyright-item.vue";
import {pageStore} from '@/store/page'
import request from "@/utils/request";
import {ElMessage} from "element-plus";
import {Download, Plus, Search} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";
import {download, formatUrl} from "@/utils/common";


export default defineComponent({
  name: 'copyright',
  computed: {
    Download() {
      return Download
    },
    Plus() {
      return Plus
    },
    Search() {
      return Search
    }
  },
  components: {CopyrightItem},
  setup() {
    return {
      router: useRouter()
    }
  },
  data() {
    return {
      delDialogVisible: false,
      infoDialogVisible: false,
      selected: null,
      searchParams: {
        title: "",
        content: "",
        createTimeAsc: 0
      },
      bcInfo: {},
      page: pageStore(),
      copyrightList: []
    };
  },
  mounted() {
    this.refreshPage();
  },
  methods: {
    download,
    formatUrl,
    refreshPage() {
      request.get(`/copyright/page/${this.page.current}/${this.page.size}`, {
        params: this.searchParams
      })
          .then(data => {
            this.page.setPage(data.current, data.total, data.size);
            this.copyrightList = data.records;
          }, error => {
            if (error.response && error.response.data)
              ElMessage.error(error.response.data);
          })
    },
    search() {
      this.refreshPage();
    },
    handleSizeChange(val) {
      console.log(`${val} items per page`)
      this.page.setPage(Math.max(
              Math.floor((this.page.current - 1) * this.page.size / val) + 1, 1)
          , this.page.total, val);
      this.refreshPage();
    },
    handleCurrentChange(val) {
      console.log(`current page: ${val}`)
      this.page.setPage(val, this.page.total, this.page.size);
      this.refreshPage();
    },
    handleCommand(command, copyright) {
      this.selected = copyright;
      if (command === 0) {
        request.get(`/copyright/get/${this.selected.txHash}`)
            .then(data => {
              this.bcInfo = data;
              this.infoDialogVisible = true;
            }, error => {
              ElMessage.error(error.response.data);
            })
      } else if (command === 1) {
        this.delDialogVisible = true;
      }
    },
    delCopyright(id) {
      if (id === -1)
        return;
      request.delete(`/copyright/del/${id}`)
          .then(data => {
            if (data) {
              ElMessage.success("删除成功");
              this.refreshPage();
            } else {
              ElMessage.error("删除失败");
            }
          }, error => {
            console.log(error);
            ElMessage.error("删除失败");
          }).then(() => {
        this.delDialogVisible = false;
      });
    }
  }
})
</script>

<style scoped>
p {
  word-wrap: break-word;
}
</style>
