package com.mygdx.game.animation.texture_store

import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.TextureRegion

/**
  * Created by evan on 5/4/16.
  * only one class per person animation, should be in persontexture, very expensive to instantiate
  */
class WalkTexture(walkSheetName: String) {



  private lazy val walkSheet = TextureStorage.buildSheet(walkSheetName, 9)




  val up = walkSheet(0)(0)
  val left = walkSheet(1)(0)
  val down = walkSheet(2)(0)
  val right = walkSheet(3)(0)

  val walkUp = walkSheet(0).drop(1)
  val walkLeft = walkSheet(1).drop(1)
  val walkDown = walkSheet(2).drop(1)
  val walkRight = walkSheet(3).drop(1)







}
