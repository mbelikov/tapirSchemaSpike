package org.migapril.api.model

import java.time.{ ZoneOffset, ZonedDateTime }

import eu.timepit.refined.auto._
import eu.timepit.refined.types.numeric.NonNegInt
import eu.timepit.refined.types.string.NonEmptyString
import org.migapril.api.model.ParameterValue.{ IntValue, ListValue, StringValue }

sealed trait ParameterValue
object ParameterValue {
  case class StringValue(value: String) extends ParameterValue
  case class IntValue(value: Int) extends ParameterValue
  case class ListValue(value: List[ParameterValue]) extends ParameterValue
}

case class DeviceConfiguration(
    version: NonNegInt,
    createdAt: DateTimeString,
    parameters: ParameterMap
)

object DeviceConfiguration {
  val test: DeviceConfiguration = DeviceConfiguration(
    version = 1,
    createdAt = DateTimeString.unsafeFrom(ZonedDateTime.now(ZoneOffset.UTC).toString),
    parameters = Map[NonEmptyString, ParameterValue](
      ("intParameter", IntValue(10)),
      ("stringParameter", StringValue("sponge bob")),
      ("listParameter", ListValue(IntValue(1) :: IntValue(2) :: IntValue(3) :: Nil))
    )
  )
}

case class DeviceConfigurationInfo(
    defaultConfiguration: DeviceConfiguration,
    lastConfiguration: DeviceConfiguration
)

object DeviceConfigurationInfo {
  val test: DeviceConfigurationInfo = DeviceConfigurationInfo(
    defaultConfiguration = DeviceConfiguration.test,
    lastConfiguration = DeviceConfiguration.test
  )
}
