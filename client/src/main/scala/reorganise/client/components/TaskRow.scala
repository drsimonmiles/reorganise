package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.FeatureControls.rowWithFeature
import reorganise.client.components.generic.{Checkbox, DatePicker, Dropdown, FocusedTextField, ReorderControl}
import reorganise.client.model.ModelOps._
import reorganise.client.model.ModelVariables._
import reorganise.client.model.{ClientState, LabelFeature, LoadableModel, ModelPoint, OrderFeature, RecurFeature, StartFeature, TaskFeature}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{Task, TaskList}
import scalacss.ScalaCssReact._

object TaskRow {
  @inline private def bss = bootstrapStyles

  case class Props (state: ModelPoint[LoadableModel, ClientState],
                    task: ModelPoint[LoadableModel, Task],
                    feature: TaskFeature)

  val component = ReactComponentB[Props] ("TaskRow")
    .render_P { p =>
      val completed = Checkbox (p.task.variable[Boolean] (_.completed, setTaskCompleted))
      val text = new FocusedTextField ("write task description") (p.task.variable (_.text, setTaskText))
      val row = <.div (bss.row, <.div (bss.columns (1), completed), <.div (bss.columns (11), compact, text))
      val control = p.feature match {
        case LabelFeature =>
          new Dropdown[TaskList] ("label", _.name, primary, p.state.value.visible.lists).
            apply (p.task.variable (t => p.state.value.visible.list (t.list) match {
              case Some (list) => list
              case None => ViewedItemsTable.emptyList
            }, setTaskList))
        case StartFeature =>
          new DatePicker ().apply (p.task.variable (_.startDate, setTaskStart))
        case RecurFeature =>
          RecurControl (p.task.variable (_.recur, setTaskRecur))
        case OrderFeature =>
          if (p.state.value.viewedList.isDefined)
            new ReorderControl (p.task.value.id).apply (p.state.zoom (_.viewedList.get).
              variable[Vector[(Long, Boolean)]] (list => orderWithVisibility (p.state.value, list.order),
              setListOrder))
          else <.span ("").render
      }

      rowWithFeature (row, control)
    }.build

  def apply (model: Props): ReactElement =
    component (model)
}
