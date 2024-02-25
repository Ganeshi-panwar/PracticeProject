package com.ganeshi.fragment

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.createBitmap
import com.ganeshi.fragment.databinding.Fragment2Binding


class Fragment2 : Fragment() {
    private lateinit var binding: Fragment2Binding

    private var displayMessage: String? = ""
    private var value:String?= ""
//    private  lateinit var tvOutputText:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Fragment2Binding.inflate(inflater, container, false)
      //  tvOutputText = binding.tvOutputText
        val data = binding.tvOutputText
        displayMessage = arguments?.getString("message").toString()
        value = arguments?.getString("address").toString()

        data.text = ("$displayMessage\n $value")

        // Inflate the layout for this fragment
        return binding.root
    }

}