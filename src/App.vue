<template>
  <el-container>
    <el-aside width="220px">
      <el-menu router default-active="/buttons">
        <el-menu-item index="/buttons">1. 按钮</el-menu-item>
        <el-menu-item index="/icons">2. 图标</el-menu-item>
        <el-menu-item index="/message">3. 提示框</el-menu-item>
        <el-menu-item index="/navigation">4. 导航</el-menu-item>
        <el-menu-item index="/tabs">5. 标签页</el-menu-item>
        <el-menu-item index="/inputs">6. 输入框</el-menu-item>
        <el-menu-item index="/selects">7. 单选框、复选框</el-menu-item>
        <el-menu-item index="/dropdown">8. 下拉框</el-menu-item>
        <el-menu-item index="/datepicker">9. 日期选择器</el-menu-item>
        <el-menu-item index="/menu">10. 菜单</el-menu-item>
        <el-menu-item index="/dialog">11. 对话框</el-menu-item>
        <el-menu-item index="/pagination">12. 分页</el-menu-item>
        <el-menu-item index="/table">13. 表格</el-menu-item>
        <el-menu-item index="/import-on-demand">14. 按需导入</el-menu-item>
      </el-menu>
    </el-aside>
    <el-main>
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
</script>