package sample.httpclient.test

import org.junit.Test
import org.scalatest.junit.AssertionsForJUnit
import sample.httpclient.ApacheHttpClientSample

class ApacheHttpClientSampleTest extends AssertionsForJUnit {

  val httpClient = new ApacheHttpClientSample()

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