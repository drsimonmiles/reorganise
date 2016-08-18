package reorganise.client.screens

import diode.data.Pot
import diode.react.ReactPot._
import diode.react.ModelProxy
import japgolly.scalajs.react.{ReactComponentB, ReactElement, Callback, BackendScope}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.Icon.plusSquare
import reorganise.client.components.{Panel, Button, TaskList}
import reorganise.client.model.{UpdateTask, ReloadVisibleTasksFromServer, VisibleTasks}
import reorganise.shared.model.Task

object TasksScreen {
  case class Props (proxy: ModelProxy [Pot[VisibleTasks]])

  case class State (selectedTask: Option[Task] = None, showTaskForm: Boolean = false)

  class Backend ($: BackendScope[Props, State]) {
    def mounted (props: Props): Callback =
      // dispatch a message to refresh the todos, which will cause TodoStore to fetch todos from the server
      Callback.when (props.proxy ().isEmpty)(props.proxy.dispatch (ReloadVisibleTasksFromServer))

    def editTask (task: Option[Task]) =
      // activate the edit dialog
      $.modState (s => s.copy (selectedTask = task, showTaskForm = true))

    def taskEdited (task: Task, cancelled: Boolean) =
      if (cancelled) $.modState (s => s.copy (showTaskForm = false))
      else ($.props >>= (_.proxy.dispatch (UpdateTask (task)))) >> $.modState (s => s.copy (showTaskForm = false))

    def render (p: Props, s: State) =
      Panel (Panel.Props ("All tasks"), <.div (
        p.proxy ().renderFailed (ex => "Error loading"),
        p.proxy ().renderPending (_ > 500, _ => "Loading..."),
        p.proxy ().render (todos => TaskList (todos.tasks,
          task => p.proxy.dispatch (UpdateTask (task)), task => editTask (Some (task)))),
        Button (Button.Props (editTask (None)), plusSquare, " New")),

        // if the dialog is open, add it to the panel, otherwise add an empty placeholder
        if (s.showTaskForm) TaskForm (TaskForm.Props (s.selectedTask, taskEdited))
        else Seq.empty[ReactElement])
  }

  // create the React component for To Do management
  val component = ReactComponentB[Props] ("TaskScreen")
    .initialState (State ()) // initial state from TodoStore
    .renderBackend[Backend]
    .componentDidMount (scope => scope.backend.mounted (scope.props))
    .build

  /** Returns a function compatible with router location system while using our own props */
  def apply (proxy: ModelProxy[Pot[VisibleTasks]]) = component (Props (proxy))
}

