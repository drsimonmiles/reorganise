package reorganise.client.screens

import diode.react.ReactPot._
import diode.react.ModelProxy
import japgolly.scalajs.react.{ReactComponentB, Callback}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.Icon.plusSquare
import reorganise.client.components.{TaskRow, UList, TaskListListItem, Panel, Button}
import reorganise.client.model.{CreateTask, ChangeView, LoadableModel, ReloadVisibleTasksFromServer}
import reorganise.client.styles.GlobalStyles.bootstrapStyles
import reorganise.shared.model.{VisibleTasks, ListTasks, WeeksTasks, TodaysTasks, AllTasks, Task}
import scalacss.ScalaCssReact._

object TasksScreen {
  @inline private def bss = bootstrapStyles

  private val permanent = Vector[TaskListListItem] (
    TaskListListItem ("All", AllTasks),
    TaskListListItem ("Today", TodaysTasks),
    TaskListListItem ("Week", WeeksTasks)
  )

  def lists (visible: VisibleTasks): Vector[TaskListListItem] =
    permanent ++ visible.lists.map (list => TaskListListItem (list, ListTasks (list)))

  val component = ReactComponentB[ModelProxy [LoadableModel]] ("TaskScreen")
    .render_P { p =>
      <.div (bss.row,
        <.div (bss.columns (2),
          Panel (Panel.Props ("Lists"),
            p.zoom (_.tasks).apply ().render (visible =>
              UList.menu[TaskListListItem] (lists (visible), _.label,
                i => p.dispatch (ChangeView (p.value.view.copy (list = i.view))),
                i => p.value.view.list == i.view))
            //  p.zoom (_.tasks).apply ().render (visible => TaskListList (visible.tasks))
          )
        ),
        <.div (bss.columns (10),
          Panel (Panel.Props ("All tasks"), <.div (
            p.zoom (_.tasks).apply ().renderFailed (ex => "Error loading"),
            p.zoom (_.tasks).apply ().renderPending (_ > 500, _ => "Loading..."),
            p.zoom (_.tasks).apply ().render (visible => UList (visible.tasks, {task: Task => TaskRow (task, p)})),
            Button (Button.Props (p.dispatch (CreateTask)), plusSquare, " New task"))
          )
        )
      )
    }.componentDidMount { scope =>
      Callback.when (scope.props.zoom (_.tasks).apply ().isEmpty)(scope.props.dispatch (ReloadVisibleTasksFromServer))
    }.build

  def apply (proxy: ModelProxy[LoadableModel]) = component (proxy)
}
