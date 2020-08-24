package org.migapril

import org.migapril.backend.services.UserAuthService.UserAuthService
import zio.logging.Logging
//import zio.test.ZTestEnv

package object backend {
  type ConfigurationApiServiceEnv = UserAuthService with Logging

  type AppEnv = ConfigurationApiServiceEnv with zio.ZEnv with backend.system.config.Config
}
