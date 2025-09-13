<template>
  <h3>单选框</h3>
  <el-radio v-model="radio" label="1">前端</el-radio>
  <el-radio v-model="radio" label="2" @click="radioChange">后端</el-radio>
  <el-radio v-model="radio" label="3">服务端</el-radio>
  <h3>单选框组</h3>
  <el-radio-group v-model="radio2" @change="radioChange">
    <el-radio value="a">前端</el-radio>
    <el-radio value="b">后端</el-radio>
    <el-radio value="c">服务端</el-radio>
  </el-radio-group>

  <h3>复选框</h3>
  <el-checkbox-group v-model="checkbox" @change="checkboxChange">
    <el-checkbox value="1">前端</el-checkbox>\
    <el-checkbox value="2">后端</el-checkbox>
    <el-checkbox value="3">服务端</el-checkbox>
  </el-checkbox-group>
</template>
<script setup>
import {ref } from 'vue'
const checkbox = ref(['1', '2'])
const checkboxChange = (val) => {
  console.log(val)
}
const radio = ref('1')
const radio2 = ref('a')
const radioChange = (val) => {
  console.log(val)
}
</script>

