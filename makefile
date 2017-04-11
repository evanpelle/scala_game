.PHONY: build

build:
	gradle desktop:dist
	rm -r ~/Code/libgdx_game/mybuild
	mkdir -p ~/Code/libgdx_game/mybuild
	cp ~/Code/libgdx_game/desktop/build/libs/desktop-1.0.jar mybuild/game.jar
	cp -r ~/Code/libgdx_game/desktop/assets ~/Code/libgdx_game/mybuild/assets
	cd ~/Code/libgdx_game/mybuild
	cp -r ~/Code/libgdx_game/mybuild ~/Desktop/

test:
	gradle :core:test

view:
	open /Users/evan/Code/libgdx_game/core/build/reports/tests/index.html


count:
	find /Users/evan/Code/libgdx_game/core/src/com/mygdx/game -name '*.scala' | xargs wc -l;


