package http.client

import scalaj.http._

class ScalajHttpClientSample extends CommonHttpClientSample {

  def Get(url: String, headers: Map[String, String]): CustomResponse = httpRequest("GET", url, headers)

  def Post(url: String, headers: Map[String, String], requestBody: String): CustomResponse = httpRequest("POST", url, headers, requestBody)

  def Put(url: String, headers: Map[String, String], requestBody: String): CustomResponse = httpRequest("PUT", url, headers, requestBody)

  def Delete(url: String, headers: Map[String, String]): CustomResponse = httpRequest("DELETE", url, headers)

  def httpRequest(webMethod: String, url: String, headers: Map[String, String], requestBody: String = ""): CustomResponse = {

    var request = Http(url)
      .headers(headers)
      .timeout(connTimeoutMs = connectTimeoutMillis, readTimeoutMs = readTimeoutMillis)
    if (requestBody != "") {
      request = request.postData(requestBody)
    }
    request = request.method(webMethod) // when request is put methods, Next to web method of postData method.
    val response: HttpResponse[String] = request.execute()

    return extractResponse(response)
  }

  def extractResponse(res: HttpResponse[String]): CustomResponse = {

    val statusCode = res.code
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
    val body = res.body
    return CustomResponse(statusCode, httpStatus, body)
  }

}
