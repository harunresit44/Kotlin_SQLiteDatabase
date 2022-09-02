package com.harunkaraca.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = this
        var db = DataBaseHelper(context)

        //Veri yazdırma
        btnKaydet.setOnClickListener(){
            var etadsoyad = etAdSoyad.text.toString()
            var etparola = etParola.text.toString()
            if (etadsoyad.isNotEmpty() && etparola.isNotEmpty()){
                var kullanici = Kullanici(etadsoyad,etparola)
                db.insertData(kullanici)
            }else{
                Toast.makeText(applicationContext, "Lütfen boşlukları doldurunuz", Toast.LENGTH_SHORT).show()
            }


        }
        //Veri okuma
        btnOku.setOnClickListener(){
            var data = db.readData()
            tvSonuc.text = ""
            for (i in 0 until data.size){
                tvSonuc.append(data.get(i).id.toString()+ " - "
                +data.get(i).kullaniciAdi+ " - " + data.get(i).kullaniciParola + "\n")
            }
        }
        //Veri güncelleme
        btnGuncelle.setOnClickListener(){
            db.update()
            btnOku.performClick()
        }
        //Veri silme
        btnSil.setOnClickListener(){
            db.deleteData()
            btnOku.performClick()
        }


    }
}