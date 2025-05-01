<script setup lang="ts">

import {baseURL} from "@/api/request.ts";


const props = defineProps<{
  table: Array<object>,
  propLabel: Array<Array<string>>
}>();
</script>


<template>
  <el-table :data="table" style="width: 100%" border height="731px" highlight-current-row
            :row-style="{ height: '10px' }">
    <el-table-column :prop="item[0]" :label="item[1]" width="180" align="center" v-for="(item,idx) in propLabel"
                     :key="idx">
      <template #default="s">
        <img v-if="item[0]==='pic'" :src="baseURL+s.row.pic" style="border-radius: 10%;width: 150px;height: 150px">

      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped lang="scss">

</style>