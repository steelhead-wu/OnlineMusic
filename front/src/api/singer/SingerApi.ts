import myAxios from "@/api/request";


const prefix = '/api/singer';
export const getSingerBySex = async (sex: number) => {
    return await myAxios.get(prefix, {
        params: {
            sex
        }
    })
}


export const getAllSinger = async () => {
    return await myAxios.get(prefix);
}




