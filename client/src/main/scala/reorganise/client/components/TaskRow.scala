package reorganise.client.components

import japgolly.scalajs.react.{ReactComponentB, Callback}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.Icon.{banned, refresh}
import reorganise.client.styles.GlobalStyles
import reorganise.client.styles.BootstrapAlertStyles.primary
import reorganise.shared.model.Task
import scalacss.ScalaCssReact._

object TaskRow {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (task: Task, stateChange: Task => Callback, editItem: Task => Callback)

  private val TaskRow = ReactComponentB[Props] ("TaskRow")
    .render_P (p => {
      <.li (bss.listGroup.item,
        <.input.checkbox (^.checked := p.task.completed, ^.onChange --> p.stateChange (p.task.copy (completed = !p.task.completed))),
        <.span ("  "),
        if (p.task.completed) <.s (p.task.text) else <.span (p.task.text),
        <.span (" "),
        <.div (
          <.span (p.task.list, bss.labelOpt (primary)),
          <.span (" "),
          <.span (p.task.startDate),
          <.span (" "),
          p.task.recur match {
            case Some (days) => <.span (refresh, days.toString)
            case None => banned ("refresh")
          },
          <.span (" "),
          Button (Button.Props (p.editItem (p.task), addStyles = Seq (bss.buttonXS)), "Edit"),
          bss.pullRight
        )
      )
    }).build

  def apply (task: Task, stateChange: Task => Callback, editItem: Task => Callback) =
    TaskRow (Props (task, stateChange, editItem))
}
