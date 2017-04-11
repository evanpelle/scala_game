package com.mygdx.game.animation.GameAnimation

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.animation.texture_store.AttackTexture
import com.mygdx.game.game_map.Direction._
import com.mygdx.game.game_map.{Direction, Location}

/**
  * Created by evan on 5/21/16.
  */
class SoldierAnimation(duration: Int, val st: AttackTexture, personAnimation: WalkAnimation) extends GameAnimation {

  var willAttack = false
  var attackDir: Direction = null

  val attackLeft = LoopAnimation(duration, st.attackLeft)
  val attackRight = LoopAnimation(duration, st.attackRight)
  val attackUp = LoopAnimation(duration, st.attackUp)
  val attackDown = LoopAnimation(duration, st.attackDown)

  override def onAttack(myLoc: Location, targetLoc: Location) = {
    willAttack = true
    attackDir = myLoc.getDirection(targetLoc).get
  }

  def getAnim = attackDir match {
    case Direction.North => attackUp
    case Direction.South => attackDown
    case Direction.NorthEast => attackRight
    case Direction.SouthEast => attackRight
    case Direction.NorthWest => attackLeft
    case Direction.SouthWest => attackLeft
  }

  def getTextureRegion(loc: Location): TextureRegion = personAnimation.getTextureRegion(loc)

  def getTextureRegion(loc: Location, isWalking: Boolean, dir: Direction): TextureRegion = {
    if(willAttack && !isWalking) {
      val tr = this.getAnim.getTextureRegion(loc)
      if(this.getAnim.hasCompletedOneLoop) {
        willAttack = false
        this.getAnim.reset()
      }
      tr
    }
    else personAnimation.getTextureRegion(loc, isWalking, dir)
  }

}
