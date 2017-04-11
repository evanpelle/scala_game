package com.mygdx.game.game_action.event

import com.mygdx.game.game_map.Direction._

/**
  * Created by evan on 3/26/16.
  */
object EventName extends Enumeration {
  type EventName = Value
  val turnEnd = Value
  val displayAttack = Value
  val displayWalk = Value
  val endTurn = Value
  val death = Value
}