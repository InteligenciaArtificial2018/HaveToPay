package com.example.carlo.aplicacionhavetopay

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar.*
import kotlinx.android.synthetic.main.activity_linea.*
import java.lang.Exception
import java.util.*

class Agregar : AppCompatActivity() {

    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0

    val dbTabla = "Pagos"
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)

        try {
            val bundle:Bundle = intent.extras
            id = bundle.getInt("ID",0)
            if(id!=0){
                Titulotxt.setText(bundle.getString("Titulo"))
                IntervaloDiastxt.setText(bundle.getString("IntervaloPagos"))
                FechaInicialtxt.setText(bundle.getString("FechaInicial"))
                Montotxt.setText(bundle.getString("Monto"))

            }
        }catch (ex:Exception){}

        var btnAgregar = findViewById<Button>(R.id.buttonAdd)
        btnAgregar.setOnClickListener {
            AgregarFun()
        }
    }
    fun AgregarFun(){
        var BaseDeDatos = BaseDeDatos(this)

        var valores = ContentValues()
        valores.put("Titulo" , txtTitulo.text.toString())
        valores.put("IntervaloPagos" , txtIntervaloDias.text.toString())
        valores.put("FechaInicial" , txtFechaInicial.text.toString())
        valores.put("Monto" , txtMonto.text.toString())

        if (id == 0)
        {
            val ID = BaseDeDatos.insertar(valores)
            if (ID>0){
                Toast.makeText(this, "El pago ha sido agregado", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "ERROR al agregar el pago", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            var selectionArgs = arrayOf(id.toString())
            var ID =BaseDeDatos.actualizar(valores,"ID=?", selectionArgs)
            if (ID>0){
                Toast.makeText(this,"El pago ha sido actualizado",Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "ERROR al actualizar el pago", Toast.LENGTH_SHORT).show()
            }
        }
    }




}

