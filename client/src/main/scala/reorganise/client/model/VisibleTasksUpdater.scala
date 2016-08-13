package reorganise.client.model

import diode.data.{Pot, Ready}
import diode.{ActionHandler, Effect, ModelRW}
import reorganise.client.comms.ServerCaller._
import scala.concurrent.ExecutionContext.Implicits.global

class VisibleTasksUpdater[Model] (modelRW: ModelRW[Model, Pot[VisibleTasks]]) extends ActionHandler (modelRW) {
  def handle = {
    case ReloadVisibleTasksFromServer =>
      effectOnly (Effect (loadTasksFromServer ().map (RefreshClientShownTasks)))
    case RefreshClientShownTasks (tasks) =>
      updated (Ready (VisibleTasks (tasks)))
    case UpdateTask (task) =>
      effectOnly (Effect (updateTaskOnServer (task).map (RefreshClientShownTasks)))
    case DeleteTask (task) =>
      updated (value.map (_.remove (task)), Effect (deleteTaskFromServer (task.id).map (RefreshClientShownTasks)))
  }
}
