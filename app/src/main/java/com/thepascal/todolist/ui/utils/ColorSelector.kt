package com.thepascal.todolist.ui.utils

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.thepascal.todolist.R
import kotlinx.android.synthetic.main.color_selector.view.*

/**
 * This is a compound component to create a color code
 * selector
 */
class ColorSelector @JvmOverloads constructor(
    _context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(_context, attrs, defStyleAttr, defStyleRes) {

    private var listOfColors = listOf(
        Color.RED, Color.YELLOW, Color.rgb(255, 165, 0),
        Color.GREEN, Color.BLUE
    )
    private var selectedColorIndex = 0 //this will keep track of which color is selected

    private var colorSelectorListener: ColorSelectorListener? = null
    private fun setListener(listener: ColorSelectorListener) {
        colorSelectorListener = listener
    }

    init {
        orientation = HORIZONTAL
        val inflater: LayoutInflater =
            _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.color_selector, this)

        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])

        backArrow.setOnClickListener {
            selectPreviousColor()
        }

        forwardArrow.setOnClickListener {
            selectNextColor()
        }

        colorCheckBox.setOnCheckedChangeListener { _, _ ->
            saveSelectedColor()
        }
    }

    interface ColorSelectorListener {
        fun onColorSelected(color: Int)
    }

    private fun selectPreviousColor() {
        if (selectedColorIndex == 0) {
            selectedColorIndex = listOfColors.lastIndex
        } else {
            selectedColorIndex--
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
        saveSelectedColor()
    }

    private fun selectNextColor() {
        if (selectedColorIndex == listOfColors.lastIndex) {
            selectedColorIndex = 0
        } else {
            selectedColorIndex++
        }
        selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
        saveSelectedColor()
    }

    //Will be called whether checkbox is checked/unchecked
    private fun saveSelectedColor() {
        val color = if (colorCheckBox.isChecked)
            listOfColors[selectedColorIndex]
        else
            Color.TRANSPARENT

        colorSelectorListener?.onColorSelected(color)
    }
}