export enum Singer {
    FEMALE_SINGER = 1,
    MALE_SINGER = 2,
    GROUP = 3,
    ALL_SINGER = 0,
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

