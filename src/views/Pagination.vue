<template>
  <h3>page-size:每页显示记录数 total:总记录数</h3>
        <el-pagination layout="prev, pager, next" :page-size="1" :total="50" />
  <h3>background:显示背景</h3>
        <el-pagination layout="prev, pager, next" :page-size="10" :total="50" background />
  <h3>layout="total" 显示总数</h3>
        <el-pagination layout="prev, pager, next,total" :page-size="10" :total="50" />
  <h3>layout="jumper" 跳转</h3>
        <el-pagination layout="prev, pager, next,total,jumper" :page-size="10" :total="50" @current-change="currentPage" />
</template>

<script setup>
const currentPage = (val) => {
            console.log("currentPage:",val)
        }
</script>

