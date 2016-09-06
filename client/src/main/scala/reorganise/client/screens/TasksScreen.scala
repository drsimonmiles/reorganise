package reorganise.client.screens

import diode.data.Ready
import diode.react.ModelProxy
import diode.react.ReactPot._
import japgolly.scalajs.react.vdom.prefix_<^._
import japgolly.scalajs.react.{Callback, CallbackTo, ReactComponentB}
import reorganise.client.components.generic.Icon.{close, cut, plusSquare}
import reorganise.client.components.generic.{Button, NamedPanel, Panel}
import reorganise.client.components.{ListRow, TaskListListItem, TaskRow, TaskStatusBar}
import reorganise.client.model.{ChangeView, CreateList, CreateTask, DeleteList, LoadableModel, ReloadVisibleTasksFromServer, UpdateList}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles.bootstrapStyles
import reorganise.shared.model.{NoRestriction, TaskList, TasksView, VisibleTasks}
import scalacss.ScalaCssReact._

object TasksScreen {
  @inline private def bss = bootstrapStyles

  val emptyList = TaskList (-1, "Select or add new list to start", Vector[Long] (), Some (NoRestriction))

  def lists (visible: VisibleTasks, currentView: TasksView): Vector[TaskListListItem] =
    visible.lists.map (list => TaskListListItem (list.name, currentView.copy (list = list.id),
      if (list.derivation.isDefined) info else danger))

  def toggleCompleted (model: LoadableModel, dispatcher: ModelProxy[_]): Callback =
    dispatcher.dispatch (ChangeView (model.view.copy (includeCompleted = !model.view.includeCompleted)))

  val component = ReactComponentB[ModelProxy [LoadableModel]] ("TaskScreen")
    .render_P { p =>
      p.value.tasks match {
        case Ready (visible) =>
          val currentList = visible.lookupList (p.value.view.list).getOrElse (emptyList)
          val isCurrentListEditable = currentList.derivation.isEmpty
          val isCurrentListDeletable =
            currentList != emptyList && (currentList.derivation.isDefined || visible.tasks.isEmpty)
          val backupList = visible.lists.find (_.id != currentList.id).getOrElse (emptyList)
          def setCurrentListName (newName: String): Callback =
            p.dispatch (UpdateList (currentList.copy (name = newName)))
          def deleteCurrentList (): CallbackTo[Unit] =
            p.dispatch (DeleteList (currentList.id)) >>
              p.dispatch (ChangeView (p.value.view.copy (list = backupList.id)))

      <.div (bss.row,
        <.div (bss.columns (2),
          Panel (Panel.Props (None),
            p.zoom (_.tasks).apply ().render (visible =>
              <.ul (bss.listGroup.listGroup) (lists (visible, p.value.view).map {
                list => ListRow (list, p)
              })),
            Button (Button.Props (p.dispatch (CreateList (false))), plusSquare, " New label list"),
            Button (Button.Props (p.dispatch (CreateList (true))), plusSquare, " New derived list")
          )
        ),
        <.div (bss.columns (10),
          NamedPanel (NamedPanel.Props (currentList.name, setCurrentListName, isCurrentListEditable),
            <.div (
                <.div (bss.listGroup.listGroup,
                  TaskStatusBar (p),
                  visible.tasks.map (t => TaskRow (p.zoom (_.tasks), t, p.zoom (_.feature), visible.lookupList, p))
                ),
              isCurrentListEditable ?= Button (Button.Props (p.dispatch (CreateTask)), plusSquare, " New task"),
              isCurrentListDeletable ?= Button (Button.Props (deleteCurrentList ()), close, " Delete list"),
              Button (Button.Props (toggleCompleted (p.value, p)), cut,
                if (p.value.view.includeCompleted) " Hide completed" else " Show completed")
            )
          )
        )
      )
        case _ => <.div ("Loading...")
      }
    }.componentDidMount { scope =>
      Callback.when (scope.props.zoom (_.tasks).apply ().isEmpty)(scope.props.dispatch (ReloadVisibleTasksFromServer))
    }.build

  def apply (proxy: ModelProxy[LoadableModel]) = component (proxy)
}
