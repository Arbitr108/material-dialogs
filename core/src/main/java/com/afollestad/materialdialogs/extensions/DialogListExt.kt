/*
 * Licensed under Apache-2.0
 *
 * Designed an developed by Aidan Follestad (afollestad)
 */

package com.afollestad.materialdialogs.extensions

import android.support.annotation.ArrayRes
import android.support.annotation.CheckResult
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.R
import com.afollestad.materialdialogs.assertOneSet
import com.afollestad.materialdialogs.internal.list.MDListAdapter
import com.afollestad.materialdialogs.internal.list.MDMultiChoiceAdapter
import com.afollestad.materialdialogs.internal.list.MDSingleChoiceAdapter

private fun MaterialDialog.addContentRecyclerView() {
  if (this.contentRecyclerView != null) {
    return
  }
  this.contentRecyclerView = inflate(
      context, R.layout.md_dialog_stub_recyclerview, this.view
  )
  this.contentRecyclerView!!.rootView = this.view
  this.contentRecyclerView!!.layoutManager =
      LinearLayoutManager(context)
  this.view.addView(this.contentRecyclerView, 1)
}

internal fun MaterialDialog.getItemSelector() =
  getDrawable(context = baseContext, attr = R.attr.md_item_selector)

@CheckResult
fun MaterialDialog.getListAdapter(): RecyclerView.Adapter<*> {
  if (this.contentRecyclerView == null ||
      this.contentRecyclerView!!.adapter == null
  ) {
    throw IllegalStateException("This dialog is not currently a list dialog.")
  }
  return this.contentRecyclerView!!.adapter!!
}

@CheckResult
fun MaterialDialog.customListAdapter(
  adapter: RecyclerView.Adapter<*>
): MaterialDialog {
  addContentRecyclerView()
  if (this.contentRecyclerView!!.adapter != null)
    throw IllegalStateException("An adapter has already been set to this dialog.")
  this.contentRecyclerView!!.adapter = adapter
  return this
}

@CheckResult
fun MaterialDialog.listItems(
  @ArrayRes arrayRes: Int? = null,
  array: Array<CharSequence>? = null,
  click: ((dialog: MaterialDialog, index: Int, text: CharSequence) -> (Unit))? = null
): MaterialDialog {
  assertOneSet(arrayRes, array)
  val items = array ?: getStringArray(arrayRes)
  return customListAdapter(MDListAdapter(this, items, click))
}

@CheckResult
fun MaterialDialog.listItemsSingleChoice(
  @ArrayRes arrayRes: Int? = null,
  array: Array<CharSequence>? = null,
  initialSelection: Int = -1,
  selectionChanged: ((MaterialDialog, Int, CharSequence) -> (Boolean))? = null
): MaterialDialog {
  assertOneSet(arrayRes, array)
  val items = array ?: getStringArray(arrayRes)
  return customListAdapter(
      MDSingleChoiceAdapter(
          this, items, initialSelection, selectionChanged
      )
  )
}

@CheckResult
fun MaterialDialog.listItemsMultiChoice(
  @ArrayRes arrayRes: Int? = null,
  array: Array<CharSequence>? = null,
  initialSelection: Array<Int> = emptyArray(),
  selectionChanged: ((MaterialDialog, Array<Int>, Array<CharSequence>) -> (Boolean))? = null
): MaterialDialog {
  assertOneSet(arrayRes, array)
  val items = array ?: getStringArray(arrayRes)
  return customListAdapter(
      MDMultiChoiceAdapter(
          this, items, initialSelection, selectionChanged
      )
  )
}