package com.example.domain

import java.util.UUID
import java.time.LocalDate

import com.example.domain.CustomerType.CustomerType

case class Customer(id: UUID, name: String, registrationDate: LocalDate, gender: Gender, customerType: CustomerType, addresses: Option[Set[Address]])
case class Address(street: String, city: String, zip: String, active: Boolean = false)
