<script setup lang="ts">
import {useRoute} from "vue-router";
import {useSingersStore} from "@/store/SingersStore";
import {baseURL} from "@/api/request";

import {onMounted, ref, watch} from "vue";
import SongList from "@/components/song/SongList.vue";
import {getSongBySongListID} from "@/api/song/SongApi";
import {getEvenRatingOfSongList, getRatingBy, updateOrSaveRating, updateSongList} from "@/api/songList/SongListApi";
import {useUserStore} from "@/store/UserStore";
import {getAllCommentsBy, saveComment, updateComment} from "@/api/comment/CommentApi";
import {getFormatTime} from "@/api/utils/MyUtils";
import Comments from "@/components/comments/Comments.vue";
import CommentDO from "@/pojo/DO/CommentDO";
import {CommentSortMode, SORT_MODE_LENGTH, SORTING_NAME} from "@/enum/CommentSortMode";
import FroalaEditor from 'froala-editor';

const route = useRoute();
const userStore = useUserStore();
const singersStore = useSingersStore();
const current_song_list = ref<SongList>(singersStore.getSongList[route.params.idx]);
const comments = ref<Array<CommentDO>>([]);


const rating = ref(0);
const colors = ref(['#99A9BF', '#F7BA2A', '#FF9900']); // same as { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }
const tableData = ref<Array<unknown>>([]);

onMounted(() => {
  getSongBySongListID(current_song_list.value.id).then(value => {
    for (const song: Song of value.data.data) {
      const split = song.title?.split('-');
      song['song'] = split[1];
      song['singer'] = split[0];
      tableData.value.push(song);
    }
  });

  getRatingBy(userStore.getLoginUser.id, current_song_list.value.id,).then(value => {
    console.log(value.data);
    rating.value = value.data.data.rating;
  })


  getAllCommentsBy(current_song_list.value.id).then(value => {
    comments.value = value.data.data;
  });

})

const changeRating = () => {
  updateOrSaveRating(userStore.getLoginUser.id, current_song_list.value.id, rating.value).then(
      value => {
        if (value.data.data) {
          getEvenRatingOfSongList(current_song_list.value.id).then(value1 => {
            if (value1.data.data != current_song_list.value.rating) {
              updateSongList({
                id: current_song_list.value.id,
                rating: value1.data.data
              }).then(value2 => {
                if (value2.data.data) {
                  current_song_list.value.rating = value1.data.data;
                  console.log("更新评分成功");
                } else {
                  console.log("更新评分失败");
                }

              })
            }

          });

        } else {
          console.log("更新评分失败")
        }
      }
  );
}

// new FroalaEditor('.editor froala', {
//   // Set the image upload parameter.
//   imageUploadParam: 'image_param',
//
//   // Set the image upload URL.
//   imageUploadURL: '/upload_image',
//
//   // Additional upload params.
//   imageUploadParams: {id: 'my_editor'},
//
//   // Set request type.
//   imageUploadMethod: 'POST',
//
//   // Set max image size to 5MB.
//   imageMaxSize: 5 * 1024 * 1024,
//
//   // Allow to upload PNG and JPG.
//   imageAllowedTypes: ['jpeg', 'jpg', 'png'],
// })
const myComment = ref();
const froala_config = ref({
  placeholderText: '理性发言，改善世界~',
  events: {
    initialized: function () {
      console.log('initialized')
    }
  },
});


const submitComment = () => {

  if (myComment.value == null || myComment.value === '') {
    return;
  }

  const formatTime = getFormatTime();
  saveComment({
    userId: userStore.getLoginUser.id,
    content: myComment.value,
    createdTime: formatTime,
    up: 0,
    songListId: current_song_list.value.id,
  }).then(value => {
    if (value.data.code === 200) {
      comments.value.push({
            id: value.data.data,
            user: userStore.getLoginUser,
            content: myComment.value,
            createdTime: formatTime, // yyyy-MM-dd HH:mm:ss
            up: 0,
            songListId: current_song_list.value.id,
          }
      )
      myComment.value = '';
    } else {
      console.log("评论添加失败");
    }
  })

}


