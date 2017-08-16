package org.dbpedia.extraction.server.resources.rml

import java.io.InputStream

import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.{ContentType, StringEntity}
import org.apache.http.impl.client.{BasicResponseHandler, DefaultHttpClient}
import org.scalatest.FunSuite

/**
  * Created by wmaroy on 12.08.17.
  */
class TemplatesAnalyzeAPITest extends FunSuite {

  test("testGetTemplates") {

    val tuple = postTest("/templateTest/mapping.json",
      "/templateTest/expected.json")

    assert(tuple._1.equals(tuple._2))

  }

  test("testGetConditional") {

    val tuple = postTest("/conditionalTemplateAnalyzingTest/mapping.json",
      "/conditionalTemplateAnalyzingTest/output.json")

  }

  test("testGetConditional2") {

    val tuple = postTest("/conditionalTemplateAnalyzingTest2/mapping.json",
      "/conditionalTemplateAnalyzingTest2/output.json")

  }

  private def postTest(resource : String, expected : String) : (String, String) = {

    val stream : InputStream = getClass.getResourceAsStream(resource)
    val json = scala.io.Source.fromInputStream( stream ).getLines.mkString

    val stream2 : InputStream = getClass.getResourceAsStream(expected)
    val expectedJson = scala.io.Source.fromInputStream( stream2 ).getLines.mkString

    val httpClient = new DefaultHttpClient()
    val requestEntity = new StringEntity(
      json,
      ContentType.APPLICATION_JSON)

    val postMethod = new HttpPost("http://localhost:9999/server/rml/templates")
    postMethod.setEntity(requestEntity)
    val rawResponse = httpClient.execute(postMethod)
    val responseString = new BasicResponseHandler().handleResponse(rawResponse)

    println("++++ Response body:")
    println(responseString)
    println("++++")

    (responseString, expectedJson)

  }


}
