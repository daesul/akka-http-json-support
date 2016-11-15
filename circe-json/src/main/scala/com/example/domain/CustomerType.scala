package com.example.domain

import cats.data.Xor
import io.circe.Decoder.Result
import io.circe._

object CustomerType extends Enumeration {
  type CustomerType = Value
  val REGULAR, VIP = Value

  implicit val customerTypeDecoder: Decoder[CustomerType] = new Decoder[CustomerType] {
    override def apply(c: HCursor): Result[CustomerType] = c.as[String].flatMap{
      case x => Xor.catchOnly[NoSuchElementException](CustomerType.withName(x)).
        leftMap(e => DecodingFailure(s"Error while decoding: ${e.getMessage}", List()))
    }
  }

  implicit val customerTypeEncoder: Encoder[CustomerType] = new Encoder[CustomerType] {
    override def apply(a: CustomerType): Json = Json.fromString(a.toString)
  }
}
