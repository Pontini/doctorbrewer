package pontinisistemas.doctorbrewer.enuns

import pontini.systems.R


enum class EnumMashStepType(val code: Int, val idName:Int) {
    INFUSION(1, R.string.infusion),
    DECOCTION(2,R.string.decoction),
    TEMPERATURE(3,R.string.temperature);

    companion object {
        private val map = EnumMashStepType.values().associateBy(EnumMashStepType::code)
        fun fromInt(type: Int) = map[type]
    }
}