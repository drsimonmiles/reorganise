package reorganise.client.components

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.Icon.{banned, refresh}
import reorganise.client.model.{UpdateTask, LoadableModel}
import reorganise.client.styles.GlobalStyles
import reorganise.client.styles.BootstrapAlertStyles.primary
import reorganise.shared.model.Task
import scalacss.ScalaCssReact._

object TaskRow {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (task: Task, stateChange: Task => Callback)

  private val TaskRow = ReactComponentB[Props] ("TaskRow")
    .render_P (p => {
        <.div (bss.row,
          <.div (bss.columns (7),
            <.div (bss.columns (1),
            <.input.checkbox (^.checked := p.task.completed,
              ^.onChange --> p.stateChange (p.task.copy (completed = !p.task.completed)))),
            <.div (bss.columns (11),
            if (p.task.completed) <.s (p.task.text) else //<.span (p.task.text),
            <.input.text (bss.formControl, ^.id := "description", ^.value := p.task.text,
              ^.placeholder := "write task description",
              ^.onChange ==> {e: ReactEventI => p.stateChange (p.task.copy (text = e.target.value))}))),
          <.div (bss.columns (2),
            <.span (p.task.list, bss.labelOpt (primary))),
          <.div (bss.columns (2),
            <.span (p.task.startDate)),
          <.div (bss.columns (1),
            p.task.recur match {
              case Some (days) => <.span (refresh, days.toString)
              case None => banned ("refresh")
            })
        )
    }).build

  def apply (task: Task, dispatcher: ModelProxy[LoadableModel]) =
    TaskRow (Props (task, task => dispatcher.dispatch (UpdateTask (task))))
}
