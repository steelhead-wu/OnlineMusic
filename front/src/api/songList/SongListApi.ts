import myAxios from "@/api/request";


const prefix = '/api/songList';
export const getAllSongList = async () => {
    return await myAxios.get(prefix)
}


export const getSongListByStyle = async (style: string) => {
    return await myAxios.get(prefix, {
        params: {
            style
        }
    })
}
