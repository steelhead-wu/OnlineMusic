export class Cookie {

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