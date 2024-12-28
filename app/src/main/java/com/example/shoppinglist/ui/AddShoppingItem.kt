package com.example.shoppinglist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.FragmentAddShoppingItemBinding
import com.example.shoppinglist.databinding.FragmentImagePickBinding


class AddShoppingItem : Fragment() {

    private lateinit var binding: FragmentAddShoppingItemBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddShoppingItemBinding.inflate(layoutInflater)
        return binding.root
    }


}