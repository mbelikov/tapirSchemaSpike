package org.migapril.backend.services

import eu.timepit.refined.auto._
import org.migapril.api.http.ClientError
import org.migapril.backend.system.Context
import zio._

object UserAuthService {
  type UserAuthService = Has[UserAuthService.Service]

  trait Service {
    def auth(token: Option[String]): IO[ClientError, Context]
  }

  def auth(token: Option[String]): ZIO[UserAuthService, ClientError, Context] =
    ZIO.accessM(_.get.auth(token))

  val live: ULayer[UserAuthService] = ZLayer.succeed((token: Option[String]) =>
    IO.succeed {
      token match {
        case Some(_) => Context(user = Some("babyboss"), authToken = Some("....."))
        case None    => Context(user = Some("babyboss"), authToken = None)
      }
    }
  )
}
