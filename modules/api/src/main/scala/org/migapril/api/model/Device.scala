package org.migapril.api.model

import eu.timepit.refined.types.string.NonEmptyString

case class Device(
    id: NonEmptyString,
    configurationInfo: DeviceConfigurationInfo
)
