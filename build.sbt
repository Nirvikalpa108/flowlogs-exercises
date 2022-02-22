ThisBuild / scalaVersion     := "2.13.7"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

val circeVersion = "0.14.1"

lazy val root = (project in file("."))
  .settings(
    name := "flowlogs",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.client3" %% "core" % "3.3.18",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.typelevel" %% "cats-core" % "2.3.0",
      "org.scalatest" %% "scalatest" % "3.2.9" % Test,
    ),
  )

lazy val server = (project in file("server"))
  .settings(
    name := "server",
    libraryDependencies ++= Seq(
      "io.javalin" % "javalin" % "4.3.0",
      "org.slf4j" % "slf4j-simple" % "1.8.0-beta4",
      "org.slf4j" % "slf4j-api" % "1.8.0-beta4",
      "org.scalatest" %% "scalatest" % "3.2.9" % Test,
    ),
    // console logging and ctrl-c to kill support
    run / fork := true,
    run / connectInput := true,
    outputStrategy := Some(StdoutOutput),
  )
