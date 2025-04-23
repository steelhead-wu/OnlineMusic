"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var SingerTimer = /** @class */ (function () {
    function SingerTimer(duration) {
        this.duration = duration;
        this.isStart = false;
    }
    SingerTimer.prototype.end = function () {
        if (this.isStart) {
            this.isStart = false;
            clearInterval(this.timer);
        }
    };
    SingerTimer.prototype.start = function (doStuff) {
        if (!this.isStart) {
            this.isStart = true;
            this.timer = setInterval(doStuff, this.duration);
        }
    };
    return SingerTimer;
}());
var singerTimer = new SingerTimer(1000);
singerTimer.start(function () { return console.log("hi"); });
setTimeout(singerTimer.end, 3000);
