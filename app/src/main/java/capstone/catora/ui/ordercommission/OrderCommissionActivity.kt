package capstone.catora.ui.ordercommission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import capstone.catora.R

class OrderCommissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_commission)

        val userid = intent.getStringExtra(USERID)
    }

    companion object {
        private const val USERID = "userid"
    }
}