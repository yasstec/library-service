#TARGET_ENV=dev
TARGET_ENV=local
./gradlew gatlingRun -D karate.env=$TARGET_ENV
echo "Press Enter to exit ..........."
#shellcheck disable=SC2162
read exit