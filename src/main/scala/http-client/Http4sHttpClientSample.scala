
package sample.httpclient

import cats.effect.IO
import org.http4s.Status.Successful
import org.http4s.client.Client
import org.http4s.client.blaze.Http1Client
import org.http4s.{Method, ParseFailure, Request, Response, Status, Uri}

import scala.concurrent.duration._

class Http4sHttpClientSample extends CommonHttpClientSample {

  val readTimeout: Duration = 5.seconds

  def Get(url: String, headers: Map[String, String]): CustomResponse = httpRequest(Method.GET, url, headers)

  def Post(url: String, headers: Map[String, String], requestBody: String): CustomResponse = httpRequest(Method.POST, url, headers, requestBody)

  def Put(url: String, headers: Map[String, String], requestBody: String): CustomResponse = httpRequest(Method.PUT, url, headers, requestBody)

  def Delete(url: String, headers: Map[String, String]): CustomResponse = httpRequest(Method.DELETE, url, headers)

  def httpRequest(webMethod: Method, url: String, headers: Map[String, String], requestBody: String = ""): CustomResponse = {

    val uri: Either[ParseFailure, Uri] = Uri.fromString(url)
    val request = Request[IO](webMethod,uri.right.get)

    val httpClient: Client[IO] = Http1Client[IO]().unsafeRunSync
    val res = httpClient.fetch(request){
      case Successful(s) => IO.pure(s)
      case _ => null
    }.unsafeRunSync()

    println(res)
    /*
    val response = httpClient.expect[Response[IO]](request){
      case Successful(s) => s
      case _ => null
    }.unsafeRunSync()*/

    return extractResponse(res)
  }

  def extractResponse(response:Response[IO]): CustomResponse = {

    val statusCode = response.status.code
    val httpStatus = statusCode match {
      case Status.Ok.code => "OK"
      case Status.Created.code => "CREATED"
      case Status.NoContent.code => "NO_CONTENT"
      case Status.Unauthorized.code => "UNAUTHORIZED"
      case Status.BadRequest.code => "BAD_REQUEST"
      case Status.NotFound.code => "NOT_FOUND"
      case Status.InternalServerError.code => "INTERNAL_SERVER_ERROR"
      case _ => "OTHER_STATUS_CODE"
    }

    //TODO must convert Stream[IO,String] type to String type
    //val body = response.bodyAsText.toString()
    return new CustomResponse(statusCode, httpStatus,"")
  }
}
