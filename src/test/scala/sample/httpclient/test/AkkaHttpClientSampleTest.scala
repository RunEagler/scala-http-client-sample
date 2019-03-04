package sample.httpclient.test

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import sample.httpclient.AkkaHttpClientSample

class AkkaHttpClientSampleTest extends AssertionsForJUnit {

  val httpClient = new AkkaHttpClientSample()

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