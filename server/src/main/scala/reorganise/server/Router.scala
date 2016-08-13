package reorganise.server

import boopickle.Default._
import java.nio.ByteBuffer

object Router extends autowire.Server[ByteBuffer, Pickler, Pickler] {
  def read[R: Pickler] (p: ByteBuffer) = Unpickle[R].fromBytes (p)
  def write[R: Pickler] (r: R) = Pickle.intoBytes (r)
}
