<template>
  <el-button @click="dialog = true">打开</el-button>
  <el-dialog v-model="dialog" title="提示" draggable @close="dialogClose">
    <el-form>
    <el-form-item label="文本框">
      <el-input v-model="data.name" placeholder="请输入内容"></el-input>
    </el-form-item>
    <el-form-item label="单选框">
      <el-radio-group v-model="data.radio">
        <el-radio value="1">前端</el-radio>
        <el-radio value="2">后端</el-radio>
        <el-radio value="3">全栈</el-radio>
      </el-radio-group>
      </el-form-item>
      <el-form-item label="多选框">
      <el-checkbox-group v-model="data.checkbox">
        <el-checkbox value="1">前端</el-checkbox>
        <el-checkbox value="2">后端</el-checkbox>
        <el-checkbox value="3">全栈</el-checkbox>
      </el-checkbox-group>
      </el-form-item>
      <el-form-item label="日期时间">
      <el-date-picker v-model="data.date" type="date" placeholder="选择日期"></el-date-picker>
      </el-form-item>
      <el-form-item label="下拉框">
        <el-select v-model="data.select" placeholder="请选择">
          <el-option label="前端" value="1"></el-option>
          <el-option label="后端" value="2"></el-option>
          <el-option label="全栈" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="下拉框">
      <el-select v-model="data.multipleSelect" multiple placeholder="请选择">
        <el-option label="前端" value="1"></el-option>
        <el-option label="后端" value="2"></el-option>
        <el-option label="全栈" value="3"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="文本框">
      <el-input type="textarea" v-model="data.textarea" placeholder="请输入内容"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="add">添加</el-button>
      <el-button @click="reset">重置</el-button>
    </el-form-item>
  </el-form>
    </el-dialog>
</template>
<script setup>
 import { ref } from 'vue'

        const data = ref({
            name: '',
            radio: '',
            checkbox: [],
            date: '',
            select: '',
            multipleSelect: [],
            textarea: ''
        })

        const add = () => {
            console.log(data.value)
        }

        const reset = () => {
            data.value = {
                name: '',
                radio: '',
                checkbox: [],
                date: '',
                select: '',
                multipleSelect: [],
                textarea: ''
            }
        }
        const dialog = ref(false)
        const dialogClose = () => {
          console.log('close')
        }
</script>

