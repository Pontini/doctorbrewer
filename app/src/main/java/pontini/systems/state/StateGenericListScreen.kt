package pontini.systems.state

sealed class StateGenericListScreen {

    object Init: StateGenericListScreen()
    object Load: StateGenericListScreen()
    object Error: StateGenericListScreen()
    object WithResult :StateGenericListScreen()
    object ResultEmpty :StateGenericListScreen()
}