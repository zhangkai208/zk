import {ref,reactive} from 'vue'
import { defineStore } from 'pinia'
//web是唯一标识符，后面是setup
export const useWebStore = defineStore('web' , () => {
    //定义一个响应式对象
    const web = reactive({
        name:'web',
        url:'www.zk.com'
    })
    //定义一个响应式变量
    const users = ref(1000)
    //定于方法
    const add = () => {
        users.value++
    
    }

    return {
        web,
        users,
        add
    }
},
{
    persist: true
}
)