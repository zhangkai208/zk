<script setup>
import {
    Edit,
    Delete
} from '@element-plus/icons-vue'

import { ref, onMounted} from 'vue'
import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'
import { Plus } from '@element-plus/icons-vue'

import { ElMessage, ElEmpty,ElMessageBox } from 'element-plus'
import { useTokenStore } from '@/stores/token'
const tokenStore = useTokenStore()


//控制抽屉是否显示
const visibleDrawer = ref(false)
//添加表单数据模型
const articleModel = ref({
    title: '',
    categoryId: '',
    coverImg: '',
    content: '',
    state: ''
})

//文章分类数据模型
const categorys = ref([
])

//用户搜索时选中的分类id
const categoryId = ref('')

//用户搜索时选中的发布状态
const state = ref('')

//文章列表数据模型
const articles = ref([
])

//分页条数据模型
const pageNum = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(5)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
    getArticles()
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    pageNum.value = num
    getArticles()
}

import { articleListService, articleAddService,articleDeleteService,articleUpdateService,articleCategoryListService } from '@/api/article.js'

onMounted(async () => {
  const result = await articleCategoryListService()
  categorys.value = Array.isArray(result.data) ? result.data : []
})
const getArticles = async () => {
    try {
        let params = {
            pageNum: pageNum.value,
            pageSize: pageSize.value,
            categoryId: categoryId.value ? categoryId.value : null,
            state: state.value ? state.value : null
        }
        let result = await articleListService(params)
        if (result && result.data) {
            //渲染列表数据
            articles.value = result.data.items || []
            //为列表中添加categoryName属性
            for (let i = 0; i < articles.value.length; i++) {
                let article = articles.value[i]
                for (let j = 0; j < categorys.value.length; j++) {
                    if (article.categoryId === categorys.value[j].id) {
                        article.categoryName = categorys.value[j].categoryName
                    }
                }
            }
            //渲染总条数
            total.value = result.data.total || 0
        }
    } catch (error) {
        console.error('获取文章列表失败:', error)
        articles.value = []
        total.value = 0
    }
}
getArticles()

const uploadSussess = (result) => {
    articleModel.value.coverImg = result.data
    console.log(result.data)
}
// 添加编辑器ref
const editorRef = ref(null)

// 添加 title ref
const title = ref('')

// 添加重置表单的方法
const resetForm = () => {
    articleModel.value = {
        title: '',
        categoryId: '',
        coverImg: '',
        content: '',
        state: ''
    }
    if (editorRef.value && editorRef.value.getQuill) {
        editorRef.value.getQuill().setText('')
    }
}

// 修改 handleAddArticle 方法
const handleAddArticle = () => {
    visibleDrawer.value = true
    title.value = '添加文章'
    resetForm()
}

const addArticle = async (state) => {
    articleModel.value.state = state
    // 调用添加文章接口
    let result = await articleAddService(articleModel.value)
    if (result.code === 0) {  // 假设 0 是成功的状态码
        resetForm()
        ElMessage.success(result.msg ? result.msg : '添加成功')
        // 刷新文章列表
        await getArticles()
        // 关闭抽屉
        visibleDrawer.value = false
    } else {
        ElMessage.error(result.msg ? result.msg : '添加失败')
    }
}

const articleDelete = async (id) => {
    
    ElMessageBox.confirm(
        '你确认删除该文章吗？',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            let result = await articleDeleteService(id)
            ElMessage.success(result.msg ? result.msg : '删除成功')
            await getArticles() // 刷新分类列表
            visibleDrawer.value = false
        })
        .catch(() => {
            ElMessage.info('已取消删除')
        })
}
const articleUpdate = async () => {
    let result = await articleUpdateService(articleModel.value)
    ElMessage.success(result.msg ? result.msg : '更新成功')
    await getArticles() // 刷新分类列表
    visibleDrawer.value = false
}
const showDialog = (row) => {
    visibleDrawer.value = true;
    title.value = '编辑文章'
    articleModel.value.title = row.title
    articleModel.value.categoryId = row.categoryId
    articleModel.value.coverImg = row.coverImg
    articleModel.value.content = row.content
    articleModel.value.state = row.state
    articleModel.value.id = row.id
}
// 修改 onConfirm 方法
const onConfirm = async (state) => {
    if (title.value === '添加文章') {
        await addArticle(state)
    } else {
        articleModel.value.state = state
        await articleUpdate()
    }
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>文章管理</span>
                <div class="extra">
                    <el-button type="primary" @click="handleAddArticle" >添加文章</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="文章分类：">
                <el-select placeholder="请选择" v-model="categoryId" style="width: 200px;">
                    <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="发布状态：">
                <el-select placeholder="请选择" v-model="state" style="width: 200px;">
                    <el-option label="已发布" value="已发布"></el-option>
                    <el-option label="草稿" value="草稿"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="getArticles">搜索</el-button>
                <el-button @click="categoryId = '', state = '', getArticles()">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 文章列表 -->
        <el-table :data="articles" style="width: 100%">
            <el-table-column label="文章标题" width="400" prop="title"></el-table-column>
            <el-table-column label="分类" prop="categoryName"></el-table-column>
            <el-table-column label="发表时间" prop="createTime"> </el-table-column>
            <el-table-column label="状态" prop="state"></el-table-column>
            <el-table-column label="操作" width="100">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary"@click=showDialog(row)></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="articleDelete(row.id)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
        <!-- 分页条 -->
        <el-pagination v-model:current-page="pageNum" v-model:page-size="pageSize" :page-sizes="[3, 5, 10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
        <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer" :title="title" direction="rtl" size="50%">
            <!-- 添加文章表单 -->
            <el-form :model="articleModel" label-width="100px">
                <el-form-item label="文章标题">
                    <el-input v-model="articleModel.title" placeholder="请输入标题"></el-input>
                </el-form-item>
                <el-form-item label="文章分类">
                    <el-select placeholder="请选择" v-model="articleModel.categoryId">
                        <el-option v-for="c in categorys" :key="c.id" :label="c.categoryName" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="文章封面">

                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"
                    action="/api/upload"
                    name="file"
                    :headers="{Authorization: tokenStore.token}"
                    :on-success="uploadSussess"
                    >
                        <img v-if="articleModel.coverImg" :src="articleModel.coverImg" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>
                <el-form-item label="文章内容">
                    <div class="editor"><quill-editor 
                        ref="editorRef"
                        theme="snow" 
                        v-model:content="articleModel.content"
                        contentType="html">
                    </quill-editor></div>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onConfirm('已发布')">发布</el-button>
                    <el-button type="info" @click="onConfirm('草稿')">草稿</el-button>
                </el-form-item>
            </el-form>
        </el-drawer>
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

/* 抽屉样式 */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.editor {
    width: 100%;

    :deep(.ql-editor) {
        min-height: 200px;
    }
}
</style>