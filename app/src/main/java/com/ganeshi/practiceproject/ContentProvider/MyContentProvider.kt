package com.ganeshi.practiceproject.ContentProvider

import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import java.lang.IllegalArgumentException
import java.net.URL

class MyContentProvider:ContentProvider() {
    companion object {
        //defining authority so that application can access it
        private const val PROVIDER_NAME = "com.demo.user.provider"

        //defining content URI
        private const val URL = "content://$PROVIDER_NAME/users"


        //parsing the content URI
        val CONTENT_URL =  Uri.parse(URL)
        const val id = "id"
        const val name = "name"
        const val uriCode = 1
        private var uriMatcher: UriMatcher? = null
        private val values: HashMap<String, String>? = null


        //declaring name of the database
        const val DATABASE_NAME = "UserDB"

        //declaring table name of the database
        const val TABLE_NAME = "Users"

        //declaring version of the database
        const val DATABASE_VERSION = 1

        //sql query to create the table
        const val CREATE_DB_TABLE =
            ("CREATE TABLE " + TABLE_NAME
                    + "(id INTEGER PRIMARY KEY AUTOINCREMENT ,"
                    + "name TEXT NOT NULL);")

        init {
            // to match the content URL
            // every  time access table under content provider
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)
            //to access whole table
            uriMatcher!!.addURI(
                PROVIDER_NAME,
                "users",
                uriCode
            )

            // to access a particular row
            //of the table
            uriMatcher!!.addURI(  // !! exclamation
                PROVIDER_NAME,
                "users/*",
                uriCode
            )
        }
    }


    override fun getType(uri: Uri): String {
        return when (uriMatcher!!.match(uri)) {
            uriCode -> "vnd.android.cursor.dir/users"
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }


    // create the database


    override fun onCreate(): Boolean {
        val context = context
        val dbHelper = DatabaseHelper(context)
        db = dbHelper.writableDatabase
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var sortOrder = sortOrder
        val qb = SQLiteQueryBuilder()
        qb.tables = TABLE_NAME
        when(uriMatcher!!.match(uri)){
            uriCode -> qb.projectionMap = values
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        if (sortOrder == null || sortOrder === ""){
            sortOrder = id
        }
        val  c = qb.query(
            db, projection, selection , selectionArgs , null, null, sortOrder
        )
        c.setNotificationUri(context!!.contentResolver , uri)
        return c
    }

override fun insert(uri: Uri, values: ContentValues?): Uri {
    val contentResolver = context?.contentResolver
    if (contentResolver != null) {
        val rowID = db?.insert(TABLE_NAME, null, values)
        if (rowID != -1L) {
            val insertedUri = ContentUris.withAppendedId(CONTENT_URL, rowID!!)
            contentResolver.notifyChange(insertedUri, null)
            return insertedUri
        }
    }
    throw SQLiteException("Failed to add a record into $uri")
}



    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        var count = 0
        count = when(uriMatcher!!.match(uri)){
            uriCode -> db!!.update(TABLE_NAME , values , selection , selectionArgs)
         else -> throw IllegalArgumentException("Unknown URL $uri")
        }
        context!!.contentResolver.notifyChange(uri , null)
        return count
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        var count = 0
        count = when(uriMatcher!!.match(uri)){
            uriCode -> db!!.delete(TABLE_NAME,selection,selectionArgs)
            else -> throw  IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri , null)
        return  count
    }



    // creating object of database
    // to perform query
    private var db:SQLiteDatabase? = null

    //create a database
    private class DatabaseHelper
    // defining a constructor
        (context: Context?): SQLiteOpenHelper(
        context, DATABASE_NAME , null , DATABASE_VERSION
    ){
        // creating a table in the database
        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(CREATE_DB_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            // sql query to drop a table
            // having similar name



            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }



}