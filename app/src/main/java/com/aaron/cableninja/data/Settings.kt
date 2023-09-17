package com.aaron.cableninja.data

class Setting {
    private var _bool = false
    private var _str = ""

    constructor(value: Boolean) {
        _bool = value
    }

    constructor(value: String) {
        _str = value
    }

    fun set(value: Boolean) { _bool = value }
    fun set(value: String) { _str = value }
}

class Settings(

) {
    val data = mutableMapOf<String, Setting>()
}