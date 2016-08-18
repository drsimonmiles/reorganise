package reorganise.client.model

import diode.{ActionHandler, ModelRW, Effect}
import reorganise.client.comms.ServerCaller.loadTasksFromServer
import reorganise.shared.model.TasksView
import scala.concurrent.ExecutionContext.Implicits.global

class TaskViewUpdater[Model] (modelRW: ModelRW[Model, TasksView]) extends ActionHandler (modelRW) {
  def handle = {
    case ReloadVisibleTasksFromServer =>
      effectOnly (Effect (loadTasksFromServer (modelRW.value).map (RefreshClientShownTasks)))
  }
}
