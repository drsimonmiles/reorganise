package reorganise.shared.model

sealed trait TaskListView
case object AllTasks extends TaskListView
case object TodaysTasks extends TaskListView
case object WeeksTasks extends TaskListView
case class ListTasks (list: Long) extends TaskListView
