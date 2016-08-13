package reorganise.server

import boopickle.Default._
import com.google.inject.Inject
import java.nio.ByteBuffer
import play.api.mvc._
import play.api.{Configuration, Environment}
import reorganise.shared.comms.TasksAPI
import scala.concurrent.ExecutionContext.Implicits.global

class Application @Inject() (implicit val config: Configuration, env: Environment) extends Controller {
  val tasksService = new TasksService ()

  def index = Action {
    Ok (views.html.index ("Reorganise"))
  }

  def autowireApi (path: String) = Action.async (parse.raw) {
    implicit request =>
      // get the request body as ByteString
      val b = request.body.asBytes (parse.UNLIMITED).get

      // call Autowire route
      Router.route[TasksAPI] (tasksService) (
        autowire.Core.Request (path.split("/"), Unpickle[Map[String, ByteBuffer]].fromBytes (b.asByteBuffer))
      ).map (buffer => {
        val data = Array.ofDim[Byte] (buffer.remaining ())
        buffer.get(data)
        Ok (data)
      })
  }

  def logging = Action (parse.anyContent) {
    implicit request =>
      request.body.asJson.foreach { msg =>
        println (s"CLIENT - $msg")
      }
      Ok("")
  }
}
