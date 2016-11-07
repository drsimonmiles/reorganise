package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.generic.{DatePicker, FixedDropdown, QuantityControl, VariableDropdown}
import reorganise.client.model.ModelVariables._
import reorganise.client.model.{DerivationFeature, LabelFeature, ListFeature, LoadableModel, ModelPoint, OrderFeature, PriorDaysFeature, RecurFeature, StartFeature, TaskFeature}
import reorganise.client.styles.BootstrapAlertStyles._
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{Derivation, NoRestriction, NoTasks, PriorToToday, Task, TaskList, VisibleTasks}
import scalacss.ScalaCssReact._

object FeatureControls {
  @inline private def bss = bootstrapStyles

  val derivations = Vector (NoTasks, NoRestriction, PriorToToday (0))

  val taskFeatures = Vector (StartFeature, RecurFeature, LabelFeature, OrderFeature)

  val listFeatures = Vector (OrderFeature, DerivationFeature, PriorDaysFeature)

  val taskFeatureControl: Map[TaskFeature, ModelPoint[LoadableModel, ((Vector[TaskList], TaskList), Task)] => ReactElement] = Map (
    LabelFeature -> (model =>
      new VariableDropdown[LoadableModel, TaskList] ("label", _.name, primary).
        apply (model.scopedVariable[Vector[TaskList], Task, TaskList] (_._1._1, _._2, t => model.value._1._1.find (_.id == t.list).get, setTaskList))
    ),
    StartFeature -> (model =>
      new DatePicker[LoadableModel].apply (model.zoom (_._2).variable (_.startDate, setTaskStart))),
    RecurFeature -> (model =>
      RecurControl (model.zoom (_._2).variable (_.recur, setTaskRecur))),
    OrderFeature -> (model =>
      new ReorderControl (model.value._2.id).apply (model.zoom (_._1._2).variable (_.order, setListOrder)))
  )

  val listFeatureControl: Map[ListFeature, ModelPoint[LoadableModel, (VisibleTasks, TaskList)] => ReactElement] = Map (
    OrderFeature -> (model =>
      new ReorderControl (model.value._2.id).apply (model.zoom (_._1).variable (_.listOrder, setAllListsOrder))),
    DerivationFeature -> (model =>
      if (model.value._2.derivation.isDefined)
        new FixedDropdown[LoadableModel, Derivation] ("derivation", _.name, primary, derivations).
          apply (model.zoom (_._2).variable (_.derivation.get, setListDerivation))
      else <.span ("")),
    PriorDaysFeature -> (model => model.value._2.derivation match {
      case Some (PriorToToday (days)) =>
        new QuantityControl[LoadableModel] ().
          apply (model.zoom (_._2).variable (_.derivation.asInstanceOf[PriorToToday].days, setListPriorDays))
      case _ => <.span ("")
    })
  )

  def rowWithFeature (row: ReactElement, featureControl: ReactElement): ReactElement =
    <.div (bss.row, compact, <.div (bss.columns (10), row), <.div (bss.columns (2), featureControl))
}
