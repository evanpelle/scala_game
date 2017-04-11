package com.mygdx.game.game_action.action

import com.mygdx.game.animation.GameAnimation.StaticTileAnimation
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.event.LocationEvent
import com.mygdx.game.game_map.tile.TileFactory
import com.mygdx.game.game_map.{Layer, Location, IGameMap}
import com.mygdx.game.tile.{Person, ITile}
import com.mygdx.game.tile.tile_traits.{Storable, Moveable, Boardable}

/**
  * Created by evan on 4/4/16.
  */

//TODO: give this the tile to unload
class DisplayUnloadCargoAction(boardable: ITile with Boardable,
                               gameMap: IGameMap,
                               tileFactory: TileFactory,
                               gem: GameEventManager) extends GameAction {




  override def execute: Boolean = {
    val toUnload: Person = boardable.board.getCargo.head
    gameMap.getAdjacentTiles(boardable.loc)
      .filter(toUnload.move.canMoveOn)
      .foreach { tc =>
        gameMap.setTile(buildDisplayUnloadTile(toUnload), tc.loc)
      }
    true

  }


  def buildDisplayUnloadTile(toUnload: Person) = {
    val displayTile = tileFactory.buildDisplay("canBoard")
    gem.addClickListener(displayTile)
    displayTile.onClick = (le: LocationEvent) => {
      if(le.loc.equals(displayTile.loc)) {
        boardable.board.removeCargo(toUnload)
        gameMap.setTile(toUnload, displayTile.loc)
      }
      gameMap.popTile(displayTile)
      gem.removeAllListener(displayTile)
    }
    displayTile
  }

  override def getDisplayName: String = "unload"

  def isValid: Boolean = {
    println(boardable.board.getCargo)
    boardable.board.getCargo.exists { person =>
      gameMap.getAdjacentTiles(boardable.loc)
        .exists(person.move.canMoveOn)
    }
  }
}
