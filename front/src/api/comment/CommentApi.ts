import myAxios from "@/api/request";
import type Comment from "@/pojo/Comment"

const prefix = '/api/comment';
export const getCommentBy = async (songListId: string, userId: string) => {
    return await myAxios.get(prefix, {
        params: {
            songListId,
            userId
        }
    })
}


export const saveComment = async (comment: Comment) => {
    return await myAxios.post(prefix, comment);
}


export const getAllCommentsBy = async (songListId: string) => {
    return await myAxios.get(prefix, {
        params: {
            songListId,
        }
    })
}

export const updateComment = async (comment: Comment) => {
    return await myAxios.put(prefix, comment);
}