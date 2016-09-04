package reorganise.client.model

import diode.data.Pot
import reorganise.shared.model.{VisibleTasks, TasksView}

case class LoadableModel (tasks: Pot[VisibleTasks], view: TasksView, feature: TaskFeature)
