package smt3.assignme_11

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import smt3.assignme_11.helper.PreferenceHelper

class BS_logout : BottomSheetDialogFragment(){
    private lateinit var btnLogout : AppCompatButton
    private lateinit var txtCancel : TextView

    private lateinit var sharedPref : PreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottomsheet_logout, container, false)

        sharedPref = PreferenceHelper(requireContext())
        btnLogout = view.findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            sharedPref.clear()
            startActivity(Intent(requireContext(), LandingActivity::class.java))
        }

        txtCancel = view.findViewById(R.id.txtCancel)
        txtCancel.setOnClickListener{
            dismiss()
        }
        return view
    }
}