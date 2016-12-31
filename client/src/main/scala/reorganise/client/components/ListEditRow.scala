package reorganise.client.components

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.GenericComponents._
import reorganise.client.components.SpecificComponents._
import reorganise.client.model.ModelVariables._
import directed.VariableOps._
import reorganise.client.model.{DerivationFeature, ListFeature, OrderFeature, PriorDaysFeature}
import reorganise.client.styles.BootstrapAlertStyles.primary
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{Derivation, NoRestriction, NoTasks, PriorToToday, TaskList, VisibleTasks}
import scalacss.ScalaCssReact._

object ListEditRow {
  @inline private def bss = bootstrapStyles

  val derivations = Vector (NoTasks, NoRestriction, PriorToToday (0))

  case class Props (feature: ListFeature, visible: ModelProxy[VisibleTasks], list: ModelProxy[TaskList])

  val component = ReactComponentB[Props] ("ListEditRow")
    .render_P { p =>
      val name = focusedTextField ("write list name") (p.list.variable (_.name, setListName))
      val feature = p.feature match {
        case OrderFeature =>
          reorderControl (p.list.value.id) (p.visible.variable (_.listOrder.map (list => (list, true)), setAllListsOrder))
        case DerivationFeature =>
          if (p.list.value.derivation.isDefined)
            dropdown[Derivation]("derivation", _.name, primary, derivations).
              apply (p.list.variable (_.derivation.get, setListDerivation))
          else <.span ("").render
        case PriorDaysFeature => p.list.value.derivation match {
          case Some (PriorToToday (_)) =>
            quantityControl (p.list.variable (_.derivation.get.asInstanceOf [PriorToToday].days, setListPriorDays))
          case _ => <.span ("").render
        }
      }

      <.div (bss.row, compact,
        <.div (bss.columns (10), <.div (compact, name)),
        <.div (bss.columns (2), feature)
      )
    }.build

  def apply (feature: ListFeature, visible: ModelProxy[VisibleTasks], list: ModelProxy[TaskList]): ReactElement =
    component (Props (feature, visible, list))
}
