#!/bin/bash
# Start QTO Backend Server in Dev Mode

cd "$(dirname "$0")"
./gradlew bootRun --args='--spring.profiles.active=dev'
