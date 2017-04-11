package com.mygdx.game.animation.GameAnimation

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.animation.texture_store.WalkTexture
import com.mygdx.game.game_map.Direction.Direction
import com.mygdx.game.game_map.Direction.Direction
import com.mygdx.game.game_map.{Direction, Location}

/**
  * Created by evan on 5/4/16.
  * needs to be passed into MoveAnimation constructor to work properly
  */
class WalkAnimation(val duration: Int, wt: WalkTexture) extends GameAnimation {

  val walkLeft = LoopAnimation(duration, wt.walkLeft)
  val walkRight = LoopAnimation(duration, wt.walkRight)
  val walkUp = LoopAnimation(duration, wt.walkUp)
  val walkDown = LoopAnimation(duration, wt.walkDown)

  val left = new StaticTileAnimation(wt.left)
  val right = new StaticTileAnimation(wt.right)
  val up = new StaticTileAnimation(wt.up)
  val down = new StaticTileAnimation(wt.down)




  def getTextureRegion(loc: Location): TextureRegion = throw new Exception("shouldn't be called")//walkLeft.getTextureRegion(loc)

  def getTextureRegion(loc: Location, isWalking: Boolean, dir: Direction): TextureRegion = {
    if(isWalking) {
      dir match {
        case Direction.North => walkUp.getTextureRegion(loc)
        case Direction.South => walkDown.getTextureRegion(loc)
        case Direction.NorthEast => walkRight.getTextureRegion(loc)
        case Direction.SouthEast => walkRight.getTextureRegion(loc)
        case Direction.NorthWest => walkLeft.getTextureRegion(loc)
        case Direction.SouthWest => walkLeft.getTextureRegion(loc)
      }
    } else down.getTextureRegion(loc)
  }
}