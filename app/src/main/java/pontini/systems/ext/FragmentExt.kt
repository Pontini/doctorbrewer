package pontinisistemas.doctorbrewer.base.ext

import androidx.fragment.app.Fragment
import org.jetbrains.anko.support.v4.toast


fun Fragment.showIrreversibleFailure(mensagem: String) {
    toast(mensagem)

}


