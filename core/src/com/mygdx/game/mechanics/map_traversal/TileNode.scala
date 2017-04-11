package com.mygdx.game.mechanics.map_traversal

import com.mygdx.game.game_map.IGameMap
import com.mygdx.game.game_map.tile.TileCell
import com.mygdx.game.tile.ITile
import com.mygdx.game.tile.tile_traits.Moveable

/**
  * Created by evan on 4/4/16.
  */
/*
stores effort and path to get to this location on map

 */
class TileNode(val gameMap: IGameMap,
               val prev: List[TileNode],
               val tileCell: TileCell,
               val toMove: ITile with Moveable,
               val effort: Double) extends Ordered[TileNode] {


  val loc = tileCell.loc

  //used for AStar search
  var priority = 0.0


  def getNeighbors: Set[TileNode] = gameMap
    .getAdjacentTiles(loc)
    .filter(canMoveOn)
    .map(createNode)



  def canMoveOn(tc: TileCell): Boolean = toMove.move.pathEffort(tc) match {
    case Some(pe) => true
    case None => false
  }

  /**
    *
    * @param tc adjacent tileCell to this.tileCell
    * @return a new TileNode
    */
  def createNode(tc: TileCell) = new TileNode(
    gameMap,
    this.prev :+ this,
    tc,
    toMove,
    this.effort + toMove.move.pathEffort(tc).get
  )


  def toWalkPath = {
    new WalkPath((prev :+ this).map(_.toWalkTile))
  }

  def toWalkTile = new WalkTile(loc, effort)

  //used with A* search
  def compare(that: TileNode): Int = if(this.priority > that.priority) -1 else 1




}