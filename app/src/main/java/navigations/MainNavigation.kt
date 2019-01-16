package navigations

import ru.makdyff.rocketbank.MainActivity
import ru.makdyff.rocketbank.R
import fragments.*

class MainNavigation(fragmentActivityBase: MainActivity) :
    NavigationBase<NavigationType>(fragmentActivityBase, R.id.activity_main_container, NavigationType.Generate) {

    private val _hashMap = hashMapOf<NavigationType, FragmentBase<NavigationType, MainNavigation>>()

    val activity: MainActivity
        get() = fragmentActivityBase as MainActivity

    override fun getFragment(type: NavigationType): FragmentBase<NavigationType, NavigationBase<NavigationType>> {
        var fragment = FragmentBase<NavigationType, MainNavigation>()
        when (type) {
            NavigationType.Images -> {
                fragment = _hashMap[type]?: ImagesFragment()

            }
            NavigationType.Generate -> {
                fragment = _hashMap[type]?: GenerateFragment()
            }
            else -> {}
        }
        if(_hashMap[type] == null) _hashMap.put(type, fragment)
        return fragment as FragmentBase<NavigationType, NavigationBase<NavigationType>>
    }

    override fun onLastFragment() {
        activity.finish()
    }
}