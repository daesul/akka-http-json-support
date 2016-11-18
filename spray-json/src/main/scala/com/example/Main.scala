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

package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import java.time.LocalDate
import java.util.UUID
import com.example.json.JsonSupport
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Failure

object Main extends App with JsonSupport {
  import domain._

  implicit val system       = ActorSystem()
  implicit val executor     = system.dispatcher
  implicit val materializer = ActorMaterializer()

  // Some dummy data
  val uuid1 = UUID.fromString("5919d228-9abf-11e6-9f33-a24fc0d9649c")
  val uuid2 = UUID.fromString("660f7186-9abf-11e6-9f33-a24fc0d9649c")
  val uuid3 = UUID.fromString("70d0d722-9abf-11e6-9f33-a24fc0d9649c")

  val address1 = Address("Musterstrasse 2", "Musterstadt", "12345")
  val address2 =
    Address("Testplatz 80 5", "Musterhausen", "45789", active = true)
  val address3 = Address("Akka-Allee 1887", "Akkaburg", "61860", active = true)

  var customers = Map(
    uuid1 -> Customer(uuid1,
                      "test1",
                      LocalDate.of(2010, 1, 11),
                      Female,
                      CustomerType.VIP,
                      None),
    uuid2 -> Customer(uuid2,
                      "test2",
                      LocalDate.of(2014, 6, 5),
                      Male,
                      CustomerType.VIP,
                      Some(Set(address1, address2))),
    uuid3 -> Customer(uuid3,
                      "test3",
                      LocalDate.of(2012, 2, 25),
                      Female,
                      CustomerType.REGULAR,
                      Some(Set(address3)))
  )

  val paths = {
    import Directives._
    pathPrefix("customer") {
      post {
        entity(as[Customer]) { customer =>
          println(customer)
          customers += customer.id -> customer
          complete(customers)
        }
      } ~
      path(JavaUUID) { id =>
        get {
          complete(customers(id))
        }
      }
    }
  }

  Http().bindAndHandle(paths, "localhost", 8080).onComplete {
    case Failure(cause) =>
      println(s"Can't bind to localhost:8000: $cause")
      system.terminate()
    case _ =>
      println(s"Server online at http://localhost:8080")
  }

  Await.ready(system.whenTerminated, Duration.Inf)
}
