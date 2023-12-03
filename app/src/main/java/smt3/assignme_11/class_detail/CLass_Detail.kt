package smt3.assignme_11.class_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
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
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONException
import org.json.JSONObject
import smt3.assignme_11.Db_User
import smt3.assignme_11.Main_Activity
import smt3.assignme_11.R
import smt3.assignme_11.timeline.tl_1_todo
import smt3.assignme_11.timeline.tl_2_completed
import smt3.assignme_11.timeline.tl_3_overdue

class CLass_Detail : AppCompatActivity() {
    private lateinit var containerF: FrameLayout
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var classDetailPagerAdapter: ClassDetailPagerAdapter
    private lateinit var backButton: ImageView
    private lateinit var TxtNamaKelas: TextView
    private lateinit var TxtNamaMapel: TextView
    private val mContext: Context? = null
    private var classId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_detail)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager)
        containerF = findViewById(R.id.containerF)

        backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@CLass_Detail, Main_Activity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        })
        TxtNamaKelas = findViewById(R.id.txtNamakelas)
        TxtNamaMapel = findViewById(R.id.txtNamaMapelDetail)

        classId = intent.getIntExtra("ClassId", -1)
        Log.d("CLass_Detail", "ClassId from Intent: $classId")

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


        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager)

        classDetailPagerAdapter = ClassDetailPagerAdapter(this)
        viewPager2.setAdapter(classDetailPagerAdapter)

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> "Task"
                1 -> "Material"
                2 -> "People"
                else -> ""
            }
        }.attach()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> showTaskFragment()
                    1 -> showMaterialFragment()
                    2 -> showPeopleFragment()
                    else -> throw IllegalArgumentException("Invalid position: $position")
                }
            }
        })
    }

    private fun showTaskFragment() {
        val fragment = Cd_Task.newInstance(classId) // classId bisa diambil dari mana saja yang sesuai dengan kebutuhan aplikasi Anda
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerF, fragment)
            .commit()
    }
    private fun showMaterialFragment() {
        val fragment = Cd_Material.newInstance(classId) // Sesuaikan dengan fungsi newInstance di fragment Cd_Material
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerF, fragment)
            .commit()
    }
    private fun showPeopleFragment() {
        val fragment = Cd_People.newInstance(classId) // Sesuaikan dengan fungsi newInstance di fragment Cd_People
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerF, fragment)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish() // Menutup aktivitas saat tombol kembali ditekan
    }
}