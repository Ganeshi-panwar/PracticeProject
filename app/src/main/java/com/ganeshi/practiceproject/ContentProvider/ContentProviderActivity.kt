package com.ganeshi.contentprovider

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.ganeshi.practiceproject.ContentProvider.MyContentProvider
import java.lang.StringBuilder

class ContentProviderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
//        return true
//    }

    fun onClickAddDetails(view: View?) {
        // class to add values in the database
        val values = ContentValues()

        // fetching text from user
        values.put(
            MyContentProvider.name, (findViewById<View>(R.id.textName) as EditText).text.toString()
        )

        // inserting into database through content URI
        contentResolver.insert(MyContentProvider.CONTENT_URL, values)

        // displaying a toast message
        Toast.makeText(baseContext, "New Record Inserted", Toast.LENGTH_LONG).show()

    }

    @SuppressLint("Recycle", "Range", "SetTextI18n", "SuspiciousIndentation")
    fun onClickShowDetails(view: View?) {
        // inserting completed table details in this text field
        val resultView = findViewById<View>(R.id.res) as TextView

        // creating a cursor object of the
        // content URL
        val cursor = contentResolver.query(MyContentProvider.CONTENT_URL, null, null, null, null)

        //iteration of the cursor
        // to print whole table
        if (cursor != null && cursor.moveToFirst()) {
            val strBuild = StringBuilder()  // mutable sequence of character
            while (!cursor.isAfterLast) {
                strBuild.append(
                    "${cursor.getString(cursor.getColumnIndex("id"))}-${
                        cursor.getString(
                            cursor.getColumnIndex("name")
                        )
                    }\n"
                )
                cursor.moveToNext()
            }
            resultView.text = strBuild

        } else {
            resultView.text = "NO Records Found"
        }
    }

    @SuppressLint("Range", "SetTextI18n")
    fun onClickDeleteData(view: View) {

        val resultView = findViewById<View>(R.id.res) as TextView
        val contentResolver = contentResolver
        val uri = MyContentProvider.CONTENT_URL
        val rowDelete = contentResolver.delete(uri, null, null)
        if (rowDelete > 0) {
            resultView.text = "Successfully delete $rowDelete Records"

        } else {
            resultView.text = "No Record were delete "
        }
    }
}





