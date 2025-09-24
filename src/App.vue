<script setup>
import { useWebStore } from './store/web';
const webStore = useWebStore();
</script>

<template>
  {{ webStore.web.name }}
  {{ webStore.web.url }}
  {{ webStore.users }}
  <button @click="webStore.add">Add User</button>
</template>


