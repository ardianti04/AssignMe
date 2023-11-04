package smt3.assignme_11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import android.widget.Toast
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.view.LayoutInflater
import androidx.navigation.ui.setupActionBarWithNavController
import smt3.assignme_11.databinding.ActivityMainBinding
import androidx.navigation.fragment.findNavController


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }




}