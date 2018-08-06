package com.afollestad.materialdialogs

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.CheckResult
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.annotation.StyleRes
import android.widget.LinearLayout
import android.widget.TextView
import com.afollestad.materialdialogs.R.layout
import com.afollestad.materialdialogs.extensions.addContentScrollView
import com.afollestad.materialdialogs.extensions.getString
import com.afollestad.materialdialogs.extensions.inflate
import com.afollestad.materialdialogs.extensions.setIcon
import com.afollestad.materialdialogs.extensions.setText
import com.afollestad.materialdialogs.extensions.setWindowConstraints
import com.afollestad.materialdialogs.internal.DialogLayout
import com.afollestad.materialdialogs.internal.DialogScrollView
import com.afollestad.materialdialogs.internal.button.DialogActionButtonLayout.Companion.INDEX_NEGATIVE
import com.afollestad.materialdialogs.internal.button.DialogActionButtonLayout.Companion.INDEX_NEUTRAL
import com.afollestad.materialdialogs.internal.button.DialogActionButtonLayout.Companion.INDEX_POSITIVE
import com.afollestad.materialdialogs.internal.list.DialogRecyclerView

typealias DialogCallback = (MaterialDialog) -> Unit

internal fun assertOneSet(
  a: Int,
  b: Any?
) {
  if (a == 0 && b == null) {
    throw IllegalArgumentException("You must specify a resource ID or literal value.")
  }
}

enum class Theme(@StyleRes val styleRes: Int) {
  LIGHT(R.style.MD_Light),
  DARK(R.style.MD_Dark)
}

/** @author Aidan Follestad (afollestad) */
class MaterialDialog(
  context: Context,
  internal val theme: Theme = Theme.LIGHT
) : Dialog(context, theme.styleRes) {

  internal val view: DialogLayout = inflate(context, R.layout.md_dialog_base)
  internal var autoDismiss: Boolean = true
    private set

  internal var textViewMessage: TextView? = null
  internal var contentScrollView: DialogScrollView? = null
  internal var contentScrollViewFrame: LinearLayout? = null
  internal var contentRecyclerView: DialogRecyclerView? = null

  init {
    setContentView(view)
    setWindowConstraints()
    view.theme = theme
  }

  @CheckResult
  fun MaterialDialog.icon(
    @DrawableRes iconRes: Int = 0,
    icon: Drawable? = null
  ): MaterialDialog {
    assertOneSet(iconRes, icon)
    setIcon(
        view.titleLayout.iconView,
        iconRes = iconRes,
        icon = icon
    )
    return this
  }

  @CheckResult
  fun title(
    @StringRes textRes: Int = 0,
    text: CharSequence? = null
  ): MaterialDialog {
    assertOneSet(textRes, text)
    setText(
        view.titleLayout.titleView,
        textRes = textRes,
        text = text
    )
    return this
  }

  @CheckResult
  fun message(
    @StringRes textRes: Int = 0,
    text: CharSequence? = null
  ): MaterialDialog {
    addContentScrollView()
    addContentMessageView(textRes, text)
    return this
  }

  @CheckResult
  fun positiveButton(
    @StringRes positiveRes: Int = 0,
    positiveText: CharSequence? = null,
    click: ((MaterialDialog) -> (Unit))? = null
  ): MaterialDialog {
    setText(
        view.buttonsLayout.actionButtons[INDEX_POSITIVE],
        textRes = positiveRes,
        text = positiveText,
        fallback = android.R.string.ok,
        click = click
    )
    return this
  }

  @CheckResult
  fun negativeButton(
    @StringRes negativeRes: Int = 0,
    negativeText: CharSequence? = null,
    click: ((MaterialDialog) -> (Unit))? = null
  ): MaterialDialog {
    setText(
        view.buttonsLayout.actionButtons[INDEX_NEGATIVE],
        textRes = negativeRes,
        text = negativeText,
        fallback = android.R.string.cancel,
        click = click
    )
    return this
  }

  @CheckResult
  fun neutralButton(
    @StringRes neutralRes: Int = 0,
    neutralText: CharSequence? = null,
    click: ((MaterialDialog) -> (Unit))? = null
  ): MaterialDialog {
    assertOneSet(neutralRes, neutralText)
    setText(
        view.buttonsLayout.actionButtons[INDEX_NEUTRAL],
        textRes = neutralRes,
        text = neutralText,
        click = click
    )
    return this
  }

  @CheckResult
  fun noAutoDismiss(): MaterialDialog {
    this.autoDismiss = false
    return this
  }

  @CheckResult
  fun debugMode(debugMode: Boolean = true): MaterialDialog {
    this.view.debugMode = debugMode
    return this
  }

  @CheckResult
  inline fun onShow(crossinline callback: DialogCallback): MaterialDialog {
    setOnShowListener { callback.invoke(this@MaterialDialog) }
    return this
  }

  @CheckResult
  inline fun onDismiss(crossinline callback: DialogCallback): MaterialDialog {
    setOnDismissListener { callback.invoke(this@MaterialDialog) }
    return this
  }

  @CheckResult
  inline fun onCancel(crossinline callback: DialogCallback): MaterialDialog {
    setOnCancelListener { callback.invoke(this@MaterialDialog) }
    return this
  }

  inline fun show(func: MaterialDialog.() -> Unit) {
    this.func()
    super.show()
  }

  private fun addContentMessageView(@StringRes res: Int, text: CharSequence?) {
    if (this.textViewMessage == null) {
      this.textViewMessage = inflate(
          context,
          layout.md_dialog_stub_message,
          this.contentScrollViewFrame!!
      )
      this.contentScrollViewFrame!!.addView(this.textViewMessage)
    }
    assertOneSet(res, text)
    this.textViewMessage!!.text = text ?: getString(res)
  }
}