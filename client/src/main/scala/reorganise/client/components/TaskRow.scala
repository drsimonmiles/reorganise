package reorganise.client.components

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.GenericComponents._
import reorganise.client.components.SpecificComponents._
import directed.VariableOps._
import reorganise.client.model.ModelOps._
import reorganise.client.model.ModelVariables._
import reorganise.client.model.{ClientState, LabelFeature, LoadableModel, OrderFeature, RecurFeature, StartFeature, TaskFeature}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{Task, TaskList}
import scalacss.ScalaCssReact._

object TaskRow {
  @inline private def bss = bootstrapStyles

  case class Props (state: ModelProxy[ClientState], task: ModelProxy[Task], feature: TaskFeature)

  private val textField = focusedTextField ("write task description")

  val component = ReactComponentB[Props] ("TaskRow")
    .render_P { p =>
      val completed = p.task.editor (_.completed, setTaskCompleted, checkbox)
      val text = p.task.editor (_.text, setTaskText, textField)
      val control = p.feature match {
        case LabelFeature =>
          p.task.editor (t => p.state.value.visible.list (t.list) match {
            case Some (list) => list
            case None => ViewedItemsTable.emptyList
          }, setTaskList, dropdown[TaskList] ("label", _.name, primary, p.state.value.visible.lists))
        case StartFeature =>
          new DatePicker ().apply (p.task.variable (_.startDate, setTaskStart))
        case RecurFeature =>
          recurControl (p.task.variable (_.recur, setTaskRecur))
        case OrderFeature =>
          if (p.state.value.viewedList.isDefined)
            reorderControl (p.task.value.id).apply (p.state.zoom (_.viewedList.get).
              variable[Vector[(Long, Boolean)]] (list => orderWithVisibility (p.state.value, list.order),
              setListOrder))
          else <.span ("").render
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

  def apply (state: ModelProxy[ClientState], task: ModelProxy[Task], feature: TaskFeature): ReactElement =
    component (Props (state, task, feature))
}
