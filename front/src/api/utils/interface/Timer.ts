export class Timer {

    private _timer: number;
    private _remainingTime: number;
    private _savedData: Array<object>;

    constructor(private store_key: string, private total_time: number,
                doStuff: Function, ...argument: any[]) {
        const item = localStorage.getItem(this.store_key);
        // console.log("locals", item);
        if (item) {
            // this._remainingTime = this.getRemainingTime();

            let {
                remainingTime,
                savedTime,
                savedData,
            }: { remainingTime: number, savedTime: number, savedData: Array<object> }
                = JSON.parse(localStorage.getItem(this.store_key));

            this._savedData = savedData;
            this._remainingTime = remainingTime + savedTime - Date.now();

            if (this._remainingTime <= 0) {
                this.resetRemainingTime();
            }
        } else {
            doStuff(...argument);
            this.resetRemainingTime();
        }
        this.startTimer(doStuff, argument);
    }


    get savedData(): Array<object> {
        return this._savedData;
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
            savedTime,
        }: { remainingTime: number, savedTime: number, savedData: any }
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
        console.log(this.store_key + ' remaining time', Math.floor(this._remainingTime / 1000));
        this._remainingTime -= 1000;
        if (this._remainingTime <= 0) {
            console.log("do stuff");
            // console.log(argument[0]);
            doStuff(...argument);
            this.resetRemainingTime();
        }
    }

    deconstructor = (savedData: Array<object>) => {
        localStorage.setItem(this.store_key, JSON.stringify(
            {
                remainingTime: this.remainingTime,
                savedTime: new Date().valueOf(),
                savedData,
            }));

        clearInterval(this.timer);
    }


}