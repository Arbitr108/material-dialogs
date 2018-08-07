package com.afollestad.materialdialogssample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.extensions.checkBoxPrompt
import com.afollestad.materialdialogs.extensions.listItems
import com.afollestad.materialdialogs.extensions.listItemsMultiChoice
import com.afollestad.materialdialogs.extensions.listItemsSingleChoice
import kotlinx.android.synthetic.main.activity_main.basic
import kotlinx.android.synthetic.main.activity_main.basic_buttons
import kotlinx.android.synthetic.main.activity_main.basic_checkbox
import kotlinx.android.synthetic.main.activity_main.basic_checkbox_buttons
import kotlinx.android.synthetic.main.activity_main.basic_checkbox_titled
import kotlinx.android.synthetic.main.activity_main.basic_checkbox_titled_buttons
import kotlinx.android.synthetic.main.activity_main.basic_icon
import kotlinx.android.synthetic.main.activity_main.basic_long
import kotlinx.android.synthetic.main.activity_main.basic_long_buttons
import kotlinx.android.synthetic.main.activity_main.basic_long_titled
import kotlinx.android.synthetic.main.activity_main.basic_long_titled_buttons
import kotlinx.android.synthetic.main.activity_main.basic_titled
import kotlinx.android.synthetic.main.activity_main.basic_titled_buttons
import kotlinx.android.synthetic.main.activity_main.buttons_callbacks
import kotlinx.android.synthetic.main.activity_main.buttons_neutral
import kotlinx.android.synthetic.main.activity_main.buttons_stacked
import kotlinx.android.synthetic.main.activity_main.buttons_stacked_checkboxPrompt
import kotlinx.android.synthetic.main.activity_main.list
import kotlinx.android.synthetic.main.activity_main.list_buttons
import kotlinx.android.synthetic.main.activity_main.list_checkPrompt
import kotlinx.android.synthetic.main.activity_main.list_checkPrompt_buttons
import kotlinx.android.synthetic.main.activity_main.list_long
import kotlinx.android.synthetic.main.activity_main.list_long_buttons
import kotlinx.android.synthetic.main.activity_main.list_long_items
import kotlinx.android.synthetic.main.activity_main.list_long_items_buttons
import kotlinx.android.synthetic.main.activity_main.list_long_items_titled
import kotlinx.android.synthetic.main.activity_main.list_long_items_titled_buttons
import kotlinx.android.synthetic.main.activity_main.list_long_titled
import kotlinx.android.synthetic.main.activity_main.list_long_titled_buttons
import kotlinx.android.synthetic.main.activity_main.list_titled
import kotlinx.android.synthetic.main.activity_main.list_titled_buttons
import kotlinx.android.synthetic.main.activity_main.misc_dialog_callbacks
import kotlinx.android.synthetic.main.activity_main.multiple_choice
import kotlinx.android.synthetic.main.activity_main.multiple_choice_buttons
import kotlinx.android.synthetic.main.activity_main.multiple_choice_disabled_items
import kotlinx.android.synthetic.main.activity_main.multiple_choice_limit
import kotlinx.android.synthetic.main.activity_main.multiple_choice_long_items
import kotlinx.android.synthetic.main.activity_main.multiple_choice_min
import kotlinx.android.synthetic.main.activity_main.single_choice
import kotlinx.android.synthetic.main.activity_main.single_choice_buttons
import kotlinx.android.synthetic.main.activity_main.single_choice_buttons_titled
import kotlinx.android.synthetic.main.activity_main.single_choice_disabled_items
import kotlinx.android.synthetic.main.activity_main.single_choice_long_items
import kotlinx.android.synthetic.main.activity_main.single_choice_titled

/** @author Aidan Follestad (afollestad) */
class MainActivity : AppCompatActivity() {

  private val debugMode = true

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    basic.setOnClickListener {
      MaterialDialog(this).show {
        message(textRes = R.string.shareLocationPrompt)
        debugMode(debugMode)
      }
    }

