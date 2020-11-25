import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.android.unewsapp.R
import com.example.android.unewsapp.ui.fragments.widget.CustomSnackbar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.snackbar_with_button.*
import kotlinx.android.synthetic.main.snackbar.view.*

class SnackFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.snackbar_with_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initClicks()

        showSimpleCustomSnackbar("test", view)
    }

    private fun initClicks() {
        snackbar_button.setOnClickListener({

        })
        //btnInfo.setOnClickListener {
        //    showSimpleCustomSnackbar("info", it)
        //}
        //btnError.setOnClickListener {
        //    showSimpleCustomSnackbar("error", it)
        //}
        //btnSuccess.setOnClickListener {
        //    showSimpleCustomSnackbar("success", it)
        //}
        //btnCenter.setOnClickListener {
        //    val snack =
        //        Snackbar.make(it, "Snack with center gravity", Snackbar.LENGTH_SHORT)
        //    val snackText =
        //        snack.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        //    snackText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        //    snack.show()
        //}
        //btnCustom.setOnClickListener {
        //    CustomSnackbar.makeCustomSnackbar(it).show()
        //}
    }

    private fun showSimpleCustomSnackbar(condition: String, view: View) {

        //const val ERROR_CODE_TIMEOUT = "ERROR_CODE_TIMEOUT"
        //const val ERROR_CODE_NO_CONTENT = "ERROR_CODE_NO_CONTENT"
        //const val ERROR_CODE_BAD_REQUEST = "ERROR_CODE_BAD_REQUEST"
        //const val ERROR_CODE_SERVER_EXCEPTION = "ERROR_CODE_SERVER_EXCEPTION"
        //const val ERROR_UNKNOWN_EXCEPTION = "ERROR_UNKNOWN_EXCEPTION"
        //const val ERROR_NO_INTERNET = "ERROR_NO_INTERNET"


        //val text = when (condition) {
        //    "info" -> getText(R.string.some_info_text_here)
        //    "error" -> getText(R.string.some_error_appeared)
        //    else -> getText(R.string.success)
        //}

        val text = condition;

        //val color = when (condition) {
        //    "info" -> ContextCompat.getColor(view.context, R.color.info_blue)
        //    "error" -> ContextCompat.getColor(view.context, R.color.error_red)
        //    else -> ContextCompat.getColor(view.context, R.color.success_green)
        //}
        //val iconId = when (condition) {
        //    "info" -> R.drawable.ic_baseline_info
        //    "error" -> R.drawable.ic_baseline_close
        //    else -> R.drawable.ic_baseline_check
        //}
        val snack =
            Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        val snackView = snack.view
        //snackView.setBackgroundColor(color)
        val snackText =
            snackView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        //snackText.setCompoundDrawablesWithIntrinsicBounds(iconId, 0, 0, 0)
        //snackText.compoundDrawablePadding = 8dp
        snack.show()
    }
}