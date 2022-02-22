package com.gu.flowlogs

import io.circe._
import sttp.client3._
import cats.implicits._
import io.circe.generic.semiauto.deriveEncoder
import io.circe.syntax.EncoderOps


object FlowLogs {
  val backend = HttpURLConnectionBackend()
  implicit val flowLogEncoder: Encoder[FlowLog] = deriveEncoder[FlowLog]

  def getBadActorsIn10s(logs: List[FlowLog]): List[List[FlowLog]] =
    logs.filter(_.srcaddr == ExampleData.badActorIpAddress).grouped(10).toList

  def main(args: Array[String]): Unit = {
    // pull out HTTP request into a function so it returns an Either
    // make main a for comp again (coz we'll have 2 eithers by then)
    // commit on git coz this is going to change now

    parse(ExampleData.example) match {
      case Left(error) => println(error)
      case Right(logs) => getBadActorsIn10s(logs)
          .map(_.asJson.spaces2)
          .foreach { batch =>
            val response = basicRequest
              .body(batch)
              .post(uri"http://localhost:7070")
              .send(backend)
            println(response.body)
          }
    }
//
//
//    def toJson(badActors: List[FlowLog]): Json = badActors.asJson
//    def toString(json: Json): String = json.spaces2
//
//    def groupBadActors(logs: List[FlowLog]): List[List[String]] = {
//      logs
//        .filter(_.srcaddr == ExampleData.badActorIpAddress)
//        .map(_.asJson)
//        .map(_.spaces2)
//        .grouped(10)
//        .toList
//    }

//    for {
//      flowLogs <- parse(ExampleData.example)
//      badActors = flowLogs.filter(_.srcaddr == ExampleData.badActorIpAddress).take(10)
//      json = badActors.asJson
//    } yield {
//      val response = basicRequest
//        .body(json.spaces2)
//        .post(uri"http://localhost:7070").send(backend)
//      println(response.body)
//    }
  }

  // match on the number elements in the string, if anything else, return a left
  def parse(input: String): Either[String, List[FlowLog]] =
    input.split("\n").toList.traverse { logline =>
      val words = logline.split(" ").toList
      words match {
        case List(AsInt(version), accountId, interfaceId, srcaddr, dstaddr, AsInt(srcport), AsInt(dstport), AsInt(protocol), AsLong(packets), AsLong(bytes), AsLong(start), AsLong(end), action, logStatus) =>
          Right(FlowLog(version, accountId, interfaceId, srcaddr, dstaddr, srcport, dstport, protocol, packets, bytes, start, end, action, logStatus))
        case _ => Left("error - couldn't parse the log lines, unexpected input")
      }
    }


  private object AsInt {
    def unapply(intStr: String): Option[Int] = {
      try {
        Some(intStr.toInt)
      } catch {
        case _: java.lang.NumberFormatException =>
          None
      }
    }
  }

  private object AsLong {
    def unapply(longStr: String): Option[Long] = {
      try {
        Some(longStr.toLong)
      } catch {
        case _: java.lang.NumberFormatException =>
          None
      }
    }
  }
}
