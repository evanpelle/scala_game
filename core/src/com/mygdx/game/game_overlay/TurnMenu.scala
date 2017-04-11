package com.mygdx.game.game_overlay

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.{TextButton, VerticalGroup}
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.mygdx.game.game_action.event.{EventName, GameEventManager}
import com.mygdx.game.game_map.event.{TurnEvent, GameEvent}
import com.mygdx.game.mechanics.TurnManager

/**
  * Created by evan on 3/26/16.
  */
class TurnMenu(val playerManager: TurnManager) extends GameMenu {

  this.setBounds(200, 900, 200, 200)

  this.setSkin(MySkin.get)

  var buttonRow = new VerticalGroup()

  var button = new TextButton("End", this.getSkin)
  button.addListener(new ClickListener() {
    override def clicked(event: InputEvent, x: Float, y: Float): Unit = {
      playerManager.nextTurn()
    }
  })


  this.addActor(button)
}
