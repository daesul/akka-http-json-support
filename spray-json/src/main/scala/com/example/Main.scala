package com.example

import java.time.LocalDate

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.example.domain._
import java.util.UUID


import com.example.json.JsonSupport


object Main extends App with JsonSupport {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  // Some dummy data
  val uuid1 = UUID.fromString("5919d228-9abf-11e6-9f33-a24fc0d9649c")
  val uuid2 = UUID.fromString("660f7186-9abf-11e6-9f33-a24fc0d9649c")
  val uuid3 = UUID.fromString("70d0d722-9abf-11e6-9f33-a24fc0d9649c")

  val address1 = Address("Musterstrasse 2", "Musterstadt", "12345")
  val address2 = Address("Testplatz 80 5", "Musterhausen", "45789", active = true)
  val address3 = Address("Akka-Allee 1887", "Akkaburg", "61860", active = true)

  var customers = Map(
    uuid1 -> Customer(uuid1, "test1", LocalDate.of(2010, 1, 11), Female, CustomerType.VIP, None),
    uuid2 -> Customer(uuid2, "test2", LocalDate.of(2014, 6, 5), Male, CustomerType.VIP, Some(Set(address1, address2))),
    uuid3 -> Customer(uuid3, "test3", LocalDate.of(2012, 2, 25), Female, CustomerType.REGULAR, Some(Set(address3)))
  )

  val paths = {
    pathPrefix("customer") {
      post {
        entity(as[Customer]) { customer =>
          println(customer)
          customers += customer.id -> customer
          complete(customers)
        }
      } ~
      path(JavaUUID){ id =>
        get {
          complete(customers(id))
        }
      }
    }
  }

  Http().bindAndHandle(paths, "localhost", 8080)

  println(s"Server online at http://localhost:8080")
}
