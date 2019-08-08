package pontini.systems.state

sealed class StateDetailsMash {

    object InitScreen: StateDetailsMash()
    object LoadMash: StateDetailsMash()
    object Error: StateDetailsMash()
    object WithResult :StateDetailsMash()
    object ResultEmptyMashStep :StateDetailsMash()
    object DeleteSucess :StateDetailsMash()
}