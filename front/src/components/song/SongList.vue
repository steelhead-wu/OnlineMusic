<script lang="ts" setup>
const tableData = [
  {
    date: '2016-05-03',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-02',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-04',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
  {
    date: '2016-05-01',
    name: 'Tom',
    address: 'No. 189, Grove St, Los Angeles',
  },
]
</script>

<template>
  <div id="song-list">
<!--    <h2>我的喜欢</h2>-->
<!--    <br>-->
    <el-table class="song-list" :data="tableData" stripe>
      <el-table-column prop="date" label="歌曲" width="350"/>
      <el-table-column prop="name" label="歌手" width="350"/>
      <el-table-column prop="address" label="专辑" width="350"/>
      <el-table-column prop="address" label="编辑" width="350"/>
    </el-table>
  </div>
</template>

<style scoped>
#song-list {
  /*margin: 50px;*/
  position: fixed;
  left: 100px;
  align-content: center;
}

#song-list .song-list {
  width: 1500px;

}
</style>