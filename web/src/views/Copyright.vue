<template>
    <masonry-wall :items="copyrightList" :column-width="300" :gap="14">
        <template #default="{item,index}">
            <CopyrightItem :copyright="item"/>
        </template>
    </masonry-wall>
    <div class="container">
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
</template>

<script name="copyright">
import {defineComponent} from 'vue';
import CopyrightItem from "@/components/copyright-item.vue";
import request from "@/utils/request";
import {pageStore} from '@/store/page'


export default defineComponent({
    name: 'copyright',
    components: {CopyrightItem},
    data() {
        return {
            page: pageStore(),
            copyrightList: [
                {
                    watermarkedFileAddress: 'https://shadow.elemecdn.com/app/element/hamburger.9cf7b091-55e9-11e9-a976-7f4d0b07eef6.png',
                    description: '测试水印信息',
                    content: '123123132',
                    chainAddress: '0x123123123',
                    createTime: Date.now(),
                    md5Hash: '123123',
                    sha1Hash: '123123',
                    sha256Hash: '123123',
                }
            ]
        };
    },
    mounted() {
        this.refreshPage();
    },
    methods: {
        refreshPage() {
            request(`http://localhost/api/copyright/page/${this.page.current}/${this.page.size}`).then(resp => {
                console.log(resp);
                if (resp.status === 200) {
                    const data = resp.data;
                    this.page.setPage(data.current, data.total, data.size);
                    this.copyrightList = data.records;
                }
            })
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
        }
    }
})
</script>

<style scoped>
</style>
