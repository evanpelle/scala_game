package com.mygdx.game.animation.factory

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.mygdx.game.animation.GameAnimation._
import com.mygdx.game.animation.texture_store.TextureStorage

/**
  * Created by evan on 4/14/16.
  */
class AnimationFactory extends IAnimationFactory {

  private def buildDefaultStatic(name: String) = {
    new StaticTileAnimation(new TextureRegion(TextureStorage.getTexture(name)))
  }


  def buildStatic(name: String): GameAnimation = {
    this.buildDefaultStatic(name)
  }

  def buildMove(name: String): GameAnimation = {
    new MoveAnimation(this.buildStatic(name))
    //this.buildStatic("soldierSheet") //TextureStorage.soldierWalkUp(0))
  }

  def buildLoop(name: String): GameAnimation = {
    null
    //new MoveAnimation(new LoopAnimation(3, TextureStorage.soldierWalkUp.map(new StaticTileAnimation(_))))
  }

  def buildMarker(color: String): GameAnimation = color match {
    case "red" => {
      val m = this.buildStatic("markerRed")
      m.offsetY = 72
      m.offsetX = 20
      m
    }
    case "blue" => {
      val m = this.buildStatic("markerBlue")
      m.offsetY = 72
      m.offsetX = 20
      m
    }
    case _ => this.buildStatic("invisible")
  }

  override def buildPerson(name: String): GameAnimation = name match {
    case "soldier" => {
      val walkAnimation = new WalkAnimation(4, TextureStorage.soldierWalkAnimation)
      val soldierAnimation = new SoldierAnimation(4, TextureStorage.soldierAttackAnimation, walkAnimation)
      new MoveAnimation(soldierAnimation)
    }
    case "worker" => new MoveAnimation(new WalkAnimation(4, TextureStorage.workerWalkAnimation))
    case _ => throw new Exception(s"person texture $name not found")
  }
}
