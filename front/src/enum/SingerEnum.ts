export enum SingerEnum {
    FEMALE_SINGER = 1,
    MALE_SINGER = 2,
    GROUP = 3,
    ALL_SINGER = 0,
}


export const singer_style_list = [
    {
        id: SingerEnum.ALL_SINGER,
        name: '全部歌手'
    },
    {
        id: SingerEnum.FEMALE_SINGER,
        name: '女歌手'
    },
    {
        id: SingerEnum.MALE_SINGER,
        name: '男歌手'
    },
    {
        id: SingerEnum.GROUP,
        name: '组和'
    },
]

