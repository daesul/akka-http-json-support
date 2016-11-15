package com.example.json

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

import com.example.domain.{Female, Male, Gender, CustomerType}
import com.example.domain.CustomerType.CustomerType
import spray.json._

trait CustomerJsonProtocol extends DefaultJsonProtocol {

  implicit val uuidJsonFormat: JsonFormat[UUID] = new JsonFormat[UUID] {
    override def write(x: UUID): JsValue = JsString(x.toString)

    override def read(value: JsValue): UUID = value match {
      case JsString(x) => UUID.fromString(x)
      case x => deserializationError("Expected UUID as JsString, but got " + x)
    }
  }

  implicit val localDateJsonFormat: JsonFormat[LocalDate] = new JsonFormat[LocalDate] {
    private val formatter = DateTimeFormatter.ISO_DATE
    override def write(x: LocalDate): JsValue = JsString(x.format(formatter))

    override def read(value: JsValue): LocalDate = value match {
      case JsString(x) => LocalDate.parse(x)
      case x => deserializationError("Wrong time format of " + x)
    }
  }

  implicit val customerTypeFormat: JsonFormat[CustomerType] = new JsonFormat[CustomerType] {
    override def write(x: CustomerType): JsValue = JsString(x.toString)

    override def read(value: JsValue): CustomerType = value match {
      case JsString("REGULAR") => CustomerType.REGULAR
      case JsString("VIP") => CustomerType.VIP
      case x => deserializationError("No CustomerType with name " + x)
    }
  }

  implicit val genderFormat: JsonFormat[Gender] = new JsonFormat[Gender] {
    override def write(x: Gender): JsValue = JsString(x.toString.toUpperCase)

    override def read(value: JsValue): Gender = value match {
      case JsString("MALE") => Male
      case JsString("FEMALE") => Female
      case x => deserializationError("Not a Gender " + x)
    }
  }
}
