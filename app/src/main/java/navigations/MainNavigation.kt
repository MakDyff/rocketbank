package navigations

import ru.makdyff.rocketbank.MainActivity
import ru.makdyff.rocketbank.R
import fragments.*

class MainNavigation(fragmentActivityBase: MainActivity) :
    NavigationBase<NavigationType>(fragmentActivityBase, R.id.activity_main_container, NavigationType.Images) {

    val activity: MainActivity
        get() = fragmentActivityBase as MainActivity

    override fun getFragment(type: NavigationType): FragmentBase<NavigationType, NavigationBase<NavigationType>> {
        var fragment = FragmentBase<NavigationType, MainNavigation>()
        when (type) {
            NavigationType.Images -> {
                fragment = ImagesFragment()
            }
            else -> {}
        }

        return fragment as FragmentBase<NavigationType, NavigationBase<NavigationType>>
    }
}