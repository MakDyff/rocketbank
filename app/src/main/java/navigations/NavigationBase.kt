package navigations

import android.widget.RelativeLayout
import ru.makdyff.rocketbank.FragmentActivityBase
import ru.makdyff.rocketbank.R
import java.util.*
import java.util.concurrent.Executors
import fragments.*

/**
 * Created by Max on 16.01.19.
 */
open class NavigationBase<T : Enum<T>>(val fragmentActivityBase: FragmentActivityBase, private val viewContainerId: Int, baseType: T) {
    private var _currentType: T = baseType
    private var fragments: RelativeLayout? = null
    private val _stackList: MutableList<T> = mutableListOf()
    protected val baseTypes = arrayListOf(baseType)
    protected val penultTypes: ArrayList<T> = arrayListOf()
    protected val pool = Executors.newCachedThreadPool()
    private var fragment = FragmentBase<T, NavigationBase<T>>()

    var currentType: T = baseType
        get() = _currentType

    val currentFragment: FragmentBase<T, NavigationBase<T>>
        get() = fragment

    private fun addFragment(fragment: FragmentBase<T, NavigationBase<T>>?, name: String) {
        fragmentActivityBase.runOnUiThread {
            val tran = fragmentActivityBase.fragmentManager.beginTransaction()

            tran.setCustomAnimations(
                R.animator.slide_up,
                    R.animator.slide_down,
                    R.animator.slide_up,
                    R.animator.slide_down)

            tran.add(viewContainerId, fragment, name)
            tran.addToBackStack(null).commitAllowingStateLoss()
        }
    }

    private fun backFragment(name: String) {
        if (fragments == null)
            fragments = fragmentActivityBase.findViewById(viewContainerId)

        fragmentActivityBase.fragmentManager.popBackStack()
    }

    open fun start() {
        if(!_stackList.contains(_currentType)) {
            _stackList.add(_currentType)
            fragmentChange(_currentType)
        }
    }

    open fun nextClick(type: T) {
        synchronized(this, {
            if (_currentType == type)
                return

            _currentType = type
            if (baseTypes.contains(_currentType))
                _stackList.clear()
            else if(penultTypes.contains(_currentType)) {
                val first = _stackList.firstOrNull()
                if(first!= null) {
                    _stackList.clear()
                    _stackList.add(first)
                }
            }

            _stackList.add(type)
            fragmentChange(type)
        })
    }

    open fun backClick() {
        synchronized(this, {
            if (_stackList.size > 1) {
                _stackList.removeAt(_stackList.size - 1)
                _currentType = _stackList.last()
                fragmentChange(_stackList.last())
            } else
                onLastFragment()
        })
    }

    private fun fragmentChange(type: T) {
        backFragment("")
        fragment = getFragment(type)
        fragment.navigation = this
        addFragment(fragment, type.name)
    }

    protected open fun getFragment(type: T): FragmentBase<T, NavigationBase<T>> = currentFragment

    /**
     * Событие "последний элемент"
     */
    protected open fun onLastFragment() {
    }
}