package com.example.carlo.aplicacionhavetopay

import android.app.DatePickerDialog
import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        val tvDate = findViewById<TextView>(R.id.calendarView)
        tvDate.setOnClickListener{
            val currentDate = Calendar.getInstance()
            var year = currentDate.get(Calendar.YEAR)
            var month = currentDate.get(Calendar.MONTH)
            var day = currentDate.get(Calendar.DAY_OF_MONTH)

            if(tvDate.text.isNotEmpty()){
                year = this.selectedYear
                month = this.selectedMonth
                day = this.selectedDay
            }

            val listener=DatePickerDialog.OnDateSetListener { datePicker, selectedYear, selectedMonth, selectedDay ->
                this.selectedYear =selectedYear
                this.selectedMonth =selectedMonth
                this.selectedDay = selectedDay
                tvDate.text = "${selectedMonth + 1}/$selectedDay/$selectedYear"

            }

            val datePicker = DatePickerDialog(this, listener, year, month,day)
            datePicker.show()
        }


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
    }
    fun AgregarFun(view: View){
        var BaseDeDatos = BaseDeDatos(this)

        var valores = ContentValues()
        valores.put("Titulo" , txtFechaInicial.text.toString())
        valores.put("IntervaloPago" , txtFechaInicial.text.toString())
        valores.put("Titulo" , txtFechaInicial.text.toString())
        valores.put("Titulo" , txtFechaInicial.text.toString())

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

