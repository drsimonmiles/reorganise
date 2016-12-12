package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.FeatureControls._
import reorganise.client.components.generic.FocusedTextField
import reorganise.client.model.ModelVariables._
import reorganise.client.model.{ListFeature, LoadableModel, ModelPoint}
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{TaskList, VisibleTasks}
import scalacss.ScalaCssReact._

object ListEditRow {
  type Props = ModelPoint[LoadableModel, ((ListFeature, VisibleTasks), TaskList)]

  val component = ReactComponentB[Props] ("ListEditRow")
    .render_P { p =>
      val list = p.zoom (_._2)
      val name = new FocusedTextField ("write list name") (list.variable (_.name, setListName))
      val row = <.div (compact, name)
      val featureData = p.zoom (m => (m._1._2, m._2))
      val feature = listFeatureControl (p.value._1._1) (featureData)

      rowWithFeature (row, feature)
    }.build

  def apply (model: Props): ReactElement =
    //model.reader (component)
    component (model)
}
