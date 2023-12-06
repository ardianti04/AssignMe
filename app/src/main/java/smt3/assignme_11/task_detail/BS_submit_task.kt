package smt3.assignme_11.task_detail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.json.JSONException
import org.json.JSONObject
import smt3.assignme_11.Db_User
import smt3.assignme_11.R
import smt3.assignme_11.class_detail.Tugas
import smt3.assignme_11.material_detail.MaterialAttachmentRecViewAdapter
import java.io.File
import java.security.AccessController.checkPermission
import java.text.SimpleDateFormat
import java.util.Locale


class BS_submit_task : BottomSheetDialogFragment() {
    companion object {
        const val FILE_REQUEST_CODE = 123
        fun newInstance(classId: Int, taskId: Int): BS_submit_task {
            val fragment = BS_submit_task()
            val args = Bundle()
            args.putInt("TaskId", taskId)
            args.putInt("ClassId", classId)
            fragment.arguments = args
            return fragment
        }
    }
    private lateinit var txtStatus: TextView
    private lateinit var txtStatusLate: TextView
    private lateinit var btnSubmit : Button
    private lateinit var btnUnsubmit : Button
    private lateinit var btnHandIn : Button
    private lateinit var imgDelete : RelativeLayout
    private lateinit var attachmentStudentRecView: RecyclerView
    private lateinit var adapter: UploadAttachmentRecViewAdapter
    private var attachmentArrayList: ArrayList<Tugas> = ArrayList()
    private lateinit var sharedPreferences: SharedPreferences
    private var classId: Int = -1
    private var taskId: Int = -1
    private val STORAGE_PERMISSION_CODE = 101
    private var pdfUri: Uri? = null
    private val TAG = "PDF_ADD_TAG"
    var selectedFile: File? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottomsheet_submit_task, container, false)

        sharedPreferences = requireContext().getSharedPreferences("MyAppName", AppCompatActivity.MODE_PRIVATE)
        val email = sharedPreferences.getString("Email", "")

        val args = arguments
        if (args != null) {
            taskId = args.getInt("TaskId", -1)
            classId = args.getInt("ClassId", -1)
            Log.d("BS_submit_task", "TaskId from Arguments: $taskId")
            Log.d("BS_submit_task", "ClassId from Arguments: $classId")
        }

        /*attachmentStudentRecView = view.findViewById(R.id.attachment_student)
        attachmentStudentRecView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UploadAttachmentRecViewAdapter(requireContext())
        attachmentStudentRecView.adapter = adapter*/

        txtStatus = view.findViewById(R.id.txtStatus)
        txtStatusLate = view.findViewById(R.id.txtLate)
        //btnSubmit = view.findViewById(R.id.btnSubmit)
        btnUnsubmit = view.findViewById(R.id.btnUnsubmit)


        btnSubmit.setOnClickListener {
            /*val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "*.-.*" // Semua jenis file
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "application/pdf" // Atau jenis file yang diinginkan
            try {
                startActivityForResult(intent, FILE_REQUEST_CODE)
            } catch (e: ActivityNotFoundException) {
                // Tangani jika tidak ada aplikasi yang dapat menangani permintaan ini
                Log.e("BS_submit_task", "No activity found to handle this intent.")
            }*/
        }

        return view
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                // Lakukan sesuatu dengan URI file yang dipilih di sini
                Log.d("BS_submit_task", "Selected file URI: $uri")

                // Buat objek Attachment baru dari URI yang dipilih
                val newAttachment = Attachment()
                newAttachment.setAttachmentUri(uri.toString()) // Set attachmentUri dari URI yang dipilih

                // Buat objek Tugas baru dengan attachment dari URI yang dipilih
                val newTugas = Tugas(-1, "", "", "", -1, "")
                newTugas.setAttachmentStudent(newAttachment)

                // Tambahkan ke ArrayList
                attachmentArrayList.add(newTugas)

                // Update RecyclerView
                adapter.setAttachment(attachmentArrayList)
                updateButtonVisibility()
            }
        }
    }

    private fun updateButtonVisibility() {
        if (attachmentArrayList.isNotEmpty()) {
            btnHandIn.visibility = View.VISIBLE
        } else {
            btnHandIn.visibility = View.GONE
        }
    }
}