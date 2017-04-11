package com.mygdx.game.game_action.action

import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.event.LocationEvent
import com.mygdx.game.game_map.{Location, IGameMap}
import com.mygdx.game.game_map.tile.{TileCell, TileFactory}
import com.mygdx.game.tile.{Person, ITile}
import com.mygdx.game.tile.tile_traits.{Boardable, Storable}

/**
  * Created by evan on 3/27/16.
  */
class DisplayBoardAction(toStore: ITile,
                         gameMap: IGameMap,
                         tileFactory: TileFactory,
                         gem: GameEventManager) extends GameAction {

  override def execute: Boolean = {
   val storable = toStore.asInstanceOf[Person]
    gameMap.getAdjacentTiles(toStore.loc)
      .map(_.person)
      .flatten
      .foreach {
        case board: ITile with Boardable => {
          if (board.board.canStore) {
            gameMap.setTile(createDisplayTile(board, storable), board.loc)
          }
        }
        case _ => Unit
      }
    true
  }

  def createDisplayTile(toStore: ITile with Boardable, storable: Person) = {
    val dis = tileFactory.buildDisplayBoard
    gem.addClickListener(dis)
    val boardAction = new BoardAction(storable, toStore, gameMap)
    dis.onClick = (le: LocationEvent) => {
      if(dis.loc.equals(le.loc)) {
        boardAction.execute
      }
      gameMap.popTile(dis)
      gem.removeAllListener(dis)
    }
    dis
  }

  override def getDisplayName: String = "board"

  override def isValid: Boolean = {
    toStore match {
      case ts: ITile with Storable => {
        gameMap.getAdjacentTiles(toStore.loc)
          .exists{ts.store.canBeStored}
      }
      case _ => false
    }
  }
}

object DisplayBoardAction {
  def apply(toStore: ITile, gameMap: IGameMap, tileFactory: TileFactory, gem: GameEventManager) = {
    new DisplayBoardAction(toStore, gameMap, tileFactory, gem)
  }
}
