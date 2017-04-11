package com.mygdx.game.mechanics.player

import com.mygdx.game.game_action.action.{AttackAction, WalkAction}
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.{Location, GameMap, IGameMap}
import com.mygdx.game.mechanics.{AIHelper, TurnManager}
import com.mygdx.game.mechanics.map_traversal.{MapTraversal, WalkTile, WalkPath}
import com.mygdx.game.tile.tile_traits.{Combatable, Moveable, Ownable}
import com.mygdx.game.tile.{ITile, Person}

/**
  * Created by evan on 4/7/16.
  */
class AIPlayer(name: String, color: String, val gameMap: IGameMap, gem: GameEventManager) extends IPlayer(name, color) {

  val helper = new AIHelper(gameMap, gem)


  override def onTurnStart(turnManager: TurnManager): Unit = {
    val opponent = turnManager.players.filter(_.color != this.color).head
    this.property
      .filter(_.isInstanceOf[Person])
      .map(_.asInstanceOf[Person])
      .foreach{person =>
        val targets = helper.listTargetsByDistance(person.loc, opponent)
        if(targets.nonEmpty) {
          val closest = targets.head
          val (isAtLocation, hasMoved) = helper.goToLocation(person, closest.loc)
          if (isAtLocation) {
            new AttackAction(person, closest, gameMap, gem).execute
          }
        }

      }
//    if(people.isEmpty) {println("no person found"); return}
//    people.map(_.asInstanceOf[Person]).foreach { person =>
//
//      val target = helper.findNearestPerson(person.loc, opponent) match {
//        case Some(t) => {
//          val (isAtLocation, hasMoved) = helper.goToLocation(person, t.loc)
//          if (isAtLocation) {
//            val tAttack = t.asInstanceOf[ITile with Combatable]
//            new AttackAction(person, tAttack, gameMap, gem).execute
//          }
//        }
//        case None =>
//      }
//
//    }


    turnManager.nextTurn()
  }



}
