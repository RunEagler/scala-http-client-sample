name := "scala-http-client-sample"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.7",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.7" % Test,
  "com.typesafe.akka" %% "akka-actor" % "2.5.21",
  "com.typesafe.akka" %% "akka-stream" % "2.5.21",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.21" % Test

)

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.7"