package com.mygdx.game.animation.GameAnimation

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.mechanics.map_traversal.WalkPath
import com.mygdx.game.tile.tile_traits.Animatable
import com.mygdx.game.game_map.Direction.Direction
import com.mygdx.game.game_map.{Direction, GameMap, Location}

/**
  * Created by evan on 3/14/16.
  */
class MoveAnimation(val animation: GameAnimation) extends GameAnimation {


  private var walkPath: WalkPath = null
  var walkSpeed = .7
  var currLoc: Location = null
  var nextLoc: Location = null
  var ratio = 1.5
  var dir: Direction = null



  override def justMoved(toWalk: WalkPath) = {
    this.walkPath = toWalk
    currLoc = toWalk.getStartTile.loc
    nextLoc = toWalk.getNext(currLoc).get.loc // <- fix that
    this.setPos(currLoc)
  }

  override def getTextureRegion(loc: Location): TextureRegion = {
    if(walkPath != null) {
      this.update()
    } else {
      this.setPos(loc)
    }
    animation match {
      case person: WalkAnimation => person.getTextureRegion(loc, walkPath != null, dir)
      case soldier: SoldierAnimation => soldier.getTextureRegion(loc, walkPath != null, dir)
      case _ => animation.getTextureRegion(loc)
    }
  }


  def update(): Unit = {
    this.move(currLoc, nextLoc)
    if(this.hasFinishedMovingTile) {
      currLoc = nextLoc
      walkPath.getNext(nextLoc) match {
        case Some(wt) => {
          nextLoc = wt.loc
          this.setPos(currLoc)
        }
        case None => this.finishMove()
      }
    } else {
      this.move(currLoc, nextLoc)
    }
  }

  def hasFinishedMovingTile = {
    val (tx, ty) = nextLoc.toPos
    val dis = Math.sqrt(Math.pow(posX - tx, 2) + Math.pow(posY - ty, 2))
    dis <= 2*walkSpeed
  }

  def finishMove() = {
    this.setPos(walkPath.getEndTile.loc)
    walkPath = null
    currLoc = null
    nextLoc = null
  }

  def move(start: Location, end: Location) = {
    start.getDirection(end) match {
      case Some(d) => dir = d
      case None => throw new Exception("trying to render," +
        " moving more than one space: " +
        start + " to " + end)
    }
    val (dx, dy) = dir match {
      case Direction.North => this.moveNorth()
      case Direction.South => this.moveSouth()
      case Direction.NorthEast => this.moveNorthEast()
      case Direction.NorthWest => this.moveNorthWest()
      case Direction.SouthEast => this.moveSouthEast()
      case Direction.SouthWest => this.moveSouthWest()
    }
    posX += dx
    posY += dy
  }

  def moveNorth(): (Double, Double) =  (0, walkSpeed * 2)
  def moveSouth(): (Double, Double) = (0, -walkSpeed * 2)
  def moveNorthEast(): (Double, Double) = (this.walkSpeed * ratio, this.walkSpeed)
  def moveNorthWest(): (Double, Double) = (-this.walkSpeed * ratio, this.walkSpeed)
  def moveSouthEast(): (Double, Double) = (this.walkSpeed * ratio, -this.walkSpeed)
  def moveSouthWest(): (Double, Double) = (-this.walkSpeed * ratio, -this.walkSpeed)

  private def setPos(toSet: Location) = {
    val p = toSet.toPos
    this.posX = p._1
    this.posY = p._2
  }

  override def onAttack(myLoc: Location, targetLoc: Location) = {
    animation.onAttack(myLoc, targetLoc)
  }

  override def getOffsetX = this.offsetX.toFloat + animation.getOffsetX
  override def getOffsetY = this.offsetY.toFloat + animation.getOffsetY
}