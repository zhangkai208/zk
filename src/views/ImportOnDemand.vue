<template>
  <h3>按钮</h3>
        <el-button>默认按钮</el-button>
        <el-button type="primary">主要按钮</el-button>
        <el-button type="success">成功按钮</el-button>
        <el-button type="info">信息按钮</el-button>
        <el-button type="warning">警告按钮</el-button>
        <el-button type="danger">危险按钮</el-button>

        <h3>图标</h3>
        <!-- <el-icon><Plus /></el-icon> -->
        <el-icon><i-ep-Plus /></el-icon> <!-- i-ep- -->
        <el-icon><IEpEdit /></el-icon> <!-- IEp- -->
        <el-icon><IEpDelete /></el-icon>
        <el-icon class="is-loading"><IEpLoading /></el-icon>

        <h3>提示框</h3>
        <el-button @click="openMsg">消息</el-button>
        <el-button @click="openConfirm">确认框</el-button>
        <el-button @click="openNotifiy">通知</el-button>
        <el-button @click="openNotifiy2">通知2</el-button>

        <h3>输入框</h3>
        <el-input v-model="url" placeholder="请输入网址" />
</template>
<script setup>
</script>

