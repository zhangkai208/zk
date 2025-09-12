import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  { path: '/', redirect: '/buttons' },
  { path: '/buttons', name: 'Buttons', component: () => import('../views/Buttons.vue') },
  { path: '/icons', name: 'Icons', component: () => import('../views/Icons.vue') },
  { path: '/message', name: 'Message', component: () => import('../views/Message.vue') },
  { path: '/navigation', name: 'Navigation', component: () => import('../views/Navigation.vue') },
  { path: '/tabs', name: 'Tabs', component: () => import('../views/Tabs.vue') },
  { path: '/inputs', name: 'Inputs', component: () => import('../views/Inputs.vue') },
  { path: '/selects', name: 'Selects', component: () => import('../views/Selects.vue') },
  { path: '/dropdown', name: 'Dropdown', component: () => import('../views/Dropdown.vue') },
  { path: '/datepicker', name: 'DatePicker', component: () => import('../views/DatePicker.vue') },
  { path: '/menu', name: 'Menu', component: () => import('../views/Menu.vue') },
  { path: '/dialog', name: 'Dialog', component: () => import('../views/Dialog.vue') },
  { path: '/pagination', name: 'Pagination', component: () => import('../views/Pagination.vue') },
  { path: '/table', name: 'Table', component: () => import('../views/Table.vue') },
  { path: '/import-on-demand', name: 'ImportOnDemand', component: () => import('../views/ImportOnDemand.vue') },
]

export const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router

