package pontinisistemas.doctorbrewer.enuns

import pontini.systems.R

enum class EnumBoilUseIn(val code: Int, val idName:Int) {
    FW(1, R.string.first_wort),
    BOIL(2,R.string.boil),
    DH(3,R.string.dry_hop),
    WHIRLPOOL(4,R.string.whirlpool);

    companion object {
        private val map = EnumBoilUseIn.values().associateBy(EnumBoilUseIn::code)
        fun fromInt(type: Int) = map[type]
    }
}