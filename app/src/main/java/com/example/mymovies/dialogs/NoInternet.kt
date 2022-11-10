import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import com.example.mymovies.R

class NoInternet(context: Context, themeResId: Int) : Dialog(context, themeResId) {
    lateinit var title: String
    lateinit var message: String
    var onCloseClick: View.OnClickListener? = null

    constructor(@NonNull context: Context, themeResId: Int, title: String, message: String) : this(
        context, themeResId
    ) {
        this.title = title
        this.message = message
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_no_internet)

        val txtTitle = findViewById<TextView>(R.id.txtTitle)
        val txtMessage = findViewById<TextView>(R.id.txtMessage)
        val imgClose = findViewById<ImageView>(R.id.imgDelete)

        imgClose.setOnClickListener(View.OnClickListener {
            onCloseClick?.onClick(it)
        })

        txtTitle.text = title
        txtMessage.text = message
    }

    fun onCloseClicked(onClickListener: View.OnClickListener) {
        this.onCloseClick = onClickListener
    }
}