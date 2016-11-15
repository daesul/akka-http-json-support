name := "circe-json"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11"
resolvers += Resolver.bintrayRepo("hseeberger", "maven")

libraryDependencies ++= List(
  "de.heikoseeberger" %% "akka-http-circe" % "1.10.1",
  "io.circe" %% "circe-generic" % "0.5.2",
  "io.circe" %% "circe-java8" % "0.5.2"

)

fork:= true
