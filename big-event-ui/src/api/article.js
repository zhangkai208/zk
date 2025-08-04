import request from '@/utils/request'
import { useTokenStore } from '@/stores/token'
export const articleCategoryListService = () => {
    /* const tokenStore = useTokenStore()
    return request.get('/category', {
        headers: {
            Authorization: `Bearer ${tokenStore.token}`
        }
    }) */
   return request.get('/category')
}
export const addCategoryService = (data) => {
  return request.post('/category', data)
}
export const updateCategoryService = (data) => {
  return request.put('/category', data)
}
export const deleteCategoryService = (id) => {
  return request.delete('/category?id='+id)
}
export const articleListService = (params) => {
    return request.get('/article', { params: params })
}
export const articleAddService = (data) => {
    return request.post('/article', data)
}
export const articleUpdateService = (data) => {
    return request.put('/article', data)
}
export const articleDeleteService = (id) => {
    return request.delete('/article?id='+id)
}