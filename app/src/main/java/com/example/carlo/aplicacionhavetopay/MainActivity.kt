package com.example.carlo.aplicacionhavetopay

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_linea.view.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var ListaPagos = ArrayList<Pago>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener{
            val intent = Intent(this,Agregar::class.java)
            startActivity(intent)
        }

        LoadQuery("%")
    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
    }

    private fun LoadQuery(title: String) {
        var BaseDeDatos = BaseDeDatos(this)
        val projections  = arrayOf("Id", "Titulo", "IntervaloPagos", "FechaInicial","Monto")
        val SelectionArgs = arrayOf(title)
        val cursor = BaseDeDatos.Query(projections, "Titulo Like ?", SelectionArgs, "Titulo" )
        ListaPagos.clear()
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
            lvLista.adapter = PagosAdapter

            val total = lvLista.count
            val mActionBar = supportActionBar
            if (mActionBar != null){
                mActionBar.subtitle = "Tienes $total pago(s) registrados"
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean{
        menuInflater.inflate(R.menu.menu,menu)
        val sv: SearchView = menu!!.findItem(R.id.app_bar_buscar).actionView as SearchView

        val sm = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        sv.setSearchableInfo(sm.getSearchableInfo(componentName))
        sv.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                LoadQuery("%"+ query +"%")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                LoadQuery("%"+ newText +"%")
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!=null){
            when(item.itemId){
                R.id.app_bar_agregarPago->{
                    startActivity(Intent(this,Agregar::class.java))
                }
                R.id.action_configuracion->{
                    Toast.makeText(this,"configuracion", Toast.LENGTH_SHORT).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
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
                val selectionArgs = arrayOf(Pago.PagoId.toString())
                BaseDeDatos.eliminar("Id=?",selectionArgs)
            }
            Vista.EditarBtn.setOnClickListener{
                editarFun(Pago)
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
    private fun editarFun(pago: Pago) {
        var intent = Intent(this, Agregar::class.java )
        intent.putExtra("Id", pago.PagoId)
        intent.putExtra("Titulo", pago.Titulo)
        intent.putExtra("IntervaloPagos", pago.IntervaloDias)
        intent.putExtra("FechaInicial", pago.FechaInicial)
        intent.putExtra("Monto", pago.Monto)
        startActivity(intent)
    }
}


