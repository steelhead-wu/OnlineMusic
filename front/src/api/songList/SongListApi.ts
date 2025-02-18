import myAxios from "@/api/request";


const prefix = '/api/songList';
export const getAllSongList = async () => {
    return await myAxios.get(prefix)
}

