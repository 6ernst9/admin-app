package com.example.finance

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import kotlin.collections.ArrayList
import android.database.Cursor
import androidx.fragment.app.FragmentActivity

class DatabaseHandler(context: FragmentActivity) : SQLiteOpenHelper( context, DATABASE_NAME, null ,DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "OspitiDatabase"
        private const val TABLE_OSPITI = "OspitiTable"

        private const val KEY_ID = "_id"
        private const val KEY_HOUSE = "house"
        private const val KEY_NUMBER = "number"
        private const val KEY_PRICE = "price"
        private const val KEY_NAMES = "names"
        private const val KEY_ARRDATE = "arrdate"
        private const val KEY_PARDATE = "pardate"
        private const val KEY_METHOD = "method"
        private const val KEY_PHONE = "phone"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_OSPITI_TABLE =
            ("CREATE TABLE " + TABLE_OSPITI + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_HOUSE + " TEXT," + KEY_NUMBER +
                    " TEXT," + KEY_PRICE + " TEXT," + KEY_NAMES + " TEXT," + KEY_ARRDATE + " TEXT," + KEY_PARDATE + " TEXT," + KEY_METHOD + " TEXT,"
                    + KEY_PHONE + " TEXT" + ")")
        db?.execSQL(CREATE_OSPITI_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_OSPITI)
        onCreate(db)
    }

    fun addOspiti(osp: OspModelClass): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_HOUSE, osp.house)
        contentValues.put(KEY_NUMBER, osp.numberOsp)
        contentValues.put(KEY_PRICE, osp.price)
        contentValues.put(KEY_NAMES, osp.names)
        contentValues.put(KEY_ARRDATE, osp.arrDate)
        contentValues.put(KEY_PARDATE, osp.parDate)
        contentValues.put(KEY_METHOD, osp.method)
        contentValues.put(KEY_PHONE, osp.phone)

        val succes = db.insert(TABLE_OSPITI, null, contentValues)
        db.close()
        return succes
    }

    @SuppressLint("Range")
    fun viewOspiti(): ArrayList<OspModelClass> {

        val ospList: ArrayList<OspModelClass> = ArrayList<OspModelClass>()
        val selectQuery = "SELECT * FROM $TABLE_OSPITI"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var house: String
        var number: String
        var price: String
        var names: String
        var arrdate: String
        var pardate: String
        var method: String
        var phone: String

        if (cursor.moveToFirst()) {
                 while (cursor.moveToNext()){
                     id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                     house = cursor.getString(cursor.getColumnIndex(KEY_HOUSE))
                     number = cursor.getString(cursor.getColumnIndex(KEY_NUMBER))
                     price = cursor.getString(cursor.getColumnIndex(KEY_PRICE))
                     names = cursor.getString(cursor.getColumnIndex(KEY_NAMES))
                     arrdate = cursor.getString(cursor.getColumnIndex(KEY_ARRDATE))
                     pardate = cursor.getString(cursor.getColumnIndex(KEY_PARDATE))
                     method = cursor.getString(cursor.getColumnIndex(KEY_METHOD))
                     phone = cursor.getString(cursor.getColumnIndex(KEY_PHONE))

                     val osp = OspModelClass(
                         id = id,
                         house = house,
                         numberOsp = number,
                         price = price,
                         names = names,
                         arrDate = arrdate,
                         parDate = pardate,
                         method = method,
                         phone = phone
                     )
                     ospList.add(osp)
                 }
        }
        return ospList
    }


    fun updateOspiti( osp: OspModelClass) : Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_HOUSE, osp.house)
        contentValues.put(KEY_NUMBER, osp.numberOsp)
        contentValues.put(KEY_PRICE, osp.price)
        contentValues.put(KEY_NAMES, osp.names)
        contentValues.put(KEY_ARRDATE, osp.arrDate)
        contentValues.put(KEY_PARDATE, osp.parDate)
        contentValues.put(KEY_METHOD, osp.method)
        contentValues.put(KEY_PHONE, osp.phone)

        val succes = db.update(TABLE_OSPITI, contentValues, KEY_ID + "=" + osp.id, null)
        db.close()
        return succes
    }
    fun deleteOspiti(osp: OspModelClass): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, osp.id)
        val succes = db.delete(TABLE_OSPITI, KEY_ID + "=" + osp.id, null )
        db.close()
        return succes
    }
}