export class Timer {

    private _timer: number;
    private _remainingTime: number;

    constructor(private store_key: string, private total_time: number,
                doStuff: Function, ...argument: any[]) {
        this._remainingTime = total_time * 1000;
        const item = localStorage.getItem(this.store_key);
        // console.log("remainingTime", item);
        if (item) {
            this._remainingTime = this.getRemainingTime();
            if (this._remainingTime <= 0) {
                this.resetRemainingTime();
            }
        } else {
            this.resetRemainingTime();
        }
        this.startTimer(doStuff, argument);
    }


    get timer(): number {
        return this._timer;
    }


    get remainingTime(): number {
        return this._remainingTime;
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

    resetRemainingTime = (): void => {
        this._remainingTime = this.total_time * 1000;
    }

    startTimer = (doStuff: Function, ...argument: any[]): void => {
        // console.log("start");
        this._timer = setInterval(this.updateTimer, 1000, doStuff, ...argument);
        // console.log('timer', timer);
    }

    updateTimer = (doStuff: Function, ...argument: any[]): void => {
        console.log('remaining time', Math.floor(this._remainingTime / 1000));
        this._remainingTime -= 1000;
        if (this._remainingTime <= 0) {
            console.log("do stuff");
            // console.log(argument[0]);
            doStuff(...argument);
            this.resetRemainingTime();
        }
    }

    deconstructor = () => {
        localStorage.setItem(this.store_key, JSON.stringify(
            {
                remainingTime: this.remainingTime,
                savedTime: new Date().valueOf(),
            }));

        clearInterval(this.timer);
    }



}