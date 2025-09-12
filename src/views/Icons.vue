<template>
  <el-row style="margin-bottom: 30px;">
    <h3>图标</h3>
    <el-icon><Plus /></el-icon>
        <el-icon><Edit /></el-icon>
        <el-icon><Delete /></el-icon>
  </el-row>
  <el-row style="margin-bottom: 30px;">
    <h3>属性</h3>
        <el-icon size="30" color="red"><Search /></el-icon>
  </el-row>
  <el-row style="margin-bottom: 30px;">
    <h3>按钮</h3>
    <el-button type="primary">
            <el-icon><Search /></el-icon>
            <span> 搜索 </span>
        </el-button>
        <el-button type="primary" >
          <el-icon><Search /></el-icon>
        </el-button>
        <el-button type="primary" circle>
          <el-icon><Search /></el-icon>
        </el-button>
  </el-row>
  <el-row style="margin-bottom: 30px;">
    <h3>图标按钮组</h3>
    <el-button-group>
      <el-button type="primary">
        <el-icon><Search /></el-icon>
        <span> 搜索 </span>
      </el-button>
      <el-button type="primary">
        <el-icon><Edit /></el-icon>
        <span> 编辑 </span>
      </el-button>
      <el-button type="primary">
        <el-icon><Delete /></el-icon>
        <span> 删除 </span>
      </el-button>
    </el-button-group>
  </el-row>

</template>
<script setup>

</script>

