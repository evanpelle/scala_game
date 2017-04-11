package com.mygdx.game.mechanics.map_traversal

/**
  * Created by evan on 4/4/16.
  */

/*
converts x, y coords to a tileNode, stores only the tileNode of least effort

 */
class TileNodeMap {
  private val tileNodeMap: scala.collection.mutable.Map[(Int, Int), TileNode] =
    scala.collection.mutable.Map()

  def get(x: Int, y: Int) = tileNodeMap((x,y))


  //returns true if tileNode is added (smaller or never seen before)
  def add(tileNode: TileNode): Boolean = {
    val x = tileNode.loc.x
    val y = tileNode.loc.y
    if(tileNodeMap.contains((x, y))) {
      val oldTileNode = tileNodeMap((x, y))
      if(oldTileNode.effort > tileNode.effort) {
        tileNodeMap((x, y)) = tileNode
        true
      } else false
    } else {
      tileNodeMap((x,y)) = tileNode
      true
    }
  }

  def size = tileNodeMap.size

  def getTileNodes = tileNodeMap.values

}