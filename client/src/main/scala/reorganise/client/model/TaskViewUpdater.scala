package reorganise.client.model

import diode.{ActionHandler, ModelRW, Effect}
import reorganise.shared.model.TasksView
import scala.concurrent.ExecutionContext.Implicits.global

class TaskViewUpdater[Model] (modelRW: ModelRW[Model, TasksView]) extends ActionHandler (modelRW) {
  def handle = {
    case ChangeView (newView) =>
      updated (newView, Effect.action (ReloadVisibleTasksFromServer))
  }
}
