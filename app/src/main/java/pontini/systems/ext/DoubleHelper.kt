package pontinisistemas.doctorbrewer.base.ext

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat

fun arredondarDouble(valor: Double, qtdCasasDecimais: Int): Double {
    var valorArredondado = BigDecimal(valor.toString())
    valorArredondado = valorArredondado.setScale(qtdCasasDecimais, BigDecimal.ROUND_HALF_UP)

    return valorArredondado.toDouble()
}

fun formatarDouble(valor: Double): String {
    return if (valor % 1 == 0.0) {
        valor.toLong().toString()
    } else {
        valor.toString()
    }
}

fun formatarValorMonetario(valor: Double): String {
    val format = NumberFormat.getCurrencyInstance()
    format.roundingMode = RoundingMode.HALF_UP

    return format.format(BigDecimal(valor.toString()))
}