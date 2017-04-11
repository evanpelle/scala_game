package com.mygdx.game.game_overlay

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Table

/**
  * Created by evan on 2/21/16.
  */
abstract class GameMenu extends Table {

  val origWidth = Gdx.graphics.getWidth
  val origHeight = Gdx.graphics.getHeight






  //this.setBounds(640, 0, 100, 100)

//  override def setBounds(x: Float, y: Float, w: Float, h: Float) = {
//
//    super.setBounds( (x/100.0).toFloat * origWidth, (y/100.0).toFloat * origHeight,  w, h)
//  }



//  def resize(width: Int, height: Int) = {
////    println("width: " + this.getWidth)
////    if(snapLeft) this.setX(-width/2 + 280)
////    else this.setX(width/2 + 190 - this.getWidth/2)
////
////    if(snapDown) this.setY(-height/2 + 160 + this.getHeight/2)
////    else this.setY(height/2)
////    println("screen width: " + width)
////    println("my x: " + this.getX)
//  }

}
