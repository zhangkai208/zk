<template>
  <h3>下拉框</h3>
  <el-select v-model="selected" placeholder="请选择" @change="selectedChange">
    <el-option label="选项1" value="1"></el-option>
    <el-option label="选项2" value="2"></el-option>
    <el-option label="选项3" value="3"></el-option>
  </el-select>
  <h3>动态下拉框</h3>
  <el-select v-model="selected" multiple  placeholder="请选择" @change="selectedChange">
    <el-option v-for="value in data.option"
      :label="value.label"
      :value="value.value"
    ></el-option>
  </el-select>
</template>
<script setup>
import { ref ,reactive } from 'vue'
const selected = ref('1')
const selectedChange = (value) => {
  console.log(value)
}
const data = reactive({
  option: [
    {
      label: '选项1',
      value: '1'
    },
    {
      label: '选项2',
      value: '2'
    },
    {
      label: '选项3',
      value: '3'
    }
  ]
})
</script>

