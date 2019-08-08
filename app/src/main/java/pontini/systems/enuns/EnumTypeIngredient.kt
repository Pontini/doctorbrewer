package pontinisistemas.doctorbrewer.enuns

import pontini.systems.R
import pontini.systems.dialogs.SelectOptionItem

enum class EnumTypeIngredient(val id: Int) : SelectOptionItem {
    YEAST(0), HOP(1), MALT(2), WATER(3), OTHER(4);

    companion object {
        private val map = EnumTypeIngredient.values().associateBy(EnumTypeIngredient::id)
        fun fromInt(type: Int) = map[type]
    }

    override fun getName(): Int {
        if (id == YEAST.id) {
            return R.string.yeast
        } else if (id == HOP.id) {
            return R.string.hop
        } else if (id == MALT.id) {
            return R.string.malt
        } else if (id == WATER.id) {
            return R.string.water
        } else if (id == OTHER.id) {
            return R.string.others
        }

        return R.string.no_records_found
    }
}