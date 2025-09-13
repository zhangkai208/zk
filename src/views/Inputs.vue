<template>
  <h3>输入框</h3>、
  <el-input v-model="name" clearable placeholder="请输入内容" ></el-input>
  <h3>密码框</h3>
  <el-input v-model="password" show-password placehloder="请输入密码" ></el-input>
  <h3>文本域</h3>
  <el-input v-model="content" type="textarea" row="2"></el-input>
  <h3>输入内容长度限制 - 输入框</h3>
  <el-input v-model="name" maxlength="10" show-word-limit></el-input>
  <h3>输入内容长度限制 - 文本域</h3>
  <el-input v-model="content" type="textarea" maxlength="30" show-word-limit></el-input>
  <h3>尺寸</h3>
            大 <el-input size="large" />
            默认 <el-input />
            小 <el-input size="small" />
  <h3>前置</h3>
  <el-input v-model="url" placeholder="请输入内容" >
    <template #prepend>https://</template>
  </el-input>
  <h3>后置</h3>
  <el-input v-model="url2" placeholder="请输入内容" >
    <template #append>qq.com</template>
  </el-input>
  <h3>前置后置</h3>
  <el-input v-model="url1" placeholder="请输入内容" >
    <template #prepend>https://</template>
    <template #append>.com</template>
  </el-input>

  <el-input placeholder="请输入内容" >
    <template #prepend>
      <el-select v-model="select" placeholder="请选择">
        <el-option label="餐厅名" value="1"></el-option>
        <el-option label="食堂名" value="2"></el-option>
        <el-option label="小摊名" value="3"></el-option>
        <el-option label="路边名" value="4"></el-option>
      </el-select>
    </template>
    <template #append>
      <el-button ><el-icon><Search /></el-icon></el-button>
    </template>
  </el-input>


</template>
<script setup>

import { ref } from 'vue'
const selected = ref('2')
const name = ref('')
const password = ref('')
 const content = ref('')
 const url = ref('baidu.com')
  const url1 = ref('2834969129')
  const url2 = ref('qq.com')
</script>

