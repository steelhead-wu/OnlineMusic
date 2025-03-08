import {Timer} from "../utils/interface/Timer";


class SingerTimer implements Timer {

    isStart: boolean;
    // unit: millisecond
    duration: number;

    timer: number


    constructor(duration: number) {
        this.duration = duration;
        this.isStart = false;
    }

    end(): void {
        if (this.isStart) {
            this.isStart = false;
            clearInterval(this.timer);
        }
    }


    start(doStuff: Function): void {
        if (!this.isStart) {
            this.isStart = true;
            this.timer = setInterval(doStuff, this.duration);
        }
    }
}


let singerTimer = new SingerTimer(1000);
singerTimer.start(() => console.log("hi"));

setTimeout(singerTimer.end,3000);

