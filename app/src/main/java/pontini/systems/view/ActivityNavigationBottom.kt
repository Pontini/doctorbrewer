package pontini.systems.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.act_navigation_bottom.*
import pontini.systems.R

class ActivityNavigationBottom : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_mash -> {

                var transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(
                    R.anim.enter_from_left,
                    R.anim.exit_to_right,
                    R.anim.enter_from_right,
                    R.anim.exit_to_left
                )
                transaction.replace(R.id.container, FragmentListMash())
                transaction.commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_boil -> {
                var transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(
                    R.anim.enter_from_left,
                    R.anim.exit_to_right,
                    R.anim.enter_from_right,
                    R.anim.exit_to_left)
                transaction.replace(R.id.container, FragmentListBoil())
                transaction.commit()
              //  startActivity(Intent(this,ActivityDetailsMash::class.java))

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_fermentation -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_navigation_bottom)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


        var transaction = supportFragmentManager.beginTransaction();
        transaction.setCustomAnimations(
            R.anim.enter_from_left,R.anim.exit_to_right,
            R.anim.enter_from_right,R.anim.exit_to_left)

        transaction.replace(R.id.container, FragmentListMash())
        transaction.commit()
        navigation.selectedItemId = R.id.navigation_mash
    }
}
