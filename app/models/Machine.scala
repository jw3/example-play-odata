package models


case class Machine(id: Int,
                   dnsName: String,
                   currentRegistrationState: Int,
                   currentPowerState: Int,
                   currentSessionCount: Int
                  )
