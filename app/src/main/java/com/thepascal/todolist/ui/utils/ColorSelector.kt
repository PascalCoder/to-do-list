package com.thepascal.todolist.ui.utils

import android.content.Context
import android.content.res.TypedArray
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

    var listOfColors = listOf(
        Color.RED, Color.rgb(255, 165, 0),
        Color.YELLOW, Color.GREEN, Color.BLUE
    )
    var selectedColorIndex = 0 //this will keep track of which color is selected
    var selectedColorValue:Int? = android.R.color.transparent //will used to keep track of the selectedColor's color
        set(value) {
            var index = listOfColors.indexOf(value)
            if (index == -1) {
                colorCheckBox.isChecked = false
                index = 0
            } else {
                colorCheckBox.isChecked = true
            }
            selectedColorIndex = index
            selectedColor.setBackgroundColor(listOfColors[selectedColorIndex])
        }

    private var colorSelectorListener: ((Int) -> Unit)? = null
    fun setListener(listener: (Int) -> Unit) {
        colorSelectorListener = listener
    }

    init {
        val typedArray: TypedArray = _context.obtainStyledAttributes(
            attrs, R.styleable.ColorSelector
        )
        listOfColors = typedArray.getTextArray(R.styleable.ColorSelector_colors)
            .map {
                Color.parseColor(it.toString())
            }
        typedArray.recycle()

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

    /*interface ColorSelectorListener {
        fun onColorSelected(color: Int)
    }*/

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

        colorSelectorListener?.let { function ->
            function(color)
        }
    }
}