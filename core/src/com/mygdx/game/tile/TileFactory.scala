package com.mygdx.game.game_map.tile

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.MapProperties
import com.mygdx.game.animation.GameAnimation.{GameAnimation, StaticTileAnimation, MoveAnimation}
import com.mygdx.game.animation.factory.IAnimationFactory
import com.mygdx.game.animation.texture_store.WalkTexture
import com.mygdx.game.game_action.event.GameEventManager
import com.mygdx.game.game_map.Layer
import com.mygdx.game.game_map.Layer.Layer
import com.mygdx.game.game_map.event.{TurnEvent, UpdateEvent, LocationEvent}
import com.mygdx.game.game_map.listener.GameListener
import com.mygdx.game.game_map.{Location, Layer, IGameMap}
import com.mygdx.game.game_overlay.ClickMenu
import com.mygdx.game.mechanics.player.IPlayer
import com.mygdx.game.mechanics.{ResContainer, Resource}
import com.mygdx.game.tile.Tile.Name
import com.mygdx.game.tile.Tile.Name
import com.mygdx.game.tile.tile_traits._
import com.mygdx.game.tile.{Tile, Person, ITile}

/**
  * Created by evan on 2/23/16.
  */
class TileFactory(val animFactory: IAnimationFactory, val gameMap: IGameMap, val gem: GameEventManager) {


  def buildWater = {
    new ITile with Treadable {
      val layer: Layer = Layer.landscape
      val tread: Tread = new Tread(canSailOn = true, canWalkOn = false)
      val anim: GameAnimation = animFactory.buildStatic("water")
      val name: Name = Tile.water
    }
  }

  def buildGrass = {
    new ITile with Treadable {
      val layer: Layer = Layer.landscape
      val tread: Tread = new Tread()
      val anim: GameAnimation = animFactory.buildStatic("grass")
      val name: Name = Tile.grass
    }
  }

  def buildMountain = {
    new ITile with Treadable {
      val layer: Layer = Layer.terrain
      val tread: Tread = new Tread(canWalkOn = false)
      val anim: GameAnimation = animFactory.buildStatic("mountain")
      val name = Tile.mountain
    }
  }

  def buildForest = {
    new ITile with Treadable {
      val layer: Layer = Layer.terrain
      val tread: Tread = new Tread(canWalkOn = true, pathEffort = 2)
      val anim: GameAnimation = animFactory.buildStatic("forest")
      val name = Tile.forest
    }
  }

  def buildWorker(player: IPlayer) = {
    new Person() with Treadable with Ownable with Moveable with Storable with Constructable {
      val tread: Tread = new Tread(canWalkOn = false)
      def owner: IPlayer = player
      val move: Move = new Walk(gem = gem)
      val anim: GameAnimation = animFactory.buildPerson("worker")
      override val store: Store = new Store
      override val marker: Marker = new Marker(animFactory, gameMap, gem)
      override val construct: Construct = new Construct(
        canBuild = Set(Tile.city, Tile.fort, Tile.mill),
        resToBuild = Resource(wood = 30)
      )
      val combat: Combat = new Combat(canAttack = false)
      val name = Tile.worker
    }
  }

  def buildSoldier(player: IPlayer) = {
      new Person() with Treadable with Ownable with Moveable
        with Combatable with Storable with Constructable with GameListener {
        val anim: GameAnimation = animFactory.buildPerson("soldier")
        val tread: Tread = new Tread(canWalkOn = false)
        def owner: IPlayer = player
        val move: Move = new Walk(moveAbility=4, gem = gem)
        val combat: Combat = new Combat(canAttack = true)
        val store: Store = new Store()
        val marker: Marker = new Marker(animFactory, gameMap, gem)
        val construct = new Construct(resToBuild = Resource(iron=40))
        val name = Tile.soldier
      }
  }

  def buildShip(player: IPlayer) = {
    new ITile with Treadable with Ownable with Moveable with Boardable with Combatable {
      val layer: Layer = Layer.person
      val tread: Tread = new Tread(canSailOn = false, canWalkOn = false)
      def owner: IPlayer = player
      val anim: GameAnimation = animFactory.buildMove("ship")
      val move: Move = new Sail(moveAbility = 6, gem = gem)
      val board: Board = new Board()
      val marker: Marker = new Marker(animFactory, gameMap, gem)
      val name = Tile.ship
      val combat: Combat = new Combat()
    }
  }

