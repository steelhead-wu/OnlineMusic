export class Timer {

    private timer: number;
    private remainingTime: number;

    constructor(private store_key: string, private total_time: number,
                doStuff: Function, ...argument: any[]) {
        this.remainingTime = total_time * 1000;
        const item = localStorage.getItem(this.store_key);
        // console.log("remainingTime", item);
        if (item) {
            this.remainingTime = this.getRemainingTime();
            if (this.remainingTime <= 0) {
                this.resetRemainingTime();
            }
        } else {
            this.resetRemainingTime();
        }
        this.startTimer(doStuff, argument);
    }


    getRemainingTime = (): number => {
        // {millisecond,millisecond}
        let {
            remainingTime,
            savedTime
        }: { remainingTime: number, savedTime: number }
            = JSON.parse(localStorage.getItem(this.store_key));

        return remainingTime + savedTime - Date.now();
    }

    resetRemainingTime = () => {
        this.remainingTime = this.total_time * 1000;
    }

    startTimer = (doStuff: Function, ...argument: any[]) => {
        // console.log("start");
        this.timer = setInterval(this.updateTimer, 1000, doStuff, ...argument);
        // console.log('timer', timer);
    }

    updateTimer = (doStuff: Function, ...argument: any[]) => {
        console.log('remaining time', Math.floor(this.remainingTime / 1000));
        this.remainingTime -= 1000;
        if (this.remainingTime <= 0) {
            console.log("do stuff");
            // console.log(argument[0]);
            doStuff(...argument);
            this.resetRemainingTime();
        }
    }

}