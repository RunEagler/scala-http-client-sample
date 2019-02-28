
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods._
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.apache.http.util.EntityUtils
import org.apache.http.{HttpHeaders, HttpStatus}

/**
  * Returns the text (content) from a REST URL as a String.
  * Returns a blank String if there was a problem.
  * This function will also throw exceptions if there are problems trying
  * to connect to the url.
  *
  * @param url A complete URL, such as "http://foo.com/bar"
  * @param connectionTimeout The connection timeout, in ms.
  * @param socketTimeout The socket timeout, in ms.
  */
object ApacheHttpClientSample extends App{

  val baseURL = "http://localhost:8080"

  case class CustomResponse(statusCode:Int,status:String,body:String)

  class CustomHttpClient(){

    //var httpClient :CloseableHttpClient
    private val i = 0
    //httpClient = HttpClients.custom().build()

    private val requestConfig :RequestConfig = RequestConfig.custom()
      .setConnectTimeout(5000)
      .setSocketTimeout(5000)
      .setConnectionRequestTimeout(5000)
      .build()

    private val defaultHttpClient:CloseableHttpClient = HttpClients.createDefault()

    def extractResponse(response: CloseableHttpResponse): CustomResponse = {

      val statusCode = response.getStatusLine.getStatusCode()
      val httpStatus = statusCode match {
        case HttpStatus.SC_OK => "OK"
        case HttpStatus.SC_UNAUTHORIZED => "UNAUTHORIZED"
        case HttpStatus.SC_BAD_REQUEST => "BAD_REQUEST"
        case HttpStatus.SC_NOT_FOUND => "NOT_FOUND"
        case HttpStatus.SC_INTERNAL_SERVER_ERROR => "INTERNAL_SERVER_ERROR"
        case _ => "OTHER_STATUS_CODE"
      }
      val body = EntityUtils.toString(response.getEntity)

      return new CustomResponse(statusCode,httpStatus,body)
    }

    def Get(url: String,headers: Map[String,String]): CustomResponse = {
      val httpGet = new HttpGet(url)
      httpGet.setConfig(requestConfig)
      headers.foreach{case(k,v) => {
        httpGet.addHeader(k,v)
      }}
      val response = defaultHttpClient.execute(httpGet)
      return extractResponse(response)
    }

    def Post(url: String,headers: Map[String,String],requestBody:String): CustomResponse = {
      val httpPost = new HttpPost(url)
      httpPost.setConfig(requestConfig)
      headers.foreach{case(k,v) => {
        httpPost.addHeader(k,v)
      }}
      httpPost.setEntity(new StringEntity(requestBody))
      val response = defaultHttpClient.execute(httpPost)
      return extractResponse(response)
    }

    def Put(url: String,headers:Map[String,String],requestBody:String):CustomResponse = {
      val httpPut = new HttpPut(url)
      httpPut.setConfig(requestConfig)
      headers.foreach{case(k,v) => {
        httpPut.addHeader(k,v)
      }}
      httpPut.setEntity(new StringEntity(requestBody))
      val response = defaultHttpClient.execute(httpPut)
      return extractResponse(response)
    }

    def Delete(url: String,headers:Map[String,String],requestBody:String):CustomResponse = {
      val httpDelete = new HttpDelete(url)
      httpDelete.setConfig(requestConfig)
      headers.foreach{case(k,v) => {
        httpDelete.addHeader(k,v)
      }}
      val response = defaultHttpClient.execute(httpDelete)
      return extractResponse(response)
    }
  }


    /*
    val httpResponse = httpClient.execute(new HttpGet(url))
    val entity = httpResponse.getEntity
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent
      content = io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close
    }
    httpClient.getConnectionManager.shutdown
    content*/

  val httpClient = new CustomHttpClient()

  val headers :Map[String,String] = Map(
    HttpHeaders.CONTENT_TYPE -> "application/json",
    "TOKEN" -> "XXXX",
  )
  val res = httpClient.Get(LocalURL.retrieveURI(baseURL,1,3),headers)
  println(res)

}
