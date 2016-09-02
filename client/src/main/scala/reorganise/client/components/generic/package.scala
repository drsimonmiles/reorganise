package reorganise.client.components

import scala.language.implicitConversions

package object generic {
  implicit def jq2bootstrap (jq: JQuery): BootstrapJQuery = jq.asInstanceOf[BootstrapJQuery]
  val jQuery = JQueryStatic
}
