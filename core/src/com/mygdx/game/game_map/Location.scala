package com.mygdx.game.game_map

import com.mygdx.game.game_map.Direction._
import com.mygdx.game.game_map.Layer.Layer


/**
  * Created by evan on 3/15/16.
  */
class Location(val x: Int, val y: Int) {

  def isOnBoard =(x >= 0 && x >= 0)

  override def toString() = {
    "[x: " + x + ", y: " + y + "]"
  }

  override def equals(obj: Any) = {
    if(!obj.isInstanceOf[Location]) false
    val ol = obj.asInstanceOf[Location]
    ol.x == this.x && ol.y == this.y

  }


  def distance(other: Location) = {
    val (y1, x1) = (this.x, this.y)
    val (y2, x2) = (other.x, other.y)
    val du = x2 - x1
    val dv = (y2 + x2 / 2) - (y1 + x1 / 2)
    if ((du >= 0 && dv >= 0) || (du < 0 && dv < 0)) Math.max(Math.abs(du), Math.abs(dv))
    else Math.abs(du) + Math.abs(dv)
  }

  /*
  returns which direction loc2 is from loc1
  returns None if loc1 and loc2 are not adjacent
   */
  def getDirection(other: Location): Option[Direction] = {
    this.getAllNeighbors.zipWithIndex.foreach {case(el, i) =>
      if(el.equals(other)) {
        return Some(Direction(i))
      }
    }
    None
  }

  def getNeighbor(dir: Direction): Location = {
    val xIsEven = this.x%2 == 0
    val newLoc = dir match {
      case Direction.North => Location(this.x, this.y + 1)
      case Direction.South => Location(this.x, this.y - 1)
      case Direction.NorthEast => {
        if(xIsEven) Location(this.x+1, this.y+1)
        else Location(this.x+1, this.y)
      }
      case Direction.NorthWest => {
        if(xIsEven) Location(this.x-1, this.y+1)
        else Location(this.x-1, this.y)
      }
      case Direction.SouthEast => {
        if(xIsEven) Location(this.x+1, this.y)
        else Location(this.x+1, this.y-1)
      }
      case Direction.SouthWest => {
        if(xIsEven) Location(this.x-1, this.y)
        else Location(this.x-1, this.y-1)
      }
    }
    newLoc
  }

  def getAllNeighbors: List[Location] = List(
    this.getNeighbor(Direction.North),
    this.getNeighbor(Direction.South),
    this.getNeighbor(Direction.NorthEast),
    this.getNeighbor(Direction.NorthWest),
    this.getNeighbor(Direction.SouthEast),
    this.getNeighbor(Direction.SouthWest)
  )

  def isAdjacent(other: Location) = {
    this.getAllNeighbors.exists(l => l.equals(other))
  }

  def pos_to_loc(posX: Int, posY: Int) = {
    val locX = (posX * GameMap.magicRatio - 5).toInt / GameMap.tileWidth
    var locY = 0
    if (locX % 2 == 0) {
      locY = (posY - GameMap.tileHeight / 2.0).toInt / GameMap.tileHeight
    } else {
      locY = posY / GameMap.tileHeight
    }
    (locX, locY)
  }

  def toPos: (Int, Int) = {
    val posX = ((this.x * GameMap.tileWidth)/GameMap.magicRatio).toInt + GameMap.tileWidth/2
    var posY = 0
    if(x%2==0) {
      posY = (this.y * GameMap.tileHeight) + GameMap.tileHeight
    }
    else {
      posY = (this.y * GameMap.tileHeight) + GameMap.tileHeight/2
    }
    (posX, posY)
  }
}

object Location {
  def apply(x: Int, y: Int): Location = new Location(x, y)
}
