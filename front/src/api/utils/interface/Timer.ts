export interface Timer {
    duration: number
    isStart: boolean
    end: () => void
    start: (doStuff: Function) => void
}