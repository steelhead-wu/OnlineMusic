export class Cookie {
    /**
     *
     * @param key cookie的键
     * @return 未找到返回空字符串，否则返回cookie的值
     */
    static get(key: string): string {
        const strings = document.cookie.split('; ');

        for (const str of strings) {
            // k=v
            let p = str.indexOf('=');
            let k = str.substring(0, p);

            if (key == k) {
                return str.substring(p + 1);
            }
        }
        return '';
    }

    static set(data: string): void {
        document.cookie = data;
    }
}