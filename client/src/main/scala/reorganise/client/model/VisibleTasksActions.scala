package reorganise.client.model

import diode.Action
import reorganise.shared.model.{TaskList, VisibleTasks, TasksView, Task}

trait VisibleTasksAction extends Action

case object ReloadVisibleTasksFromServer extends VisibleTasksAction
case class RefreshClientShownTasks (data: VisibleTasks) extends VisibleTasksAction
case object CreateTask extends VisibleTasksAction
case class UpdateTask (task: Task) extends VisibleTasksAction
case class DeleteTask (task: Task) extends VisibleTasksAction
case class DeleteList (listID: Long) extends VisibleTasksAction
case class CreateList (isDerived: Boolean) extends VisibleTasksAction
case class UpdateList (list: TaskList) extends VisibleTasksAction
case class ChangeView (newView: TasksView) extends VisibleTasksAction
case class ChangeSettingsView (showSettings: Boolean) extends VisibleTasksAction
case class ChangeFeature (newFeature: TaskFeature) extends VisibleTasksAction