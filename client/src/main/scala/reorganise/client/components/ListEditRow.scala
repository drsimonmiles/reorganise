package reorganise.client.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.components.GenericComponents._
import reorganise.client.components.SpecificComponents._
import reorganise.client.model.ModelVariables._
import reorganise.client.model.{ClientState, DerivationFeature, ListFeature, OrderFeature, PriorDaysFeature}
import reorganise.client.styles.BootstrapAlertStyles.primary
import reorganise.client.styles.GlobalStyles._
import reorganise.shared.model.{Derivation, NoRestriction, NoTasks, PriorToToday, TaskList}
import scalacss.ScalaCssReact._

object ListEditRow {
  @inline private def bss = bootstrapStyles

  val derivations = Vector (NoTasks, NoRestriction, PriorToToday (0))

  case class Props (feature: ListFeature, visible: ClientState, list: TaskList)

  private val listNameField = focusedTextField ("write list name")

  val component = ReactComponentB[Props] ("ListEditRow")
    .render_P { p =>
      val name = listNameField (SetListName.from (p.list))
      val feature = p.feature match {
        case OrderFeature =>
          reorderControl (p.list.id) (SetAllListsOrder.from (p.visible.listOrder))
        case DerivationFeature =>
          if (p.list.derivation.isDefined)
            dropdown[Derivation]("derivation", _.name, primary, derivations) (SetListDerivation.from (p.list))
          else <.span ("").render
        case PriorDaysFeature => p.list.derivation match {
          case Some (PriorToToday (_)) => quantityControl (SetListPriorDays.from (p.list))
          case _ => <.span ("").render
        }
      }

      <.div (bss.row, compact,
        <.div (bss.columns (10), <.div (compact, name)),
        <.div (bss.columns (2), feature)
      )
    }.build

  def apply (feature: ListFeature, visible: ClientState, list: TaskList): ReactElement =
    component (Props (feature, visible, list))
}
