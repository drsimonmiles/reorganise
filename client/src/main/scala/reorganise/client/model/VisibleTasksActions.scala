package reorganise.client.model

import diode.Action
import reorganise.shared.model.{VisibleTasks, TasksView, Task}

trait VisibleTasksAction extends Action

case object ReloadVisibleTasksFromServer extends VisibleTasksAction
case class RefreshClientShownTasks (data: VisibleTasks) extends VisibleTasksAction
case object CreateTask extends VisibleTasksAction
case class UpdateTask (task: Task) extends VisibleTasksAction
case class DeleteTask (task: Task) extends VisibleTasksAction
case class ChangeView (newView: TasksView) extends VisibleTasksAction