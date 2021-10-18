package com.hansilk.two.blocks.uploads.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.hansilk.two.R
import com.hansilk.two.blocks.uploads.frags.UploadsFragment
import com.hansilk.two.blocks.uploads.service.UploadService
import com.hansilk.two.blocks.uploads.uploadsList.ItemTouchHelperCallback
import com.hansilk.two.blocks.uploads.uploadsList.UploadDiffUtil
import com.hansilk.two.blocks.uploads.uploadsList.UploadListAdapter
import com.hansilk.two.databinding.ActivityUploadsBinding
import com.hansilk.two.support.MyApplication
import com.hansilk.two.support.dagger.viewModelSet.DaggerViewModelFactory
import com.hansilk.two.support.utils.permissions.CheckAskPermissions
import org.json.JSONException
import javax.inject.Inject

class UploadsActivity : AppCompatActivity() {

    @Inject lateinit var viewModelFactory: DaggerViewModelFactory

    private lateinit var binding: ActivityUploadsBinding
    private lateinit var viewModel: UploadsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploads)

        initBindingDiAndViewModel()

        initUploads()

        CheckAskPermissions.checkAskRequestStoragePermissions(this, this)


    }

    private fun initBindingDiAndViewModel() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_uploads)

        (application as MyApplication).netComponent?.inject(this)

        viewModel = viewModelFactory.create(UploadsViewModel::class.java)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

    }

    private fun initUploads(){

        val adapter = UploadListAdapter(UploadDiffUtil())

        binding.rcvRemainingUploadsAct.adapter = adapter
        binding.rcvRemainingUploadsAct.layoutManager = LinearLayoutManager(this)

        val callback: ItemTouchHelper.Callback = ItemTouchHelperCallback(adapter)
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(binding.rcvRemainingUploadsAct)

        viewModel.getAllUpload()?.observe(this) {
            adapter.submitList(it)
        }

        binding.fabAddProduct.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.flv_fragment, UploadsFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }

    }


}