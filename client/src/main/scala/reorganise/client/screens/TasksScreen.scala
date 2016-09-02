package reorganise.client.screens

import diode.data.Ready
import diode.react.ReactPot._
import diode.react.ModelProxy
import japgolly.scalajs.react.{ReactComponentB, Callback}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.{UList, Panel, NamedPanel, Icon, Button}
import Icon.{close, plusSquare}
import reorganise.client.components.{ListRow, TaskRow, TaskListListItem}
import reorganise.client.model.{ChangeView, UpdateList, CreateList, CreateTask, DeleteList, LoadableModel, ReloadVisibleTasksFromServer}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles.bootstrapStyles
import reorganise.shared.model.{Task, TaskList, VisibleTasks, ListTasks, WeeksTasks, TodaysTasks, AllTasks}
import scalacss.ScalaCssReact._

object TasksScreen {
  @inline private def bss = bootstrapStyles

  private val permanent = Vector[TaskListListItem] (
    TaskListListItem ("All", AllTasks, info),
    TaskListListItem ("Today", TodaysTasks, info),
    TaskListListItem ("Week", WeeksTasks, info)
  )

  def lists (visible: VisibleTasks): Vector[TaskListListItem] =
    permanent ++ visible.lists.map (list => TaskListListItem (list.name, ListTasks (list.id), danger))

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

  def isCurrentListEditable (model: LoadableModel): Boolean =
    model.view.list match {
      case ListTasks (listID) => true
      case _ => false
    }

  def isCurrentListDeletable (model: LoadableModel): Boolean =
    model.view.list match {
      case ListTasks (listID) => model.tasks match {
        case Ready (visible) => visible.tasks.isEmpty
        case _ => false
      }
      case _ => false
    }

  def deleteCurrentList (model: LoadableModel, dispatcher: ModelProxy[_]): Callback =
    model.view.list match {
      case ListTasks (listID) =>
        dispatcher.dispatch (DeleteList (listID)) >>
          dispatcher.dispatch (ChangeView (model.view.copy (list = AllTasks)))
      case _ => Callback.empty
    }

  val component = ReactComponentB[ModelProxy [LoadableModel]] ("TaskScreen")
    .render_P { p =>
      val loadedData = p.zoom (_.tasks)
      <.div (bss.row,
        <.div (bss.columns (2),
          Panel (Panel.Props ("Lists"),
            p.zoom (_.tasks).apply ().render (visible =>
              <.ul (bss.listGroup.listGroup) (lists (visible).map {
                list => ListRow (list, p)
              })),
            Button (Button.Props (p.dispatch (CreateList)), plusSquare, " New list")
          )
        ),
        <.div (bss.columns (10),
          NamedPanel (NamedPanel.Props (currentListName (p.value), setCurrentListName (p), isCurrentListEditable (p.value)),
            <.div (
              loadedData.value.renderFailed (ex => "Error loading"),
              loadedData.value.renderPending (_ > 500, _ => "Loading..."),
              loadedData.value.render (visible => UList (visible.tasks, {task: Task => TaskRow (loadedData, task, visible.lookupList, p)})),
              isCurrentListEditable (p.value) ?= Button (Button.Props (p.dispatch (CreateTask)), plusSquare, " New task"),
              isCurrentListDeletable (p.value) ?= Button (Button.Props (deleteCurrentList (p.value, p)), close, " Delete list")
            )
          )
        )
      )
    }.componentDidMount { scope =>
      Callback.when (scope.props.zoom (_.tasks).apply ().isEmpty)(scope.props.dispatch (ReloadVisibleTasksFromServer))
    }.build

  def apply (proxy: ModelProxy[LoadableModel]) = component (proxy)
}
