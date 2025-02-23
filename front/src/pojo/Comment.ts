// src/pojo/Comment.ts
export default interface Comment {
    userId: string,
    createdTime: string, // yyyy-MM-dd HH:mm:ss
    content: string,
    up: number,
    songListId: string,
}
