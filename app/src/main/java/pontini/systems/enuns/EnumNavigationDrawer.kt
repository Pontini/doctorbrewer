package pontinisistemas.doctorbrewer.enuns

enum class EnumNavigationDrawer(val nro: Int) {
    RECIPE(1),
    INGREDIENT(2),
    BREWING(3),
    UTILITY(4),
    SETTINGS(5),
    PROFILES(6),
    MASH(7);

    companion object {
        private val map = EnumNavigationDrawer.values().associateBy(EnumNavigationDrawer::nro)
        fun fromInt(type: Int) = map[type]
    }
}