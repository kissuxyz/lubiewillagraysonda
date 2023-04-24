package com.example.lubiewillagraysonda

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var shoppingItems: Map<String, List<String>>
    private var selectedItems: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shoppingItems = mapOf(
            "Owoce" to listOf("Truskawki", "Banany", "Mandarynki", "Jagody", "Mango"),
            "Warzywa" to listOf("Papryka", "Marchew", "Ogorki", "Salata", "Pomidor"),
            "Ryby" to listOf("Makrela", "Sledz", "Losos", "Dorsz", "Tunczyk")
        )

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.AddButton -> showAddMode()
                R.id.RemoveButton -> showCheckOffMode()
            }
        }


        val fruitgroup = findViewById<ChipGroup>(R.id.fruitgroup)
        val VeggiGroup = findViewById<ChipGroup>(R.id.VeggiGroup)
        val FishGroup = findViewById<ChipGroup>(R.id.FishGroup)

        fruitgroup.removeAllViews()
        VeggiGroup.removeAllViews()
        FishGroup.removeAllViews()

        val shoppingItemList = shoppingItems.values.flatten()
        for (item in shoppingItemList) {
            val checkbox = Chip(this)
            checkbox.text = item
            checkbox.isCheckable = true
            checkbox.tag = shoppingItems.entries.firstOrNull { it.value.contains(item) }?.key

            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItems.add(item)
                } else {
                    selectedItems.remove(item)
                }
            }

            when (checkbox.tag) {
                "Owoce" -> {
                    checkbox.chipBackgroundColor =
                        resources.getColorStateList(R.color.owoc)
                }
                "Warzywa" -> {
                    checkbox.chipBackgroundColor =
                        resources.getColorStateList(R.color.warzyw)
                }
                "Ryby" -> {
                    checkbox.chipBackgroundColor = resources.getColorStateList(R.color.ryba)
                }
            }

            when (checkbox.tag) {
                "Owoce" -> fruitgroup.addView(checkbox)
                "Warzywa" -> VeggiGroup.addView(checkbox)
                "Ryby" -> FishGroup.addView(checkbox)
            }
        }


    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private fun showAddMode() {
        val checkboxesLayout: View = findViewById(R.id.checkboxesLayout)
        val chipsLayout: View = findViewById(R.id.chipsLayout)

        with(checkboxesLayout) {
            visibility = View.VISIBLE
        }

        with(chipsLayout) {
            visibility = View.GONE
        }
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private fun showCheckOffMode() {
        val checkboxesLayout = findViewById<View>(R.id.checkboxesLayout)
        val chipsLayout = findViewById<View>(R.id.chipsLayout)
        checkboxesLayout.visibility = View.GONE
        chipsLayout.visibility = View.VISIBLE

        val chipsGroup = findViewById<ChipGroup>(R.id.chipsGroup)
        chipsGroup.removeAllViews()

        for ((groupName, items) in shoppingItems) {
            for (item in items) {
                if (selectedItems.contains(item)) {
                    val chip = Chip(this)
                    chip.text = item
                    chip.isCloseIconVisible = true
                    chip.tag = groupName


                    when (groupName) {
                        "Owoce" -> {
                            chip.chipBackgroundColor =
                                resources.getColorStateList(R.color.owoc)
                        }
                        "Warzywa" -> {
                            chip.chipBackgroundColor =
                                resources.getColorStateList(R.color.warzyw)
                        }
                        "Ryby" -> {
                            chip.chipBackgroundColor =
                                resources.getColorStateList(R.color.ryba)
                        }
                    }

                    chipsGroup.addView(chip)

                    chip.setOnCloseIconClickListener {
                        selectedItems.remove(item)
                        chipsGroup.removeView(chip)
                    }
                }
            }
        }
    }
}