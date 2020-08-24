package org.migapril.backend.http.routes

//import cats.implicits._
import org.http4s.HttpRoutes
import org.migapril.api.http.ApiDefinition
import org.migapril.api.http.ClientError.mapError
import org.migapril.api.model.DeviceConfigurationInfo
import org.migapril.backend.ConfigurationApiServiceEnv
import org.migapril.backend.services.UserAuthService
import zio.interop.catz._
import zio.{ Task, URIO }
import sttp.tapir.server.http4s.ztapir._
import sttp.tapir.ztapir._

object ConfigurationRoutes extends ApiDefinition {
  private val getConfigurationRoute: URIO[ConfigurationApiServiceEnv, HttpRoutes[Task]] =
    getConfiguration
      .zServerLogicPart(UserAuthService.auth)
      .andThen {
        case (_, _) =>
          Task.succeed { DeviceConfigurationInfo.test }.mapToClientError
      }
      .toRoutesR

  val routes: URIO[ConfigurationApiServiceEnv, HttpRoutes[Task]] = for {
    getConfigurationRoute <- getConfigurationRoute
  } yield getConfigurationRoute

}
