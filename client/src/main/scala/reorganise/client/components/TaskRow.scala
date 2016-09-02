package reorganise.client.components

import diode.data.{Ready, Pot}
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.{Dropdown, Icon}
import Icon.{banned, refresh}
import reorganise.client.model.UpdateTask
import reorganise.client.styles.BootstrapAlertStyles.primary
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{VisibleTasks, TaskList, Task}
import scalacss.ScalaCssReact._

object TaskRow {
  @inline private def bss = bootstrapStyles

  case class Props (visible: ModelProxy[Pot[VisibleTasks]], task: Task,
                    listLookup: Long => Option[TaskList], stateChange: Task => Callback)

  class Backend (t: BackendScope[Props, Task]) {
    def listDropdown (p: Props) = {
      def setList (list: TaskList) = p.visible.dispatch (UpdateTask (p.task.copy (list = list.id)))
      p.visible.value match {
        case Ready (tasksData) =>
          val listLabel: String = p.listLookup (p.task.list).map (_.name).getOrElse ("Unknown")
          Dropdown[TaskList] (listLabel, tasksData.lists, _.name, setList)
        case _ =>
          Dropdown[TaskList] ("Unknown", Vector[TaskList] (), _.name, setList)
      }
    }

    def render (p: Props, s: Task): ReactElement = {
      <.div (bss.row,
        <.div (bss.columns (7),
          <.div (bss.columns (1),
            <.input.checkbox (^.checked := s.completed,
              ^.onChange --> p.stateChange (s.copy (completed = !s.completed)))),
          <.div (bss.columns (11), bss.formGroup, compact,
            if (s.completed) <.s (s.text)
            else
              <.input.text (bss.formControl, ^.id := "description", ^.value := s.text,
                ^.placeholder := "write task description",
                ^.onChange ==> { e: ReactEventI => t.setState (s.copy (text = e.target.value)) },
                ^.onKeyPress ==> { e: ReactKeyboardEventI =>
                  Callback (if (e.charCode == 13) e.currentTarget.blur ())
                },
                ^.onBlur ==> { e: ReactEventI => p.stateChange (s) }
              ))),
        <.div (bss.columns (2),
          <.span (listDropdown (p))),
        <.div (bss.columns (2),
          <.span (s.startDate)),
        <.div (bss.columns (1),
          s.recur match {
            case Some (days) => <.span (refresh, days.toString)
            case None => banned ("refresh")
          })
      )
    }
  }

  val component = ReactComponentB[Props]("TaskRow")
    .initialState_P (p => p.task)
    .renderBackend[Backend]
    .build

  def apply (visible: ModelProxy[Pot[VisibleTasks]], task: Task,
             listLookup: Long => Option[TaskList], dispatcher: ModelProxy[_]) =
    component (Props (visible, task, listLookup, task => dispatcher.dispatch (UpdateTask (task))))
}
