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


export const updateOrSaveRating = async (userId: string, songListId: string, rating: number) => {
    return await myAxios.put(prefix, {}, {
        params: {
            userId,
            songListId,
            rating,
        }
    })
}


export const getRatingBy = async (userId: string, songListId: string) => {
    return await myAxios.get(prefix, {
        params: {
            userId,
            songListId,
        }
    })
}

export const getEvenRatingOfSongList = async (songListId: string) => {
    return await myAxios.get(prefix + '/calculateEvenRating', {
        params: {
            songListId,
        }
    })
}


export const updateSongList = async (songList: SongList) => {
    return await myAxios.put(prefix, songList);
}

export const getRandomSongList = async (capacity: number) => {
    return await myAxios.get(prefix + '/random', {
        params: {capacity}
    });
}


