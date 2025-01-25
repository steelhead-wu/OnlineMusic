import myAxios from "@/api/request";


const prefix = '/api/song';
export const searchSong = async (keyword) => {
    return await myAxios.get(prefix + '/s', {
        params: {
            kw: keyword
        }
    })
}