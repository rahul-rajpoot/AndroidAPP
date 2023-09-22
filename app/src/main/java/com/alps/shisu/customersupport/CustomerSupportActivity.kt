package com.alps.shisu.customersupport

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alps.shisu.Adapters.SupportTicketListAdapter
import com.alps.shisu.ApiUtil.Config
import com.alps.shisu.BaseActivity
import com.alps.shisu.DashBoard
import com.alps.shisu.PaymentConfirmActivity
import com.alps.shisu.R
import com.alps.shisu.Session.SessionManagement
import com.alps.shisu.db.local.entity.ProductsDetails
import com.alps.shisu.modelclass.StateDetails
import com.alps.shisu.modelclass.SupportTicketItem
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.util.HashMap

class CustomerSupportActivity : BaseActivity() {

    val arrayList = ArrayList<SupportTicketItem>()//Creating an empty arraylist
    var count = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_support)


        val toolbarsp = findViewById<View>(R.id.toolbar_customer) as Toolbar
        val compose = findViewById<View>(R.id.compose) as Button
        val name = findViewById<View>(R.id.name) as TextView
        val email = findViewById<View>(R.id.email) as TextView
        val subject = findViewById<View>(R.id.subject) as EditText
        val message = findViewById<View>(R.id.message) as EditText
        val send = findViewById<View>(R.id.send) as Button
        val clear = findViewById<View>(R.id.clear) as Button
        val backToList = findViewById<View>(R.id.backToList) as Button
        val errorMessage = findViewById<View>(R.id.errorMessage) as TextView
        val composeLayout = findViewById<View>(R.id.composeLayout) as LinearLayout
        val recyclerviewCS = findViewById<View>(R.id.recyclerview_CS) as RecyclerView

        recyclerviewCS.setHasFixedSize(true)
        recyclerviewCS.layoutManager = LinearLayoutManager(baseContext)


        toolbarsp.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                //What to do on back clicked
                performBack()
            }
        })


        // Session Manager Class
        val sessionManagement = SessionManagement(applicationContext)
        sessionManagement.checkLogin()

        val user = sessionManagement.getUserDetails()

        if (!isConnection(this)) buildDialog(this).show()
        setSupportActionBar(toolbarsp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setTitle("Support Center")
        toolbarsp.setTitleTextColor(Color.WHITE)

        name.setText(user.get(SessionManagement.KEY_USERNAME))
        email.setText(user.get(SessionManagement.KEY_EMAIL))

        val url =
            Config.url + "getSupportList/" + Config.merchantid + "/" + Config.securtykey + "/" +
                    user[SessionManagement.KEY_USERNAME] + "/" + user[SessionManagement.KEY_PASSWORD]

        val createTicket =
            Config.url + "supportTicket/" + Config.merchantid + "/" + Config.securtykey + "/" +
                    user[SessionManagement.KEY_USERNAME] + "/" + user[SessionManagement.KEY_PASSWORD]

        Log.e(TAG, "onCreate: $url")

        compose.setOnClickListener {
            composeFun(composeLayout, recyclerviewCS, compose);
        }

        clear.setOnClickListener {
            subject.setText("")
            message.setText("")
            errorMessage.visibility = View.GONE
        }

        backToList.setOnClickListener {
            backToList(composeLayout, recyclerviewCS, compose)
        }

        send.setOnClickListener {
            var subject = subject.text.toString()
            var message = message.text.toString()
            when {
                subject.isEmpty() -> {
                    errorMessage.visibility = View.VISIBLE
                    showErrorSnackBar(errorMessage, "Please enter Subject.")
                }
                message.isEmpty() -> {
                    errorMessage.visibility = View.VISIBLE
                    showErrorSnackBar(errorMessage, "Please enter Message.")
                }
                else -> {
                    errorMessage.visibility = View.GONE
                    createTicketFunction(
                        subject,
                        message,
                        createTicket,
                        composeLayout,
                        recyclerviewCS,
                        url
                    );
                }
            }
        }

        setClickListener()

        getTicketList(recyclerviewCS, url)

    }

    private fun composeFun(composeLayout : LinearLayout, recyclerviewCS : RecyclerView, compose : Button){
        Log.e(TAG, "onCreate: Clicked ")
        count = 1;
        supportActionBar!!.setTitle("Submit New Ticket")
        composeLayout.visibility = View.VISIBLE
        recyclerviewCS.visibility = View.GONE
        compose.visibility = View.GONE
    }

    private fun backToList(composeLayout : LinearLayout, recyclerviewCS : RecyclerView, compose : Button){
//    private fun backToList(){
        count = 0;
        composeLayout.visibility = View.GONE
        recyclerviewCS.visibility = View.VISIBLE
        compose.visibility = View.VISIBLE
    }

    private fun performBack() {
        try {
//            val manager = supportFragmentManager
//            val count = manager.backStackEntryCount
            Log.e(TAG, "onBackPressed: count : $count")
            if (count > 0) {
//                manager.popBackStackImmediate()
                count =0;
//                backToList(composeLayout, recyclerviewCS, compose);
                backToList(findViewById<View>(R.id.composeLayout) as LinearLayout, findViewById<View>(R.id.recyclerview_CS) as RecyclerView, findViewById<View>(R.id.compose) as Button);
            } else {
                val i = Intent(this@CustomerSupportActivity, DashBoard::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(i)
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    public fun refreshList(composeLayout: LinearLayout, recyclerviewCS: RecyclerView, url: String) {
        composeLayout.visibility = View.VISIBLE
        recyclerviewCS.visibility = View.GONE
        getTicketList(recyclerviewCS, url)
    }

    fun setClickListener() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getTicketList(recyclerviewCS: RecyclerView, url: String) {
        val queue = Volley.newRequestQueue(applicationContext)
        val request = StringRequest(
            Request.Method.GET, url,
            { response: String? ->
                try {
                    arrayList.clear()
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {

                        val jsonObject = jsonArray.getJSONObject(i)
                        val contact = jsonObject.getString("Contact")
                        val email = jsonObject.getString("Email")
                        val fullName = jsonObject.getString("FullName")
//                        val merchantID = jsonObject.getString("MerchantID")
                        val message = jsonObject.getString("Message")
                        val rmsgid = jsonObject.getString("Rmsgid")
                        val sendstatus = jsonObject.getString("Sendstatus")
//                        val status = jsonObject.getString("Status")
                        val activeFlag = jsonObject.getString("activeFlag")
                        val msgDate = jsonObject.getString("msgDate")
                        val msgID = jsonObject.getString("msgID")
                        val msgSubject = jsonObject.getString("msgSubject")
                        val msgType = jsonObject.getString("msgType")
                        val seen = jsonObject.getString("seen")

                        var supportTicketItem = SupportTicketItem()
                        supportTicketItem.contact = contact
                        supportTicketItem.email = email
                        supportTicketItem.fullName = fullName
//                        supportTicketItem.merchantID = ""
                        supportTicketItem.message = message
                        supportTicketItem.rmsgid = rmsgid
                        supportTicketItem.sendstatus = sendstatus
//                        supportTicketItem.status = ""
                        supportTicketItem.activeFlag = activeFlag
                        supportTicketItem.msgDate = msgDate
                        supportTicketItem.msgID = msgID
                        supportTicketItem.msgSubject = msgSubject
                        supportTicketItem.msgType = msgType
                        supportTicketItem.seen = seen

                        Log.e(TAG, "onCreate fullName : $fullName")
                        arrayList.add(supportTicketItem)

                    }

                    val data = SupportTicketListAdapter(baseContext, arrayList)
                    recyclerviewCS.adapter = data


                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        ) { error: VolleyError? ->

        }
        queue.add(request)
    }

    private fun createTicketFunction(
        subject: String,
        message: String,
        createTicket: String,
        composeLayout: LinearLayout,
        recyclerviewCS: RecyclerView,
        url: String
    ) {

        val jsonArray = JSONArray()
        val jsonObject = JSONObject()
        jsonObject.put("subject", subject)
        jsonObject.put("message", message)

        jsonArray.put(jsonObject)
        Log.e(TAG, "createTicketFunction req : "+ jsonArray )

        val mRequestBody = jsonArray.toString()


        val stringRequest: StringRequest = object : StringRequest(Method.POST, createTicket,
            Response.Listener { response ->
//                Toast.makeText(this, response, Toast.LENGTH_LONG).show()
                try {
                    Log.e(TAG, "Added : "+ response)
                    val jsonArray = JSONArray(response)
                    val jsonObject = jsonArray.getJSONObject(0)
                    val status = jsonObject.getString("Status")
                    if (status.equals("True")) {
                        composeLayout.visibility = View.GONE
                        recyclerviewCS.visibility = View.VISIBLE
                        getTicketList(recyclerviewCS, url)
                    }
                    //Parse your api responce here
                    /*val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)*/
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            }) {
//           override fun getParams(): Map<String, String> {
//                val params: MutableMap<String, String> = HashMap()
//                //Change with your post params
//                params["subject"] = subject
//                params["message"] = message
//                return params
//            }
            @Throws(AuthFailureError::class)
            override fun getBody(): ByteArray? {
                return try {
                    if (mRequestBody == null) null else mRequestBody.toByteArray(charset("utf-8"))
                } catch (uee: UnsupportedEncodingException) {
                    VolleyLog.wtf(
                        "Unsupported Encoding while trying to get the bytes of %s using %s",
                        mRequestBody,
                        "utf-8"
                    )
                    uee.printStackTrace()
                    null
                }
            }
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>? {
                val params: MutableMap<String, String> = HashMap()
                params["Content-Type"] = "application/json; charset=UTF-8"
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }


    override fun onBackPressed() {
//        super.onBackPressed()
        performBack()

    }
}