/*
 * Copyright 2016 Christian Hof
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.domain

import cats.data.Xor
import io.circe.Decoder._
import io.circe._

sealed trait Gender
case object Female extends Gender
case object Male   extends Gender

object Gender {
  implicit val genderDecoder: Decoder[Gender] = new Decoder[Gender] {
    override def apply(c: HCursor): Result[Gender] = c.as[String].flatMap {
      case "FEMALE" => Xor.right(Female)
      case "MALE"   => Xor.right(Male)
      case x        => Xor.left(DecodingFailure(s"Not a gender $x", List()))
    }
  }

  implicit val genderEncoder: Encoder[Gender] = new Encoder[Gender] {
    override def apply(a: Gender): Json =
      Json.fromString(a.toString.toUpperCase)
  }
}
