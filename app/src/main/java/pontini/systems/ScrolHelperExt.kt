package pontini.systems

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton


fun disableScroll(toolbar: Toolbar) {
    val params = toolbar.layoutParams as AppBarLayout.LayoutParams
    params.scrollFlags = 0
}

fun enableScroll(toolbar: Toolbar) {
    val params = toolbar.layoutParams as AppBarLayout.LayoutParams
    params.scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
}

fun addOnScrollListenerWithFab(recycler: RecyclerView, fab: FloatingActionButton) {
    recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy > 0) {
                fab.hide()
            } else {
                fab.show()
            }

            super.onScrolled(recyclerView, dx, dy)
        }
    })

}