  def buildFort(player: IPlayer) = {
    new ITile with Ownable with Treadable with Constructable with Combatable {
      val layer: Layer = Layer.structure
      def owner: IPlayer = player
      val tread: Tread = new Tread(canWalkOn = false)
      val construct: Construct = new Construct(resToBuild = Resource(wood=10))
      val anim: GameAnimation = animFactory.buildStatic("fort")
      val marker = new Marker(animFactory, gameMap, gem)
      val name = Tile.fort
      val combat: Combat = new Combat()
    }
  }

  def buildMill(player: IPlayer) = {
    new ITile with Ownable with Treadable with Constructable with Combatable with GameListener {
      val layer: Layer = Layer.structure
      def owner: IPlayer = player
      val tread: Tread = new Tread(canWalkOn = false)
      val construct: Construct = new Construct(resToBuild = Resource(gold=20))
      val anim: GameAnimation = animFactory.buildStatic("mill")
      val marker = new Marker(animFactory, gameMap, gem)
      val name = Tile.mill
      onTurn = (te: TurnEvent) => {
        if(te.isEndingMyTurn(this.owner))
          this.owner.resource += Resource(wood=100)
      }
      gem.addTurnListener(this)

      val combat: Combat = new Combat()
    }
  }
  def buildCity(player: IPlayer) = {
    new ITile with Ownable with Treadable with Constructable with Combatable {
      def owner: IPlayer = player
      val layer: Layer = Layer.structure
      val anim: GameAnimation = animFactory.buildStatic("city")
      val tread: Tread = new Tread(canWalkOn = false)
      val marker = new Marker(animFactory, gameMap, gem)
      val name = Tile.city
      val combat: Combat = new Combat()
      val construct: Construct = new Construct(
        canBuild = Set(Tile.worker, Tile.soldier),
        resToBuild = Resource(wood = 20, iron = 10)
      )
    }
  }

  def buildHover = {
    val hover = new ITile with GameListener {
      val layer: Layer = Layer.overlay
      onHover = (le: LocationEvent) => {
        if (!le.loc.equals(loc)) {
          gameMap.getTile(Location(le.loc.x, le.loc.y), Layer.overlay) match {
            case None => gameMap.setTile(this, le.loc)
            case _ => gameMap.popTile(loc, this.layer)
          }
        }
      }
      val anim: GameAnimation = animFactory.buildStatic("hover")

      val name: Name = Tile.overlay
    }
    hover
  }

  def buildDisplayAttack = {
    new ITile with GameListener {
      val layer: Layer = Layer.overlay
      val anim: GameAnimation = animFactory.buildStatic("canAttack")
      val name = Tile.overlay
    }
  }

  def buildDisplayBoard = {
    new ITile with GameListener {
      val layer: Layer = Layer.overlay
      val anim: GameAnimation = animFactory.buildStatic("canBoard")
      val name = Tile.overlay
    }
  }

  def buildDisplay(animationName: String) = {
    new ITile with GameListener {
      val layer: Layer = Layer.overlay
      val anim = animFactory.buildStatic(animationName)
      val name = Tile.overlay
    }
  }

  def buildSelect = {
    new ITile with GameListener {
      val layer: Layer = Layer.overlay
      val anim: GameAnimation = animFactory.buildStatic("select")
      val name = Tile.overlay
      onClick = (le: LocationEvent) => {
        gameMap.getTile(le.loc, Layer.overlay) match {
          case Some(gl: GameListener) => if (gl != this) gl.onClick(le)
          case _ => Unit
        }
        gameMap.setTile(this, le.loc)
      }
    }
  }

//  def buildBlueMarker(player: IPlayer, gameMap: IGameMap, gem: GameEventManager) = {
//    val staticMarkerAnimation = animFactory.buildMarker(player.color)
//    //new Marker(staticMarkerAnimation, gameMap, gem)
//  }
}