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

import java.util.UUID
import java.time.LocalDate
import cats.data.Xor
import io.circe.{ Decoder, DecodingFailure, Encoder, HCursor, Json }

object Customer {

  final object Type extends Enumeration {

    type Type = Value

    val REGULAR, VIP = Value

    implicit val customerTypeDecoder: Decoder[Type] =
      new Decoder[Type] {
        override def apply(c: HCursor) =
          c.as[String].flatMap {
            case x =>
              Xor
                .catchOnly[NoSuchElementException](Type.withName(x))
                .leftMap(e =>
                  DecodingFailure(s"Error while decoding: ${e.getMessage}",
                                  List()))
          }
      }

    implicit val customerTypeEncoder: Encoder[Type] =
      new Encoder[Type] {
        override def apply(a: Type) = Json.fromString(a.toString)
      }
  }
}

final case class Customer(id: UUID,
                          name: String,
                          registrationDate: LocalDate,
                          gender: Gender,
                          customerType: Customer.Type.Type,
                          addresses: Option[Set[Address]])

final case class Address(street: String,
                         city: String,
                         zip: String,
                         active: Boolean = false)
