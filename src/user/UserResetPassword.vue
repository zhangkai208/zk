<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userinfo'
import { updatePasswordService } from '@/api/user.js'
import { ElMessage } from 'element-plus'

const userInfoStore = useUserInfoStore()
const userInfo = ref({
    ...userInfoStore.info
})
const rules = {
    oldPwd: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        {
            pattern: /^\S{5,16}$/,
            message: '密码必须是5-16位的非空字符串',
            trigger: 'blur'
        }
    ],
    newPwd: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        {
            pattern: /^\S{5,16}$/,
            message: '密码必须是5-16位的非空字符串',
            trigger: 'blur'
        }
    ],
    rePwd: [
        { required: true, message: '请输入确认密码', trigger: 'blur' },
        {
            pattern: /^\S{5,16}$/,
            message: '密码必须是5-16位的非空字符串',
            trigger: 'blur'
        },
        {
            validator: (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'))
                } else if (value !== userInfo.value.newPwd) {
                    callback(new Error('两次输入的密码不一致'))
                } else {
                    callback()
                }
            },
            trigger: ['blur', 'change']  // 失去焦点和值改变时都触发验证
        }
    ]
}

const updatepassword = async () => {
    try {
        // 检查数据是否完整
        if (!userInfo.value.oldPwd || !userInfo.value.newPwd || !userInfo.value.rePwd) {
            ElMessage.error('请填写完整的密码信息')
            return
        }

        // 检查两次密码是否一致
        if (userInfo.value.newPwd !== userInfo.value.rePwd) {
            ElMessage.error('两次输入的密码不一致')
            return
        }

        // 构造请求参数
        const params = {
            username: userInfo.value.username,
            oldPwd: userInfo.value.oldPwd,
            newPwd: userInfo.value.newPwd,
            rePwd: userInfo.value.rePwd
        }

        console.log('发送的参数：', params)  // 添加日志
        let result = await updatePasswordService(params)
        console.log('返回结果：', result)  // 添加日志

        if(result.code === 0) {
            ElMessage.success(result.msg ? result.msg : '修改成功')
        } else {
            ElMessage.error(result.msg ? result.msg : '修改失败')
        }
    } catch (error) {
        console.error('修改密码错误：', error)  // 添加错误日志
        ElMessage.error('修改密码失败，请重试')
    }
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>基本资料</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo" :rules="rules" label-width="100px" size="large">
                    <el-form-item label="登录名称">
                        <el-input v-model="userInfo.username" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="oldPwd">
                        <el-input v-model="userInfo.oldPwd"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="newPwd">
                        <el-input v-model="userInfo.newPwd"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="rePwd">
                        <el-input v-model="userInfo.rePwd"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updatepassword">修改密码</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>
