package smt3.assignme_11.class_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.viewpager2.widget.ViewPager2
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import org.json.JSONException
import org.json.JSONObject
import smt3.assignme_11.Db_User
import smt3.assignme_11.Main_Activity
import smt3.assignme_11.R

class CLass_Detail : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var classDetailPagerAdapter: ClassDetailPagerAdapter
    private lateinit var backButton: ImageView
    private lateinit var TxtNamaKelas: TextView
    private lateinit var TxtNamaMapel: TextView
    private val mContext: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_detail)
        backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@CLass_Detail, Main_Activity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        })
        TxtNamaKelas = findViewById(R.id.txtNamakelas)
        TxtNamaMapel = findViewById(R.id.txtNamaMapelDetail)

        val classId = intent.getIntExtra("ClassId", -1)
        val cdTaskFragment = Cd_Task()
        val bundle = Bundle()
        bundle.putInt("ClassId", classId)
        cdTaskFragment.arguments = bundle

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerF, cdTaskFragment)
        transaction.commit()
        Log.d("Class_Detail", "Received classId: $classId")

        val intent = intent
        if (intent != null) {
            val requestQueue = Volley.newRequestQueue(this)
            val url = Db_User.urlClassDetail
            val stringRequest: StringRequest = object : StringRequest(
                com.android.volley.Request.Method.POST, url,
                object : com.android.volley.Response.Listener<String?> {
                    override fun onResponse(response: String?) {
                        try {
                            val jsonResponse = JSONObject(response)
                            val status = jsonResponse.getString("status")

                            if (status.equals("success", ignoreCase = true)) {
                                val className = jsonResponse.getString("className")
                                val subjectName = jsonResponse.getString("subjectName")

                                // Tampilkan data di UI
                                TxtNamaKelas.text = className
                                TxtNamaMapel.text = subjectName
                            } else {
                                val message = jsonResponse.optString("message", "Unknown error")
                                Toast.makeText(this@CLass_Detail, "Error: $message", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }, object : com.android.volley.Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        error.printStackTrace()
                    }
                }) {
                override fun getParams(): Map<String, String?> {
                    val paramV: MutableMap<String, String?> = HashMap()
                    paramV["classId"] = classId.toString()
                    return paramV
                }
            }
            requestQueue.add(stringRequest)
        }
        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager)
        classDetailPagerAdapter = ClassDetailPagerAdapter(this)
        viewPager2.setAdapter(classDetailPagerAdapter)
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)!!.select()
            }
        })
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish() // Menutup aktivitas saat tombol kembali ditekan
    }
}