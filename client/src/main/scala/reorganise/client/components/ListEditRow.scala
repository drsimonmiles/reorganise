package reorganise.client.components

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.FeatureControls._
import reorganise.client.components.generic.{Dropdown, FocusedTextField, QuantityControl, ReorderControl}
import reorganise.client.components.generic.BasicComponents._
import reorganise.client.model.ModelVariables._
import reorganise.client.model.generic.VariableOps._
import reorganise.client.model.{DerivationFeature, ListFeature, OrderFeature, PriorDaysFeature}
import reorganise.client.styles.BootstrapAlertStyles.primary
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{Derivation, PriorToToday, TaskList, VisibleTasks}
import scalacss.ScalaCssReact._

object ListEditRow {
  case class Props (feature: ListFeature, visible: ModelProxy[VisibleTasks], list: ModelProxy[TaskList])

  val component = ReactComponentB[Props] ("ListEditRow")
    .render_P { p =>
      val name = new FocusedTextField ("write list name") (p.list.variable (_.name, setListName))
      val row = <.div (compact, name)
      val feature = p.feature match {
        case OrderFeature =>
          reorderControl (p.list.value.id) (p.visible.variable (_.listOrder.map (list => (list, true)), setAllListsOrder))
        case DerivationFeature =>
          if (p.list.value.derivation.isDefined)
            new Dropdown[Derivation]("derivation", _.name, primary, derivations).
              apply (p.list.variable (_.derivation.get, setListDerivation))
          else <.span ("").render
        case PriorDaysFeature => p.list.value.derivation match {
          case Some (PriorToToday (_)) =>
            quantityControl (p.list.variable (_.derivation.get.asInstanceOf [PriorToToday].days, setListPriorDays))
          case _ => <.span ("").render
        }
      }

      rowWithFeature (row, feature)
    }.build

  def apply (feature: ListFeature, visible: ModelProxy[VisibleTasks], list: ModelProxy[TaskList]): ReactElement =
    component (Props (feature, visible, list))
}