const thumbUp = (idx: number) => {
  ++comments.value[idx].up;
  updateComment({
    id: comments.value[idx].id,
    userId: comments.value[idx].user.id,
    createdTime: comments.value[idx].createdTime,
    content: comments.value[idx].content,
    up: comments.value[idx].up,
    songListId: comments.value[idx].songListId
  }).then(value => {
    if (value.data.data) {
      console.log('更新comment成功');
    } else {
      console.log('更新comment失败');
    }
  })
}
const sort_mode = ref(0);
watch([comments, sort_mode], ([new_comments, new_sort_mode]) => {
  new_comments.sort((a, b) => {
    switch (new_sort_mode) {
      case CommentSortMode.UP_UP:
        return a.up - b.up;
      case CommentSortMode.UP_DOWN:
        return b.up - a.up;
      default:
        return 0;
    }
  });
}, {deep: true})


const switchSortMode = () => {

  sort_mode.value = (sort_mode.value + 1) % SORT_MODE_LENGTH;
}


</script>


<template>
  <el-container class="container">
    <el-aside class="aside">
      <div class="song-list-img-box">
        <el-image class="cover-img" :src="baseURL + current_song_list.pic"></el-image>
      </div>
      <h1>{{ current_song_list.title }}</h1>
    </el-aside>
    <el-main class="main">
      <div class="introduction">
        <h1>简介</h1>
        <p>{{ current_song_list.introduction }}</p>
      </div>


      <div class="rate-block">
        <div class="rating-of-song">
          <h2 class="demonstration">歌单评分</h2>
          <el-rate ref="rater" v-model="singersStore.getSongList[route.params.idx].rating" :colors="colors" show-score
                   disabled/>
        </div>

        <div class="my-rating">
          <h2 class="demonstration">我的评分</h2>
          <el-rate v-model="rating" :colors="colors" show-score @change="changeRating"/>
        </div>

      </div>


      <div class="songList">
        <SongList :table-data="tableData"/>
      </div>

      <div class="comment-box">
        <el-avatar class="header-img" :size="40" :src="userStore.getLoginUser.avatar"></el-avatar>
        <div class="editor">
          <froala :tag="'textarea'" :config="froala_config" v-model:value="myComment"></froala>
        </div>

        <button class="comment-btn" @click="submitComment">发布</button>
      </div>

      <div class="sort-btn">
        <el-button style="color: #818086;background-color: white;border: none;border-radius: 10%"
                   @click="switchSortMode">{{ SORTING_NAME[sort_mode] }}
        </el-button>
      </div>

      <Comments :comments="comments" @click="thumbUp"/>
    </el-main>
  </el-container>
</template>

<style scoped lang="scss">
.main {
  margin-top: 20px;
  margin-left: 50px;

  .sort-btn {
    width: 76%;
    margin-top: 20px;
    text-align: right;

  }

  .comment-box {
    display: flex;
    margin-top: 15px;

    .header-img {
      margin-top: 10px;
      margin-left: 10px;
    }

    .editor {
      margin-left: 10px;
      border: 0;
      padding: 0;
      width: 70%;
    }

    .comment-btn {
      border: 0;
      padding: 0;
      margin-top: 110px;
      margin-left: 10px;
      width: 50px;
      height: 50px;
      background: #e8e8e8;
      color: #07c060;
      border-radius: 20%;
      cursor: pointer;

      &:hover {
        background-color: #b0b0b0;
      }
    }
  }

  .songList {
    margin-top: 20px;
  }

  .introduction {
    max-width: 800px;
    padding: 0;
    margin: 0;

    h1 {
      font-weight: bold;
      font-size: 30px;
    }

    p {
      margin: 0;
      text-align: left;
      line-height: 1.5;
      min-width: 500px;
    }
  }

  .rate-block {
    display: flex;
    padding: 0;
    margin-top: 10px;


    .el-rate {
      //margin-left: 10px;
    }

    .rating-of-song {
      margin-top: 10px;

      .demonstration {
        text-align: center;
        font-weight: bold;
      }

    }


    .my-rating {
      margin-top: 10px;
      margin-left: 120px;

      .demonstration {
        text-align: center;
        font-weight: bold;
      }
    }
  }
}

.aside {
  margin-top: 50px;
  margin-left: 140px;

  h1 {
    margin-top: 10px;
    font-weight: bold;
  }

  .song-list-img-box {
    .cover-img {
      height: 250px;
      width: 250px;
      border-radius: 10%;
    }
  }
}


</style>