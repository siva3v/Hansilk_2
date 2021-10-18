package com.hansilk.two.support.utilsUI.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.hansilk.two.R

class ProgressDialog {
    companion object{

        fun getProgressDialogMid(context: Context): Dialog{

            val dialog = Dialog(context)
            dialog.requestWindowFeature(10)
            val view: View =
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.dialog_progress_mid,
                    null,
                    false
                )

            (context as Activity).window.setSoftInputMode(20)
            dialog.setContentView(view)
            val window = dialog.window
            window!!.setLayout(-1, -1)
            window.setBackgroundDrawableResource(R.color.transperrent)
            //window.setGravity(17)
            //dialog.show()

            return dialog
        }
    }
}