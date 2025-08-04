import { defineStore } from "pinia";
import { ref } from "vue";

const useUserInfoStore = defineStore('userinfo',()=>{

    const info = ref({
        
    })
    const setinfo = (newInfo) => {
        info.value = newInfo
    }
    const removeInfo = () => {
        info.value = {
            
        }
    }
    return {
        info,
        setinfo,
        removeInfo
    }
    
},{persist:true
})
export default useUserInfoStore;