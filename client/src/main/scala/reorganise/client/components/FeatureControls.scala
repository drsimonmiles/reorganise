package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.{Dropdown, QuantityControl, ReorderControl}
import reorganise.client.model.ModelVariables._
import reorganise.client.model.{DerivationFeature, LabelFeature, ListFeature, LoadableModel, ModelPoint, OrderFeature, PriorDaysFeature, RecurFeature, StartFeature}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{Derivation, NoRestriction, NoTasks, PriorToToday, TaskList, VisibleTasks}
import scalacss.ScalaCssReact._

object FeatureControls {
  @inline private def bss = bootstrapStyles

  val derivations = Vector (NoTasks, NoRestriction, PriorToToday (0))

  val taskFeatures = Vector (StartFeature, RecurFeature, LabelFeature, OrderFeature)

  val listFeatures = Vector (OrderFeature, DerivationFeature, PriorDaysFeature)

  val listFeatureControl: Map[ListFeature, ModelPoint[LoadableModel, (VisibleTasks, TaskList)] => ReactElement] = Map (
    OrderFeature -> (model =>
      new ReorderControl (model.value._2.id).apply (model.zoom (_._1).variable (_.listOrder.map (list => (list, true)), setAllListsOrder))),
    DerivationFeature -> (model =>
      if (model.value._2.derivation.isDefined)
        new Dropdown[Derivation] ("derivation", _.name, primary, derivations).
          apply (model.zoom (_._2).variable (_.derivation.get, setListDerivation))
      else <.span ("")),
    PriorDaysFeature -> (model => model.value._2.derivation match {
      case Some (PriorToToday (_)) =>
        new QuantityControl ().
          apply (model.zoom (_._2).variable (_.derivation.get.asInstanceOf[PriorToToday].days, setListPriorDays))
      case _ => <.span ("")
    })
  )

  def rowWithFeature (row: ReactElement, featureControl: ReactElement): ReactElement =
    <.div (bss.row, compact, <.div (bss.columns (10), row), <.div (bss.columns (2), featureControl))
}
