package reorganise.client.components

import japgolly.scalajs.react.ReactComponentB
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.FeatureControls.{listFeatures, rowWithFeature, taskFeatures}
import reorganise.client.components.generic.Dropdown
import reorganise.client.model.generic.ScopedVariable
import reorganise.client.model.{Feature, LoadableModel}
import reorganise.client.styles.BootstrapAlertStyles.warning
import reorganise.client.styles.GlobalStyles._

object TaskStatusBar {
  val component = ReactComponentB[ScopedVariable[LoadableModel, Boolean, Feature]] ("TaskStatusBar")
    .render_P { p =>
      rowWithFeature (
        <.span (""),
        new Dropdown[LoadableModel, Feature] ("feature", _.label, warning,
          if (p.scope) taskFeatures else listFeatures).
          apply (p.variableOnly)
      )
    }.build

  def apply (data: ScopedVariable[LoadableModel, Boolean, Feature]) =
    //data.createEditor (component)
    component (data)
}
