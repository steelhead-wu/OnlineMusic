import myAxios from "@/api/request";
import type {User} from "@/pojo/User.ts";


const prefix = '/api/user';

export const getAllUser = async () => {
    return await myAxios.get(prefix);
}


export const addUser = async (user: User) => {
    return await myAxios.post(prefix, user);
}


export const updateUserById = async (user: User) => {
    return await myAxios.put(prefix, user);
}

export const updateUserByAccount = async (user: User) => {
    return await myAxios.put(prefix + '/account', user);
}

export const register = async (user: User) => {
    return await myAxios.post(prefix + '/registry', user);
}


export const login = async (user: User) => {
    return await myAxios.post(prefix + '/login', user);
}


export const logoff = async (id: string) => {
    return await myAxios.get(prefix + '/logoff', {
        params: {
            id
        }
    });
}







