package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.model.generic.ScopedVariable
import reorganise.client.styles.{BootstrapAlertStyles, GlobalStyles}
import scalacss.ScalaCssReact._

class VariableDropdown[Model <: AnyRef, Item] (fieldID: String, label: Item => String, style: BootstrapAlertStyles.Value) {
  def bss = GlobalStyles.bootstrapStyles

  val component = ReactComponentB[ScopedVariable[Model, Vector[Item], Item]] ("Dropdown")
    .render_P (p =>
      <.div (bss.dropdown, ^.id := fieldID,
        <.button (bss.buttonOpt (style), bss.dropdownButton, ^.tpe := "button", bss.dataToggle := "dropdown",
          label (p.value) + " ", <.span (bss.caret)),
        <.ul (bss.dropdownMenu,
          p.scope.map (item => <.li (bss.listGroup.item, label (item), ^.onClick --> p.set (item)))
        )
      )
    ).build

  def apply (data: ScopedVariable[Model, Vector[Item], Item]): ReactElement =
    data.createEditor (component)
}
