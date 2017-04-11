package com.mygdx.game.game_map

import scala.util.control.Breaks._
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.{Input, Gdx}
import com.badlogic.gdx.scenes.scene2d.{Actor, InputEvent, InputListener}
import com.badlogic.gdx.utils.viewport.{ScreenViewport, Viewport}

/**
  * Created by evan on 3/3/16.
  */
class MapInput(val gameMap: IGameMap, /*val overlay: Overlay, */val port: ScreenViewport) extends Actor {


  val cam = port.getCamera
  port.setUnitsPerPixel(2)

  def handleInput(): Unit = {
    val transSpeed = 14 * port.getUnitsPerPixel
    val zoomSpeed = 1.04f

    if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
      var screenX = Gdx.input.getX()
      var screenY = Gdx.input.getY()
      var screen_coords = new Vector3(screenX, screenY, 0)
      var game_coords = cam.unproject(screen_coords)
      //println(game_coords.x)
      //tiled_map.clicked()
    }


    if(Gdx.input.isTouched()) {
      //game_cam.translate(10, 0)
    }
    if(Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
      cam.translate(-transSpeed, 0, 0)
    }
    if(Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
      cam.translate(transSpeed, 0, 0)
    }
    if(Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
      cam.translate(0, transSpeed, 0)
    }
    if(Gdx.input.isKeyPressed(Keys.DPAD_DOWN)) {
      cam.translate(0, -transSpeed, 0)
    }

    if(Gdx.input.isKeyPressed(Keys.EQUALS)) {
      port.setUnitsPerPixel(port.getUnitsPerPixel / zoomSpeed)
      if (port.getUnitsPerPixel < .5) {
        port.setUnitsPerPixel(.5f)
      }
      port.update(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    }


    if(Gdx.input.isKeyPressed(Keys.MINUS)) {
      port.setUnitsPerPixel(port.getUnitsPerPixel * zoomSpeed)
      if (port.getUnitsPerPixel > 2) {
        port.setUnitsPerPixel(2)
      }
      port.update(Gdx.graphics.getWidth, Gdx.graphics.getHeight)
    }

  }

  override def act(dt: Float) = {
    //println("i'm acting")
    //this.handleInput()
  }

}
