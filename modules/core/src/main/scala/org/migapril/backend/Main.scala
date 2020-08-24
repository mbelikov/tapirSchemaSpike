package org.migapril.backend

import org.migapril.backend.http.Server
import org.migapril.backend.services.UserAuthService
import org.migapril.backend.system.Logger
import org.migapril.backend.system.config.Config
import zio.logging.log
import zio.{ App, ExitCode, URIO }

object Main extends App {
  val logger      = Logger.test
  val config      = logger >>> Config.live
  val authService = UserAuthService.live

  val appLayers = logger ++
        config ++
        authService

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    Server.runServer
      .tapError(err => log.error(s"Execution failed with: $err"))
      .provideCustomLayer(appLayers)
      .exitCode
}
