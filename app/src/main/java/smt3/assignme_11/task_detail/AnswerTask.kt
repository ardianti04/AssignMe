package smt3.assignme_11.task_detail

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.RequestQueue
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import smt3.assignme_11.Db_User
import smt3.assignme_11.R


class AnswerTask : AppCompatActivity() {
    private lateinit var btnSubmit : Button
    private lateinit var btnUnsubmit : Button
    private lateinit var textInputAnswer : TextInputEditText
    private lateinit var AnswerLayout : TextInputLayout
    private lateinit var txtErrorAnswer : TextView
    private lateinit var txtYourAnswer : TextView

    private lateinit var sharedPreferences: SharedPreferences
    private var taskId: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottomsheet_submit_task1)
        sharedPreferences = getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)
        val email = sharedPreferences.getString("Email", "")

        taskId = intent.getIntExtra("TaskId", -1)
        Log.d("Detail_Tugas", "TaskId from Intent: $taskId")

        // Inisialisasi komponen
        val btnBack = findViewById<ImageView>(R.id.backButtonAnswer)
        btnBack.setOnClickListener(){
            val intent = Intent( this@AnswerTask, Detail_Tugas::class.java)
            intent.putExtra("TaskId", taskId)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        AnswerLayout = findViewById(R.id.AnswerLayout)
        txtErrorAnswer = findViewById(R.id.txtErrorAnswer)
        txtYourAnswer = findViewById(R.id.txtYourAnswer)
        textInputAnswer = findViewById(R.id.inputAnswer)
        btnSubmit = findViewById(R.id.btnSubmit);
        btnUnsubmit = findViewById(R.id.btnUnsubmit);

        btnSubmit.setOnClickListener {

            val answers: String = textInputAnswer.text.toString()

            if (answers.isEmpty()) {
                txtErrorAnswer.text = getString(R.string.answer_cannot_be_empty)
                return@setOnClickListener
            }

            val queue: RequestQueue = Volley.newRequestQueue(this)
            val url = Db_User.urlSubmitAnswer

            val stringRequest: StringRequest = object : StringRequest(
                com.android.volley.Request.Method.POST, url,
                object : com.android.volley.Response.Listener<String?> {
                    override fun onResponse(response: String?) {
                        Log.d("Response", "Response: $response")
                        try {
                            val jsonObject = JSONObject(response ?: "{}")
                            val status = jsonObject.getString("status")

                            if (status == "success") {
                                Toast.makeText(this@AnswerTask, "Task submitted", Toast.LENGTH_SHORT).show()
                                txtYourAnswer.text = answers
                                checkAnswerStatus()
                                txtErrorAnswer.visibility = View.VISIBLE
                            } else {
                                val errorMessage = jsonObject.optString("message")
                                txtErrorAnswer.text = errorMessage
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            txtErrorAnswer.text = "Error in response"
                        }
                    }
                }, object : com.android.volley.Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        error.printStackTrace()
                    }
                }) {
                override fun getParams(): Map<String, String?> {
                    val paramV: MutableMap<String, String?> = HashMap()
                    paramV["Email"] = email
                    paramV["TaskId"] = taskId.toString()
                    paramV["Answers"] = answers
                    return paramV
                }
            }
            queue.add(stringRequest)
        }
        btnUnsubmit.setOnClickListener {
            val queue: RequestQueue = Volley.newRequestQueue(this)
            val url = Db_User.urlUnsubmitAnswer

            val stringRequest: StringRequest = object : StringRequest(
                com.android.volley.Request.Method.POST, url,
                object : com.android.volley.Response.Listener<String?> {
                    override fun onResponse(response: String?) {
                        Log.d("Response", "Response: $response")
                        val jsonObject = JSONObject(response ?: "{}")
                        val status = jsonObject.getString("status")

                        if (status == "success") {
                            // Implement the desired action after unsubmission here
                            // For example, update UI or perform any necessary operations
                            // In this case, it might be appropriate to hide the unsubmit button

                            // Hide the unsubmit button
                            checkAnswerStatus()
                            Toast.makeText(this@AnswerTask, "Task unsubmitted", Toast.LENGTH_SHORT).show()
                            // Show other UI elements or perform other actions as needed
                            // For instance, show a success message or update UI components
                        } else {
                            val errorMessage = jsonObject.optString("message")
                            txtErrorAnswer.text = errorMessage
                        }
                    }
                }, object : com.android.volley.Response.ErrorListener {
                    override fun onErrorResponse(error: VolleyError) {
                        error.printStackTrace()
                    }
                }) {
                override fun getParams(): Map<String, String?> {
                    val paramV: MutableMap<String, String?> = HashMap()
                    paramV["Email"] = email
                    paramV["TaskId"] = taskId.toString()
                    return paramV
                }
            }
            queue.add(stringRequest)
        }

        getAnswer()
        checkAnswerStatus()
    }

    private fun getAnswer() {
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = Db_User.urlgetAnswer

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    try {
                        val jsonResult = JSONObject(response)
                        val status = jsonResult.getString("status")

                        if (status == "Success") {
                            val userData = jsonResult.getJSONObject("data")
                            val answer = userData.getString("Answers")

                            txtYourAnswer.text = answer
                        } else {
                            // Handle failed response
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
                paramV["Email"] = sharedPreferences.getString("Email", "")
                paramV["TaskId"] = taskId.toString()
                return paramV
            }
        }
        queue.add(stringRequest)
    }
    private fun checkAnswerStatus() {
        val queue: RequestQueue = Volley.newRequestQueue(this)
        val url = Db_User.urlgetAnswer

        val stringRequest: StringRequest = object : StringRequest(
            com.android.volley.Request.Method.POST, url,
            object : com.android.volley.Response.Listener<String?> {
                override fun onResponse(response: String?) {
                    try {
                        val jsonResult = JSONObject(response)
                        val status = jsonResult.getString("status")

                        if (status == "Success") {
                            // Hide input field, show answered text, and show unsubmit button
                            AnswerLayout.visibility = View.GONE
                            txtYourAnswer.visibility = View.VISIBLE
                            btnUnsubmit.visibility = View.VISIBLE
                            btnSubmit.visibility = View.GONE
                        } else {
                            // Show input field and submit button
                            AnswerLayout.visibility = View.VISIBLE
                            txtYourAnswer.visibility = View.GONE
                            btnUnsubmit.visibility = View.GONE
                            btnSubmit.visibility = View.VISIBLE
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
                paramV["Email"] = sharedPreferences.getString("Email", "")
                paramV["TaskId"] = taskId.toString()
                return paramV
            }
        }
        queue.add(stringRequest)
    }
}

