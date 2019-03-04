package http.client

import com.softwaremill.sttp._

import scala.concurrent.duration._

class SttpHttpClientSample extends CommonHttpClientSample {

  implicit val backend = HttpURLConnectionBackend()
  val readTimeout: Duration = 5.seconds

  def Get(url: String, headers: Map[String, String]): CustomResponse = httpRequest(Method.GET, url, headers)

  def Post(url: String, headers: Map[String, String], requestBody: String): CustomResponse = httpRequest(Method.POST, url, headers, requestBody)

  def Put(url: String, headers: Map[String, String], requestBody: String): CustomResponse = httpRequest(Method.PUT, url, headers, requestBody)

  def Delete(url: String, headers: Map[String, String]): CustomResponse = httpRequest(Method.DELETE, url, headers)

  def httpRequest(webMethod: Method, url: String, headers: Map[String, String], requestBody: String = ""): CustomResponse = {

    var request = sttp
      .method(webMethod, uri"$url")
      .headers(headers)
      .readTimeout(DefaultReadTimeout)
    if (requestBody != "") {
      request = request.body(requestBody)
    }
    val response = request.send()
    extractResponse(response)
  }

  def extractResponse(response: Response[String]): CustomResponse = {

    val statusCode = response.code
    val httpStatus = statusCode match {
      case StatusCodes.Ok => "OK"
      case StatusCodes.Created => "CREATED"
      case StatusCodes.Unauthorized => "UNAUTHORIZED"
      case StatusCodes.BadRequest => "BAD_REQUEST"
      case StatusCodes.NotFound => "NOT_FOUND"
      case StatusCodes.NoContent => "NO_CONTENT"
      case StatusCodes.InternalServerError => "INTERNAL_SERVER_ERROR"
      case _ => "OTHER_STATUS_CODE"
    }
    val body = response.body.right.get
    return new CustomResponse(statusCode, httpStatus, body)
  }
}
