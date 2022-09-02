package com.harunkaraca.sqlite

class Kullanici {

    var id:Int = 0
    var kullaniciAdi:String = ""
    var kullaniciParola:String = ""
    constructor(adsoyad:String, parola:String ){
        this.kullaniciAdi = adsoyad
        this.kullaniciParola = parola
    }
    constructor(){

    }

}