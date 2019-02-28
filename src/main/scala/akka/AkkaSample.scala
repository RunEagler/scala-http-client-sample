import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpRequest, HttpResponse, Uri}
import akka.stream.ActorMaterializer

import scala.util.{Failure, Success}
import scala.concurrent.Future

object AkkaSample extends App{

  val baseURL = "http://localhost:8080"
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher
  case class Header(key:String,value:String)

  def Get(uri: String,headers:List[Header]): Future[HttpResponse] = {

    val rawHeaders = headers.map(header => RawHeader(header.key,header.value))
    val req = HttpRequest(GET, uri = Uri(uri))
      .withHeaders(
        rawHeaders: _*,
      )
    val responseFuture = Http().singleRequest(req)

    responseFuture
      .onComplete {
        case Success(res) => println(res)
        case Failure(_)   => sys.error("something wrong")
      }
    return responseFuture
  }

  def Post(uri: String): Future[HttpResponse] = {


    return null
  }

  def Delete(uri: String): Future[HttpResponse] = {

    return null
  }
  def Put(uri: String): Future[HttpResponse] = {

    return null
  }

  val header = List(
    Header("TOKEN","XXXX"),
    Header("Contenty-Type","application/json")
  )

  val res = Get(LocalURL.retrieveURI(baseURL,1,1),header)
  println(res)
}