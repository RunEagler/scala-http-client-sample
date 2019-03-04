package sample.httpclient

import dispatch._
import org.asynchttpclient.util.HttpConstants.Methods
import org.asynchttpclient.{RequestBuilder, Response}

class DispatcherHttpClientSample extends CommonHttpClientSample {

  def Get(url: String, headers: Map[String, String]): CustomResponse = httpRequest(Methods.GET, url, headers)

  def Post(url: String, headers: Map[String, String], requestBody: String): CustomResponse = httpRequest(Methods.POST, url, headers, requestBody)

  def Put(url: String, headers: Map[String, String], requestBody: String): CustomResponse = httpRequest(Methods.PUT, url, headers, requestBody)

  def Delete(url: String, headers: Map[String, String]): CustomResponse = httpRequest(Methods.DELETE, url, headers)

  def httpRequest(webMethod: String, uri: String, headers: Map[String, String], requestBody: String = ""): CustomResponse = {

    val request = setRequest(webMethod,uri,headers,requestBody)
    val futureResponse = Http.default.client.prepareRequest(request).execute()
    return extractResponse(futureResponse.get())

  }

  def setRequest(webMethod: String, uri: String, headers: Map[String, String], requestBody: String): RequestBuilder = {

    var request = new RequestBuilder(webMethod)
      .setUrl(uri)
      .setReadTimeout(readTimeoutMillis)
      .setRequestTimeout(connectTimeoutMillis)

    for(header <- headers){
      request = request.setHeader(header._1,header._2)
    }
    if(requestBody != ""){
      request = request.setBody(requestBody)
    }

    return request
  }

  def extractResponse(res: Response): CustomResponse = {

    val statusCode = res.getStatusCode
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
    val body = res.getResponseBody
    return CustomResponse(statusCode, httpStatus, body)
  }


}
