package http.client

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit

class SkinnyHttpClientSampleTest extends AssertionsForJUnit {

  val httpClient = new SkinnyHttpClientSample()

  @Test
  def testGet() {
    CommonHttpClientSampleTest.testGet(httpClient)
  }

  @Test
  def testPost() {
    CommonHttpClientSampleTest.testPost(httpClient)
  }

  @Test
  def testPut(): Unit ={
    CommonHttpClientSampleTest.testPut(httpClient)
  }

  @Test
  def testDelete(): Unit ={
    CommonHttpClientSampleTest.testDelete(httpClient)
  }
}