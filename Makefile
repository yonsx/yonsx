default: all

all: projects build
	./gradlew clean build --warning-mode all

build:
	./gradlew clean build -x test -x testClasses

test:
	./gradlew testClasses

clean:
	./gradlew clean

check:
	./gradlew build --refresh-dependencies checkUpdates -Dcheck=true

run:
	./gradlew run

stop:
	./gradlew --stop

projects:
	./gradlew -q projects