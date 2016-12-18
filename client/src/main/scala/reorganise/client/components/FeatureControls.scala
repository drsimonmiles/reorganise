package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.model.{DerivationFeature, LabelFeature, OrderFeature, PriorDaysFeature, RecurFeature, StartFeature}
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{NoRestriction, NoTasks, PriorToToday}
import scalacss.ScalaCssReact._

object FeatureControls {
  @inline private def bss = bootstrapStyles

  val derivations = Vector (NoTasks, NoRestriction, PriorToToday (0))

  val taskFeatures = Vector (StartFeature, RecurFeature, LabelFeature, OrderFeature)

  val listFeatures = Vector (OrderFeature, DerivationFeature, PriorDaysFeature)

  def rowWithFeature (row: ReactElement, featureControl: ReactElement): ReactElement =
    <.div (bss.row, compact, <.div (bss.columns (10), row), <.div (bss.columns (2), featureControl))
}
