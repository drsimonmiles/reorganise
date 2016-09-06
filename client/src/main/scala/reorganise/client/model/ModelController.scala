package reorganise.client.model

import diode.Circuit
import diode.data.Empty
import diode.react.ReactConnector
import reorganise.shared.model.TasksView

object ModelController extends Circuit[LoadableModel]  with ReactConnector[LoadableModel] {
  def initialModel =
    LoadableModel (Empty, TasksView (includeCompleted = false, -1), ListFeature)

  def actionHandler: ModelController.HandlerFunction =
    composeHandlers (
      new VisibleTasksUpdater (zoomRW (_.tasks) ((model, value) => model.copy (tasks = value))),
      new TaskViewUpdater (zoomRW (_.view) ((model, value) => model.copy (view = value))),
      new TaskFeatureUpdater (zoomRW (_.feature) ((model, value) => model.copy (feature = value)))
    )
}