    basic_titled.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.useGoogleLocationServices)
        message(textRes = R.string.useGoogleLocationServicesPrompt)
        debugMode(debugMode)
      }
    }

    basic_buttons.setOnClickListener {
      MaterialDialog(this).show {
        message(textRes = R.string.useGoogleLocationServicesPrompt)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        debugMode(debugMode)
      }
    }

    basic_titled_buttons.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.useGoogleLocationServices)
        message(textRes = R.string.useGoogleLocationServicesPrompt)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        debugMode(debugMode)
      }
    }

    basic_long.setOnClickListener {
      MaterialDialog(this).show {
        message(textRes = R.string.loremIpsum)
        debugMode(debugMode)
      }
    }

    basic_long_titled.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.useGoogleLocationServices)
        message(textRes = R.string.loremIpsum)
        debugMode(debugMode)
      }
    }

    basic_long_buttons.setOnClickListener {
      MaterialDialog(this).show {
        message(textRes = R.string.loremIpsum)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        debugMode(debugMode)
      }
    }

    basic_long_titled_buttons.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.useGoogleLocationServices)
        message(textRes = R.string.loremIpsum)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        debugMode(debugMode)
      }
    }

    basic_icon.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.useGoogleLocationServices)
        icon(iconRes = R.mipmap.ic_launcher)
        message(textRes = R.string.loremIpsum)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        debugMode(debugMode)
      }
    }

    basic_checkbox.setOnClickListener {
      MaterialDialog(this).show {
        message(textRes = R.string.loremIpsum)
        checkBoxPrompt(R.string.checkboxConfirm) {
          toast("Checked? $it")
        }
        debugMode(debugMode)
      }
    }

    basic_checkbox_buttons.setOnClickListener {
      MaterialDialog(this).show {
        message(textRes = R.string.loremIpsum)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        checkBoxPrompt(R.string.checkboxConfirm) {
          toast("Checked? $it")
        }
        debugMode(debugMode)
      }
    }

    basic_checkbox_titled.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.useGoogleLocationServices)
        message(textRes = R.string.loremIpsum)
        checkBoxPrompt(R.string.checkboxConfirm) {
          toast("Checked? $it")
        }
        debugMode(debugMode)
      }
    }

    basic_checkbox_titled_buttons.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.useGoogleLocationServices)
        message(textRes = R.string.loremIpsum)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        checkBoxPrompt(R.string.checkboxConfirm) {
          toast("Checked? $it")
        }
        debugMode(debugMode)
      }
    }

    list.setOnClickListener {
      MaterialDialog(this).show {
        listItems(arrayRes = R.array.socialNetworks) { _, index, text ->
          toast("Clicked item $text at index $index")
        }
        debugMode(debugMode)
      }
    }

    list_buttons.setOnClickListener {
      MaterialDialog(this).show {
        listItems(arrayRes = R.array.socialNetworks)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        debugMode(debugMode)
      }
    }

    list_titled.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.socialNetworks)
        listItems(arrayRes = R.array.socialNetworks)
        debugMode(debugMode)
      }
    }

    list_titled_buttons.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.socialNetworks)
        listItems(arrayRes = R.array.socialNetworks)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        debugMode(debugMode)
      }
    }

    list_long.setOnClickListener {
      MaterialDialog(this).show {
        listItems(arrayRes = R.array.states)
        debugMode(debugMode)
      }
    }

    list_long_buttons.setOnClickListener {
      MaterialDialog(this).show {
        listItems(arrayRes = R.array.states)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        debugMode(debugMode)
      }
    }

    list_long_titled.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.states)
        listItems(arrayRes = R.array.states)
        debugMode(debugMode)
      }
    }

    list_long_titled_buttons.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.states)
        listItems(arrayRes = R.array.states)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        debugMode(debugMode)
      }
    }

    list_long_items.setOnClickListener {
      MaterialDialog(this).show {
        listItems(arrayRes = R.array.socialNetworks_longItems)
        debugMode(debugMode)
      }
    }

    list_long_items_buttons.setOnClickListener {
      MaterialDialog(this).show {
        listItems(arrayRes = R.array.socialNetworks_longItems)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        debugMode(debugMode)
      }
    }

    list_long_items_titled.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.socialNetworks)
        listItems(arrayRes = R.array.socialNetworks_longItems)
        debugMode(debugMode)
      }
    }

    list_long_items_titled_buttons.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.socialNetworks)
        listItems(arrayRes = R.array.socialNetworks_longItems)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        debugMode(debugMode)
      }
    }

    list_checkPrompt.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.socialNetworks)
        listItems(arrayRes = R.array.socialNetworks_longItems)
        checkBoxPrompt(R.string.checkboxConfirm) {
          toast("Checked? $it")
        }
        debugMode(debugMode)
      }
    }

    list_checkPrompt_buttons.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.socialNetworks)
        listItems(arrayRes = R.array.socialNetworks_longItems)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        checkBoxPrompt(R.string.checkboxConfirm) {
          toast("Checked? $it")
        }
        debugMode(debugMode)
      }
    }

    single_choice.setOnClickListener {
      MaterialDialog(this).show {
        listItemsSingleChoice(arrayRes = R.array.socialNetworks, initialSelection = 1)
        debugMode(debugMode)
      }
    }

    single_choice_titled.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.socialNetworks)
        listItemsSingleChoice(arrayRes = R.array.socialNetworks, initialSelection = 1)
        debugMode(debugMode)
      }
    }

    single_choice_buttons.setOnClickListener {
      MaterialDialog(this).show {
        listItemsSingleChoice(arrayRes = R.array.socialNetworks, initialSelection = 2)
        positiveButton(R.string.choose)
        debugMode(debugMode)
      }
    }

    single_choice_buttons_titled.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.socialNetworks)
        listItemsSingleChoice(arrayRes = R.array.socialNetworks, initialSelection = 2)
        positiveButton(R.string.choose)
        debugMode(debugMode)
      }
    }

    single_choice_long_items.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.socialNetworks)
        listItemsSingleChoice(arrayRes = R.array.socialNetworks_longItems)
        positiveButton(R.string.choose)
        debugMode(debugMode)
      }
    }

    single_choice_disabled_items.setOnClickListener {
      // TODO
    }

    multiple_choice.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.socialNetworks)
        listItemsMultiChoice(arrayRes = R.array.socialNetworks, initialSelection = arrayOf(1, 3))
        debugMode(debugMode)
      }
    }

    multiple_choice_buttons.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.socialNetworks)
        listItemsMultiChoice(arrayRes = R.array.socialNetworks, initialSelection = arrayOf(1, 3))
        positiveButton(R.string.choose)
        debugMode(debugMode)
      }
    }

    multiple_choice_limit.setOnClickListener {
      // TODO
    }

    multiple_choice_min.setOnClickListener {
      // TODO
    }

    multiple_choice_long_items.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.socialNetworks)
        listItemsMultiChoice(arrayRes = R.array.socialNetworks_longItems)
        positiveButton(R.string.choose)
        debugMode(debugMode)
      }
    }

    multiple_choice_disabled_items.setOnClickListener {
      // TODO
    }

    buttons_stacked.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.useGoogleLocationServices)
        message(textRes = R.string.useGoogleLocationServicesPrompt)
        positiveButton(positiveText = "Hello World")
        negativeButton(negativeText = "How are you doing?")
        neutralButton(neutralText = "Testing long buttons")
        debugMode(debugMode)
      }
    }

    buttons_stacked_checkboxPrompt.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.useGoogleLocationServices)
        message(textRes = R.string.useGoogleLocationServicesPrompt)
        positiveButton(positiveText = "Hello World")
        negativeButton(negativeText = "How are you doing?")
        neutralButton(neutralText = "Testing long buttons")
        checkBoxPrompt(R.string.checkboxConfirm) {
          toast("Checked? $it")
        }
        debugMode(debugMode)
      }
    }

    buttons_neutral.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.useGoogleLocationServices)
        message(textRes = R.string.useGoogleLocationServicesPrompt)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        neutralButton(R.string.more_info)
        debugMode(debugMode)
      }
    }

    buttons_callbacks.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.useGoogleLocationServices)
        message(textRes = R.string.useGoogleLocationServicesPrompt)
        positiveButton(R.string.agree) {
          toast("On positive")
        }
        negativeButton(R.string.disagree) {
          toast("On negative")
        }
        neutralButton(R.string.more_info) {
          toast("On neutral")
        }
        debugMode(debugMode)
      }
    }

    misc_dialog_callbacks.setOnClickListener {
      MaterialDialog(this).show {
        title(textRes = R.string.useGoogleLocationServices)
        message(textRes = R.string.useGoogleLocationServicesPrompt)
        positiveButton(R.string.agree)
        negativeButton(R.string.disagree)
        onShow { toast("onShow") }
        onCancel { toast("onCancel") }
        onDismiss { toast("onDismiss") }
        debugMode(debugMode)
      }
    }
  }
}