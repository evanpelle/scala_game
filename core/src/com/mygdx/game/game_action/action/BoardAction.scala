package com.mygdx.game.game_action.action

import com.mygdx.game.game_map.IGameMap
import com.mygdx.game.tile.tile_traits.Boardable
import com.mygdx.game.tile.{Person, ITile}

/**
  * Created by evan on 3/27/16.
  */
class BoardAction(toBoard: Person, toBeBoarded: ITile with Boardable, gameMap: IGameMap) extends GameAction {

  override def execute: Boolean = {
    toBeBoarded.board.addCargo(toBoard)
    gameMap.popTile(toBoard)
    true
  }

  override def getDisplayName: String = "board"

  override def isValid: Boolean = true
}
