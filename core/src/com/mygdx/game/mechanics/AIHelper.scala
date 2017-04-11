package com.mygdx.game.mechanics

import com.mygdx.game.game_action.action.WalkAction
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.{IGameMap, GameMap, Location}
import com.mygdx.game.mechanics.map_traversal.{MapTraversal, WalkPath}
import com.mygdx.game.mechanics.player.IPlayer
import com.mygdx.game.tile.tile_traits.{Combatable, Moveable, Ownable}
import com.mygdx.game.tile.{ITile, Person}

/**
  * Created by evan on 4/28/16.
  * ai helper functions
  */
class AIHelper(val gameMap: IGameMap, gem: GameEventManager) {


  /**
    * trims path to only tiles toWalk has the walk ability to move to
    */
  def toWalkablePath(toWalk: ITile with Moveable, walkPath: WalkPath) = {
    new WalkPath(walkPath.path.takeWhile(toWalk.move.moveLeft >= _.pathEffort))
  }


  /**
    * TODO: broken, returns all property not just person
    */
  def findNearestPerson(loc: Location, owner: IPlayer): Option[ITile with Ownable] = {
    var closest: ITile with Ownable = null
    owner.property
      .foreach { p =>
        if(closest == null) closest = p
        else if(loc.distance(p.loc) < loc.distance(closest.loc)) {
          closest = p
        }
      }
    Option(closest)
  }


  /**
    * returns a list of ITile with Combatable objects, ordered by distance
    */
  def listTargetsByDistance(loc: Location, owner: IPlayer): List[ITile with Ownable with Combatable] = {
    this.listPropertyByDistance(loc, owner)
      .filter(_.isInstanceOf[ITile with Ownable with Combatable])
      .map(_.asInstanceOf[ITile with Ownable with Combatable])
  }

  def listPropertyByDistance(loc: Location, owner: IPlayer): List[ITile with Ownable] = {
    owner.property.toList
      .filter(_.loc.isOnBoard)
      .map(prop => (loc.distance(prop.loc), prop))
      .sortBy(_._1)
      .map(_._2)
  }

  def goToLocation(toMove: Person, target: Location): (Boolean, Boolean) = {
    var isAtLocation = false
    var hasMoved = false
    if(target != null && !target.isOnBoard) return (false, false)

    val mt = new MapTraversal(gameMap)
    mt.AStar(toMove, target) match {
      case Some(path) => {
        val p = this.toWalkablePath(toMove, path)
        new WalkAction(toMove, p, gameMap, gem).execute
        hasMoved = true
      }
      case None => Unit
    }

    if(mt.reachedGoal(toMove, target)) isAtLocation = true
    (isAtLocation, hasMoved)
  }


}
