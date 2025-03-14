import {baseURL} from "@/api/request";


const prefix = baseURL + '/api/ai';


// AiApi.ts
export const getDefaultAIResponse = (msg: string, aFunc: (data: string) => void): Promise<void> => {
    return new Promise((resolve, reject) => {
        const sse = new EventSource(prefix + '/default?msg=' + msg);

        sse.onmessage = (ev) => {
            console.log('ev', ev);
            if (ev.data === '[complete]') {
                sse.close();
                resolve(); // 返回完整的 AI 响应
                return;
            }
            aFunc(ev.data );
        };

        sse.onerror = () => {
            sse.close();
            reject(new Error('SSE error occurred')); // 返回错误
        };
    });
};