import type Song from "@/pojo/Song.ts";

export default interface SongDO extends Song {
    singerName?: string,
}