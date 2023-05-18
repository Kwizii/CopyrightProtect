import {srcRoot} from "@/store/meta";

export function now() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const date = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    return `${year}年${month}月${date}日 ${hours}:${minutes}:${seconds}`;
}

export function formatUrl(url) {
    if (url.startsWith('http'))
        return url;
    return `${srcRoot}${url}`;
}

export function download(url, filename) {
    fetch(url)
        .then(response => response.blob())
        .then(blob => {
            const link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = filename;
            link.click();
        })
}