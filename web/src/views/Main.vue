<template>
    <v-header/>
    <v-sidebar/>
    <div class="content-box" :class="{ 'content-collapse': sidebar.collapse }">
        <el-scrollbar>
            <div class="content">
                <router-view v-slot="{ Component }"></router-view>
            </div>
        </el-scrollbar>
    </div>
</template>
<script setup>
import {useSidebarStore} from '@/store/sidebar';
import VHeader from '@/components/header.vue';
import VSidebar from '@/components/sidebar.vue';

const sidebar = useSidebarStore();
</script>