package com.mygdx.game.screens

import java.util.logging.FileHandler

import ActionNode.ActionFinder
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.maps.tiled.{TiledMapTileLayer, TmxMapLoader}
import com.badlogic.gdx.maps.tiled.renderers.{HexagonalTiledMapRenderer, OrthogonalTiledMapRenderer}
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.{TextButton, Skin}
import com.badlogic.gdx.utils.viewport.{ExtendViewport, StretchViewport, ScreenViewport}
import com.badlogic.gdx.{InputMultiplexer, Input, Gdx, Screen}
import com.badlogic.gdx.graphics._
import com.mygdx.game.MyGdxGame
import com.mygdx.game.animation.GameAnimation.{StaticTileAnimation, MoveAnimation}
import com.mygdx.game.animation.factory.AnimationFactory
import com.mygdx.game.game_action.action.ActionNode.{ActionNode}
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.event.UpdateEvent
import com.mygdx.game.game_map.tile.{TileFactory}
import com.mygdx.game.game_map._
import com.mygdx.game.game_overlay.{ResourceMenu, TurnMenu, ClickMenu}
import com.mygdx.game.mechanics.TurnManager
import com.mygdx.game.mechanics.map_traversal.MapTraversal
import com.mygdx.game.mechanics.player.{AIPlayer, Human, IPlayer}
import com.mygdx.game.tile.TileLoader

class PlayScreen(val game: MyGdxGame) extends Screen {


  val filename = "assets/tile_map/test3.tmx"

  var mapInput: MapInput = null

  val game_cam = new OrthographicCamera()
  val game_port = new ScreenViewport(game_cam)

  //building the ui
  private val uiCam = new OrthographicCamera(1000, 1000)
  private val uiPort = new ExtendViewport(1000, 1000, uiCam)



  val uiStage = new Stage(uiPort)
  val gameStage = new Stage(game_port)


  val gem = new GameEventManager()
  val animFactory = new AnimationFactory()
  val gameMap = this.buildEmptyGameMap(gem)
  val tileFactory = new TileFactory(animFactory, gameMap, gem) //, gameMap, gem)

  val human =  new Human("player", "blue")
  val ai = new AIPlayer("barbarian", "red", gameMap, gem)
  val players = Set(human, ai)
  val turnManager = new TurnManager(players.toList, gem)

  //lazy val gameMap: GameMap = this.buildGameMap(filename, players, tileFactory, gem)
  this.loadGameMap(filename, gameMap, players, tileFactory)
  this.addOverlayTiles(gameMap, tileFactory, gem)
  this.postProcessMap()
  this.addToStage(gameMap)
  this.buildMenu(human, turnManager, gameMap)




  val im = new InputMultiplexer(uiStage, gameStage)
  Gdx.input.setInputProcessor(im)

  game_cam.position.set(500, 500, 0)
  

  def buildMenu(human: IPlayer, turnManager: TurnManager, gameMap: IGameMap) = {
    val actionFinder = new ActionFinder(human, turnManager, gameMap, tileFactory, gem)
    val clickMenu = new ClickMenu(actionFinder, gameMap, tileFactory, gem)
    val turnMenu = new TurnMenu(turnManager)
    val resMenu = new ResourceMenu(human)
    uiStage.addActor(clickMenu)
    uiStage.addActor(turnMenu)
    uiStage.addActor(resMenu)
    gem.addClickListener(clickMenu)
  }

//  def buildGameMap(filename: String, players: Set[IPlayer],
//                   tileFactory: TileFactory, gameEventManager: GameEventManager) = {
//    val gameMap = this.buildEmptyGameMap(gem)
//    this.loadGameMap(filename, gameMap, players, tileFactory)
//    gameMap
//  }


  def buildEmptyGameMap(gem: GameEventManager): GameMap = {
    val mapBuilder = new MapBuilder()
    mapBuilder.buildEmptyMap(100, 100, gem)
  }

  def loadGameMap(filename: String, gameMap: GameMap, players: Set[IPlayer], tileFactory: TileFactory) = {
    val tileLoader = new TileLoader(players, tileFactory)
    new MapLoader(filename, gameMap, tileLoader, gem).generateMap
  }


  def addToStage(gameMap: GameMap) = {
    mapInput = new MapInput(gameMap, game_port)
    gameStage.addActor(mapInput)
    gameStage.addActor(gameMap)
  }

  def addOverlayTiles(gameMap: IGameMap, tileFactory: TileFactory, gem: GameEventManager) = {
    gem.addHoverListener(tileFactory.buildHover)
    gem.addClickListener(tileFactory.buildSelect)
  }

  def buildPlayers = {

  }

  def postProcessMap() = {

    for(i <- 0 to 10) {
      val barbSoldier = tileFactory.buildSoldier(ai)
      gameMap.setTile(barbSoldier, Location(i,0))
    }


    gameMap.setTile(tileFactory.buildMill(human), Location(1, 7))

    val fort = tileFactory.buildFort(ai)
    gameMap.setTile(fort, Location(15,8))


    val city = tileFactory.buildCity(human)
    gameMap.setTile(city, Location(15, 7))

    val soldier = tileFactory.buildSoldier(human)
    gameMap.setTile(soldier, Location(1, 5))

    val mill = tileFactory.buildMill(human)
    gameMap.setTile(mill, Location(20, 10))

//    for(y <- 0 until 100) {
//      for(x <- 0 until 100) {
//        val s = tileFactory.buildSoldier(human, gameMap, gem)
//        gameMap.setTile(s, Location(x, y))
//      }
//    }

  }





  def update(dt: Float): Unit = {
    gameStage.act(dt)
    mapInput.handleInput() // must go after gameStage.act
    gem.fireEvent(new UpdateEvent())

    game_cam.update()
    uiStage.act(dt)
  }


  override def hide(): Unit = {}


  override def resize(width: Int, height: Int): Unit = {
    game_port.update(width, height)
    uiPort.update(width, height)
  }

  override def dispose(): Unit = {}


  override def pause(): Unit = {}


  override def render(delta: Float): Unit = {
    //Thread.sleep(300)
    update(delta)
    Gdx.gl.glClearColor(0, 0, 0, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    //game.batch.setProjectionMatrix(game_cam.combined)
    game.batch.begin()
    gameStage.draw()
    uiStage.draw()
    game.batch.end()
  }


  @Override
  override def show(): Unit = {

  }


  override def resume(): Unit = {}
}

