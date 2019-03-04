package http.client

import skinny.http._

class SkinnyHttpClientSample extends CommonHttpClientSample {

  def Get(url: String, headers: Map[String, String]): CustomResponse = httpRequest(Method.GET, url, headers)

  def Post(url: String, headers: Map[String, String], requestBody: String): CustomResponse = httpRequest(Method.POST, url, headers, requestBody)

  def Put(url: String, headers: Map[String, String], requestBody: String): CustomResponse = httpRequest(Method.PUT, url, headers, requestBody)

  def Delete(url: String, headers: Map[String, String]): CustomResponse = httpRequest(Method.DELETE, url, headers)

  def httpRequest(webMethod: Method, url: String, headers: Map[String, String], requestBody: String = ""): CustomResponse = {

    val request = new Request(url)
    request.connectTimeoutMillis(this.readTimeoutMillis)
    request.readTimeoutMillis(this.connectTimeoutMillis)
    if (requestBody != "") {
      request.body(requestBody.getBytes)
    }
    headers.foreach {
      case (k, v) => {
        request.header(k, v)
      }
    }
    val response = HTTP.request(webMethod, request)

    return extractResponse(response)
  }

  def extractResponse(res: Response): CustomResponse = {

    val statusCode = res.status
    val httpStatus = statusCode match {
      case 200 => "OK"
      case 201 => "CREATED"
      case 204 => "NO_CONTENT"
      case 401 => "UNAUTHORIZED"
      case 400 => "BAD_REQUEST"
      case 404 => "NOT_FOUND"
      case 500 => "INTERNAL_SERVER_ERROR"
      case _ => "OTHER_STATUS_CODE"
    }
    val body = res.textBody
    return CustomResponse(statusCode, httpStatus, body)
  }


}
