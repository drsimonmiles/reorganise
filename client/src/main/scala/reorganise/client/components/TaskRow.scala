package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.GenericComponents._
import reorganise.client.components.SpecificComponents._
import reorganise.client.model.ModelVariables._
import reorganise.client.model._
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.TaskList
import scalacss.ScalaCssReact._

object TaskRow {
  @inline private def bss = bootstrapStyles

  case class Props (state: ClientState, task: ClientTask, feature: TaskFeature)

  private val textField = focusedTextField ("write task description")

  val component = ReactComponentB[Props] ("TaskRow")
    .render_P { p =>
      val completed = checkbox (SetTaskCompleted.from (p.task))
      val text = textField (SetTaskText.from (p.task))
      val control = p.feature match {
        case LabelFeature =>
          dropdown[TaskList] ("label", _.name, primary, p.state.lists) (SetTaskList.from (p.task))
        case StartFeature =>
          DatePicker (SetTaskStart.from (p.task))
        case RecurFeature =>
          recurControl (SetTaskRecur.from (p.task))
        case OrderFeature =>
          reorderControl (p.task.id) (SetListOrder.from ((p.state, p.task.list)))
      }

      <.div (bss.row, compact,
        <.div (bss.columns (10),
          <.div (bss.row,
            <.div (bss.columns (1), completed),
            <.div (bss.columns (11), compact, text)
          )
        ),
        <.div (bss.columns (2), control)
      )
    }.build

  def apply (state: ClientState, task: ClientTask, feature: TaskFeature): ReactElement =
    component (Props (state, task, feature))
}
