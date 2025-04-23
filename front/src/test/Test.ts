

const express = require('express');
const app = express();
const port = 3000;

// 模拟数据
const data = {
    message: "Hello, Vue 3!",
    timestamp: new Date().toISOString(),
};

// 定义接口
app.get('/api/data', (req, res) => {
    res.json(data); // 返回 JSON 数据
});

app.listen(port, () => {
    console.log(`服务器运行在 http://localhost:${port}`);
});