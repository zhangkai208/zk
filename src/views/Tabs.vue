<template>
  <el-tabs v-model="selectedName" @tab-click="tabClick" type="border-card">
    <el-tab-pane label="用户管理" name="1">用户管理</el-tab-pane>
    <el-tab-pane label="配置管理" name="2">配置管理</el-tab-pane>
    <el-tab-pane label="角色管理" name="3">角色管理</el-tab-pane>
    <el-tab-pane label="定时任务补偿" name="4">定时任务补偿</el-tab-pane>
  </el-tabs>

  <el-button @click="tabAdd">添加</el-button>
  <el-tabs v-model="selectedName" @tab-click="tabClick"  @tab-remove="tabRemove" closable type="border-card">
    <el-tab-pane v-for="(value, key) in tab.arr" :key="key" :label="value.title" :name="value.name">
      {{ value.content }}
    </el-tab-pane>
  </el-tabs>
</template>
<script setup>
import { ref, reactive } from 'vue'
const tabRemove = (name) => {
            console.log("name:", name)

            const index = tab.arr.findIndex((value) => {
                return value.name === name
            })

            tab.arr.splice(index, 1) 
        }
const tab = reactive({
            arr: [
                { name: "1", title: '1', content: '内容1' },
                { name: "2", title: '2', content: '内容2' },
                { name: "3", title: '3', content: '内容3' },
            ]
        })
const selectedName = ref("2")
 const tabClick = (tab, event) => {
            console.log("tab", tab.props, "event", event)
        }
const tabAdd = () => {
  let index = tab.arr.length
  index = index + 1
  tab.arr.push({name: String(index),
                title: index,
                content: '内容' + index })
}
</script>

