package com.hansilk.two.blocks.uploads.frags

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hansilk.two.blocks.common.comUtils.CatalogUtils
import com.hansilk.two.blocks.common.comUtils.DatabaseUtils
import com.hansilk.two.blocks.common.comUtils.RetrofitUtils
import com.hansilk.two.blocks.uploads.data.Upload
import com.hansilk.two.blocks.uploads.main.UploadsViewModel
import com.hansilk.two.blocks.uploads.service.UploadService
import com.hansilk.two.databinding.FragmentUploadsBinding
import com.hansilk.two.support.MyApplication
import com.hansilk.two.support.retrofit.RetrofitApi
import com.hansilk.two.support.utils.fileUtils.FileIO
import com.hansilk.two.support.utils.fileUtils.FilePath
import com.hansilk.two.support.utils.imageUtils.ImageEdit
import com.hansilk.two.support.utils.listUtils.ListUtils
import com.hansilk.two.support.utils.uidUtils.UidUtils
import com.hansilk.two.support.utilsUI.dialog.ProgressDialog
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.StringBuilder
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class UploadsFragment : Fragment() {

    @Inject lateinit var retrofit: Retrofit
    private lateinit var binding: FragmentUploadsBinding
    private lateinit var viewModel: UploadsViewModel

    private lateinit var progressDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUploadsBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    override fun onAttach(context: Context) {
        (context.applicationContext as MyApplication).netComponent?.inject(this)
        viewModel = ViewModelProvider(requireActivity()).get(UploadsViewModel::class.java)
        super.onAttach(context)
    }

    private fun init(){

        viewModel.uploadFragStep.value = -1

        viewModel.uploadFragModel.put("cb", 280)
        viewModel.uploadFragModel.put("ck", 336)
        viewModel.uploadFragModel.put("cl", 339)
        viewModel.uploadFragModel.put("cm", 345)
        viewModel.uploadFragModel.put("cc", 248)
        viewModel.uploadFragModel.put("ac", 2)

        binding.ivAddProductsUploadsFrag.setOnClickListener{
            selectImages()
        }

        viewModel.getAllDra()?.observe(viewLifecycleOwner) {
            val size = it?.size ?: 0
            if (size>0){
                for (dra in it!!) {
                    try {
                        if (dra != null) {
                            viewModel.uploadAllDraSet.put(dra.ba, dra.bb)
                            viewModel.uploadAllDraSet.put(dra.bc, dra.aa)
                            viewModel.uploadAllDraSet.put(dra.aa, dra.bc)
                        }
                    } catch (e: JSONException){
                        e.printStackTrace()
                    }
                }
            }
        }

        viewModel.syncDra()

        initChipGroup()

        binding.ivPrevUploadsFrag.setOnClickListener{
            val currentStep = viewModel.uploadFragStep.value
            viewModel.uploadFragStep.value = if (currentStep != null && currentStep>-1) currentStep-1 else currentStep
        }

        binding.ivNextUploadsFrag.setOnClickListener{
            val currentStep = viewModel.uploadFragStep.value
            viewModel.uploadFragStep.value = if (currentStep != null && currentStep<viewModel.uploadFragAttrSet.size) currentStep+1 else currentStep
        }

        binding.ivDoneUploadsFrag.setOnClickListener{
            validateAndProcess()
        }

        binding.ivAddDraUploadsFrag.setOnClickListener{
            newAttrEntry()
        }

        binding.ivAddDraUploadsFrag.setOnLongClickListener {
            viewModel.syncDra()
            Toast.makeText(context, "Syncing Attr DataSet",Toast.LENGTH_LONG).show()
            return@setOnLongClickListener true
        }

    }


    private fun selectImages() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true)
        resultLauncher.launch(intent)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data: Intent? = it.data

                val pathString = if (viewModel.uploadFragModel.has("path")) viewModel.uploadFragModel.getString("path") else ""
                val list = ListUtils.StringToArrayListWith___(pathString)

                if (data != null) {
                    if (data.clipData != null) {
                        val mClipData = data.clipData
                        for (i in 0 until mClipData!!.itemCount) {
                            val uri = mClipData.getItemAt(i).uri
                            val path = context?.let { it1 -> FilePath.getPath(it1, uri) }
                            if (path != null) list.add(path)
                        }
                    } else if (data.data != null) {
                        val uri = data.data!!
                        val path = context?.let { it1 -> FilePath.getPath(it1, uri) }
                        if (path != null) list.add(path)
                    }

                    viewModel.collGridImage = ImageEdit.buildImageGridFromUriArrayForProductGroup(list)
                    binding.ivAddProductsUploadsFrag.setImageBitmap(viewModel.collGridImage)
                    viewModel.uploadFragModel.put("path", ListUtils.ArrayListToStringWith___(list))

                    Toast.makeText(context, "Selected ${list.size} Images",Toast.LENGTH_LONG).show()

                }
            }
        }

    private fun initChipGroup(){
        viewModel.uploadFragStep.observe(viewLifecycleOwner, {

            binding.chpgrpSelectionsUploadsFrag.removeAllViews()

            val attrSetSize = viewModel.uploadFragAttrSet.size

            if (it>=attrSetSize || it<0) {
                binding.llvImageEtcUploadsFrag.visibility = View.VISIBLE
                binding.vsvChipScrollUploadsFrag.visibility = View.GONE
            } else {
                binding.llvImageEtcUploadsFrag.visibility = View.GONE
                binding.vsvChipScrollUploadsFrag.visibility = View.VISIBLE

                val ba = viewModel.uploadFragAttrSet[it]

                val selected = if (viewModel.uploadFragModel.has(ba)) viewModel.uploadFragModel.getString(ba) else ""

                viewModel.getFilteredDra(ba)?.observe(viewLifecycleOwner) { it1 ->
                    for (dra in it1!!) {
                        val chip = Chip(context)
                        chip.isCheckable = true
                        chip.text = dra?.bc
                        binding.chpgrpSelectionsUploadsFrag.addView(chip)
                        chip.chipCornerRadius = 16F
                        chip.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT

                        if (selected == dra?.bc) chip.isChecked = true
                    }
                }

                binding.chpgrpSelectionsUploadsFrag.setOnCheckedChangeListener { group, checkedId ->
                    if (checkedId != -1) {
                        val selectedChip: Chip = binding.chpgrpSelectionsUploadsFrag.findViewById(checkedId)
                        viewModel.uploadFragModel.put(ba, selectedChip.text)
                    }
                }

            }

        })
    }


    private fun validateAndProcess(){

        var dataValid = true

        val enteredStr: String = binding.etPriceUploadsFrag.text.toString()
        if (enteredStr.isEmpty()) {
            dataValid = false
            Toast.makeText(context, "Please enter a valid Price",Toast.LENGTH_LONG).show()
        }

        for (xx in viewModel.uploadFragAttrSet){
            if (dataValid && !viewModel.uploadFragModel.has(xx)) {
                Toast.makeText(context, "Please select a ${viewModel.uploadAllDraSet.getString(xx)}",Toast.LENGTH_LONG).show()
                dataValid = false
            }
        }

        if (dataValid) {
            var summary = ""

            val cq: Int = enteredStr.toInt()
            val rb: Int = cq+ min(320, max(50.0,(cq*0.12)).toInt())
            val cp: Int = cq+ min(640,max(100.0,(cq*0.24)).toInt())
            val ca: Int = CatalogUtils.getReoCa(cp)

            for (ky in viewModel.uploadFragAttrSet){
                if (viewModel.uploadFragModel.has(ky)) {
                    val vl = viewModel.uploadFragModel.getString(ky)
                    viewModel.uploadFragModel.put(ky, viewModel.uploadAllDraSet.getString(vl))
                }
            }

            viewModel.uploadFragModel.put("cq", cq)
            viewModel.uploadFragModel.put("rb", rb)
            viewModel.uploadFragModel.put("cp", cp)
            viewModel.uploadFragModel.put("ca", ca)

            for (xx in viewModel.uploadFragAttrSet){

                val ky = viewModel.uploadAllDraSet.getString(xx)
                var vl = viewModel.uploadFragModel.getString(xx)
                vl = viewModel.uploadAllDraSet.getString(vl)

                summary += "$ky : $vl\n"
            }

            summary += "Original price : $cq/-\n"
            summary += "General price : $cp/- > ${cp-cq}/-\n"
            summary += "Reseller price : $rb/- > ${rb-cq}/-\n"

            AlertDialog.Builder(context)
                .setTitle("Confirm Product Uploads")
                .setMessage(summary)
                .setPositiveButton(android.R.string.yes) { dialog, which ->

                    context?.let {
                        progressDialog = ProgressDialog.getProgressDialogMid(it)
                        progressDialog.show()
                    }

                    processCollection()
                }
                .setNegativeButton(android.R.string.no) { dialog, which -> }
                .show()

        }

    }

    private fun processCollection(){

        val jj = viewModel.uploadFragModel

        val pathString = if (jj.has("path")) jj.getString("path") else ""
        val list = ListUtils.StringToArrayListWith___(pathString)

        val ab = UidUtils.getUidMillies()
        jj.put("ab", ab)
        jj.put("bc", 1)
        jj.put("iy", list.size)

        pushCollectionToReo(jj)
    }

    private fun pushCollectionToReo(jj: JSONObject){
        val ab = jj.getLong("ab")
        val query = RetrofitUtils.getInsertQueryFromJSONObj(DatabaseUtils.filterOutExtrasReo(jj), "reo")
        val api = retrofit.create(RetrofitApi::class.java)
        api.sset(query)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful && response.body()!=null) {

                    val collectionImagePath = context?.let {
                        FileIO.saveCollectionImageReturnPath(it, viewModel.collGridImage, ab)
                    }

                    val upload = Upload()
                    upload.path = collectionImagePath?.absolutePath
                    upload.state = 0
                    upload.ab = ab
                    upload.bc = 0
                    viewModel.insertUpload(upload)

                    getCuFromReo(ab)

                }
            }
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                pushCollectionToReo(jj)
            }
        })
    }

    private fun getCuFromReo(ab: Long){
        val query2 = "SELECT aa FROM `reo` WHERE ab=$ab"
        retrofit.create(RetrofitApi::class.java).sget(query2)?.enqueue(object :
            Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful && response.body()!=null) {
                    val got = response.body()!!.string()
                    if (got.isNotEmpty()){
                        val aa = JSONArray(got).getJSONObject(0).getString("aa")
                        goAhead(aa)
                    }
                }
            }
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                getCuFromReo(ab)
            }
        })
    }

    private fun goAhead(aa: String){
        activity?.let { UploadService.startService(it.applicationContext,"") }
        viewModel.addProductsToQueue(aa)
        progressDialog.dismiss()
        activity?.onBackPressed()
    }

    private fun newAttrEntry(){
        val step = viewModel.uploadFragStep.value ?: 0

        if (step < viewModel.uploadFragAttrSet.size && step >= 0) {
            val ba = viewModel.uploadFragAttrSet[step]
            val bb = viewModel.uploadAllDraSet.getString(ba)

            val llLayout = LinearLayout(context)
            llLayout.orientation = LinearLayout.VERTICAL
            llLayout.setPadding(60,60,60,60)

            val etEntryA = EditText(context)
            val etEntryB = EditText(context)
            val etEntryC = EditText(context)

            llLayout.addView(etEntryA)

            if (ba=="bb") {
                llLayout.addView(etEntryB)
                llLayout.addView(etEntryC)
            }

            AlertDialog
                .Builder(context)
                .setTitle("Add New $bb")
                .setPositiveButton("Add"){ dialog,which ->
                    val jj = JSONObject()
                    jj.put("ab",UidUtils.getUidMillies())
                    jj.put("ac",1)
                    jj.put("ba",ba)
                    jj.put("bb",bb)
                    jj.put("bc",etEntryA.text.toString())

                    if (ba=="bb") {
                        jj.put("bd",etEntryB.text.toString())
                        jj.put("be",etEntryC.text.toString())
                    }

                    pushToDra(jj)

                    lifecycleScope.launch {
                        coroutineScope {
                            delay(3000)
                            viewModel.syncDra()
                        }
                    }

                }
                .setNegativeButton("Cancel"){_,_->

                }
                .setView(llLayout)
                .show()

        }

    }


    fun pushToDra(jj: JSONObject){
        val query = RetrofitUtils.getInsertQueryFromJSONObj(jj, "dra")
        val api = retrofit.create(RetrofitApi::class.java)
        api.sset(query)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful && response.body()!=null) {
                    val got = response.body()!!.string()
                    println("query : $query :: got : $got")
                }
            }
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                pushToDra(jj)
            }
        })
    }

}