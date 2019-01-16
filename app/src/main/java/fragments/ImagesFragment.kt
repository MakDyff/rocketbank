package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import navigations.MainNavigation
import navigations.NavigationType
import ru.makdyff.rocketbank.R

class ImagesFragment : FragmentBase<NavigationType, MainNavigation>() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_images, container, false)

        return view
    }
}