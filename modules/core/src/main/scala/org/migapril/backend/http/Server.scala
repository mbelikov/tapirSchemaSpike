package org.migapril.backend.http

import cats.implicits._
import org.http4s.HttpApp
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.migapril.api.http.apiDefinitionObject
import org.migapril.backend.{ AppEnv, ConfigurationApiServiceEnv }
import org.migapril.backend.http.routes.ConfigurationRoutes
import org.migapril.backend.system.config.Config
import sttp.tapir.openapi.circe.yaml._
import sttp.tapir.swagger.http4s.SwaggerHttp4s

import zio.clock.Clock
import zio.interop.catz._
import zio.interop.catz.implicits._
import zio.{ blocking, Runtime, Task, URIO, ZIO }

object Server {

  private val appRoutes: URIO[ConfigurationApiServiceEnv, HttpApp[Task]] =
    for {
      configurationRouts <- ConfigurationRoutes.routes
      apiDocsRouts = new SwaggerHttp4s(apiDefinitionObject.docs.toYaml).routes[Task]
    } yield (configurationRouts <+> apiDocsRouts).orNotFound

  val runServer: ZIO[AppEnv, Throwable, Unit] =
    for {
      app <- appRoutes
      svConfig <- Config.httpServerConfig
      implicit0(r: Runtime[Clock]) <- ZIO.runtime[Clock]
      bec <- blocking.blocking(ZIO.descriptor.map(_.executor.asEC))
      _ <- BlazeServerBuilder[Task](bec)
            .bindHttp(svConfig.port.value, svConfig.host.value)
            .withoutBanner
            .withNio2(true)
            .withHttpApp(app)
            .serve
            .compile
            .drain
    } yield ()

}
