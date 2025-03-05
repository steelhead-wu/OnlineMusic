import myAxios from "@/api/request";
import type Likes from "@/pojo/Likes"


const prefix = '/api/likes';


export const addLike = async (likes: Likes) => {
    return await myAxios.post(prefix, likes);
}


export const getLikeBy = async (userId: string, songId: string) => {
    return await myAxios.get(prefix, {
        params: {
            userId,
            songId,
        }
    });
}

export const delLikeBy = async (likes: Likes) => {
    return await myAxios.delete(prefix, {
        data: likes,
    });
}
