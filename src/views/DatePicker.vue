<template>
  <h3>日期</h3>
  <el-date-picker v-model="date" @change="dataChanged" />
  <h3>日期时间</h3>
  <el-date-picker v-model="date" type="datetime" @change="dataChanged"
  value-from="YYYY-MM-DD HH:mm:ss" 
  ></el-date-picker>
</template>
<script setup>
import { ref } from 'vue'
const date = ref()
const dataChanged = (value) => {
  console.log(value)
}
</script>

