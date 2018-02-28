# Makefile


FORCE:
	sbt test package

docs: FORCE
	sbt doc
	cp -r target/scala-2.12/api/* docs/api/
	git add docs/api

edit:
	emacs src/main/scala/scala-view/*.scala examples-sfx/src/main/scala/*.scala examples-swing/src/main/scala/scala-view-examples/*.scala &



