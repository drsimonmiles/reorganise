package reorganise.client

import scala.language.implicitConversions

package object components {
  implicit def jq2bootstrap (jq: JQuery): BootstrapJQuery = jq.asInstanceOf[BootstrapJQuery]
  val jQuery = JQueryStatic
}
