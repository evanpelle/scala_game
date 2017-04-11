package com.mygdx.game.mechanics.player

import com.mygdx.game.mechanics.{Resource, TurnManager}

/**
  * Created by evan on 4/7/16.
  */
class Human(name: String, color: String) extends IPlayer(name, color) {

  this.resource = Resource.all(100)
  override def onTurnStart(turnManager: TurnManager): Unit = {}//this.property foreach println
}
