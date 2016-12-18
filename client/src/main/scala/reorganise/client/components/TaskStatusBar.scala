package reorganise.client.components

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.FeatureControls.{listFeatures, rowWithFeature, taskFeatures}
import reorganise.client.components.generic.Dropdown
import reorganise.client.model.Feature
import reorganise.client.model.generic.DiodeVariable
import reorganise.client.styles.BootstrapAlertStyles.warning

object TaskStatusBar {
  case class Props (isTasksView: Boolean, feature: DiodeVariable[Feature])

  val component = ReactComponentB[Props] ("TaskStatusBar")
    .render_P { p =>
      rowWithFeature (
        <.span (""),
        new Dropdown[Feature] ("feature", _.label, warning,
          if (p.isTasksView) taskFeatures else listFeatures).
          apply (p.feature)
      )
    }.build

  def apply (isTasksView: Boolean, feature: DiodeVariable[Feature]) =
    component (Props (isTasksView, feature))
}
