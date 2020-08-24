package org.migapril.api.http

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec
import zio.{ Cause, ZIO }
import zio.logging.{ log, Logging }

sealed trait ClientError

object ClientError {
  final case class InternalServerError(message: String) extends ClientError
  object InternalServerError {
    implicit val codec: Codec[InternalServerError] = deriveCodec
  }

  final case class BadRequest(message: String) extends ClientError
  object BadRequest {
    implicit val codec: Codec[BadRequest] = deriveCodec
  }

  final case class NotFound(message: String) extends ClientError
  object NotFound {
    implicit val codec: Codec[NotFound] = deriveCodec
  }

  final case class NotAuthorized(message: String) extends ClientError
  object NotAuthorized {
    implicit val coded: Codec[NotAuthorized] = deriveCodec
  }

  /**
    * Expose the full error cause when handling a request and recovers failures and deaths appropriately.
    */
  implicit class mapError[R, E, A](private val f: ZIO[R, E, A]) extends AnyVal {
    def mapToClientError: ZIO[R with Logging, ClientError, A] =
      f.tapError(error => log.error(s"Error processing request: $error."))
        .mapErrorCause { cause =>
          val clientFailure = cause.failureOrCause match {
            //            case Left(r: BadDeviceName)  => BadRequest(r.getMessage)
//            case Left(r: DeviceNotFound)       => NotFound(r.getMessage)
//            case Left(r: EntityNotFound)       => NotFound(r.getMessage)
//            case Left(r: AuthorizationProblem) => NotAuthorized(r.getMessage)
            case _ => InternalServerError("Internal Server Error")
          }
          Cause.fail(clientFailure)
        }
  }
}
