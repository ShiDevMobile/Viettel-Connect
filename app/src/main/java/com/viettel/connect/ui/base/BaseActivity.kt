package com.viettel.connect.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


abstract class BaseActivity<T: ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: T
    protected abstract fun initBinding(): T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initBinding()
        setContentView(binding.root)
    }
}