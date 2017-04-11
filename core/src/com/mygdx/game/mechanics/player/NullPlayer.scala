package com.mygdx.game.mechanics.player

import com.mygdx.game.mechanics.TurnManager

/**
  * Created by evan on 4/23/16.
  * doesn't do anything, used for testing
  */
class NullPlayer(name: String, color: String) extends IPlayer(name, color) {
  override def onTurnStart(turnManager: TurnManager): Unit = {}
}

object NullPlayer {
  def apply() = new NullPlayer("null", "none")
}