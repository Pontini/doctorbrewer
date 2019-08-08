package pontini.systems.state

sealed class StateInsertOrUpdateMashScreen {

    object InitScreen: StateInsertOrUpdateMashScreen()
    object LoadMash: StateInsertOrUpdateMashScreen()
    object Error: StateInsertOrUpdateMashScreen()
    object WithResult : StateInsertOrUpdateMashScreen()
    object ResultEmpty : StateInsertOrUpdateMashScreen()
    object Insert : StateInsertOrUpdateMashScreen()
    object InsertSucess : StateInsertOrUpdateMashScreen()
    object Update : StateInsertOrUpdateMashScreen()
    object UpdateSucess : StateInsertOrUpdateMashScreen()
    object FieldEmpty : StateInsertOrUpdateMashScreen()
    object MashStepInsertOrUpdateSucess : StateInsertOrUpdateMashScreen()

}