package fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import navigations.MainNavigation
import navigations.NavigationType
import ru.makdyff.rocketbank.R

class GenerateFragment : FragmentBase<NavigationType, MainNavigation>() {
    private lateinit var hSizeEdit: EditText
    private lateinit var wSizeEdit: EditText

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater?.inflate(R.layout.fragment_generate, container, false)

        if (view != null) {
            hSizeEdit = view.findViewById(R.id.fragment_generate_h)
            wSizeEdit = view.findViewById(R.id.fragment_generate_w)

            val b = view.findViewById<Button>(R.id.fragment_generate_button)
            b.setOnClickListener {
                if(hSizeEdit.text.isNotEmpty())
                    navigation.activity.hSize = hSizeEdit.text.toString().toInt()

                if( wSizeEdit.text.isNotEmpty())
                    navigation.activity.wSize = wSizeEdit.text.toString().toInt()

                navigation.nextClick(NavigationType.Images)
            }
        }

        return view
    }
}