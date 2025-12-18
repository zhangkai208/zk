<template>
  <div class="app-container">
    <!-- 左侧边栏 -->
    <aside class="sidebar">
      <div class="sidebar-header">
        <h2>聊天记录</h2>
        <button class="new-chat-btn" @click="createNewChat">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <line x1="12" y1="5" x2="12" y2="19"></line>
            <line x1="5" y1="12" x2="19" y2="12"></line>
          </svg>
          新对话
        </button>
      </div>
      <div class="chat-list">
        <div 
          v-for="chat in chatHistory" 
          :key="chat.id"
          class="chat-item"
          :class="{ active: currentChatId === chat.id }"
          @click="selectChat(chat.id)"
        >
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
          </svg>
          <span class="chat-title">{{ chat.title }}</span>
          <button class="delete-btn" @click.stop="deleteChat(chat.id)">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <line x1="18" y1="6" x2="6" y2="18"></line>
              <line x1="6" y1="6" x2="18" y2="18"></line>
            </svg>
          </button>
        </div>
      </div>
    </aside>

    <!-- 主聊天区域 -->
    <main class="chat-main">
      <!-- 消息展示区 -->
      <div class="messages-container" ref="messagesContainer">
        <div v-if="currentMessages.length === 0" class="welcome-screen">
          <div class="welcome-icon">
            <svg width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
              <circle cx="12" cy="12" r="10"></circle>
              <path d="M8 14s1.5 2 4 2 4-2 4-2"></path>
              <line x1="9" y1="9" x2="9.01" y2="9"></line>
              <line x1="15" y1="9" x2="15.01" y2="9"></line>
            </svg>
          </div>
          <h1>Spring AI 智能助手</h1>
          <p>有什么可以帮助你的？</p>
        </div>

        <div v-for="(msg, index) in currentMessages" :key="index" class="message" :class="msg.role">
          <div class="message-avatar">
            <span v-if="msg.role === 'user'">你</span>
            <span v-else>AI</span>
          </div>
          <div class="message-content">
            <div class="message-text" v-html="formatMessage(msg.content)"></div>
          </div>
        </div>

        <div v-if="isLoading" class="message assistant">
          <div class="message-avatar"><span>AI</span></div>
          <div class="message-content">
            <div class="typing-indicator">
              <span></span>
              <span></span>
              <span></span>
            </div>
          </div>
        </div>
      </div>

      <!-- 输入区域 -->
      <div class="input-container">
        <div class="input-wrapper">
          <textarea 
            v-model="userInput" 
            @keydown.enter.exact.prevent="sendMessage"
            placeholder="输入消息，可上传图片、音频或视频..."
            rows="1"
            ref="inputField"
          ></textarea>
          <button 
            class="send-btn" 
            :class="{ active: userInput.trim() }"
            @click="sendMessage"
            :disabled="!userInput.trim() || isLoading"
          >
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <line x1="22" y1="2" x2="11" y2="13"></line>
              <polygon points="22 2 15 22 11 13 2 9 22 2"></polygon>
            </svg>
          </button>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, computed, nextTick, onMounted } from 'vue'

// 状态
const userInput = ref('')
const isLoading = ref(false)
const currentChatId = ref(null)
const chatHistory = ref([])
const currentMessages = ref([])
const messagesContainer = ref(null)
const inputField = ref(null)

// 从后端加载会话列表
const loadConversations = async () => {
  try {
    const response = await fetch('/api/conversations')
    if (response.ok) {
      chatHistory.value = await response.json()
      // 自动选择第一个会话
      if (chatHistory.value.length > 0 && !currentChatId.value) {
        selectChat(chatHistory.value[0].id)
      }
    }
  } catch (error) {
    console.error('加载会话列表失败:', error)
  }
}

// 加载会话消息
const loadMessages = async (conversationId) => {
  try {
    const response = await fetch(`/api/conversations/${conversationId}/messages`)
    if (response.ok) {
      currentMessages.value = await response.json()
      scrollToBottom()
    }
  } catch (error) {
    console.error('加载消息失败:', error)
  }
}

// 初始化
onMounted(() => {
  loadConversations()
})

// 创建新对话
const createNewChat = async () => {
  try {
    const response = await fetch('/api/conversations', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ title: '新对话' })
    })
    if (response.ok) {
      const newChat = await response.json()
      chatHistory.value.unshift(newChat)
      selectChat(newChat.id)
    }
  } catch (error) {
    console.error('创建会话失败:', error)
  }
}

// 选择聊天
const selectChat = async (id) => {
  currentChatId.value = id
  await loadMessages(id)
}

