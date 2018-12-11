package com.example.carlo.aplicacionhavetopay

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class BaseDeDatos {

    var dbNombre= "MisPagos"

    var dbTabla = "Pagos"

    var colId = "Id"
    var colTitulo = "Titulo"
    var colIntervaloPagos = "IntervaloPagos"
    var colFechaIncial = "FechaInicial"
    var colMonto = "Monto"

    var dbVersion = 1

    var SqlCrearTabla = "CREATE TABLE IF NOT EXISTS"+ dbTabla + "("+ colId + "INTERGER PRIMARY KEY,"+ colTitulo +"TEXT,"+ colIntervaloPagos +"TEXT," + colFechaIncial +"TEXT,"+ colMonto +"TEXT)"

    var sqlDB:SQLiteDatabase? = null

    constructor(context: Context){
        var db = DataBaseHelperPays(context)
        sqlDB = db.writableDatabase
    }

    inner class DataBaseHelperPays:SQLiteOpenHelper{
        var context: Context? = null
        constructor(context: Context):super(context,dbNombre,null,dbVersion){
            this.context = context
        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(SqlCrearTabla)
            Toast.makeText(this.context, "Base de Datos Creada ...", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Borrar la tabla si existe" + dbTabla)
        }
    }

    fun insertar(values: ContentValues):Long{
        val Id = sqlDB!!.insert(dbTabla,"",values)
        return Id
    }

    fun Query(protection:Array<String>, selection:String, selectionArgs:Array<String>, sorOrder:String): Cursor? {
        val qb = SQLiteQueryBuilder();
        qb.tables = dbTabla
        val cursor = qb.query(sqlDB, protection, selection, selectionArgs, null,null, sorOrder)
        return cursor
    }

    fun eliminar(selection: String, selectionArgs: Array<String>):Int{
        val count =  sqlDB!!.delete(dbTabla, selection, selectionArgs)
        return count
    }

    fun actualizar(values: ContentValues,selection: String, selectionArgs: Array<String>):Int{
        val count =  sqlDB!!.update(dbTabla, values, selection, selectionArgs)
        return count
    }
}