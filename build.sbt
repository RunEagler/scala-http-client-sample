name := "scala-http-client-sample"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  //akka
  "com.typesafe.akka" %% "akka-http" % "10.1.7",
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.7" % Test,
  "com.typesafe.akka" %% "akka-actor" % "2.5.21",
  "com.typesafe.akka" %% "akka-stream" % "2.5.21",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.21" % Test,

  //apache
  "org.apache.httpcomponents" % "httpclient" % "4.5.7",

  //skinny
  "org.skinny-framework" %% "skinny-http-client" % "3.0.1",
  "org.slf4j" % "slf4j-log4j12" % "1.7.26" % Test,
  "log4j" % "log4j" % "1.2.17",

  //sttp
  "com.softwaremill.sttp" %% "core" % "1.5.11",

  //http4s
  "org.http4s" %% "http4s-client" % "0.18.22",
  "org.http4s" %% "http4s-dsl" % "0.18.22",
  "org.http4s" %% "http4s-blaze-client" % "0.18.22",

  //dispatch
  "org.dispatchhttp" %% "dispatch-core" % "1.0.0",

  //test
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "junit" % "junit" % "4.12",
  "com.novocode" % "junit-interface" % "0.11" % "test",
  "net.liftweb" %% "lift-json" % "3.3.0"

)