// 删除聊天
const deleteChat = async (id) => {
  try {
    const response = await fetch(`/api/conversations/${id}`, { method: 'DELETE' })
    if (response.ok) {
      const index = chatHistory.value.findIndex(c => c.id === id)
      if (index > -1) {
        chatHistory.value.splice(index, 1)
        if (currentChatId.value === id) {
          currentChatId.value = chatHistory.value.length > 0 ? chatHistory.value[0].id : null
          if (currentChatId.value) {
            await loadMessages(currentChatId.value)
          } else {
            currentMessages.value = []
          }
        }
      }
    }
  } catch (error) {
    console.error('删除会话失败:', error)
  }
}

// 滚动到底部
const scrollToBottom = () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
}

// 格式化消息（支持简单的Markdown）
const formatMessage = (text) => {
  if (!text) return ''
  let formatted = text
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
  
  formatted = formatted.replace(/```(\w*)\n?([\s\S]*?)```/g, '<pre><code>$2</code></pre>')
  formatted = formatted.replace(/`([^`]+)`/g, '<code>$1</code>')
  formatted = formatted.replace(/\n/g, '<br>')
  
  return formatted
}

// 发送消息
const sendMessage = async () => {
  const message = userInput.value.trim()
  if (!message || isLoading.value) return

  // 如果没有当前对话，创建一个
  if (!currentChatId.value) {
    await createNewChat()
  }

  // 添加用户消息到界面（乐观更新）
  currentMessages.value.push({
    role: 'user',
    content: message
  })

  userInput.value = ''
  isLoading.value = true
  scrollToBottom()

  try {
    // 调用后端API（流式响应）
    const response = await fetch(`/ai/chat?message=${encodeURIComponent(message)}&conversationId=${currentChatId.value}`)
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    // 添加AI消息占位
    const aiMessageIndex = currentMessages.value.length
    currentMessages.value.push({
      role: 'assistant',
      content: ''
    })
    isLoading.value = false

    // 读取流式响应
    const reader = response.body.getReader()
    const decoder = new TextDecoder()
    let fullContent = ''

    while (true) {
      const { done, value } = await reader.read()
      if (done) break

      const chunk = decoder.decode(value, { stream: true })
      
      // 解析SSE格式，去掉 "data:" 前缀
      const lines = chunk.split('\n')
      for (const line of lines) {
        if (line.startsWith('data:')) {
          const content = line.substring(5)
          fullContent += content
        } else if (line.trim() !== '') {
          fullContent += line
        }
      }
      
      // 使用Vue的响应式更新方式
      currentMessages.value[aiMessageIndex] = {
        role: 'assistant',
        content: fullContent
      }
      
      scrollToBottom()
    }

    // 重新加载会话列表以更新标题
    await loadConversations()

  } catch (error) {
    console.error('发送消息失败:', error)
    isLoading.value = false
    
    currentMessages.value.push({
      role: 'assistant',
      content: '抱歉，发生了错误：' + error.message
    })
  }

  scrollToBottom()
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.app-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
}

/* 左侧边栏 */
.sidebar {
  width: 280px;
  background: linear-gradient(180deg, #1a1a2e 0%, #16213e 100%);
  color: #ffffff;
  display: flex;
  flex-direction: column;
  border-right: 1px solid rgba(255, 255, 255, 0.1);
}

.sidebar-header {
  padding: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.sidebar-header h2 {
  font-size: 16px;
  font-weight: 600;
  opacity: 0.9;
}

.new-chat-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 8px 16px;
  background: #2563eb;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
}

.new-chat-btn:hover {
  background: #1d4ed8;
  transform: translateY(-1px);
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
}

.chat-list {
  flex: 1;
  overflow-y: auto;
  padding: 12px;
}

.chat-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  border-radius: 10px;
  cursor: pointer;
  transition: all 0.2s ease;
  margin-bottom: 4px;
  position: relative;
}

.chat-item:hover {
  background: rgba(255, 255, 255, 0.1);
}

.chat-item.active {
  background: rgba(37, 99, 235, 0.3);
}

.chat-item svg {
  opacity: 0.7;
  flex-shrink: 0;
}

.chat-title {
  flex: 1;
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  opacity: 0.9;
}

.delete-btn {
  opacity: 0;
  background: none;
  border: none;
  color: #ffffff;
  cursor: pointer;
  padding: 4px;
  border-radius: 4px;
  transition: all 0.2s ease;
}

.chat-item:hover .delete-btn {
  opacity: 0.6;
}

.delete-btn:hover {
  opacity: 1 !important;
  background: rgba(239, 68, 68, 0.3);
}

/* 主聊天区域 */
.chat-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: #f8fafc;
}

.messages-container {
  flex: 1;
  overflow-y: auto;
  padding: 24px;
  scroll-behavior: smooth;
}

/* 欢迎界面 */
.welcome-screen {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  text-align: center;
  color: #64748b;
}

.welcome-icon {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #2563eb, #8b5cf6);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 24px;
  color: white;
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
}

.welcome-screen h1 {
  font-size: 28px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 8px;
}

.welcome-screen p {
  font-size: 16px;
}

/* 消息样式 */
.message {
  display: flex;
  gap: 16px;
  max-width: 800px;
  margin: 0 auto 24px;
  animation: fadeIn 0.3s ease;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.message-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 600;
  flex-shrink: 0;
}

.message.user .message-avatar {
  background: linear-gradient(135deg, #2563eb, #3b82f6);
  color: white;
}

.message.assistant .message-avatar {
  background: linear-gradient(135deg, #10b981, #059669);
  color: white;
}

.message-content {
  flex: 1;
  min-width: 0;
}

.message-text {
  padding: 16px 20px;
  border-radius: 16px;
  font-size: 15px;
  line-height: 1.7;
  word-wrap: break-word;
}

.message.user .message-text {
  background: #2563eb;
  color: white;
  border-bottom-left-radius: 4px;
}

.message.assistant .message-text {
  background: #ffffff;
  color: #1e293b;
  border: 1px solid #e2e8f0;
  border-bottom-left-radius: 4px;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

/* 打字指示器 */
.typing-indicator {
  display: flex;
  gap: 4px;
  padding: 16px 20px;
  background: #ffffff;
  border-radius: 16px;
  border: 1px solid #e2e8f0;
}

.typing-indicator span {
  width: 8px;
  height: 8px;
  background: #2563eb;
  border-radius: 50%;
  animation: bounce 1.4s infinite ease-in-out;
}

.typing-indicator span:nth-child(1) {
  animation-delay: -0.32s;
}

.typing-indicator span:nth-child(2) {
  animation-delay: -0.16s;
}

@keyframes bounce {
  0%, 80%, 100% {
    transform: scale(0);
  }
  40% {
    transform: scale(1);
  }
}

/* 输入区域 */
.input-container {
  padding: 20px 24px 24px;
  background: #f8fafc;
  border-top: 1px solid #e2e8f0;
}

.input-wrapper {
  max-width: 800px;
  margin: 0 auto;
  display: flex;
  align-items: flex-end;
  gap: 12px;
  background: white;
  border: 2px solid #e2e8f0;
  border-radius: 16px;
  padding: 12px 16px;
  transition: all 0.2s ease;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.input-wrapper:focus-within {
  border-color: #2563eb;
  box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.1);
}

.input-wrapper textarea {
  flex: 1;
  border: none;
  outline: none;
  font-size: 15px;
  font-family: inherit;
  resize: none;
  max-height: 150px;
  line-height: 1.5;
  background: transparent;
}

.input-wrapper textarea::placeholder {
  color: #64748b;
}

.send-btn {
  width: 40px;
  height: 40px;
  border-radius: 10px;
  border: none;
  background: #e2e8f0;
  color: #64748b;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.2s ease;
}

.send-btn.active {
  background: #2563eb;
  color: white;
}

.send-btn.active:hover {
  background: #1d4ed8;
  transform: scale(1.05);
}

.send-btn:disabled {
  cursor: not-allowed;
  opacity: 0.5;
}

/* 滚动条样式 */
::-webkit-scrollbar {
  width: 6px;
}

::-webkit-scrollbar-track {
  background: transparent;
}

::-webkit-scrollbar-thumb {
  background: rgba(0, 0, 0, 0.2);
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: rgba(0, 0, 0, 0.3);
}

/* 代码块样式 */
.message-text :deep(pre) {
  background: #1e293b;
  color: #e2e8f0;
  padding: 16px;
  border-radius: 8px;
  overflow-x: auto;
  margin: 12px 0;
  font-family: 'Fira Code', monospace;
  font-size: 14px;
}

.message-text :deep(code) {
  background: rgba(0, 0, 0, 0.1);
  padding: 2px 6px;
  border-radius: 4px;
  font-family: 'Fira Code', monospace;
  font-size: 14px;
}

.message.user .message-text :deep(code) {
  background: rgba(255, 255, 255, 0.2);
}

/* 响应式 */
@media (max-width: 768px) {
  .sidebar {
    position: fixed;
    left: -100%;
    top: 0;
    bottom: 0;
    z-index: 1000;
    transition: all 0.2s ease;
  }

  .sidebar.open {
    left: 0;
  }
}
</style>
