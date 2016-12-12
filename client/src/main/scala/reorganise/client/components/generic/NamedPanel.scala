package reorganise.client.components.generic

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.prefix_<^._
import reorganise.client.model.generic.Variable
import reorganise.client.styles.GlobalStyles
import scalacss.ScalaCssReact._

class NamedPanel[Model <: AnyRef] (headerPlaceholder: String) {
  @inline private def bss = GlobalStyles.bootstrapStyles

  val component = ReactComponentB[Variable[Model, String]]("NamedPanel")
    .renderPC ((_, p, c) =>
      <.div (bss.panel,
        <.div (bss.panelHeading, new FocusedTextField (headerPlaceholder) (p)),
        <.div (bss.panelBody, c)
      )
    ).build

  def apply (header: Variable[Model, String], children: ReactNode*) =
    //header.createEditor (component, children: _*)
    component (header, children)
}
