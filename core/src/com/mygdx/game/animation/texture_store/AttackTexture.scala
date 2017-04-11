package com.mygdx.game.animation.texture_store


/**
  * Created by evan on 5/21/16.
  */
class AttackTexture(val attackSheetName: String) {

  private lazy val attackSheet = TextureStorage.buildSheet(attackSheetName, 8)


  val attackUp = attackSheet(0)
  val attackLeft = attackSheet(1)
  val attackDown = attackSheet(2)
  val attackRight = attackSheet(3)

}
