package reorganise.client.styles

import scalacss.Defaults._

object GlobalStyles extends StyleSheet.Inline {
  import dsl._

  style (unsafeRoot ("body")(
    paddingTop (70.px))
  )

  val compact = style (paddingTop (1.px), paddingBottom (1.px), marginTop (0.px), marginBottom (0.px))

  val bootstrapStyles = new BootstrapStyles
}
