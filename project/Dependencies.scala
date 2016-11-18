import sbt._

object Version {
  final val Akka         = "2.4.11"
  final val AkkaHttpJson = "1.10.1"
  final val Circe        = "0.5.2"
  final val Scala        = "2.11.8"
}

object Library {
  val akkaHttp          = "com.typesafe.akka" %% "akka-http-experimental"            % Version.Akka
  val akkaHttpSprayJson = "com.typesafe.akka" %% "akka-http-spray-json-experimental" % Version.Akka
  val akkaHttpCirce     = "de.heikoseeberger" %% "akka-http-circe"                   % Version.AkkaHttpJson
  val circeGeneric      = "io.circe"          %% "circe-generic"                     % Version.Circe
  val circeJava8        = "io.circe"          %% "circe-java8"                       % Version.Circe
}
