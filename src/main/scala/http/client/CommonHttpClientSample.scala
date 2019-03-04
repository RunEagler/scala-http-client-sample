package http.client


trait WebMethod {
  def Get(url: String, headers: Map[String, String]): CustomResponse

  def Post(url: String, headers: Map[String, String], requestBody: String): CustomResponse

  def Put(url: String, headers: Map[String, String], requestBody: String): CustomResponse

  def Delete(url: String, headers: Map[String, String]): CustomResponse
}

abstract class CommonHttpClientSample extends WebMethod {

  val readTimeoutMillis = 5000
  var connectTimeoutMillis = 5000
}
