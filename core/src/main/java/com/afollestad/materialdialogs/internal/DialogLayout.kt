package com.afollestad.materialdialogs.internal

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.View.MeasureSpec.AT_MOST
import android.view.View.MeasureSpec.EXACTLY
import android.view.View.MeasureSpec.UNSPECIFIED
import android.widget.FrameLayout
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.Theme
import com.afollestad.materialdialogs.extensions.dimenPx
import com.afollestad.materialdialogs.extensions.updatePadding
import com.afollestad.materialdialogs.internal.button.DialogActionButtonLayout
import com.afollestad.materialdialogs.internal.main.DialogTitleLayout

/**
 * The root layout of a dialog. Contains a [DialogTitleLayout], [DialogActionButtonLayout],
 * along with a content view that goes in between.
 *
 * @author Aidan Follestad (afollestad)
 */
internal class DialogLayout(
  context: Context,
  attrs: AttributeSet?
) : FrameLayout(context, attrs) {

  var maxHeight: Int = 0
  var theme: Theme = Theme.LIGHT
    set(value) {
      if (field == value) return
      field = value
    }
  var debugMode: Boolean = false

  private val frameMarginVertical = dimenPx(R.dimen.md_dialog_frame_margin_vertical)
  private val contentView: View
    get() = rootLayout.getChildAt(1)

  internal lateinit var rootLayout: DialogLayout
  internal lateinit var titleLayout: DialogTitleLayout
  internal lateinit var buttonsLayout: DialogActionButtonLayout

  override fun onFinishInflate() {
    super.onFinishInflate()
    rootLayout = findViewById(R.id.md_root)
    titleLayout = findViewById(R.id.md_title_layout)
    buttonsLayout = findViewById(R.id.md_button_layout)
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    if (debugMode) {
      contentView.setBackgroundColor(Color.parseColor("#40cc0000"))
    }
  }

  internal fun invalidateDividers(
    scrolledDown: Boolean,
    atBottom: Boolean
  ) {
    titleLayout.drawDivider = scrolledDown
    buttonsLayout.drawDivider = atBottom
  }

  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    val specWidth = MeasureSpec.getSize(widthMeasureSpec)
    var specHeight = MeasureSpec.getSize(heightMeasureSpec)
    if (specHeight > maxHeight) {
      specHeight = maxHeight
    }

    titleLayout.measure(
        MeasureSpec.makeMeasureSpec(specWidth, EXACTLY),
        MeasureSpec.makeMeasureSpec(0, UNSPECIFIED)
    )
    buttonsLayout.measure(
        MeasureSpec.makeMeasureSpec(specWidth, EXACTLY),
        MeasureSpec.makeMeasureSpec(0, UNSPECIFIED)
    )

    val titleAndButtonsHeight =
      titleLayout.measuredHeight + buttonsLayout.measuredHeight
    val remainingHeight = specHeight - titleAndButtonsHeight
    contentView.measure(
        MeasureSpec.makeMeasureSpec(specWidth, EXACTLY),
        MeasureSpec.makeMeasureSpec(remainingHeight, AT_MOST)
    )

    if (titleLayout.shouldNotBeVisible()) {
      contentView.updatePadding(
          top = frameMarginVertical
      )
    }

    val totalHeight = titleLayout.measuredHeight +
        contentView.measuredHeight +
        buttonsLayout.measuredHeight
    setMeasuredDimension(specWidth, totalHeight)
  }

  override fun onLayout(
    changed: Boolean,
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
  ) {
    val titleLeft = 0
    val titleTop = 0
    val titleRight = measuredWidth
    val titleBottom = titleLayout.measuredHeight
    titleLayout.layout(
        titleLeft,
        titleTop,
        titleRight,
        titleBottom
    )

    val buttonsLeft = 0
    val buttonsTop =
      measuredHeight - buttonsLayout.measuredHeight
    val buttonsRight = measuredWidth
    val buttonsBottom = measuredHeight
    buttonsLayout.layout(
        buttonsLeft,
        buttonsTop,
        buttonsRight,
        buttonsBottom
    )

    val contentLeft = 0
    val contentTop = titleBottom + 1
    val contentRight = measuredWidth
    val contentBottom = buttonsTop - 1

    contentView.layout(
        contentLeft,
        contentTop,
        contentRight,
        contentBottom
    )
  }
}
