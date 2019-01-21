package fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import navigations.MainNavigation
import navigations.NavigationType
import ru.makdyff.rocketbank.R
import views.CustomImageView

class ImagesFragment : FragmentBase<NavigationType, MainNavigation>() {
    private var _view: View? = null
    private var _cusImgView1: CustomImageView? = null
    private var _cusImgView2: CustomImageView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _view = inflater?.inflate(R.layout.fragment_images, container, false)

        _cusImgView1 = _view?.findViewById(R.id.fragment_images_img1)
        _cusImgView2 = _view?.findViewById(R.id.fragment_images_img2)

        _cusImgView1?.model?.mass = navigation.activity.mass
        _cusImgView2?.model?.mass = navigation.activity.mass

        return _view
    }

    override fun onStart() {
        super.onStart()

        _cusImgView1?.notifyDataSetChanged()
        _cusImgView2?.notifyDataSetChanged()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        // Проверяем ориентацию экрана
        if (newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(activity, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(activity, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}