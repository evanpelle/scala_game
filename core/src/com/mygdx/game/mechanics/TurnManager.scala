package com.mygdx.game.mechanics

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.event.{TurnEvent}
import com.mygdx.game.mechanics.player.IPlayer

/**
  * Created by evan on 4/6/16.
  */
class TurnManager(var players: List[IPlayer], gem: GameEventManager) {

  if(players.size < 1) throw new Exception("not enough players to play game")

  var indexPlayerCurrTurn = 0
  var isInTurn = false



  def getPlayerCurrTurn: Option[IPlayer] = {
    if(isInTurn) {
      Some(players(indexPlayerCurrTurn))
    } else None
  }

  def nextTurn() = {
    if(this.isInTurn){
      this.endTurn()
      this.startTurn()
    } else this.startTurn()
  }

  def endTurn() = {
    if(!isInTurn) throw new Exception("turn not started, can't be ended")
    val playerJustEndTurn = getPlayerCurrTurn.get
    indexPlayerCurrTurn += 1
    if(indexPlayerCurrTurn >= players.size) indexPlayerCurrTurn = 0
    this.isInTurn = false
    gem.fireEvent(new TurnEvent(false, playerJustEndTurn))
  }

  def startTurn() = {
    if(this.isInTurn) throw new Exception("turn already started, can't start again")
    this.isInTurn = true
    this.getPlayerCurrTurn.get.onTurnStart(this)
    gem.fireEvent(new TurnEvent(true, this.getPlayerCurrTurn.get))
  }
}
