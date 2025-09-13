<template>
  <h3>表格</h3>
  <el-button type="primary" @click="handleDelete">Delete</el-button>
  <el-table :data="data.arr" border height="240" style="width: 100%"  @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="55" />
    <el-table-column prop="id" label="ID" width="180" />
    <el-table-column prop="name" label="Name" width="180" />
    <el-table-column prop="web" label="Web" width="180" />
    <el-table-column prop="date" label="Date" width="180" />
    <el-table-column label="Operations">
      <template #default="scope">
        <el-button size="small" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
        <el-button size="small" type="danger" @click="handleDelete()">Delete</el-button>
      </template>
    </el-table-column>
  </el-table>
  <el-pagination background layout="prev, pager, next,total,jumper" :page-size="10" :total="100" />
</template>
<script setup>
import { ref } from 'vue'

        const data = ref({
            arr: [
                { id: '1', name: 'zk', web: 'www.zk.com', date: '2025-09-20' },
                { id: '2', name: 'David', web: 'www.David.com', date: '2025-10-21' },
                { id: '3', name: 'Luna', web: 'www.Luna.com', date: '2025-11-22' },
                { id: '4', name: 'Lisa', web: 'www.Lisa.com', date: '2025-12-22' }
            ]
        })
        const handleEdit = (index, row) => {
            console.log("index:", index, "row:", row)
        }
        const handleDelete = () => {
            console.log(idarr)
        }
        let idarr = []
        const handleSelectionChange = (data) => {
          let idarr = []
          data.forEach((item) => {
            idarr.push(item.id)
          })
          console.log(idarr)
        }
</script>

