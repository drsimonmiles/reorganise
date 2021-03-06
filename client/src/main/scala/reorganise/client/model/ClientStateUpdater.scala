package reorganise.client.model

import diode.data.{Pot, Ready}
import diode.{ActionHandler, Effect, ModelRW}
import reorganise.client.comms.ServerCaller._
import reorganise.client.model.ModelOps._
import scala.concurrent.ExecutionContext.Implicits.global

class ClientStateUpdater (data: ModelRW[Pot[ClientState], Pot[ClientState]]) extends ActionHandler (data) {
  def handle = {
    case LoadAllVisibleDataFromServer =>
      effectOnly (Effect (loadVisibleDataFromServer.map (RefreshClientData)))
    case RefreshTasksForView (newTasks) =>
      updated (value.map (_.withTasks (newTasks)))
    case RefreshClientData (visible) =>
      value match {
        case Ready (_) => updated (value.map (_.withVisible (visible)))
        case _ => updated (Ready (new ClientState (visible)))
      }
    case CreateTask =>
      effectOnly (Effect (createTask.map (RefreshClientData)))
    case CreateList (isDerived) =>
      effectOnly (Effect (createList (isDerived).map (RefreshClientData)))
    case UpdateTask (task) =>
      updated (value.map (_.updatedTask (task)), Effect (updateTaskOnServer (toSharedTask (task)).map (RefreshClientData)))
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
      updated (value.map (_.copy (view = newView)), Effect (setView (toSharedView (newView)).map (RefreshTasksForView)))
  }
}
