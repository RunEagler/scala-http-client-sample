

object ScalaIoSourceSample extends App{

  val baseURL = "http://localhost:8080"
  def Get(uri: String): String = {

    val response = scala.io.Source.fromURL(uri)
    return response.mkString
  }

  val res = Get(LocalURL.deleteURI(baseURL,1,5))
  println(res)
}
