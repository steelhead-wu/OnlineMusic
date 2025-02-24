<script setup lang="ts">

import {useUserStore} from "@/store/UserStore";
import {onMounted, ref, watch} from "vue";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import CommentDO from "@/pojo/DO/CommentDO";
import Comment from "@/pojo/Comment";


const userStore = useUserStore();


const props = defineProps<{
  comments: Array<CommentDO>
}>();

const emits = defineEmits<{
  click: [idx: number]
}>();

onMounted(() => {
  console.log("props.comments", props.comments.length);
})


</script>


<template>
  <div class="comments">
    <ul>
      <li v-for="(commentDO,idx) in props.comments" :key="idx">
        <div class="comment">
          <div class="header">

            <el-avatar class="avatar" :src="commentDO.user.avatar"/>

            <span>
              <div class="nickname">{{ commentDO.user.nickname }}</div>
              <div class="time">{{ commentDO.createdTime.slice(0, 10) }}</div>
            </span>
          </div>

          <div class="main">
            <div v-html="commentDO.content"></div>
          </div>

          <div class="footer">

            <span class="up-number" style="margin-top: 22px">{{ commentDO.up }}</span>

            <FontAwesomeIcon class="up-icon" icon="fa fa-thumbs-up" @click="emits('click',idx)"/>
            <!--            <FontAwesomeIcon icon="fa fa-thumbs-o-up" style="cursor: pointer"></FontAwesomeIcon>-->
            <!--            <FontAwesomeIcon icon="fa fa-heart" size="2x" style="color: red;"/>-->

          </div>
        </div>
      </li>
    </ul>
  </div>
</template>


<style scoped lang="scss">
.comments {
  width: 72%;
  margin-left: 40px;

  .comment {
    margin-top: 10px;
    margin-left: 20px;

    .header {
      // include avatar, nickname, time
      display: flex;
      width: 800px;
      height: 100%;
      //border: 1px solid;

      .avatar {
        margin-top: 10px;
        height: 50px;
        width: 50px;
      }

      .nickname {
        align-content: center;
        margin-left: 10px;
        margin-top: 10px;
      }

      .time {
        font-size: 13px;
        margin-left: 10px;
        margin-top: 5px;
      }
    }

    .main {
      //p {
      text-align: left;
      margin-top: 10px;
      margin-left: 65px;
      //}
    }

    .footer {
      height: 50px;
      text-align: right;

      .up-number {

        margin-top: 40px;
        margin-right: 5px;
      }

      .up-icon {
        cursor: pointer;
      }
    }
  }

  ul {
    padding: 0;

    li {
      list-style: none;
    }
  }


}
</style>