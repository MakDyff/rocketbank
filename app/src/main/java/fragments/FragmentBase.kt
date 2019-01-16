package fragments

import android.app.Fragment
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import navigations.NavigationBase
import java.util.concurrent.Executors
import java.util.concurrent.Future

/**
 * Created by Max on 16.01.19.
 */
open class FragmentBase<TEnum : Enum<TEnum>, TNavigation : NavigationBase<TEnum>>() : Fragment() {
    private lateinit var _progress: ProgressDialog
    private var _navigation: TNavigation? = null

    val pool = Executors.newCachedThreadPool()
    var navigation: TNavigation
        get() = _navigation as TNavigation
        set(value) {
            if(_navigation == null)
                _navigation = value
        }
    open fun activityEvent(parameter: Any? = null) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _progress = ProgressDialog(activity)
        _progress.setCanceledOnTouchOutside(false)
    }

    override fun onStart() {
        super.onStart()

    }

    fun progressShow(message: String = "Ожидайте...") {
        _progress.setMessage(message)
        _progress.show()
    }

    fun progressDismiss() {
        _progress.dismiss()
    }

    fun requestAsync(action: () -> Unit, post: (isException: Boolean) -> Unit) : Future<*>{
        return pool.submit {
            var isException = true
            try {
                action.invoke()
                isException = false
            } catch(ex: Exception) {
                ex.printStackTrace()
            }
            activity.runOnUiThread {
                post.invoke(isException)
            }
        }
    }

    fun <T> requestAsync2(action: () -> T, post: (isException: Boolean) -> Unit) : Future<T>{
        return pool.submit<T> {
            var isException = true
            var value: T? = null
            try {
                value = action.invoke()
                isException = false
            } catch(ex: Exception) {
                ex.printStackTrace()
            }
            post.invoke(isException)
            value
        }
    }

    protected fun hideKeyboard(view: View?) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}