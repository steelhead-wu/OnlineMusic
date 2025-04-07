import {fetchPost} from "@/api/MyFetch";
import {baseURL} from "@/api/request";
import type {ChatModel} from "@/pojo/ChatModel.ts";


const prefix = '/api/ai';


// AiApi.ts
// export const getDefaultAIResponse = (msg: string, aFunc: (data: string) => void): Promise<void> => {
export const getDefaultAIResponse = (msg: string, aFunc: (data: string) => void)/*: Promise<void> */ => {
    handleSSE(prefix + '/default?msg=' + msg, aFunc)
    // handleSSE(prefix + '?msg=' + msg, aFunc)
};


export const zhipuAi = async (chatModel: ChatModel, aFunc: (data: string) => void) => {
    const response = await fetchPost(prefix + '/zhipu', chatModel);
    await handleResponse(response, aFunc);
}


const handleResponse = async (response: any, aFunc: (data: string) => void) => {
    if (!response.body) {
        throw new Error('ReadableStream not supported');
    }

    const reader = response.body.getReader();
    const decoder = new TextDecoder();

    while (true) {
        const {done, value} = await reader.read();
        if (done) break;

        const chunk = decoder.decode(value, {stream: true});
        // 处理数据
        aFunc(chunk);
    }
}


const handleSSE = (url: string, aFunc: (data: string) => void) => {
    const sse = new EventSource(baseURL + url, {
        withCredentials: true
    });
    const message = (ev: MessageEvent) => {
        // console.log('ev', ev.data);
        if (ev.data === '[complete]') {
            sse.close();
            // resolve(); // 返回完整的 AI 响应
            return;
        }
        aFunc(ev.data);
    };

    const error = () => {
        sse.close();
        // reject(new Error('SSE error occurred')); // 返回错误
    };


    set_SSE_Event(sse, message, error);
}
const set_SSE_Event = (sse: EventSource, message: (ev: MessageEvent) => void, error: () => void) => {
    sse.onmessage = message;
    sse.onerror = error;
}