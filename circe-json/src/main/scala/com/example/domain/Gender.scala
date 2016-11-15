package com.example.domain

import cats.data.Xor
import io.circe.Decoder._
import io.circe._

sealed trait Gender
case object Female extends Gender
case object Male extends Gender

object Gender {
  implicit val genderDecoder: Decoder[Gender] = new Decoder[Gender] {
    override def apply(c: HCursor): Result[Gender] = c.as[String].flatMap {
      case "FEMALE" => Xor.right(Female)
      case "MALE" => Xor.right(Male)
      case x => Xor.left(DecodingFailure(s"Not a gender $x", List()))
    }
  }

  implicit val genderEncoder: Encoder[Gender] = new Encoder[Gender] {
    override def apply(a: Gender): Json = Json.fromString(a.toString.toUpperCase)
  }
}

