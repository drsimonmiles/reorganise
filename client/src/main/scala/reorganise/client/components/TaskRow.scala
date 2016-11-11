package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.FeatureControls.{rowWithFeature, taskFeatureControl}
import reorganise.client.components.generic.{Checkbox, FocusedTextField}
import reorganise.client.model.ModelVariables._
import reorganise.client.model.{LoadableModel, ModelPoint, TaskFeature}
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{Task, TaskList}
import scalacss.ScalaCssReact._

object TaskRow {
  @inline private def bss = bootstrapStyles

  type Props = ModelPoint[LoadableModel, (((TaskFeature, Vector[TaskList]), TaskList), Task)]

  val component = ReactComponentB[Props] ("TaskRow")
    .render_P { p =>
      val task = p.zoom (_._2)
      val completed = Checkbox (task.variable[Boolean] (_.completed, setTaskCompleted))
      val text = new FocusedTextField ("write task description") (task.variable (_.text, setTaskText))
      val row = <.div (bss.row, <.div (bss.columns (1), completed), <.div (bss.columns (11), compact, text))
      val featureData = p.zoom (m => ((m._1._1._2, m._1._2), m._2))
      val feature = taskFeatureControl (p.value._1._1._1) (featureData)

      rowWithFeature (row, feature)
    }.build

  def apply (model: Props): ReactElement =
    model.wrap (component (_))
}
