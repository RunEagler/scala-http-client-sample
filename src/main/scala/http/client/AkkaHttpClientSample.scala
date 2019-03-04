package http.client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

class AkkaHttpClientSample() extends CommonHttpClientSample{

  implicit val timeout = Timeout(5 seconds)
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  def Get(uri: String, headers: Map[String, String]): CustomResponse = httpRequest(GET, uri, headers)

  def Post(uri: String, headers: Map[String, String], body: String): CustomResponse = httpRequest(POST, uri, headers, body)

  def Put(uri: String, headers: Map[String, String], body: String): CustomResponse = httpRequest(PUT, uri, headers, body: String)

  def Delete(uri: String, headers: Map[String, String]): CustomResponse = httpRequest(DELETE, uri, headers)


  def extractResponse(res: HttpResponse): CustomResponse = {

    val statusCode = res.status.intValue()
    val httpStatus = statusCode match {
      case StatusCodes.OK.intValue => "OK"
      case StatusCodes.Created.intValue => "CREATED"
      case StatusCodes.Unauthorized.intValue => "UNAUTHORIZED"
      case StatusCodes.BadRequest.intValue => "BAD_REQUEST"
      case StatusCodes.NotFound.intValue => "NOT_FOUND"
      case StatusCodes.NoContent.intValue => "NO_CONTENT"
      case StatusCodes.InternalServerError.intValue => "INTERNAL_SERVER_ERROR"
      case _ => "OTHER_STATUS_CODE"
    }
    val body = Unmarshal(res.entity).to[String]
    return CustomResponse(statusCode, httpStatus, Await.result(body, timeout.duration))
  }

  def httpRequest(webMethod: HttpMethod, uri: String, headers: Map[String, String], requestBody: String = ""): CustomResponse = {

    val rawHeaders = headers.map {
      case (k, v) => {
        RawHeader(k, v)
      }
    }.toList

    val httpEntity = requestBody match {
      case "" => HttpEntity.Empty
      case _ => HttpEntity(requestBody)
    }
    val req = HttpRequest(webMethod, uri = Uri(uri), entity = httpEntity)
      .withHeaders(
        rawHeaders: _*,
      )
    val responseFuture = Http().singleRequest(req)
    return extractResponse(Await.result(responseFuture, timeout.duration)) //同期取得
  }

}