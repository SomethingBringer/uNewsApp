package com.example.android.unewsapp.ui.fragments.widget

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.android.unewsapp.ui.fragments.widget.CustomSnackbarView
import com.example.android.unewsapp.R
import com.example.android.unewsapp.utils.findSuitableParent
import com.google.android.material.snackbar.BaseTransientBottomBar
import kotlinx.android.synthetic.main.snackbar_with_button.view.*

class CustomSnackbar(
    parent: ViewGroup,
    content: CustomSnackbarView
) : BaseTransientBottomBar<CustomSnackbar>(parent, content, content) {

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        getView().setPadding(0, 0, 0, 0)
    }

    companion object{

        fun makeCustomSnackbar(view: View, errorText: String): CustomSnackbar{
            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                "No suitable parent found from the given view. Please provide a valid view."
            )
            val customView = LayoutInflater.from(view.context).inflate(
                R.layout.layout_snackbar,
                parent,
                false
            ) as CustomSnackbarView

            val error = when(errorText){
                "ERROR_CODE_TIMEOUT" -> "Code timeout"
                "ERROR_CODE_NO_CONTENT" -> "No content"
                "ERROR_CODE_BAD_REQUEST" -> "Bad request"
                "ERROR_CODE_SERVER_EXCEPTION" -> "Server exception"
                "ERROR_UNKNOWN_EXCEPTION" -> "Unknown exception"
                "ERROR_NO_INTERNET" -> "No connection"
                else -> "Unknown error"
            }
            customView.snackbar_text.text = error
            //customView.setBackgroundColor()

            return CustomSnackbar(parent, customView)
        }
    }
}