
package sample.httpclient.test

import net.liftweb.json
import net.liftweb.json.{DefaultFormats, Extraction, compactRender}
import org.apache.http.HttpHeaders
import org.scalatest.junit.AssertionsForJUnit
import sample.httpclient.CommonHttpClientSample


object CommonHttpClientSampleTest extends AssertionsForJUnit{

  val baseURL = "http://localhost:8080"
  val headers: Map[String, String] = Map(
    HttpHeaders.CONTENT_TYPE -> "application/json",
    "TOKEN" -> "XXXX",
  )
  val OK = "OK"
  val CREATED = "CREATED"
  val NO_CONTENT = "NO_CONTENT"
  implicit val formats = DefaultFormats

  def testGet(httpClient: CommonHttpClientSample): Unit = {
    val res = httpClient.Get(LocalURL.retrieveURI(baseURL, 1, 3), headers)
    val expectJsonObject = User(name = "user3", age = 32, programming_skills = List(
      ProgrammingSkill(item = "java"),
      ProgrammingSkill(item = "c#"),
      ProgrammingSkill(item = "javascript"),
    ))
    val actual = json.parse(res.body)

    assert(res.status == OK)
    assert(res.statusCode == 200)
    equalResponseBody(expectJsonObject, actual.extract[User])

  }

  def testPost(httpClient: CommonHttpClientSample): Unit = {
    val requestBodyObj = User(name = "user6", age = 35, programming_skills = List(
      ProgrammingSkill(item = "golang"),
      ProgrammingSkill(item = "c++"),
      ProgrammingSkill(item = "perl"),
    ))
    val requestBody = compactRender(Extraction.decompose(requestBodyObj))
    val res = httpClient.Post(LocalURL.createURI(baseURL, 1), headers, requestBody)
    val expectJsonObject = User(name = "user6", age = 35, programming_skills = List(
      ProgrammingSkill(item = "golang"),
      ProgrammingSkill(item = "c++"),
      ProgrammingSkill(item = "perl"),
    ))
    val actual = json.parse(res.body)

    assert(res.status == CREATED)
    assert(res.statusCode == 201)
    equalResponseBody(expectJsonObject, actual.extract[User])
  }

  def testPut(httpClient: CommonHttpClientSample): Unit = {
    val requestBodyObj = User(name = "user3", age = 22, programming_skills = List(
      ProgrammingSkill(item = "golang"),
    ))
    val requestBody = compactRender(Extraction.decompose(requestBodyObj))
    val res = httpClient.Put(LocalURL.updateURI(baseURL, 1, 3), headers, requestBody)

    assert(res.status == NO_CONTENT)
    assert(res.statusCode == 204)
    assert(res.body == "")

  }

  def testDelete(httpClient: CommonHttpClientSample): Unit = {
    val res = httpClient.Delete(LocalURL.deleteURI(baseURL, 1, 1), headers)

    assert(res.status == NO_CONTENT)
    assert(res.statusCode == 204)
    assert(res.body == "")

  }

  def equalResponseBody(expectObj: User, actualObj: User): Unit = {
    if (expectObj.user_id != 0) {
      assert(expectObj.user_id == actualObj.user_id)
    }
    assert(expectObj.age == actualObj.age)
    assert(expectObj.name == actualObj.name)
    assert(expectObj.programming_skills.length == actualObj.programming_skills.length)

    if (expectObj.programming_skills.length == actualObj.programming_skills.length) {
      val mergeSkills = expectObj.programming_skills.zip(actualObj.programming_skills)
      for (skill <- mergeSkills) {
        assert(skill._1 == skill._2)
      }
    }
  }
}
