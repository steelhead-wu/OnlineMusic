import type {PictureRepoType} from "@/enums/PictureRepoType.ts";

export default interface Transfer {
    path: string
    mediaType: string
    contentDisposition: string;
}