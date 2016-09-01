package reorganise.client.screens

import diode.react.ReactPot._
import diode.react.ModelProxy
import japgolly.scalajs.react.{ReactComponentB, Callback}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.Icon.plusSquare
import reorganise.client.components.{NamedPanel, TaskRow, UList, TaskListListItem, Panel, Button}
import reorganise.client.model.{UpdateList, CreateList, CreateTask, ChangeView, LoadableModel, ReloadVisibleTasksFromServer}
import reorganise.client.styles.GlobalStyles.bootstrapStyles
import reorganise.shared.model.{TaskList, VisibleTasks, ListTasks, WeeksTasks, TodaysTasks, AllTasks, Task}
import scalacss.ScalaCssReact._

object TasksScreen {
  @inline private def bss = bootstrapStyles

  private val permanent = Vector[TaskListListItem] (
    TaskListListItem ("All", AllTasks),
    TaskListListItem ("Today", TodaysTasks),
    TaskListListItem ("Week", WeeksTasks)
  )

  def lists (visible: VisibleTasks): Vector[TaskListListItem] =
    permanent ++ visible.lists.map (list => TaskListListItem (list.name, ListTasks (list.id)))

  def currentListName (model: LoadableModel): String =
    model.view.list match {
      case AllTasks => "All"
      case TodaysTasks => "Today"
      case WeeksTasks => "Week"
      case ListTasks (listID) =>
        if (model.tasks.isReady) model.tasks.get.lookupList (listID).map (_.name).getOrElse ("Unknown list") else "Loading..."
    }

  def setCurrentListName (proxy: ModelProxy[LoadableModel]) (newName: String): Callback =
    proxy.value.view.list match {
      case ListTasks (listID) => proxy.dispatch (UpdateList (TaskList (listID, newName)))
      case _ => Callback.empty
    }

  def isCurrentListRenamable (model: LoadableModel): Boolean =
    model.view.list match {
      case ListTasks (listID) => true
      case _ => false
    }

  val component = ReactComponentB[ModelProxy [LoadableModel]] ("TaskScreen")
    .render_P { p =>
      <.div (bss.row,
        <.div (bss.columns (2),
          Panel (Panel.Props ("Lists"),
            p.zoom (_.tasks).apply ().render (visible =>
              UList.menu[TaskListListItem] (lists (visible), _.label,
                i => p.dispatch (ChangeView (p.value.view.copy (list = i.view))),
                i => p.value.view.list == i.view)),
            Button (Button.Props (p.dispatch (CreateList)), plusSquare, " New list")
          )
        ),
        <.div (bss.columns (10),
          NamedPanel (NamedPanel.Props (currentListName (p.value), setCurrentListName (p), isCurrentListRenamable (p.value)),
            <.div (
            p.zoom (_.tasks).apply ().renderFailed (ex => "Error loading"),
            p.zoom (_.tasks).apply ().renderPending (_ > 500, _ => "Loading..."),
            p.zoom (_.tasks).apply ().render (visible => UList (visible.tasks, {task: Task => TaskRow (task, visible.lookupList, p)})),
            Button (Button.Props (p.dispatch (CreateTask)), plusSquare, " New task"))
          )
        )
      )
    }.componentDidMount { scope =>
      Callback.when (scope.props.zoom (_.tasks).apply ().isEmpty)(scope.props.dispatch (ReloadVisibleTasksFromServer))
    }.build

  def apply (proxy: ModelProxy[LoadableModel]) = component (proxy)
}
