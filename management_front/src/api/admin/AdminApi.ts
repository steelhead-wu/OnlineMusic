import type Admin from "@/pojo/Admin.ts";
import myAxios from "@/api/request.ts";
import type Singer from "@/pojo/Singer.ts";
import type Song from "@/pojo/Song.ts";
import type Transfer from "@/pojo/Transfer.ts";
import type SongList from "@/pojo/SongList.ts";

const prefix = '/api/admin';

export const login = async (loginAdmin: Admin) => {
    return await myAxios.post(prefix + '/login', loginAdmin);
}


export const registry = async (registryAdmin: Admin) => {
    return await myAxios.post(prefix + '/registry', registryAdmin);
}

export const querySingerByID = async (singer: Singer) => {
    return await myAxios.post(prefix + '/singer', singer);
}


export const deleteSingerById = async (id: number) => {
    return await myAxios.put(prefix + '/singer/delete', null, {
        params: {
            id
        }
    });
}


export const updateSinger = async (singer: Singer) => {
    return await myAxios.put(prefix + '/singer', singer);
}

/**
 * add a singer
 */
export const addSinger = async (singer: Singer) => {
    return await myAxios.post(prefix + '/singer/add', singer);
}


export const searchSinger = async (keyword: string) => {
    return await myAxios.get(prefix + '/singer/s', {
        params: {
            keyword
        }
    });
}

export const querySongByID = async (song: Song) => {
    return await myAxios.patch(prefix + '/song', song);
}


export const updateSongByID = async (song: Song, isUploadLyric: boolean) => {
    return await myAxios.put(prefix + '/song', song, {
        params: {
            isUploadLyric
        }
    });
}


export const deleteSongById = async (id: string) => {
    return await myAxios.put(prefix + '/song/delete', null, {
        params: {
            id
        }
    });
}


export const download = async (transfer: Transfer) => {
    return await myAxios.post('/api/files/download', transfer,
        {
            responseType: 'json'
        });

}


export const upload = async (transfer: object) => {
    return await myAxios.post('/api/files/upload', transfer, {
        withCredentials: true,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });

}

export const downloadMusic = async (transfer: Transfer, responseType = 'json') => {
    return await myAxios.post('/api/files/download', transfer,
        {
            responseType: 'blob'
        });

}


export const addSong = async (song: Song) => {
    return await myAxios.post(prefix + '/song', song);
}

export const searchSong = async (keyword: string) => {
    return await myAxios.get(prefix + '/song/s', {
        params: {
            keyword
        }
    });
}

export const conditionalQuerySongList = async (songList: SongList) => {
    return await myAxios.patch(prefix + '/song-list', songList);
}

export const updateSongListByID = async (songList: SongList) => {
    return await myAxios.put(prefix + '/song-list', songList);
}


export const deleteSongListByID = async (id: string) => {
    return await myAxios.put(prefix + '/song-list', null, {
        params: {
            id
        }
    });
}


export const uploadSongListImg = async (transfer: object) => {
    return await myAxios.post(prefix + '/song-list/uploadImg', transfer, {
        headers: {
            "Content-Type": "multipart/form-data"
        },
    });
}

export const addSongList = async (transfer: object) => {
    return await myAxios.post(prefix + '/song-list', transfer, {
        headers: {
            "Content-Type": "multipart/form-data"
            // "Content-Type": "application/octet-stream"
        },
    });
}

export const searchSongList = async (keyword: string) => {
    return await myAxios.get(prefix + '/song-list/s', {
        params: {
            keyword
        }
    });
}

export const getSongBySongListID = async (songListID: string) => {
    return await myAxios.get(prefix + '/song-list/detail', {
        params: {
            songListID
        }
    });
}

export const addSongToSongList = async (songID: string, songListID: string) => {
    return await myAxios.post(prefix + '/song', null, {
        params: {
            songID,
            songListID,
        }
    });
}

export const deleteSongFromSongList = async (songID: string, songListID: string) => {
    return await myAxios.put(prefix + '/song', null, {
        params: {
            songID,
            songListID,
        }
    });
}


export const searchSongFromSongList = async (songListID: string, kw: string) => {
    return await myAxios.get(prefix + '/song-list-detail/s', {
        params: {
            kw,
            songListID,
        }
    });
}