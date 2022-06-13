package com.example.recylerview


class mymodel {
    var filename: String? = null
    var fileurl:kotlin.String? = null
    var nod = 0
    var nol:kotlin.Int = 0
    var nov:kotlin.Int = 0
    constructor() {}

    constructor(filename: String?, fileurl: String?, nod: Int, nol: Int, nov: Int) {
        this.filename = filename
        this.fileurl = fileurl
        this.nod = nod
        this.nol = nol
        this.nov = nov
    }
}