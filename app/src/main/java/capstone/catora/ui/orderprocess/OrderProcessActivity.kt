package capstone.catora.ui.orderprocess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import capstone.catora.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OrderProcessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oder_process)

        val orderSectionsPagerAdapter = OrderSectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = orderSectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

//        supportActionBar?.elevation = 0f
        setSupportActionBar(findViewById(R.id.toolbar_order_process))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "order"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.send_order,
            R.string.have_order
        )
    }
}