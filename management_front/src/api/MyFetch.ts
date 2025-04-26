import {baseURL} from "@/api/request";


export const fetchPost = async (url: string, body: object) => {
    return await fetchCommon(url, 'POST', body);
}

export const fetchGet = async (url: string) => {
    return await fetchCommon(url, 'GET');
}

export const fetchCommon = async (url: string, method: string, body = null) => {
    method = method.toUpperCase();
    if (method == 'GET' || method == 'HEAD') {
       // console.log('body', body != {})

        if (body == null) {
            return await fetch(baseURL + url, {
                method,
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include' // 允许携带 Cookie
            })
        }
        throw TypeError("Failed to execute 'fetch' on 'Window': Request with GET/HEAD method cannot have body");
    }

    return await fetch(baseURL + url, {
        method,
        body: JSON.stringify(body),
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'include' // 允许携带 Cookie
    })
}

// get only
export const getWithSSE = (url: string, aFunc: (data: string) => void) => {
    const sse = new EventSource(baseURL + url);
    sse.onmessage = (ev) => {
        console.log('ev', ev.data);
        if (ev.data === '[complete]') {
            sse.close();
            // resolve(); // 返回完整的 AI 响应
            return;
        }
        aFunc(ev.data);
    };

    sse.onerror = () => {
        sse.close();
        // reject(new Error('SSE error occurred')); // 返回错误
    };
}