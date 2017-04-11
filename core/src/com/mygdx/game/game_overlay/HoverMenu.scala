package com.mygdx.game.game_overlay


import com.badlogic.gdx.graphics.{Pixmap, Color}
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui._
import com.mygdx.game.game_map.IGameMap
import com.mygdx.game.game_map.tile.{TileCell}

/**
  * Created by evan on 2/19/16.
//  */
//class HoverMenu(val overlay: Overlay, val game: GameStage) extends  Table {
//
//  val vtg = new VerticalGroup()
//  this.add(vtg)
//  //this.scaleBy(0)
//  //displayTable.bottom()
//  //displayTable.right()
//
//  val skin = overlay.skin
//  this.setSkin(skin)
//
//  this.setBounds(400, 350, 300, 350)
//
//  //this.setBackground("menu_background")
//  this.setFillParent(true)
//  //this.setSkin(skin)
//  //this.row()
//
//  //this.setBounds(300, 100, 100, 200)
//  this.top()
//
//
//  val lastLayer = IGameMap.lastGameLayer
//
//
//  var tileHoverViews = new Array[TileHoverView](lastLayer + 1)
//  for(layer <- 0 to lastLayer) {
//    tileHoverViews(layer) = new TileHoverView(layer, skin)
//    this.vtg.addActor(tileHoverViews(layer))
//  }
//
//
//  def setTile(tileCell: TileCell) = {
//
//    tileHoverViews.foreach(thv =>
//      thv.setTile(tileCell.getTile(thv.layer))
//    )
//
//  }
//
//
//}
//
//
//class TileHoverView(val layer: Int, val skin: Skin) extends HorizontalGroup {
//
//  this.setTransform(true)
//  //this.scaleBy(.2f)
//  this.setWidth(75)
//  this.setHeight(75)
////  this.padTop(20)
////  this.padBottom(20)
//
//
//  //this.setSkin(skin)
//  var labelContainer = new Container[Label]()
//  labelContainer.padRight(10)
//  var imageContainer = new Container[Image]()
//  this.addActor(labelContainer)
//  this.addActor(imageContainer)
//  var label = new Label("test", new LabelStyle(new BitmapFont(), Color.WHITE))
//  labelContainer.setActor(label)
//  var image = new Image()
//
//
//  def setTile(tile: Option[Tile]) = tile match {
//    case Some(t) => {
//      this.setVisible(true)
//      image.remove()
//      label.setText("what")
//      image = new Image(t.getTextureRegion)
//      imageContainer.setActor(image)
//    }
//    case None => image.remove(); this.setVisible(false)
//  }
//
//}