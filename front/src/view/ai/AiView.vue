<script setup lang="ts">

import {nextTick, ref} from "vue";
import {MagicStick} from '@element-plus/icons-vue'
import {useUserStore} from "@/store/UserStore";
import {getDefaultAIResponse, zhipuAi} from "@/api/ai/AiApi";
import {ZhiPu} from "@/enum/AIModel";
import {Cookie} from "@/api/utils/Cookie";
import {CookiesName} from "@/enum/CookiesName";

const userStore = useUserStore();
const messages = ref<Array<object>>([]);
const isShowAi = ref(true)
const iconColor = ref('#7e57c2')
const iconSize = ref(32)
const userInput = ref<string>('');
const chatBody = ref(null);
const toggleAi = () => {
  isShowAi.value = !isShowAi.value
}


const scrollToBottom = () => {
  nextTick(() => {
    if (chatBody.value) {
      chatBody.value.scrollTop = chatBody.value.scrollHeight;
    }
  });
};


const sendMessage = async () => {



  if (!userInput.value.trim()) return;
  // 添加用户消息
  messages.value.push({type: 'user', text: userInput.value});
  scrollToBottom();


  const us_au: string = Cookie.get(CookiesName.US_AU);
  console.log(us_au);
  // 如果用户未登录
  if (us_au == '') {
    messages.value.push(
        {
          type: 'ai',
          text: '请先登入再跟我聊天吧~',
        }
    )
    userInput.value = '';
    return;
  }

  // 发送到后端 AI 服务
  try {
    const aFunc = (data: string) => {
      const lastEle = messages.value[messages.value.length - 1];
      if (lastEle.type === 'user') {
        messages.value.push({
          type: 'ai',
          text: data,
        })
      } else {
        lastEle.text += data;
      }
      scrollToBottom();
    };

    // await getDefaultAIResponse(userInput.value, aFunc);
    const chatModel: ChatModel = {
      model: ZhiPu.GLM_4_AIR,
      message: userInput.value,
      temperature: 0.7,
    }
    // zhipuAi(chatModel, aFunc).then();
    getDefaultAIResponse(userInput.value, aFunc);

  } catch (error) {
    console.log(error);
    messages.value.push({type: 'ai', text: 'Error: Failed to get AI response.'});
    scrollToBottom();
  }
  // 清空输入框
  userInput.value = '';
}

</script>

<template>
  <div class="ai-container">
    <el-icon :size="iconSize" :color="iconColor" class="animated-icon" @click="toggleAi">
      <magic-stick/>
    </el-icon>
  </div>

  <div class="chat-container">
    <!-- 聊天窗口 -->
    <div class="chat-window" v-show="isShowAi">
      <div class="chat-header">
        <h3>ChatBot</h3>
        <button @click="isShowAi=false">Close</button>
      </div>

      <div class="chat-body" ref="chatBody">
        <div v-for="(message, index) in messages" :key="index" :class="message.type" class="chat-area">
          <el-container>
            <el-aside class="el-aside">
              <el-avatar class="avatar" :src="message.type==='user' ? userStore.getLoginUser.avatar :''">AI</el-avatar>
            </el-aside>
            <el-main class="el-main">
              <span class="body">{{ message.text }}</span>
            </el-main>
          </el-container>
        </div>
      </div>
      <div class="chat-input">
        <input v-model="userInput" @keyup.enter="sendMessage" placeholder="Type a message..."/>
        <button @click="sendMessage">Send</button>
        <button @click="sendMessage">Stop</button>
      </div>
    </div>
  </div>

</template>


<style scoped lang="scss">
.ai-container {
  width: 50px;
  height: 50px;
  position: fixed;
  right: -25px;
  bottom: 120px;
  z-index: 2100;
  display: flex;
  justify-content: center;
  align-items: center;

  .animated-icon {
    transition: transform 0.3s ease;

    &:hover {
      transform: translateX(-20px);
    }
  }
}

.chat-container {
  height: auto;
  width: auto;
  position: fixed;
  top: 100px;
  /*bottom: 20px;*/
  right: 20px;


  .chat-window {
    width: 600px;
    height: 500px;
    background: white;
    border: 1px solid #ccc;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;

    .chat-header {
      padding: 10px;
      background: #f0f0f0;
      border-bottom: 1px solid #ccc;
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .chat-body {
      flex: 1;
      padding: 10px;
      overflow-y: auto;


      .chat-area {
        margin-bottom: 20px;

        .el-aside {
          width: auto;
          height: auto;
          display: flex;
          justify-content: center;
          align-items: center;
        }

        .el-main {
          margin-left: 15px;
          padding: 0;
          line-height: 30px;
        }
      }
    }

    .chat-input {
      padding: 10px;
      border-top: 1px solid #ccc;
      display: flex;

      input {
        flex: 1;
        padding: 5px;
        border: 1px solid #ccc;
        border-radius: 5px;
      }

      button {
        margin-left: 10px;
        padding: 5px 10px;
        background: #007bff;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
      }
    }
  }


  .open-chat-button {
    position: fixed;
    bottom: 20px;
    right: 20px;
    padding: 10px 20px;
    background: #007bff;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
  }
}
</style>