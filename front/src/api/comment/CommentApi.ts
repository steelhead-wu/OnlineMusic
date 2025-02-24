import myAxios from "@/api/request";
// import CommentDO from "@/pojo/DO/CommentDO";
// import Comment from "@/pojo/Comment"

const prefix = '/api/comment';
export const getCommentBy = async (songListId: string, userId: string) => {
    return await myAxios.get(prefix, {
        params: {
            songListId,
            userId
        }
    })
}


export const saveComment = async (comment) => {
    return await myAxios.post(prefix, comment);
}


export const getAllCommentsBy = async (songListId: string) => {
    return await myAxios.get(prefix, {
        params: {
            songListId,
        }
    })
}

export const updateComment = async (comment) => {
    return await myAxios.put(prefix, comment);
}