import myAxios from "@/api/request";
import type Song from "@/pojo/Song.ts";


const prefix = '/api/song';
export const searchSong = async (keyword:string) => {
    return await myAxios.get(prefix + '/s', {
        params: {
            kw: keyword
        }
    })
}


export const download = async (song: Song) => {
    return await myAxios.post(prefix + '/download', song, {
        responseType: "blob"
    });
}

export const getAllSongBySingerId = async (singerId: number) => {
    return await myAxios.get(prefix, {
        params: {
            singerId
        }
    });
}

export const getSongBySongListID = async (songListId: number) => {
    return await myAxios.get(prefix, {
        params: {
            songListId
        }
    });
}

export const getLikedSongByUserId = async (userId: string) => {
    return await myAxios.get(prefix, {
        params: {
            userId
        }
    });
}
