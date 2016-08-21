package reorganise.client.styles

import scalacss.Defaults._

object GlobalStyles extends StyleSheet.Inline {
  import dsl._

  style (unsafeRoot ("body")(
    paddingTop (70.px))
  )

  val rowEqHeight = style (display :=! "-webkit-box", display :=! "-webkit-flex", display :=! "-ms-flexbox", display.flex)

  val bootstrapStyles = new BootstrapStyles
}
