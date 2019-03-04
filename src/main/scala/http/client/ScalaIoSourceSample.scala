package http.client

object ScalaIoSourceSample extends App{

  val baseURL = "http://localhost:8080"
  def Get(uri: String): String = {
    val response = scala.io.Source.fromURL(uri)
    return response.mkString
  }
}
