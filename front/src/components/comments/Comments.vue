<script setup lang="ts">

import Comment from "@/pojo/Comment"
import {useUserStore} from "@/store/UserStore";
import {onMounted, ref} from "vue";


const userStore = useUserStore();


const props = defineProps<{
  comments: Array<Comment>
}>();

onMounted(() => {
  console.log("props.comments", props.comments.length);
})

</script>


<template>
  <div class="comments">
    <ul>
      <li v-for="(comment,idx) in props.comments" :key="idx">
        <!--        <el-avatar class="avatar" :src="comment.user.avatar"/>-->
        <!--        <span>{{ comment. }}</span>-->
        时间<span>{{ comment.createdTime }}</span>
        点赞<span>{{ comment.up }}</span>
        评论<span v-html="comment.content"> </span>
      </li>
    </ul>
  </div>
</template>

<style scoped lang="scss">
.comments {
  li {
    list-style: none;

    span {
      margin-left: 2px;
    }
  }

}
</style>