package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.model.generic.Variable
import reorganise.client.styles.{BootstrapAlertStyles, GlobalStyles}
import scalacss.ScalaCssReact._

class Dropdown[Model <: AnyRef, Item] (fieldID: String, label: Item => String,
                                       style: BootstrapAlertStyles.Value, items: Seq[Item]) {
  def bss = GlobalStyles.bootstrapStyles

  val component = ReactComponentB[Variable[Model, Item]] ("Dropdown")
    .render_P (p =>
      <.div (bss.dropdown, ^.id := fieldID,
        <.button (bss.buttonOpt (style), bss.dropdownButton, ^.tpe := "button", bss.dataToggle := "dropdown",
          label (p.value) + " ", <.span (bss.caret)),
        <.ul (bss.dropdownMenu,
          items.map (item => <.li (bss.listGroup.item, label (item), ^.onClick --> p.set (item)))
        )
      )
    ).build

  def apply (data: Variable[Model, Item])(implicit feq: diode.FastEq[_ >: Item]): ReactElement =
    //data.createEditor (component)
    component (data)
}
