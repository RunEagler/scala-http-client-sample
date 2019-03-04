package http.client

import org.apache.http.HttpStatus
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods._
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.apache.http.util.EntityUtils


class ApacheHttpClientSample()extends CommonHttpClientSample {

  def Get(url: String, headers: Map[String, String]): CustomResponse = httpRequestNoBody(headers, new HttpGet(url))

  def Post(url: String, headers: Map[String, String], requestBody: String): CustomResponse = httpRequest(headers, new HttpPost(url), requestBody)

  def Put(url: String, headers: Map[String, String], requestBody: String): CustomResponse = httpRequest(headers, new HttpPut(url), requestBody)

  def Delete(url: String, headers: Map[String, String]): CustomResponse = httpRequestNoBody(headers, new HttpDelete(url))

  //timeout setting
  private val requestConfig: RequestConfig = RequestConfig.custom()
    .setConnectTimeout(this.connectTimeoutMillis)
    .setConnectionRequestTimeout(this.readTimeoutMillis)
    .build()

  private val defaultHttpClient: CloseableHttpClient = HttpClients.createDefault()

  def extractResponse(response: CloseableHttpResponse): CustomResponse = {

    val statusCode = response.getStatusLine.getStatusCode()
    val httpStatus = statusCode match {
      case HttpStatus.SC_OK => "OK"
      case HttpStatus.SC_CREATED => "CREATED"
      case HttpStatus.SC_UNAUTHORIZED => "UNAUTHORIZED"
      case HttpStatus.SC_BAD_REQUEST => "BAD_REQUEST"
      case HttpStatus.SC_NOT_FOUND => "NOT_FOUND"
      case HttpStatus.SC_NO_CONTENT => "NO_CONTENT"
      case HttpStatus.SC_INTERNAL_SERVER_ERROR => "INTERNAL_SERVER_ERROR"
      case _ => "OTHER_STATUS_CODE"
    }
    val body = statusCode match {
      case HttpStatus.SC_OK | HttpStatus.SC_CREATED => EntityUtils.toString(response.getEntity)
      case _ => ""
    }
    return new CustomResponse(statusCode, httpStatus, body)
  }

  def httpRequest(headers: Map[String, String], webMethod: HttpEntityEnclosingRequestBase, body: String): CustomResponse = {
    webMethod.setConfig(requestConfig)
    headers.foreach {
      case (k, v) => {
        webMethod.addHeader(k, v)
      }
    }
    webMethod.setEntity(new StringEntity(body))
    val response = defaultHttpClient.execute(webMethod)
    return extractResponse(response)
  }

  def httpRequestNoBody(headers: Map[String, String], webMethod: HttpRequestBase): CustomResponse = {
    webMethod.setConfig(requestConfig)
    headers.foreach {
      case (k, v) => {
        webMethod.addHeader(k, v)
      }
    }
    val response = defaultHttpClient.execute(webMethod)
    return extractResponse(response)
  }
}

