<script lang="ts" setup>
import {ref} from 'vue'
import Icon from "@/components/play/Icon.vue";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";


const value4 = ref(0)
const isPlay = ref(false);
const formatTooltip = (val: number) => {
  return val / 100
}
</script>

<template>
  <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
  <div id="playBar">

    <div class="song-process">
      <el-slider v-model="value4" :format-tooltip="formatTooltip"/>
    </div>

    <div class="control-box">
      <div class="cover">
        <img src="../../assets/img/logo.jpg" alt="" srcset="">
      </div>

      <div class="control-btn-area">

        <FontAwesomeIcon icon="fa fa-heart" size="2x" style="color: red;"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <FontAwesomeIcon class="control-btn-each" icon="fa-refresh" size="2x"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <FontAwesomeIcon class="control-btn-each" icon="fa-step-backward" size="2x"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <FontAwesomeIcon v-if="isPlay" icon="fa-play" size="2x"/>
        <FontAwesomeIcon v-else icon="fa-pause" size="2x"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <FontAwesomeIcon icon="fa-step-forward" size="2x"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <FontAwesomeIcon icon="fa-navicon" size="2x"/>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <FontAwesomeIcon icon="fa-cloud-download" size="2x"/>
      </div>
    </div>
  </div>
</template>


<style scoped>
#playBar {
  /*width: 100%;*/
  /*position: fixed;*/
  /*bottom: 0;*/

}


.control-box .control-btn-area {
  position: relative;
  bottom: 70px;
  left: 245px;
  z-index: 1;
}

.control-btn-each:hover {
  color: red;
}


.control-box .cover {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  /*background-color: #fff;*/
  background-color: snow;
  /*margin-top: -60px;*/
  padding: 6px;
  position: relative;
  bottom: 0;
  animation: spin 5s infinite linear;
  /*animation:  3s linear 1s slidein;*/
  animation-play-state: running;
}

.cover img {
  width: 100%;
  border-radius: 50%;
  position: relative;
  left: 17px;
}


#playBar .control-box {
  width: 100%;
  height: 100px;
  position: fixed;
  left: 0;
  bottom: 50px;
  z-index: 1;
  background-color: snow;
}

#playBar .song-process {
  position: fixed;
  z-index: 100;
  bottom: 150px;
  width: 100%;
  transition: all 0.5s;
  left: 0;
}

.slider-demo-block {
  max-width: 600px;
  display: flex;
  align-items: center;

}

.slider-demo-block .el-slider {
  margin-top: 0;
  margin-left: 12px;
}

.slider-demo-block .demonstration {
  font-size: 14px;
  color: var(--el-text-color-secondary);
  line-height: 44px;
  flex: 1;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-bottom: 0;
}

.slider-demo-block .demonstration + .el-slider {
  flex: 0 0 70%;
}
</style>