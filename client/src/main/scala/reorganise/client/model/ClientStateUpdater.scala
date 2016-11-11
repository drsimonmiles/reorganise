package reorganise.client.model

import diode.data.{Pot, Ready}
import diode.{NoAction, ActionHandler, Effect, ModelRW}
import reorganise.client.comms.ServerCaller._
import scala.concurrent.ExecutionContext.Implicits.global

class ClientStateUpdater[Model] (data: ModelRW[Model, Pot[ClientState]]) extends ActionHandler (data) {
  def handle = {
    case LoadAllVisibleDataFromServer =>
      effectOnly (Effect (loadTasksFromServer.map (RefreshClientData)))
    case RefreshTasksForView (newTasks) =>
      updated (value.map (data => data.copy (visible = data.visible.copy (tasks = newTasks))))
    case RefreshClientData (visible) =>
      updated (Ready (new ClientState (visible)))
    case CreateTask =>
      effectOnly (Effect (createTask.map (x => NoAction)))
    case CreateList (isDerived) =>
      effectOnly (Effect (createList (isDerived).map (x => NoAction)))
    case UpdateTask (task) =>
      updated (value.map (_.updatedTask (task)), Effect (updateTaskOnServer (task).map (x => NoAction)))
    case UpdateList (list) =>
      updated (value.map (_.updatedList (list)), Effect (updateListOnServer (list).map (x => NoAction)))
    case UpdateListOrder (order) =>
      updated (value.map (_.withListOrder (order)), Effect (updateListOrderOnServer (order).map (x => NoAction)))
    case DeleteTask (task) =>
      updated (value.map (_.removeTask (task)), Effect (deleteTaskFromServer (task.id).map (x => NoAction)))
    case DeleteList (listID) =>
      updated (value.map (_.removeList (listID)), Effect (deleteListFromServer (listID).map (x => NoAction)))
    case ChangeListFeature (newFeature) =>
      updated (value.map (_.copy (listFeature = newFeature)))
    case ChangeTaskFeature (newFeature) =>
      updated (value.map (_.copy (taskFeature = newFeature)))
    case ChangeView (newView) =>
      updated (value.map (_.copy (view = newView)), Effect (setView (newView).map (RefreshTasksForView)))
  }
}
