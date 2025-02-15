export enum Singer {
    FEMALE_SINGER,
    MALE_SINGER,
    GROUP,
    ALL_SINGER,
}


export const singer_style_list = [
    {
        id: Singer.ALL_SINGER,
        name: '全部歌手'
    },
    {
        id: Singer.FEMALE_SINGER,
        name: '女歌手'
    },
    {
        id: Singer.MALE_SINGER,
        name: '男歌手'
    },
    {
        id: Singer.GROUP,
        name: '组和'
    },
]

