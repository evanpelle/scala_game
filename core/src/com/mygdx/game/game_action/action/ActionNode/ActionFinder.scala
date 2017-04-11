package ActionNode

import com.mygdx.game.game_action.action.ActionNode
import com.mygdx.game.game_action.action.ActionNode.ActionNode
import com.mygdx.game.game_action.action.ActionNode.build_action.BuildAction
import com.mygdx.game.game_action.action._
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.IGameMap
import com.mygdx.game.game_map.tile.{TileCell, TileFactory}
import com.mygdx.game.mechanics.TurnManager
import com.mygdx.game.mechanics.player.IPlayer
import com.mygdx.game.tile.ITile
import com.mygdx.game.tile.tile_traits._

/**
  * Created by evan on 4/4/16.
  */
class ActionFinder(val player: IPlayer,
                   playerManager: TurnManager,
                   gameMap: IGameMap,
                   tileFactory: TileFactory,
                   gem: GameEventManager) {


  def isMyTurn: Boolean = playerManager.getPlayerCurrTurn match {
    case Some(currPlayer) => {
      if(currPlayer == player) true
      else false
    }
    case None => false
  }


  def getActions(tileCell: TileCell): ActionNode = {
    val anr = new ActionNode("root")
    if(isMyTurn) {
      tileCell.person match {
        case Some(person: ITile with Ownable) =>
          if (person.owner == player)
            anr.add(this.getActions(person))
        case _ =>
      }
      tileCell.structure match {
        case Some(structure: ITile with Ownable) =>
          if(structure.owner == player)
            anr.add(this.getActions(structure))
        case _ =>
      }
    }
    anr
  }


  def getActions(tile: ITile): ActionNode = {
    val rootNode = new ActionNode(tile.name.toString)
    tile match {
      case move: Moveable =>
        rootNode.add(new DisplayWalkAction(move, gameMap, tileFactory.animFactory, gem))
      case _ =>
    }
    tile match {
      case combat: Combatable =>
        rootNode.add(new DisplayAttackAction(combat, gameMap, gem, tileFactory))
      case _ =>
    }
    tile match {
      case store: ITile with Storable =>
        rootNode.add(DisplayBoardAction(store, gameMap, tileFactory, gem))
      case _ =>
    }
    tile match {
      case board: Boardable =>
        rootNode.add(new DisplayUnloadCargoAction(board, gameMap, tileFactory, gem))
      case _ =>
    }
    tile match {
      case const: Constructable with Ownable =>
        rootNode.add(BuildAction.buildNode(const,gameMap,tileFactory, gem))
      case _ =>
    }
    rootNode
  }

}
