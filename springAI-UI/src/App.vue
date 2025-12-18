<template>
  <div class="app-container">
    <!-- 左侧边栏 -->
    <aside class="sidebar">
      <div class="sidebar-header">
        <div class="logo">
          <span class="logo-icon">🌸</span>
          <span class="logo-text">糖糖</span>
        </div>
        <button class="new-chat-btn" @click="createNewChat" title="新对话">
          <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M12 5v14M5 12h14"></path>
          </svg>
        </button>
      </div>
      
      <div class="chat-list">
        <div class="chat-list-header">历史记录</div>
        <div 
          v-for="chat in chatHistory" 
          :key="chat.id"
          class="chat-item"
          :class="{ active: currentChatId === chat.id }"
          @click="selectChat(chat.id)"
        >
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
          </svg>
          <span class="chat-title">{{ chat.title }}</span>
          <button class="delete-btn" @click.stop="deleteChat(chat.id)" title="删除">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M3 6h18M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path>
            </svg>
          </button>
        </div>
        <div v-if="chatHistory.length === 0" class="empty-hint">
          暂无对话记录
        </div>
      </div>
    </aside>

    <!-- 主聊天区域 -->
    <main class="chat-main">
      <!-- 消息展示区 -->
      <div class="messages-container" ref="messagesContainer">
        <div v-if="currentMessages.length === 0" class="welcome-screen">
          <div class="welcome-icon">🌸</div>
          <h1>糖糖 · 智能助手</h1>
          <p class="welcome-subtitle">嗨~ 有什么我可以帮你的吗? ✨</p>
        </div>

        <div v-for="(msg, index) in currentMessages" :key="index" class="message" :class="msg.role">
          <div class="message-avatar">
            <span v-if="msg.role === 'user'">😊</span>
            <span v-else>🌸</span>
          </div>
          <div class="message-content">
            <div class="message-role">{{ msg.role === 'user' ? '你' : '糖糖' }}</div>
            <div class="message-text" v-html="formatMessage(msg.content)"></div>
          </div>
        </div>

        <div v-if="isLoading" class="message assistant">
          <div class="message-avatar"><span>🌸</span></div>
          <div class="message-content">
            <div class="message-role">糖糖</div>
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
            placeholder="输入消息..."
            rows="1"
            ref="inputField"
          ></textarea>
          <button 
            class="send-btn" 
            @click="sendMessage"
            :disabled="!userInput.trim() || isLoading"
            :class="{ active: userInput.trim() && !isLoading }"
          >
            <svg width="20" height="20" viewBox="0 0 24 24" fill="currentColor">
              <path d="M2.01 21L23 12 2.01 3 2 10l15 2-15 2z"></path>
            </svg>
          </button>
        </div>
        <div class="input-footer">
                    糖糖会尽力帮助你~ 但偶尔也会出错哦，请核实重要内容 💕
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, nextTick, onMounted } from 'vue'

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

// 格式化消息
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

  if (!currentChatId.value) {
    await createNewChat()
  }

  currentMessages.value.push({
    role: 'user',
    content: message
  })

  userInput.value = ''
  isLoading.value = true
  scrollToBottom()

  try {
    const response = await fetch(`/ai/chat?message=${encodeURIComponent(message)}&conversationId=${currentChatId.value}`)
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }

    const aiMessageIndex = currentMessages.value.length
    currentMessages.value.push({
      role: 'assistant',
      content: ''
    })
    isLoading.value = false

    const reader = response.body.getReader()
    const decoder = new TextDecoder()
    let fullContent = ''

    while (true) {
      const { done, value } = await reader.read()
      if (done) break

      const chunk = decoder.decode(value, { stream: true })
      
      const lines = chunk.split('\n')
      for (const line of lines) {
        if (line.startsWith('data:')) {
          const content = line.substring(5)
          fullContent += content
        } else if (line.trim() !== '') {
          fullContent += line
        }
      }
      
      currentMessages.value[aiMessageIndex] = {
        role: 'assistant',
        content: fullContent
      }
      
      scrollToBottom()
    }

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
  background: #ffffff;
}

/* 左侧边栏 - ChatGPT风格 */
.sidebar {
  width: 260px;
  background: #f9f9f9;
  display: flex;
  flex-direction: column;
  border-right: 1px solid #e5e5e5;
}

.sidebar-header {
  padding: 12px 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e5e5e5;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
}

.logo-icon {
  font-size: 24px;
}

.logo-text {
  font-size: 16px;
  font-weight: 600;
  color: #1a1a1a;
}

.new-chat-btn {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: transparent;
  border: 1px solid #e5e5e5;
  border-radius: 8px;
  cursor: pointer;
  color: #666;
  transition: all 0.15s ease;
}

.new-chat-btn:hover {
  background: #ececec;
  color: #1a1a1a;
}

.chat-list {
  flex: 1;
  overflow-y: auto;
  padding: 8px;
}

.chat-list-header {
  padding: 8px 12px;
  font-size: 12px;
  font-weight: 500;
  color: #666;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.chat-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 12px;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.15s ease;
  margin-bottom: 2px;
  color: #1a1a1a;
}

.chat-item:hover {
  background: #ececec;
}

