<template>
  <div>
    <el-button type="primary" @click="openMsg">消息提示</el-button>
    <el-button type="primary" @click="openConfirm">确认框</el-button>
    <el-button type="primary" @click="openNotify">通知</el-button>
    <el-button type="primary" @click="openNotify2">通知2</el-button>
  </div>
</template>
<script setup>

import { ElMessage, ElMessageBox, ElNotification } from 'element-plus'
const openMsg = () => {
  ElMessage({
    type: 'success',
    message: '操作成功',
    showClose: true,
  })
}
const openConfirm = () => {
  ElMessageBox.confirm('确定要删除吗？', '提示', {
    type: 'warning',
    confirnButtonText: '确定',
    cancelButtonText: '取消',
  })
    .then(() => {
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '已取消删除',
      })
    })
  }
 const openNotify = () => {
  ElNotification({
    title: '提示',
    message: '这是一条不会自动关闭的消息',
    duration: 0,
  })
 }
 const openNotify2 = () => {
  ElNotification({
    type: 'success',
  title: '成功',
  message: '这是一条成功的提示',
  duration: 2000,
  position: 'bottom-right'
  })
 }
</script>

