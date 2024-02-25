package com.ganeshi.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ganeshi.fragment.databinding.ActivityMainBinding

class FragmentMainActivity : AppCompatActivity() ,Communicator{

    private lateinit var fragmentManager: FragmentManager
    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialise the binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonFragment1.setOnClickListener {
            goToFragment(Fragment1())
        }
        binding.buttonFragment2.setOnClickListener {
            goToFragment(Fragment2())
        }
    }

    private fun goToFragment(fragment:Fragment){
        //manage fragments within an activity
        fragmentManager = supportFragmentManager
       fragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
    }


    override fun passDataCom(editTextInput: String, data: String) {
        var bundle = Bundle()
        bundle.putString("message",editTextInput)
        bundle.putString("address" , data)
        val  fragment2 = Fragment2()
        fragment2.arguments = bundle
        goToFragment(fragment2)
    }
}

