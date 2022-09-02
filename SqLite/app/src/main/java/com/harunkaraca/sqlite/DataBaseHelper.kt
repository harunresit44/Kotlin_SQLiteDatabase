package com.harunkaraca.sqlite


import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import androidx.core.content.contentValuesOf

var dataBaseName = "Veritabani"
var table_name = "Kullanici"
var col_name = "adiSoyadi"
var col_parola = "parola"
var col_id = "id"

class DataBaseHelper ( var context: Context):SQLiteOpenHelper(context,
    dataBaseName, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        //veritabanı çalıştığı zaman sadece 1 kere çalıştırılır

        var createTable = " CREATE TABLE "+ table_name + "(" +
                col_id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                col_name + " VARCHAR(256), " +
                col_parola + " VARCHAR(256)) "
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    //Veri kaydetmek için fonksiyon tanımlıyoruz
    fun insertData(kullanici: Kullanici){
        val db= this.writableDatabase
        val cv = ContentValues()

        cv.put(col_name,kullanici.kullaniciAdi)
        cv.put(col_parola,kullanici.kullaniciParola)

        var sonuc = db.insert(table_name, null,cv)
        if (sonuc== (-1).toLong()){
            Toast.makeText(context, "hatalı", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "başarılı", Toast.LENGTH_SHORT).show()
        }

    }
    //Veri okumak için fonksiyon tanımlıyoruz
    @SuppressLint("Range")
    fun readData(): MutableList<Kullanici> {
        var liste:MutableList<Kullanici> = ArrayList()
        val db = this.readableDatabase
        val sorgu = "Select * from "+ table_name
        var sonuc = db.rawQuery(sorgu,null)
        if (sonuc.moveToFirst()){
            do {
                var kullanici = Kullanici()
                kullanici.id = sonuc.getString(sonuc.getColumnIndex(col_id)).toInt()
                kullanici.kullaniciAdi = sonuc.getString(sonuc.getColumnIndex(col_name))
                kullanici.kullaniciParola = sonuc.getString(sonuc.getColumnIndex(col_parola))
                liste.add(kullanici)

            }while (sonuc.moveToNext())
        }
        sonuc.close()
        db.close()
        return liste

    }
    //Veri güncellemek için fonksiyon tanımlıyoruz
    @SuppressLint("Range")
    fun update(){
        val db = this.readableDatabase
        var sorgu = " Select * from $table_name"
        var sonuc = db.rawQuery(sorgu, null)
        if (sonuc.moveToFirst()){
            do {
                var cv = ContentValues()
                cv.put(col_parola,(sonuc.getString(sonuc.getColumnIndex(col_parola)))+ " " + "+1")
                cv.put(col_name,(sonuc.getString(sonuc.getColumnIndex(col_name)))+ " " + "Güncel")
                db.update(table_name, cv, "$col_id=? AND $col_name=?",
                arrayOf(sonuc.getString(sonuc.getColumnIndex(col_id)),
                sonuc.getString(sonuc.getColumnIndex(col_name))))

            }while (sonuc.moveToNext())
        }
        sonuc.close()
        db.close()
    }
    //Veri silmek için fonksiyon tanımlıyoruz
    fun deleteData(){
        val db = this.writableDatabase
        db.delete(table_name, null,null)
        db.close()
    }

}