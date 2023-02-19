package com.asustug.themoviedb.ui.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.asustug.themoviedb.R
import com.asustug.themoviedb.ui.MainActivity.CLASSIFY.finalData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class FilterBottomSheet : BottomSheetDialogFragment() {

    lateinit var radioGroup: ChipGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view = inflater.inflate(R.layout.filter_bottom_sheet_content, container, false)
        radioGroup = view.findViewById(R.id.chip_filter)
        return view
    }

    companion object {
        const val TAG = "FilterBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}