package reorganise.client.comms

import boopickle.Default._
import java.nio.ByteBuffer
import org.scalajs.dom
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js.typedarray._

object AjaxClient extends autowire.Client[ByteBuffer, Pickler, Pickler] {
  def doCall (req: Request): Future[ByteBuffer] = {
    dom.ext.Ajax.post (
      url = "/api/" + req.path.mkString ("/"),
      data = Pickle.intoBytes (req.args),
      responseType = "arraybuffer",
      headers = Map ("Content-Type" -> "application/octet-stream")
    ).map (r => TypedArrayBuffer.wrap (r.response.asInstanceOf[ArrayBuffer]))
  }

  def read[Result: Pickler] (p: ByteBuffer): Result = Unpickle[Result].fromBytes (p)

  def write[Result: Pickler] (r: Result): ByteBuffer = Pickle.intoBytes (r)
}
