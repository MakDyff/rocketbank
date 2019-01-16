package views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import java.util.concurrent.Executors

abstract class ViewBase : LinearLayout, IViewData {
    protected val pool = Executors.newCachedThreadPool()

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

}