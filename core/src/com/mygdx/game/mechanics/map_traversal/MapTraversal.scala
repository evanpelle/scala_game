package com.mygdx.game.mechanics.map_traversal

import com.mygdx.game.animation.GameAnimation.StaticTileAnimation
import com.mygdx.game.game_action.event.{GameEventManager, EventName}
import com.mygdx.game.game_map.event.{GameEvent, LocationEvent}
import com.mygdx.game.game_map.listener.GameListener
import com.mygdx.game.game_map.tile.{TileCell}
import com.mygdx.game.game_map.{GameMap, IGameMap, Layer, Location}
import com.mygdx.game.tile.ITile
import com.mygdx.game.tile.tile_traits.Moveable
import scala.util.control.Breaks._


/**
  * Created by evan on 3/9/16.
  */

class MapTraversal(gameMap: IGameMap) {

  def getTilesCanMove(toMove: ITile with Moveable): Set[WalkPath] = {

    val visited = new TileNodeMap()

    //TODO fix none.get bug here
    val myNode = new TileNode(
      gameMap,
      List(),
      gameMap.getTileCell(toMove.loc).get,
      toMove,
      0
    )

    visited.add(myNode)
    var oldSize = 0

    //need to change this, tiles can still update without adding new tiles
    while(oldSize != visited.size) {
      oldSize = visited.size
      visited.getTileNodes.foreach{ tn =>
        tn.getNeighbors
          .filter{neighbor => neighbor.effort <= toMove.move.moveLeft}
          .foreach{newTn => visited.add(newTn)}
      }

    }
    visited.getTileNodes.map(_.toWalkPath).toSet
  }

  def AStar(start: ITile with Moveable, goal: Location): Option[WalkPath] = {
    val cmog = this.canMoveOnGoal(start, goal)
    val tileNodeMap = new TileNodeMap()
    val frontier = scala.collection.mutable.PriorityQueue[TileNode]()
    val startNode = new TileNode(gameMap, List(), gameMap.getTileCell(start.loc).get, start, 0.0)
    frontier.enqueue(startNode)

    while(frontier.nonEmpty) {
      val current = frontier.dequeue()
      if(reachedGoal(start, current.loc, goal, cmog)) return Some(current.toWalkPath)
      current.getNeighbors.foreach{ next =>
        if(tileNodeMap.add(next)) {
          next.priority = next.effort + next.loc.distance(goal)
          frontier.enqueue(next)
        }
      }
    }
    None
  }

  private def reachedGoal(toMove: ITile with Moveable, current: Location, goal: Location, canMoveOnGoal: Boolean) = {
    current.equals(goal) || (!canMoveOnGoal && current.isAdjacent(goal))
  }

  def reachedGoal(toMove: ITile with Moveable, goal: Location): Boolean = {
    val cmog = canMoveOnGoal(toMove, goal)
    reachedGoal(toMove, toMove.loc, goal, cmog)
  }

  def canMoveOnGoal(toMove: ITile with Moveable, goal: Location): Boolean = {
    try {
      toMove
        .move
        .canMoveOn(gameMap.getTileCell(goal).get)
    } catch {
      case ex: Exception => throw new Exception(s"here's the problem: $goal")
    }

  }

}