.chat-item.active {
  background: #e5e5e5;
}

.chat-item svg {
  flex-shrink: 0;
  opacity: 0.5;
}

.chat-title {
  flex: 1;
  font-size: 14px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.delete-btn {
  opacity: 0;
  background: none;
  border: none;
  color: #666;
  cursor: pointer;
  padding: 4px;
  border-radius: 4px;
  transition: all 0.15s ease;
}

.chat-item:hover .delete-btn {
  opacity: 1;
}

.delete-btn:hover {
  background: #ddd;
  color: #d32f2f;
}

.empty-hint {
  padding: 20px;
  text-align: center;
  color: #999;
  font-size: 14px;
}

/* 主聊天区域 */
.chat-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  background: #ffffff;
}

.messages-container {
  flex: 1;
  overflow-y: auto;
  padding: 0;
}

/* 欢迎界面 - ChatGPT风格 */
.welcome-screen {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  text-align: center;
  padding: 40px;
}

.welcome-icon {
  font-size: 48px;
  margin-bottom: 20px;
}

.welcome-screen h1 {
  font-size: 28px;
  font-weight: 600;
  color: #1a1a1a;
  margin-bottom: 8px;
}

.welcome-subtitle {
  font-size: 16px;
  color: #666;
}

/* 消息样式 - ChatGPT风格 */
.message {
  display: flex;
  gap: 16px;
  padding: 24px 20%;
  border-bottom: 1px solid #f0f0f0;
}

.message.user {
  background: #ffffff;
}

.message.assistant {
  background: #f7f7f8;
}

.message-avatar {
  width: 32px;
  height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  flex-shrink: 0;
}

.message-content {
  flex: 1;
  min-width: 0;
}

.message-role {
  font-size: 13px;
  font-weight: 600;
  color: #1a1a1a;
  margin-bottom: 4px;
}

.message-text {
  font-size: 15px;
  line-height: 1.7;
  color: #374151;
  word-wrap: break-word;
}

/* 打字指示器 */
.typing-indicator {
  display: flex;
  gap: 4px;
  padding: 8px 0;
}

.typing-indicator span {
  width: 8px;
  height: 8px;
  background: #10a37f;
  border-radius: 50%;
  animation: bounce 1.4s infinite ease-in-out;
}

.typing-indicator span:nth-child(1) { animation-delay: -0.32s; }
.typing-indicator span:nth-child(2) { animation-delay: -0.16s; }

@keyframes bounce {
  0%, 80%, 100% { transform: scale(0); }
  40% { transform: scale(1); }
}

/* 输入区域 - ChatGPT风格 */
.input-container {
  padding: 16px 20%;
  background: #ffffff;
  border-top: 1px solid #e5e5e5;
}

.input-wrapper {
  display: flex;
  align-items: flex-end;
  gap: 12px;
  background: #f4f4f4;
  border: 1px solid #e5e5e5;
  border-radius: 24px;
  padding: 12px 16px;
  transition: all 0.15s ease;
}

.input-wrapper:focus-within {
  border-color: #10a37f;
  box-shadow: 0 0 0 2px rgba(16, 163, 127, 0.1);
}

.input-wrapper textarea {
  flex: 1;
  border: none;
  outline: none;
  font-size: 15px;
  font-family: inherit;
  resize: none;
  max-height: 200px;
  line-height: 1.5;
  background: transparent;
  color: #1a1a1a;
}

.input-wrapper textarea::placeholder {
  color: #999;
}

.send-btn {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  border: none;
  background: #e5e5e5;
  color: #999;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.15s ease;
}

.send-btn.active {
  background: #10a37f;
  color: white;
}

.send-btn.active:hover {
  background: #0d8a6a;
}

.send-btn:disabled {
  cursor: not-allowed;
}

.input-footer {
  text-align: center;
  margin-top: 8px;
  font-size: 12px;
  color: #999;
}

/* 滚动条 */
::-webkit-scrollbar {
  width: 6px;
}

::-webkit-scrollbar-track {
  background: transparent;
}

::-webkit-scrollbar-thumb {
  background: #d1d5db;
  border-radius: 3px;
}

::-webkit-scrollbar-thumb:hover {
  background: #9ca3af;
}

/* 代码块样式 */
.message-text :deep(pre) {
  background: #1e1e1e;
  color: #e5e5e5;
  padding: 16px;
  border-radius: 8px;
  overflow-x: auto;
  margin: 12px 0;
  font-family: 'Fira Code', 'Consolas', monospace;
  font-size: 14px;
}

.message-text :deep(code) {
  background: rgba(0, 0, 0, 0.05);
  padding: 2px 6px;
  border-radius: 4px;
  font-family: 'Fira Code', 'Consolas', monospace;
  font-size: 14px;
}

/* 响应式 */
@media (max-width: 1200px) {
  .message {
    padding: 24px 10%;
  }
  .input-container {
    padding: 16px 10%;
  }
}

@media (max-width: 768px) {
  .sidebar {
    position: fixed;
    left: -100%;
    top: 0;
    bottom: 0;
    z-index: 1000;
    transition: all 0.2s ease;
  }

  .message {
    padding: 16px;
  }

  .input-container {
    padding: 16px;
  }
}
</style>
