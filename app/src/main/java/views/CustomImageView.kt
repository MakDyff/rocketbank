package views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import models.ImageModel
import ru.makdyff.rocketbank.R
import android.graphics.*
import android.graphics.Bitmap
import android.widget.*
import models.RombModel
import java.util.concurrent.Future


class CustomImageView : ViewBase {

    private var _model = ImageModel(Array(0) { Array(0) { i -> i } })
    private val _img: ImageView
    private val _bar: SeekBar
    private val _methods: Spinner
    private var _future: Future<*>? = null
    private var _bitmap: Bitmap? = null

    var model: ImageModel
        get() = _model
        set(value) {
            _model = value
        }

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    init {
        LinearLayout.inflate(context, R.layout.view_image, this)
        _img = findViewById(R.id.view_image_id)
        _bar = findViewById(R.id.view_image_bar)
        _methods = findViewById(R.id.view_image_methods)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        _img.setOnTouchListener { v, event ->
            val inverse = Matrix()
            (v as ImageView).imageMatrix.invert(inverse)
            val touchPoint = floatArrayOf(event.x, event.y)
            inverse.mapPoints(touchPoint)
            val xCoord = touchPoint[0].toInt()
            val yCoord = touchPoint[1].toInt()

            Toast.makeText(context, "x ${xCoord} y ${yCoord}", Toast.LENGTH_LONG).show()

            start(xCoord, yCoord)

            true
        }
    }

    /*
    Выбор типа раскраки и начало выполнения
     */
    private fun start(x: Int, y: Int) {
//        _future?.cancel(false)

        _future = pool.submit {
            val method =  _methods.selectedItemPosition
            when(method) {
                0 -> horz(_model.mass[x][y], x, y)
                1 -> vert(_model.mass[x][y], x, y)
                2 -> romb(_model.mass[x][y], x, y)
                else -> {

                }
            }

            post {
                Toast.makeText(context, "Finish", Toast.LENGTH_LONG).show()
            }
        }
    }

    /*
     * Горизонтальный метод
     */
    private fun horz(v: Int, x: Int, y: Int) {
        if (x >= 0
            && _model.mass.size > x
            && y >= 0
            && _model.mass[x].size > y
            && _model.mass[x][y] == v
        ) {
            _model.mass[x][y] = 2
            timeChange(x,y)

            horz(v, x - 1, y)
            horz(v, x + 1, y)
            horz(v, x, y - 1)
            horz(v, x, y + 1)
        }
    }

    /*
     * Вертикальный метод
     */
    private fun vert(v: Int, x: Int, y: Int) {
        if (x >= 0
            && _model.mass.size > x
            && y >= 0
            && _model.mass[x].size > y
            && _model.mass[x][y] == v
        ) {
            _model.mass[x][y] = 2
            timeChange(x,y)

            vert(v, x, y - 1)
            vert(v, x, y + 1)
            vert(v, x - 1, y)
            vert(v, x + 1, y)
        }
    }

    /*
    Ромб
     */
    private fun romb(v: Int, xC: Int, yC: Int) {
        val check = arrayListOf(RombModel(xC,yC))

        while (check.size != 0) {
            val vv = check[0]
            if (vv.x >= 0
                && _model.mass.size > vv.x
                && vv.y >= 0
                && _model.mass[vv.x].size > vv.y
                && _model.mass[vv.x][vv.y] == v
            ) {
                _model.mass[vv.x][vv.y] = 2
                timeChange(vv.x, vv.y)

                check.addAll(arrayOf(
                    RombModel(vv.x-1,vv.y),
                    RombModel(vv.x+1,vv.y),
                    RombModel(vv.x,vv.y-1),
                    RombModel(vv.x,vv.y+1)))
            }

            check.remove(vv)
        }
    }

    private fun timeChange(x: Int, y: Int) {
        _bitmap?.setPixel(x, y, Color.RED);
        post{
            _img.setImageBitmap(_bitmap)
        }
        val time = _bar.progress.toLong()
        Thread.sleep(time)
    }

    override fun notifyDataSetChanged() {
        post {
            _bitmap = Bitmap.createBitmap(
                _model.mass.size, _model.mass[0].size,
                Bitmap.Config.ARGB_8888
            )

            var x = -1
            for (m in _model.mass) {
                x++
                var y = -1
                for (c in m) {
                    y++
                    if (c == 2) {
                        _bitmap?.setPixel(x, y, Color.RED);
                    } else if (c == 1) {
                        _bitmap?.setPixel(x, y, Color.BLACK);
                    } else {
                        _bitmap?.setPixel(x, y, Color.WHITE);
                    }
                }
            }

            _img.setImageBitmap(_bitmap)
        }
    }

}