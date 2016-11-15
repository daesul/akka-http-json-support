package com.example.json

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.example.domain.{Address, Customer}

trait JsonSupport extends SprayJsonSupport with CustomerJsonProtocol {
  implicit val addressFormat = jsonFormat4(Address)
  implicit val customerFormat = jsonFormat6(Customer)
}
