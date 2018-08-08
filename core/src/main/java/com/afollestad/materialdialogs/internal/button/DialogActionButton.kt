package com.afollestad.materialdialogs.internal.button

import android.content.Context
import android.support.v7.widget.AppCompatButton
import android.util.AttributeSet
import android.view.View
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.extensions.dimenPx
import com.afollestad.materialdialogs.extensions.getDrawable
import com.afollestad.materialdialogs.extensions.updatePadding

/**
 * Represents an action button in a dialog, positive, negative, or neutral. Handles switching
 * out its selector, padding, and text alignment based on whether buttons are in stacked mode or not.
 *
 * @author Aidan Follestad (afollestad)
 */
internal class DialogActionButton(
  context: Context,
  attrs: AttributeSet? = null
) : AppCompatButton(context, attrs) {

  private val paddingDefault = dimenPx(R.dimen.md_action_button_padding_horizontal)
  private val paddingStacked = dimenPx(R.dimen.md_stacked_action_button_padding_horizontal)

  init {
    isClickable = true
    isFocusable = true
  }

  fun update(stacked: Boolean) {
    // Selector
    val selectorAttr = if (stacked) R.attr.md_item_selector else R.attr.md_button_selector
    background = getDrawable(context, attr = selectorAttr)

    // Padding
    val sidePadding = if (stacked) paddingStacked else paddingDefault
    updatePadding(left = sidePadding, right = sidePadding)

    // Text alignment
    textAlignment = if (stacked) View.TEXT_ALIGNMENT_VIEW_END else View.TEXT_ALIGNMENT_CENTER
  }
}
