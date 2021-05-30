package com.example.capstoneproject.model

class DataItem {
    var nama: String? = null
    var img: String? = null
    var gejala: String? = null
    var obat: String? = null
    var penyebab: String? = null
    var cegah: String? = null

    constructor() {}

    constructor(
        nama: String?,
        img: String?,
        gejala: String?,
        obat: String?,
        penyebab: String?,
        cegah: String?
    ) {
        this.nama = nama
        this.img = img
        this.gejala = gejala
        this.obat = obat
        this.penyebab = penyebab
        this.cegah = cegah
    }
}