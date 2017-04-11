package com.mygdx.game.game_overlay

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.{BitmapFont, TextureAtlas}
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.{Label, TextButton, Skin}

/**
  * Created by evan on 3/9/16.
  */
object MySkin {

  val get = {

    val atlas = new TextureAtlas("assets/skin/ui_atlas/ui-gray.atlas")
    val font = new BitmapFont()

    val skin = new Skin(atlas)

    skin.add("default", font)

    skin.add("menu_background", new Texture("assets/menu_background.png"))


    val textButtonStyle = new TextButton.TextButtonStyle()
    textButtonStyle.up = skin.getDrawable("button_03")
    textButtonStyle.down = skin.getDrawable("button_01")
    //textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
    textButtonStyle.over = skin.getDrawable("button_03")
    textButtonStyle.font = skin.getFont("default")
    skin.add("default", textButtonStyle)


    val labelStyle = new LabelStyle()
    labelStyle.font = skin.getFont("default")
    //labelStyle.background = skin.getDrawable("button_03")
    skin.add("default", labelStyle)

    skin
  }

}
