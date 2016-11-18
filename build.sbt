lazy val `akka-http-json-support` =
  project
    .in(file("."))
    .aggregate(`spray-json`, `circe`)
    .enablePlugins(GitVersioning)

lazy val `spray-json` =
  project.enablePlugins(AutomateHeaderPlugin)

lazy val `circe` =
  project.enablePlugins(AutomateHeaderPlugin)

unmanagedSourceDirectories.in(Compile) := Vector.empty
unmanagedSourceDirectories.in(Test)    := Vector.empty

publishArtifact := false
