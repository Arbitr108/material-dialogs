package com.afollestad.materialdialogs.internal.button

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.extensions.dimenPx
import com.afollestad.materialdialogs.extensions.isVisible
import com.afollestad.materialdialogs.internal.BaseSubLayout

/**
 * Manages a set of three [DialogActionButton]'s (measuring, layout, etc.).
 * Also handles switching between stacked and unstacked configuration.
 *
 * @author Aidan Follestad (afollestad)
 */
internal class DialogActionButtonLayout(
  context: Context,
  attrs: AttributeSet? = null
) : BaseSubLayout(context, attrs) {

  companion object {
    const val INDEX_POSITIVE = 0
    const val INDEX_NEGATIVE = 1
    const val INDEX_NEUTRAL = 2
  }

  private val buttonHeightDefault = dimenPx(R.dimen.md_action_button_height)
  private val buttonHeightStacked = dimenPx(R.dimen.md_stacked_action_button_height)
  private val buttonFramePadding = dimenPx(R.dimen.md_action_button_frame_padding)
  private val buttonSpacing = dimenPx(R.dimen.md_action_button_spacing)

  private var stackButtons: Boolean = false
    set(value) {
      if (field == value) return
      field = value
      requestLayout()
    }

  lateinit var actionButtons: Array<DialogActionButton>
  val visibleButtons: Array<DialogActionButton>
    get() = actionButtons.filter { it.isVisible() }
        .toTypedArray()

  override fun onFinishInflate() {
    super.onFinishInflate()
    actionButtons = arrayOf(
        findViewById(R.id.md_button_positive),
        findViewById(R.id.md_button_negative),
        findViewById(R.id.md_button_neutral)
    )
  }

  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    val parentWidth = MeasureSpec.getSize(widthMeasureSpec)

    // Buttons plus any spacing around that makes up the "frame"
    for (button in visibleButtons) {
      button.update(theme, stackButtons)
      if (stackButtons) {
        button.measure(
            MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(buttonHeightStacked, MeasureSpec.EXACTLY)
        )
      } else {
        button.measure(
            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
            MeasureSpec.makeMeasureSpec(buttonHeightDefault, MeasureSpec.EXACTLY)
        )
      }
    }

    if (visibleButtons.isNotEmpty() && !stackButtons) {
      var totalWidth = 0
      for (button in visibleButtons) {
        totalWidth += button.measuredWidth + buttonSpacing
      }
      if (totalWidth >= parentWidth) {
        stackButtons = true
        requestLayout()
      }
    }

    setMeasuredDimension(parentWidth, requiredHeight())
  }

  override fun onLayout(
    changed: Boolean,
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
  ) {
    if (stackButtons) {
      var topY = measuredHeight - requiredHeight()
      for (button in visibleButtons) {
        val bottomY = topY + buttonHeightStacked
        button.layout(0, topY, measuredWidth, bottomY)
        topY = bottomY
      }
    } else {
      var rightX = measuredWidth - buttonFramePadding
      val topY = measuredHeight - (requiredHeight() - buttonFramePadding)
      val bottomY = measuredHeight - buttonFramePadding
      for (button in visibleButtons) {
        val leftX = rightX - button.measuredWidth
        button.layout(leftX, topY, rightX, bottomY)
        rightX = leftX - buttonSpacing
      }
    }
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    if (dialogParent().debugMode) {
      canvas.drawColor(Color.parseColor("#400000cc"))
    }

    if (drawDivider) {
      canvas.drawLine(
          0f,
          0f,
          measuredWidth.toFloat(),
          dividerHeight.toFloat(),
          dividerPaint()
      )
    }
  }

  private fun requiredHeight() = when {
    visibleButtons.isEmpty() -> 0
    stackButtons -> (visibleButtons.size * buttonHeightStacked) + buttonFramePadding
    else -> buttonHeightDefault + (buttonFramePadding * 2)
  }
}