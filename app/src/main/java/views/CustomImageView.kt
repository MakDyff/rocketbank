package views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import models.ImageModel
import android.widget.ImageView
import ru.makdyff.rocketbank.R
import android.graphics.*
import android.graphics.Bitmap
import android.widget.Toast
import android.R.attr.y
import android.R.attr.x
import android.R.attr.bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable





class CustomImageView: ViewBase {

    private var _model = ImageModel(Array(0){Array(0) { i -> i} })
    private val _img: ImageView

    var model: ImageModel
        get() = _model
        set(value) { _model = value }

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { }

    init {
        LinearLayout.inflate(context, R.layout.view_image, this)
        _img = findViewById(ru.makdyff.rocketbank.R.id.view_image_id)



        _img.setOnTouchListener { v, event ->
            val imgDrawable = (v as ImageView).drawable
            //imgDrawable will not be null if you had set src to ImageView, in case of background drawable it will be null
            val bitmap = (imgDrawable as BitmapDrawable).bitmap

            val inverse = Matrix()
            (v as ImageView).imageMatrix.invert(inverse)
            val touchPoint = floatArrayOf(event.x, event.y)
            inverse.mapPoints(touchPoint)
            val xCoord = touchPoint[0].toInt()
            val yCoord = touchPoint[1].toInt()

            val touchedRGB = bitmap.getPixel(xCoord, yCoord)

            Toast.makeText(context, "x ${xCoord} y ${yCoord}", Toast.LENGTH_LONG).show()

            true
        }
    }

    override fun notifyDataSetChanged() {
        post {
            val bitmap = Bitmap.createBitmap(
                _model.mass.size, _model.mass[0].size,
                Bitmap.Config.ARGB_8888
            )
            _img.setImageBitmap(bitmap)

            var x = -1
            for(m in _model.mass) {
                x++
                var y = -1
                for(c in m) {
                   y++
                    if(c == 2) {
                        bitmap?.setPixel(x, y, Color.RED);
                    } else if(c == 1) {
                        bitmap?.setPixel(x, y, Color.BLACK);
                    } else {
                        bitmap?.setPixel(x, y, Color.WHITE);
                    }
                }
            }
        }
    }

}