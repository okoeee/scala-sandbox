scalaVersion := "2.13.12"

name := "scala-sandbox"
organization := "ch.epfl.scala"
version := "1.0"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0",
  "org.scalatest" %% "scalatest" % "3.2.19" % "test",
  "org.scalatestplus" %% "mockito-5-12" % "3.2.19.0" % "test"
)
