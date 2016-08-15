package reorganise.client.components

import japgolly.scalajs.react.{ReactComponentB, Callback}
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.styles.GlobalStyles
import reorganise.shared.model.Task
import scalacss.ScalaCssReact._

object TaskRow {
  @inline private def bss = GlobalStyles.bootstrapStyles

  case class Props (task: Task, stateChange: Task => Callback, editItem: Task => Callback)

  private val TaskRow = ReactComponentB[Props] ("TaskRow")
    .render_P (p => {
      <.li (bss.listGroup.item,
        <.input.checkbox (^.checked := p.task.completed, ^.onChange --> p.stateChange (p.task.copy (completed = !p.task.completed))),
        <.span(" "),
        if (p.task.completed) <.s (p.task.text) else <.span (p.task.text),
        Button (Button.Props (p.editItem (p.task), addStyles = Seq (bss.pullRight, bss.buttonXS)), "Edit")
      )
    }).build

  def apply (task: Task, stateChange: Task => Callback, editItem: Task => Callback) =
    TaskRow (Props (task, stateChange, editItem))
}
