package com.example.android.unewsapp.ui.fragments.converter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.android.unewsapp.MyApplication
import com.example.android.unewsapp.R
import com.example.android.unewsapp.ui.fragments.widget.CustomSnackbar
import kotlinx.android.synthetic.main.fragment_converter.*
import kotlinx.android.synthetic.main.item_currency.view.*
import javax.inject.Inject

class ConverterFragment : Fragment() {

    @Inject
    lateinit var providerFactory: ViewModelProvider.Factory
    lateinit var viewModel: ConverterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApplication).appComponent.inject(this)
        viewModel = ViewModelProvider(this, providerFactory).get(ConverterViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_converter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
        viewModel.getPairs()

        spinnerCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.getValues(spinnerCurrency.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }

        etCoef.doAfterTextChanged {
            if (it.isNullOrBlank())
                viewModel.coef = 1
            else
                viewModel.coef = it.toString().toInt()
            viewModel.getValues(spinnerCurrency.selectedItem.toString())
        }
    }

    private fun observeLiveData() {
        viewModel.pairsLiveData.observe(viewLifecycleOwner) {

        }
        viewModel.valuesLiveData.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                llValues.removeAllViews()
                it.forEach { item -> inflateItem(item.key, item.value, viewModel.coef) }
            }
        }
        viewModel.currenciesLiveData.observe(viewLifecycleOwner) {
            spinnerCurrency.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                viewModel.currenciesList
            )
            spinnerCurrency.setSelection(0)
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Log.e("ERROR_ENTITY", it.toString())

            //val view = layoutInflater.inflate(R.layout.item_currency, llValues, false)
            val view = layoutInflater.inflate(R.layout.snackbar_with_button, llValues, false)
            CustomSnackbar.makeCustomSnackbar(view)
        }
    }

    private fun inflateItem(key: String, value: String, coef: Int) {
        val view = layoutInflater.inflate(R.layout.item_currency, llValues, false)
        view.tvName.text = key.drop(3)
        view.tvValue.text = (value.toFloat() * coef).toString()
        llValues.addView(view, 0)
    }
}