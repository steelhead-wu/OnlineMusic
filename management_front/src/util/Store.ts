class Store {

    constructor(private _key: string, private _value: object) {
        localStorage.setItem(_key, JSON.stringify(_value));
    }


    get key(): string {
        return this._key;
    }

    get value(): object {
        return this._value;
    }


    set value(value: object) {
        this._value = value;
        localStorage.setItem(this._key, JSON.stringify(value));
    }
}