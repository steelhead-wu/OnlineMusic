export default interface CommentDO {
    id: string,
    user: User,
    createdTime: string, // yyyy-MM-dd HH:mm:ss
    content: string,
    up: number,
    songListId: string,
}
