import request from '@/utils/request'


export const userRegisterService = (registerDate) => {
    const params = new URLSearchParams()
    for (const key in registerDate) {
        params.append(key, registerDate[key])
    }
    return request.post('/user/register', params)
}
export const userLoginService = (loginDate) => {
    const params = new URLSearchParams()
    for (const key in loginDate) {
        params.append(key, loginDate[key])
    }
    return request.post('/user/login', params)
}

export const userInfoService = () => {
    return request.get('/user/userinfo')
}

export const updateUserInfoService = (data) => {
    return request.put('/user/update', data)
}

export const updateAvatarService = (data) => {
    const params = new URLSearchParams()
    for (const key in data) {
        params.append(key, data[key])
    }
    return request.patch('/user/updateAvatar', params)
}
export const updatePasswordService = (data) => {
    return request.patch('/user/updatePwd', data)
}