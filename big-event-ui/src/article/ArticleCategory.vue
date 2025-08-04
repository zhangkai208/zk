<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage, ElEmpty,ElMessageBox } from 'element-plus'
const categorys = ref([
    {
    
    }
])
import { articleCategoryListService, addCategoryService, updateCategoryService, deleteCategoryService } from '@/api/article.js'
const articleCategoryList = async () => {
    const result = await articleCategoryListService()
    categorys.value = result.data
}
articleCategoryList()

//控制添加分类弹窗
const dialogVisible = ref(false)

//添加分类数据模型
const categoryModel = ref({
    categoryName: '',
    categoryAlias: ''
})
//添加分类表单校验
const rules = {
    categoryName: [
        { required: true, message: '请输入分类名称', trigger: 'blur' },
    ],
    categoryAlias: [
        { required: true, message: '请输入分类别名', trigger: 'blur' },
    ]
}
const addCategory = async () => {
    
    let result = await addCategoryService(categoryModel.value)

    ElMessage.success(result.msg ? result.msg : '添加成功')

    articleCategoryList() // 刷新分类列表
    dialogVisible.value = false

}

const updateCategory = async () => {
    let result = await updateCategoryService(categoryModel.value)

    ElMessage.success(result.msg ? result.msg : '更新成功')
    articleCategoryList() // 刷新分类列表
    dialogVisible.value = false

}

const deleteCategory = async (id) => {
    
    ElMessageBox.confirm(
        '你确认删除该分类信息吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            let result = await deleteCategoryService(id)

            ElMessage.success(result.msg ? result.msg : '删除成功')
            articleCategoryList() // 刷新分类列表
            dialogVisible.value = false
        })
        .catch(() => {
            ElMessage.info('已取消删除')
        })


}
const showDialog = (row) => {
    dialogVisible.value = true;
    title.value = '编辑分类'
    categoryModel.value.categoryName = row.categoryName
    categoryModel.value.categoryAlias = row.categoryAlias
    categoryModel.value.id = row.id
}
const title = ref('')

const onConfirm = async () => {
    if (title.value === '添加分类') {
        await addCategory()
    } else {
        await updateCategory()
    }
}

const resetCategoryModel = () => {
    categoryModel.value = {
        categoryName: '',
        categoryAlias: ''
    }
    title.value = ''
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>文章分类</span>
                <div class="extra">
                    <el-button type="primary"
                        @click="dialogVisible = true; resetCategoryModel(); title = '添加分类'">添加分类</el-button>
                </div>
            </div>
        </template>
        <el-table :data="categorys" style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="分类名称" prop="categoryName"></el-table-column>
            <el-table-column label="分类别名" prop="categoryAlias"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary" @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteCategory(row.id)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 添加分类弹窗 -->
        <el-dialog v-model="dialogVisible" :title="title" width="30%">
            <el-form :model="categoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
                <el-form-item label="分类名称" prop="categoryName">
                    <el-input v-model="categoryModel.categoryName" minlength="1" maxlength="10"></el-input>
                </el-form-item>
                <el-form-item label="分类别名" prop="categoryAlias">
                    <el-input v-model="categoryModel.categoryAlias" minlength="1" maxlength="15"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="onConfirm"> 确认 </el-button>
                </span>
            </template>
        </el-dialog>
    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    min-height: 100%;
    box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
}
</style>