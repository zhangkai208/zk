<script setup>
import { Plus, Upload } from '@element-plus/icons-vue'
import {ref} from 'vue'
import avatar from '@/assets/default.png'
import useUserInfoStore from '@/stores/userinfo.js'
import {useTokenStore} from '@/stores/token.js'
import {updateAvatarService} from '@/api/user.js'
import {ElMessage} from 'element-plus'
const tokenStore = useTokenStore()
const userInfoStore = useUserInfoStore()
const uploadRef = ref()

//用户头像地址
const imgUrl= ref(userInfoStore.info.userPic || avatar)


const uploadSussess = (result) => {
   
    imgUrl.value = result.data
    
}

const updateAvatar = async () => {
    console.log('准备更新的头像地址：', imgUrl.value)
    let result = await updateAvatarService(
        imgUrl.value
    )
    console.log('更新头像返回结果：', result)
    if(result.code === 0) {
        ElMessage.success(result.msg ? result.msg : '修改成功')
        userInfoStore.setinfo({
            ...userInfoStore.info,
            userPic: imgUrl.value
        })
    } else {
        ElMessage.error(result.msg ? result.msg : '修改失败')
    }
}
</script>

<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>更换头像</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-upload 
                    ref="uploadRef"
                    class="avatar-uploader" 
                    :auto-upload="true" 
                    :show-file-list="false"
                    action="/api/upload"
                    name="file"
                    :headers="{Authorization: tokenStore.token}"
                    :on-success="uploadSussess"
                    >
                    <img v-if="imgUrl" :src="imgUrl" class="avatar" />
                    <img v-else src="" width="278" />
                </el-upload>
                <br />
                <el-button type="primary" :icon="Plus" size="large"  @click="uploadRef.$el.querySelector('input').click()">
                    选择图片
                </el-button>
                <el-button type="success" :icon="Upload" size="large" @click="updateAvatar">
                    上传头像
                </el-button>
            </el-col>
        </el-row>
    </el-card>
</template>

<style lang="scss" scoped>
.avatar-uploader {
    :deep() {
        .avatar {
            width: 278px;
            height: 278px;
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
            width: 278px;
            height: 278px;
            text-align: center;
        }
    }
}
</style>