package reorganise.client.model

import diode.data.{Pot, Ready}
import diode.{ActionHandler, Effect, ModelRW}
import reorganise.client.comms.ServerCaller._
import reorganise.shared.model.VisibleTasks
import scala.concurrent.ExecutionContext.Implicits.global

class VisibleTasksUpdater[Model] (visible: ModelRW[Model, Pot[VisibleTasks]]) extends ActionHandler (visible) {
  def handle = {
    case RefreshClientShownTasks (data: VisibleTasks) =>
      updated (Ready (data))
    case CreateTask =>
      effectOnly (Effect (createTask.map (RefreshClientShownTasks)))
    case CreateList =>
      effectOnly (Effect (createList.map (RefreshClientShownTasks)))
    case UpdateTask (task) =>
      updated (value.map (_.updatedTask (task)), Effect (updateTaskOnServer (task).map (RefreshClientShownTasks)))
    case UpdateList (list) =>
      updated (value.map (_.updatedList (list)), Effect (updateListOnServer (list).map (RefreshClientShownTasks)))
    case DeleteTask (task) =>
      updated (value.map (_.removeTask (task)), Effect (deleteTaskFromServer (task.id).map (RefreshClientShownTasks)))
    case ReloadVisibleTasksFromServer =>
      effectOnly (Effect (loadTasksFromServer.map (RefreshClientShownTasks)))
  }
}
