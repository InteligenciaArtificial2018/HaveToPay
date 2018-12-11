package com.example.carlo.aplicacionhavetopay

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_linea.view.*

class MainActivity : AppCompatActivity() {

    var ListaPagos = ArrayList<Pago>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoadQuery("%")

        val btnAdd = findViewById<Button>(R.id.btnAdd)

        btnAdd.setOnClickListener {
            val intent = Intent(this, Agregar::class.java)
            startActivity(intent)
            Toast.makeText(this, "Agregar", Toast.LENGTH_SHORT).show()
        }

    }

    private fun LoadQuery(title: String) {
        var BaseDeDatos = BaseDeDatos(this)
        var projections = arrayOf("Id", "Titulo", "IntervaloPagos", "FechaInicial","Monto")
        val SelectionArgs = arrayOf(title)
        val cursor = BaseDeDatos.Query(projections, " Que titulo ?", SelectionArgs, "Titulo" )
        ListaPagos.clear()

        if (cursor != null) {
            if (cursor.moveToFirst())
            {
                do{
                    val Id = cursor.getInt(cursor.getColumnIndex("Id"))
                    val Titulo = cursor.getString(cursor.getColumnIndex("Titulo"))
                    val IntervaloPagos = cursor.getString(cursor.getColumnIndex("IntervaloPagos"))
                    val FechaInicial = cursor.getString(cursor.getColumnIndex("FechaIncial"))
                    val Monto = cursor.getString(cursor.getColumnIndex("Monto"))
                }while (cursor.moveToNext())
            }

            //El adapter
            var PagosAdapter = PagosAdapter(this, ListaPagos)
        }

    }

    inner class PagosAdapter:BaseAdapter {

        var ListaPagosAdapter = ArrayList<Pago>()
        var context: Context?= null

        constructor(context: Context, ListaPagosArray: ArrayList<Pago>) : super() {
            this.ListaPagosAdapter = ListaPagosArray
            this.context = context
        }


        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var Vista = layoutInflater.inflate(R.layout.activity_linea,null)
            var Pago =  ListaPagosAdapter[position]
            Vista.txtTitulo.text = Pago.Titulo
            Vista.txtIntervaloDias.text = Pago.IntervaloDias.toString()
            Vista.txtFechaInicial.text = Pago.FechaInicial.toString()
            Vista.txtMonto.text = Pago.Monto.toString()

            Vista.BorrarBtn.setOnClickListener{
                var BaseDeDatos = BaseDeDatos(this.context!!)
                val selectionArgs = arrayListOf(Pago.PagoId.toString())
                BaseDeDatos.eliminar("Id=?",selectionArgs)
            }
            Vista.EditarBtn.setOnClickListener{
                EditarFun(Pago)
            }
            return Vista
        }

        override fun getItem(position: Int): Any {
            return ListaPagosAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return ListaPagosAdapter.size
        }

    }

    private fun EditarFun(pago: Pago) {
        var intent = Intent(this, Agregar::class.java )
        intent.putExtra("Id", pago.PagoId)
        intent.putExtra("Titulo", pago.Titulo)
        intent.putExtra("IntervaloPagos", pago.IntervaloDias)
        intent.putExtra("FechaInicial", pago.FechaInicial)
        intent.putExtra("Monto", pago.Monto)
        startActivity(intent)
    }
}


