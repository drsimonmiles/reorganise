package reorganise.client.model

import reorganise.shared.model.{Task, TaskList, TasksView}

object ModelOps {
  def toClientTasks (tasks: Vector[Task], lists: Vector[TaskList]): Vector[ClientTask] =
    tasks.flatMap (t => lists.find (_.id == t.list).map (l => ClientTask (t.id, t.text, t.startDate, l, t.recur, t.completed)))

  def toClientView (view: Option[TasksView], lists: Vector[TaskList]): Option[ClientTasksView] =
    view.flatMap (t => lists.find (_.id == t.list).map (l => ClientTasksView (t.includeCompleted, l)))

  def toSharedTask (task: ClientTask): Task =
    Task (task.id, task.text, task.startDate, task.list.id, task.recur, task.completed)

  def toSharedView (view: Option[ClientTasksView]): Option[TasksView] =
    view.map (t => TasksView (t.includeCompleted, t.list.id))

  def visible (state: ClientState, task: ClientTask): Boolean =
    state.view.exists (_.includeCompleted || !task.completed)

  def orderWithVisibility (state: ClientState, order: Vector[Long]): Vector[(Long, Boolean)] =
    order.map { taskID =>
      state.task (taskID).map (task => (taskID, visible (state, task))).getOrElse ((taskID, false))
    }
}
