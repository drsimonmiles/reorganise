package reorganise.client.components

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.Icon.{banned, refresh}
import reorganise.client.model.UpdateTask
import reorganise.client.styles.BootstrapAlertStyles.primary
import reorganise.client.styles.GlobalStyles
import reorganise.shared.model.{TaskList, Task}
import scalacss.ScalaCssReact._

object TaskRow {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (task: Task, listLookup: Long => Option[TaskList], stateChange: Task => Callback)

  class Backend (t: BackendScope[Props, Task]) {
    def render (p: Props, s: Task): ReactElement = {
      val listLabel: String = p.listLookup (s.list).map (_.name).getOrElse ("Unknown")
      <.div (bss.row,
        <.div (bss.columns (7),
          <.div (bss.columns (1),
            <.input.checkbox (^.checked := s.completed,
              ^.onChange --> p.stateChange (s.copy (completed = !s.completed)))),
          <.div (bss.columns (11), bss.formGroup,
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
          <.span (listLabel, bss.labelOpt (primary))),
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

  def apply (task: Task, listLookup: Long => Option[TaskList], dispatcher: ModelProxy[_]) =
    component (Props (task, listLookup, task => dispatcher.dispatch (UpdateTask (task))))
}
