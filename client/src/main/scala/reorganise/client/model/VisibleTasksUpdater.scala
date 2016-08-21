package reorganise.client.model

import diode.data.{Pot, Ready}
import diode.{ModelR, ActionHandler, Effect, ModelRW}
import reorganise.client.comms.ServerCaller._
import reorganise.shared.model.{VisibleTasks, TasksView}
import scala.concurrent.ExecutionContext.Implicits.global

class VisibleTasksUpdater[Model] (visible: ModelRW[Model, Pot[VisibleTasks]], view: ModelR[Model, TasksView])
  extends ActionHandler (visible) {

  def handle = {
    case RefreshClientShownTasks (data: VisibleTasks) =>
      updated (Ready (data))
    case CreateTask =>
      effectOnly (Effect (createTask (view.value).map (RefreshClientShownTasks)))
    case UpdateTask (task) =>
      updated (value.map (_.updated (task)),
        Effect (updateTaskOnServer (task, view.value).map (RefreshClientShownTasks)))
    case DeleteTask (task) =>
      updated (value.map (_.remove (task)),
        Effect (deleteTaskFromServer (task.id, view.value).map (RefreshClientShownTasks)))
    case ReloadVisibleTasksFromServer =>
      effectOnly (Effect (loadTasksFromServer (view.value).map (RefreshClientShownTasks)))
  }
}
