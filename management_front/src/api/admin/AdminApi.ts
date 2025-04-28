import type Admin from "@/pojo/Admin.ts";
import myAxios from "@/api/request.ts";
import type Singer from "@/pojo/Singer.ts";

const prefix = '/api/admin';

export const login = async (loginAdmin: Admin) => {
    return await myAxios.post(prefix + '/login', loginAdmin);
}


export const registry = async (registryAdmin: Admin) => {
    return await myAxios.post(prefix + '/registry', registryAdmin);
}

export const conditionalQuerySinger = async (singer: Singer) => {
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