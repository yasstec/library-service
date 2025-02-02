package com.test.library_service

import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef.{Simulation, closedInjectionProfileFactory, constantConcurrentUsers, scenario}

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class ManageLibrarySimulation extends Simulation {

  private val scenarioConfig = scenario("manage-library").exec(karateFeature("src/system-test/resources/karate/create-and-get-books.feature"))

  setUp(
    scenarioConfig.inject(
      constantConcurrentUsers(1) during (1 second)
    )
  )
}
