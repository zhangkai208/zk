<template>
  <h3>水平导航</h3>
  <el-menu  :default-active="selectedIndex" :default-openeds="defaultOpeneds" @select="selected"  background-color="#ffffff" text-color="#303133" active-text-color="#409eff">
     <el-sub-menu index="1">
                <template #title>
                    <el-icon><Search /></el-icon>
                    <span>处理中心</span>
                </template>
                <el-menu-item-group title="分组一">
                    <el-menu-item index="1-1">选项1</el-menu-item>
                    <el-menu-item index="1-2">选项2</el-menu-item>
                </el-menu-item-group>
                <el-menu-item-group title="分组二">
                    <el-menu-item index="1-3">选项3</el-menu-item>
                    <el-menu-item index="1-4">选项4</el-menu-item>
                </el-menu-item-group>
            </el-sub-menu>
            <el-sub-menu index="2">
                
                <template #title>
                  <el-icon><Edit /></el-icon>
                  <span>我的工作台</span>
                </template>
                
                    <el-menu-item index="2-1">选项1</el-menu-item>
                    <el-menu-item index="2-2">选项2</el-menu-item>
                
            </el-sub-menu>
            <el-menu-item index="3">
                <el-icon><Delete /></el-icon>
                <template #title>订单管理</template>
            </el-menu-item>
            <el-menu-item index="4">
                <el-icon><Setting /></el-icon>
                <template #title>导航四</template>
            </el-menu-item>
  </el-menu>
  
</template>
<script setup>
import { ref , reactive} from 'vue'
 const selectedIndex = ref("1-1")
 const selected = (index, indexPath) => {
            console.log("index", index, "indexPath", indexPath)
        }
const defaultOpeneds = ref(["1","2"])
</script>



