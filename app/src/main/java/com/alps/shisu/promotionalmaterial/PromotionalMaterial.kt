package com.alps.shisu.promotionalmaterial

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alps.shisu.Adapters.PromotionalMaterialListAdapter
import com.alps.shisu.ApiUtil.Config
import com.alps.shisu.BaseActivity
import com.alps.shisu.R
import com.alps.shisu.Session.SessionManagement
import com.alps.shisu.modelclass.PromotionalItem
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class PromotionalMaterial : BaseActivity() {

    val arrayList = ArrayList<PromotionalItem>()//Creating an empty arraylist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promotional_material)

        val toolbarsp = findViewById<View>(R.id.toolbar_promotional) as Toolbar

        val recyclerviewCS = findViewById<View>(R.id.recyclerview_CS) as RecyclerView
        recyclerviewCS.setHasFixedSize(true)
        recyclerviewCS.layoutManager = LinearLayoutManager(baseContext)


        // Session Manager Class
        val sessionManagement = SessionManagement(applicationContext)
        sessionManagement.checkLogin()

        val user = sessionManagement.getUserDetails()

        val url = Config.url + "promotionalMaterials/" + Config.merchantid + "/" + Config.securtykey + "/" +
                user[SessionManagement.KEY_USERNAME] + "/0"


        if (!isConnection(this)) buildDialog(this).show()
        setSupportActionBar(toolbarsp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Promotional Materials"
        toolbarsp.setTitleTextColor(Color.WHITE)

        getPromotionalMaterialList(recyclerviewCS, url)

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

    private fun getPromotionalMaterialList(recyclerviewCS : RecyclerView, url : String) {
        val queue = Volley.newRequestQueue(applicationContext)
        val request = StringRequest(
            Request.Method.GET, url,
            { response: String? ->
                try {
                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {


                        val jsonObject = jsonArray.getJSONObject(i)
                        val date = jsonObject.getString("date")
                        val id = jsonObject.getString("id")
                        val name = jsonObject.getString("name")
                        val title = jsonObject.getString("title")
                        val url = jsonObject.getString("url")

                        var promotionalMaterial = PromotionalItem()
                        promotionalMaterial.id = id
                        promotionalMaterial.date = date
                        promotionalMaterial.name = name
                        promotionalMaterial.title = title
                        promotionalMaterial.url = url

                        Log.e(ContentValues.TAG, "onCreate fullName : $name")
                        arrayList.add(promotionalMaterial)

                    }

                    val data = PromotionalMaterialListAdapter(baseContext, arrayList)
                    recyclerviewCS.adapter = data


                } catch (e: JSONException) {
                    e.printStackTrace()
                }

            }
        ) { error: VolleyError? ->

        }
        queue.add(request)
    }
}