import myAxios from "@/api/request";
import type Likes from "@/pojo/Likes"


const prefix = '/api/likes';


export const addLike = async (likes: Likes) => {
    return await myAxios.post(prefix, likes);
}

export const conditionalDelete = async (likes: Likes) => {
    return await myAxios.delete(prefix, {
        data: likes,
    });
}


export const conditionalUpdate = async (updatedLikes: Likes, conditionalLikes: Likes) => {
    return await myAxios.put(prefix, updatedLikes, {
        params: conditionalLikes
    });
}

export const conditionalQuery = async (fieldLike: Likes, conditionalLike: Likes) => {
    return await myAxios.patch(prefix, {
            fieldLike,
            conditionalLike
        }
    );
}