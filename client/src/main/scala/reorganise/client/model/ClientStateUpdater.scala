package reorganise.client.model

import diode.data.{Pot, Ready}
import diode.{NoAction, ActionHandler, Effect, ModelRW}
import reorganise.client.comms.ServerCaller._
import scala.concurrent.ExecutionContext.Implicits.global

class ClientStateUpdater[Model] (data: ModelRW[Model, Pot[ClientState]]) extends ActionHandler (data) {
  def handle = {
    case LoadAllVisibleDataFromServer =>
      effectOnly (Effect (loadVisibleDataFromServer.map (RefreshClientData)))
    case RefreshTasksForView (newTasks) =>
      updated (value.map (data => data.copy (visible = data.visible.copy (tasks = newTasks))))
    case RefreshClientData (visible) =>
      value match {
        case Ready (existing) => updated (value.map (_.withVisible (visible)))
        case _ => updated (Ready (new ClientState (visible)))
      }
    case CreateTask =>
      effectOnly (Effect (createTask.map (RefreshClientData)))
    case CreateList (isDerived) =>
      effectOnly (Effect (createList (isDerived).map (RefreshClientData)))
    case UpdateTask (task) =>
      updated (value.map (_.updatedTask (task)), Effect (updateTaskOnServer (task).map (RefreshClientData)))
    case UpdateList (list) =>
      updated (value.map (_.updatedList (list)), Effect (updateListOnServer (list).map (RefreshClientData)))
    case UpdateListOrder (order) =>
      updated (value.map (_.withListOrder (order)), Effect (updateListOrderOnServer (order).map (RefreshClientData)))
    case DeleteTask (task) =>
      updated (value.map (_.removeTask (task)), Effect (deleteTaskFromServer (task.id).map (RefreshClientData)))
    case DeleteList (listID) =>
      updated (value.map (_.removeList (listID)), Effect (deleteListFromServer (listID).map (RefreshClientData)))
    case ChangeListFeature (newFeature) =>
      updated (value.map (_.copy (listFeature = newFeature)))
    case ChangeTaskFeature (newFeature) =>
      updated (value.map (_.copy (taskFeature = newFeature)))
    case ChangeView (newView) =>
      updated (value.map (_.copy (view = newView)), Effect (setView (newView).map (RefreshTasksForView)))
  }
}
