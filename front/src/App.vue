<script setup lang="ts">



const user = {
  password: '2',
  // id: '1882054264398209025', // 根据id更新
  account: '8184332582', // 根据账户更新
  // nickname: '赵世毅1',

}


const song: Song = {
  singerId: '1',
  title: '光辉岁月',
  id: '1',
  album: '光辉岁月十五年'

}

// console.log(login(user));


// register(user);

// addUser({
//   account: '123',
//   password: '123',
// }).then(value => value, reason => reason)

// updateUserByAccount(user)
// searchSong('你好');

// const globalStatusStore = useGlobalStatusStore();
// const userStore = useUserStore();
// console.log("========app.vue=========");
// console.log(JSON.stringify(globalStatusStore));
// console.log(JSON.stringify(userStore));
// console.log("========app.vue-out=========");
</script>

<template>

  <router-view></router-view>
</template>



